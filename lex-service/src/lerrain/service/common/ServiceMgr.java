package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import lerrain.tool.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.*;

public class ServiceMgr
{
//    public static final long MAX = 1024L * 1024 * 16;

    public static int SPEND_SLOW = 300;
    public static int SERVICE_TIME_OUT = 500;

    @Resource
    private Environment env;

    Random ran = new Random();

    Map<String, Servers> map = new HashMap<>();
    Map<String, Integer> reqTimeout = new HashMap<>();

    ServiceListener listener;

    public void reset(Map<String, Object> json)
    {
        for (Map.Entry<String, Object> e : json.entrySet())
        {
            Servers servers = getServers(e.getKey());
            servers.resetClients(Common.trimStringOf(e.getValue()));
        }
    }

    public void setConfig(JSONObject v)
    {
        for (String srvName : v.keySet())
        {
            JSONArray list = v.getJSONArray(srvName);
            for (int i = 0; i < list.size(); i++)
            {
                JSONObject rs = list.getJSONObject(i);
                String uri = rs.getString("uri");
                int timeout = Common.intOf(rs.get("timeout"), -1);

                if (uri != null && timeout > 0)
                {
                    String reqKey = uri.startsWith("/") ? srvName + uri : srvName + "/" + uri;
                    reqTimeout.put(reqKey, timeout);
                }
            }
        }
    }

    public boolean hasServers(String serviceName)
    {
        return map.containsKey(serviceName) || env.containsProperty("service." + serviceName);
    }

    public Servers getServers(String serviceName)
    {
        synchronized (map)
        {
            Servers servers = map.get(serviceName);
            if (servers == null)
            {
                servers = new Servers(serviceName);
                servers.resetClients(env.getProperty("service." + serviceName));
                servers.setDispatch(serviceName);

                map.put(serviceName, servers);
            }

            return servers;
        }
    }

    public void setServerPicker(String serviceName, ServicePicker picker)
    {
        Servers servers = getServers(serviceName);
        servers.picker = picker;
    }

    public void setDefaultServer(String serviceName, int defaultIndex)
    {
        Servers servers = getServers(serviceName);
        servers.defaultIndex = defaultIndex;
    }

    public void setLog(String service, int level)
    {
        getServers(service).level = level;
    }

    public Client[] getAllClient(String str)
    {
        return getServers(str).getAllClient();
    }

    public Client getClient(String str)
    {
        return getServers(str).getClient(null);
    }

    public Client getClient(String str, int index)
    {
        return getServers(str).getClient(index);
    }

    public JSONObject req(String service, String loc, JSON param)
    {
        return req(service, loc, param, -1);
    }

    public JSONObject req(String service, String loc, JSON param, int timeout)
    {
        return JSONObject.parseObject(reqStr(service, loc, param, timeout));
    }

    public Object reqVal(String service, String loc, JSON param)
    {
        return reqVal(service, loc, param, -1);
    }

    public Object reqVal(String service, String loc, JSON param, int timeout)
    {
        JSONObject res = req(service, loc, param, timeout);

        if (!"success".equals(res.getString("result")))
            throw new RuntimeException(res.getString("reason"));

        return res.get("content");
    }

    public String reqStr(String service, String loc, Object param)
    {
        return reqStr(service, loc, param, -1);
    }

    public String reqStr(String service, String loc, Object param, int timeout)
    {
        Servers servers = this.getServers(service);
        Client client = servers.getClient(param);

        return reqStr(servers, client, loc, param, timeout);
    }

    public String[] reqAll(String service, String loc, Object param)
    {
        return reqAll(service, loc, param, -1);
    }

    public String[] reqAll(String service, String loc, Object param, int timeout)
    {
        Servers servers = this.getServers(service);
        Client[] clients = servers.getAllClient();

        String[] res = new String[clients.length];

        for (int i = 0; i < clients.length; i++)
        {
            try
            {
                res[i] = reqStr(servers, clients[i], loc, param, timeout);
            }
            catch (Exception e)
            {
            }
        }

        return res;
    }

    private String reqStr(Servers servers, Client client, String loc, Object param, int timeout)
    {
        long t = System.currentTimeMillis();
        Object passport = null;

        try
        {
            if (listener != null)
                passport = listener.onBegin(client, loc, param);

            synchronized (client)
            {
                client.post++;
            }

            String req = param == null ? null : param.toString();
            String res = client.client.req(loc, req, timeout);

            int t1 = (int)(System.currentTimeMillis() - t);

            synchronized (client)
            {
                client.moreFail = 0;
                client.recTime(loc, t1);

                if (t1 >= SPEND_SLOW)
                    client.slow++;
            }

            if (servers.level == 1)
                Log.debug("%s => %s/%s(%dms) => %s", param, servers.name, loc, t1, res);
            else if (servers.level == 2)
                Log.info("%s => %s/%s(%dms) => %s", param, servers.name, loc, t1, res);

            if (listener != null)
                listener.onSucc(passport, t1, res);

            return res;
        }
        catch (Exception e)
        {
            if (listener != null)
                listener.onFail(passport, (int)(System.currentTimeMillis() - t), e);

            synchronized (client)
            {
                client.fail++;

                if (e instanceof java.net.SocketTimeoutException || e instanceof java.net.SocketException)
                    client.moreFail++;
                else //这种一般是地址不对，不是服务错误
                    client.moreFail = 0;
            }

            Log.error("request: " + servers.name + "/" + loc + " -- " + param + " -- " + e.getMessage());
            throw new ServiceException(e, "request: " + servers.name + "/" + loc + " -- " + e.getMessage());
        }
    }

    /**
     * 这个是带有重试功能的，10秒一次，重试1分钟
     * 潜在的隐患是当请求量大的时候，多个请求会挂在这里等待，会导致这段时间的大量请求阻塞，可能引发系统问题
     *
     * @param service
     * @param loc
     * @param param
     * @return
     */
    public Object ask(String service, String loc, JSON param)
    {
        return retry(service, loc, param, new int[] {10000, 10000, 10000, 10000, 10000});
    }

    private Object retry(String service, String loc, JSON param, int... sleep)
    {
        try
        {
            JSONObject res = req(service, loc, param, -1);

            if (!"success".equals(res.getString("result")))
                throw new RuntimeException(res.getString("reason"));

            return res.get("content");
        }
        catch (Exception e3)
        {
            if (sleep != null && sleep.length > 0)
            {
                try
                {
                    Thread.sleep(sleep[0]);
                }
                catch (InterruptedException i3)
                {
                    throw new RuntimeException(i3.getMessage(), i3);
                }

                int[] ns = new int[sleep.length - 1];
                for (int i = 0; i < ns.length; i++)
                    ns[i] = sleep[i + 1];

                return retry(service, loc, param, ns);
            }
        }

        throw new ServiceException("服务<" + service + "/" + loc + ">异常，多次重试失败");
    }

    /**
     * @deprecated
     * @return
     */
    public JSONObject report()
    {
        JSONObject r = new JSONObject();

        for (Map.Entry<String, Servers> e : map.entrySet())
        {
            JSONArray list = new JSONArray();
            for (Client c : e.getValue().clients)
            {
                synchronized (c.client)
                {
                    JSONObject r1 = new JSONObject();
                    r1.put("client", c.client.toString());
                    r1.put("post", c.post);
                    r1.put("fail", c.fail);
                    r1.put("slow", c.slow);

                    if (c.post - c.fail > 0)
                        r1.put("average", c.totalTime / (c.post - c.fail));

                    int[] t = new int[c.time.length];
                    for (int i = 0; i < t.length; i++)
                        t[i] = c.time[(c.pos + t.length - i) % t.length];
                    r1.put("time", t);

                    String[] uri = new String[c.uri.length];
                    for (int i = 0; i < uri.length; i++)
                        uri[i] = c.uri[(c.pos + uri.length - i) % uri.length];
                    r1.put("uri", c.uri);

                    list.add(r1);
                }
            }

            r.put(e.getKey(), list);
        }

        return r;
    }

    public void resetTimes()
    {
        for (Map.Entry<String, Servers> e : map.entrySet())
        {
            for (Client c : e.getValue().clients)
            {
                synchronized (c)
                {
                    c.post = 0;
                    c.fail = 0;
                    c.slow = 0;
                    c.totalTime = 0;
                }
            }
        }
    }

    public ServiceListener getListener()
    {
        return listener;
    }

    public void setListener(ServiceListener listener)
    {
        this.listener = listener;
    }

    public void requestFatal(String name, int index)
    {
        if ("secure".equals(name))
        {
            Log.error("secure is down");
            return;
        }

        JSONArray list = new JSONArray();
        JSONObject v = new JSONObject();
        v.put("from", name);
        v.put("index", index);
        v.put("result", "down");
        v.put("type", "service");
        v.put("time", System.currentTimeMillis());
        list.add(v);

        ServiceMgr.this.req("secure", "action.json", list);
    }

    /*
    class JSONEncoder implements Encoder
    {
        @Override
        public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException
        {
            requestTemplate.header("Content-Type", "application/json;charset=utf-8");

            byte[] b = o.toString().getBytes(Util.UTF_8);
            if (b.length > MAX)
                throw new RuntimeException("param is too long");

            requestTemplate.body(b, Util.UTF_8);
        }
    }

    class JSONDecoder implements Decoder
    {
        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException
        {
            try (InputStream is = response.body().asInputStream())
            {
                return Common.stringOf(is, "utf-8");
//                return JSONObject.parse(Common.byteOf(is));
            }
        }
    }
    */

    public class Servers
    {
        String name;

        Client[] clients;

        ServicePicker picker;

        int defaultIndex = -1;

        int level = 0;

        public Servers(String name)
        {
            this.name = name;
        }

        public void resetClients(String addrs)
        {
            if (addrs == null)
            {
                clients = new Client[0];
                return;
            }

            String[] url = addrs.split(",");

            Client[] last = clients;

            clients = new Client[url.length];
            for (int i = 0; i < url.length; i++)
            {
                clients[i] = new Client(this);
                clients[i].index = i;
                clients[i].url = Common.trimStringOf(url[i]);
//                clients[i].client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, clients[i].url);
                clients[i].client = new NetworkClient(name, clients[i].url);

                if (last != null && last.length > i)
                {
                    clients[i].post = last[i].post;
                    clients[i].fail = last[i].fail;
                    clients[i].slow = last[i].slow;
                    clients[i].totalTime = last[i].totalTime;
                }
            }
        }

        public Client[] getAllClient()
        {
            return clients;
        }

        public Client getClient(int index)
        {
            if (index < 0)
                index = ran.nextInt(clients.length);

            Client client = clients[index % clients.length];

            if (client.moreFail >= 3)
            {
                long t = System.currentTimeMillis();

                if (t > client.restoreTime)
                {
                    synchronized (client)
                    {
                        if (client.moreFail >= 5) //连续5次错误
                        {
                            client.restoreTime = System.currentTimeMillis() + 3600L;
                        }
                        else if (client.moreFail >= 4) //连续4次错误
                        {
                            client.restoreTime = System.currentTimeMillis() + 1800L;
                        }
                        else if (client.moreFail >= 3) //连续3次错误
                        {
                            client.restoreTime = System.currentTimeMillis() + 600L;
                        }
                    }

                    /*
                     * 标机停机
                     * 标记停机后先给一次机会，如果成功就消除记录，不会停机；如果失败则停机，并错误累加1，好了以后直接累加进入下一次停机，但是也先给一次机会，如果好了停机计划取消
                     */
                    requestFatal(name, index);
                }
                else //处于无效期，这里用else，而不是另起if（上面的结果可能已经要求停机了），是考虑每次标记失败都要先请求一次，判断是不是好了
                {
                    Client res = null;

                    if (clients.length > 1) //多台机器才有调整停机的空间一台机器只能挂了
                    {
                        for (Client c : clients)
                        {
                            if (t > c.restoreTime) //为0也是大于
                            {
                                res = c;
                                break;
                            }
                        }
                    }

                    if (res != null)
                        client = res;
                    else
                        Log.error("没有可用的client，继续使用有问题的client");
                }
            }

            return client;
        }

        public Client getClient(Object req)
        {
            return getClient(picker == null ? defaultIndex : picker.getIndex(req));
        }

        public void setDispatch(String srvName)
        {
            if (env.containsProperty("dispatch." + srvName))
            {
                final String[] dispatch = env.getProperty("dispatch." + srvName).split(",");
                this.picker = new ServicePicker()
                {
                    @Override
                    public int getIndex(Object req)
                    {
                        if (req instanceof JSONObject)
                        {
                            JSONObject m = (JSONObject)req;
                            for (String str : dispatch)
                            {
                                String s = m.getString(str);
                                if (s != null)
                                    return hash(s);
                            }
                        }

                        return -1;
                    }
                };
            }
        }

        public String getName()
        {
            return name;
        }
    }

    public int hash(String key)
    {
        return key.hashCode() & 0x7FFFFFFF;
    }

    public static class Client
    {
        Servers servers;

        ServiceClient client;

        String url;

        int index;

        int post;
        int fail;
        int slow;

        int moreFail; //连续错误
        long restoreTime; //连续错误后停机的恢复时间

        long totalTime;

        String[] uri = new String[100];
        int[] time = new int[uri.length * 10];
//        int[] count = new int[3600 * 24];

        int pos = time.length - 1;

        public Client(Servers servers)
        {
            this.servers = servers;
        }

        public void recTime(String loc, int t)
        {
            pos++;

            if (pos >= time.length)
                pos = pos % time.length;

            time[pos] = t;
            uri[pos % uri.length] = loc;

            totalTime += t;
        }

        public String getUrl()
        {
            return url;
        }

        public int getIndex()
        {
            return index;
        }

        public Servers getServers()
        {
            return servers;
        }
    }

    public class NetworkClient implements ServiceClient
    {
        String name;
        String addr;

        public NetworkClient(String name, String addr)
        {
            this.name = name;
            this.addr = addr.endsWith("/") ? addr.substring(0, addr.length() - 1) : addr;
        }

        @Override
        public String req(String link, String param, int timeout) throws Exception
        {
            String url = link.startsWith("/") ? addr + link : addr + "/" + link;

            if (timeout <= 0)
            {
                String loc = link.startsWith("/") ? name + link : name + "/" + link;
                Integer time = reqTimeout.get(loc);

                if (time == null)
                    timeout = SERVICE_TIME_OUT;
                else
                    timeout = time;
            }

            return Network.request(url, param, timeout);
        }
    }

    public interface ServicePicker
    {
        public int getIndex(Object req);
    }

    public interface ServiceListener
    {
        public Object onBegin(Client client, String loc, Object param);

        public void onSucc(Object passport, int time, Object res);

        public void onFail(Object passport, int time, Exception e);
    }
}
