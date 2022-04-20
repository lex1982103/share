package lerrain.service.common;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import lerrain.tool.Common;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class ServiceMgr
{
    public static int SPEND_SLOW = 300;
    public static int SERVICE_TIME_OUT = 500;
    public static int JSON_LIMIT = 1024 * 1024 * 20;

    @Value("${sys.code:null}")
    String serviceCode;

    @Value("${sys.index:null}")
    String serviceIndex;

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

    public void setConfig(Map<String, List> v)
    {
        for (String srvName : v.keySet())
        {
            List list = v.get(srvName);
            for (int i = 0; i < list.size(); i++)
            {
                Map rs = (Map)list.get(i);
                String uri = (String)rs.get("uri");
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

    public String getCallbackKey(String uri, String uuid)
    {
        return serviceCode + ";" + serviceIndex + ";" + uri + ";" + uuid;
    }

    /**
     * 回调门户
     * @param key
     * @param param
     * @return
     */
    public Object callback(String key, Object... param)
    {
        String[] keys = key.split(";");
        Client client = getClient(keys[0], Integer.parseInt(keys[1]));

        Map req = new HashMap();
        req.put("key", keys[3]);
        req.put("param", param);

        return reqVal(client, keys[2], req, null, SERVICE_TIME_OUT);
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

    public <T> Result<T> req(String service, int index, String loc, Object param, Class<T> clazz)
    {
        return req(service, index, loc, param, clazz, -1);
    }

    public <T> Result<T> req(String service, int index, String loc, Object param, Class<T> clazz, int timeout)
    {
        Client client = getClient(service, index);
        return req(client, loc, param, clazz, timeout);
    }

    public <T> Result<T> req(String service, String loc, Object param, Class<T> clazz)
    {
        return req(service, loc, param, clazz, -1);
    }

    public <T> Result<T> req(String service, String loc, Object param, Class<T> clazz, int timeout)
    {
        Client client = this.getServers(service).getClient(param);
        return req(client, loc, param, clazz, timeout);
    }

    public <T> Result<T> req(Client client, String loc, Object param, Class<T> clazz, int timeout)
    {
        Object passport = null;
        long t = System.currentTimeMillis();

        if (listener != null)
            passport = listener.onBegin(client, loc, param);

        try
        {
            Result res = call(client, loc, param, clazz, timeout);
            if (res.is("error"))
                throw new ServiceException(Common.trimStringOf(res.getReason()), res.getDetail());

            if (listener != null)
            {
                int t1 = (int)(System.currentTimeMillis() - t);
                if (res.is("success"))
                    listener.onSuccess(passport, t1, res);
                else
                    listener.onFail(passport, t1, res.getReason());
            }

            return res;
        }
        catch (Exception e)
        {
            if (listener != null)
                listener.onError(passport, (int)(System.currentTimeMillis() - t), e);

            throw new RuntimeException(e);
        }
    }

    public <T> T reqVal(String service, String loc, Object param, Class<T> clazz)
    {
        return reqVal(getClient(service), loc, param, clazz, -1);
    }

    public <T> T reqVal(String service, String loc, Object param, Class<T> clazz, int timeout)
    {
        return reqVal(getClient(service), loc, param, clazz, timeout);
    }

    public <T> T reqVal(Client client, String loc, Object param, Class<T> clazz)
    {
        return reqVal(client, loc, param, clazz, -1);
    }

    public <T> T reqVal(Client client, String loc, Object param, Class<T> clazz, int timeout)
    {
        Result<T> res = req(client, loc, param, clazz, timeout);
        if (!res.is("success"))
            throw new ServiceFeedback(res.getReason(), res.getDetail());

        return res.getContent();
    }

    public <T> Result<T>[] reqAll(String service, String loc, Object param, Class<T> clazz)
    {
        return reqAll(service, loc, param, clazz, -1);
    }

    public <T> Result<T>[] reqAll(String service, String loc, Object param, Class<T> clazz, int timeout)
    {
        Client[] clients = this.getServers(service).getAllClient();
        Result[] res = new Result[clients.length];

        for (int i = 0; i < clients.length; i++)
        {
            try
            {
                res[i] = req(clients[i], loc, param, clazz, timeout);
            }
            catch (Exception e)
            {
            }
        }

        return res;
    }

    public <T> Result<T> call(Client client, String loc, Object param, Class<T> clazz, int timeout)
    {
        Servers servers = client.getServers();

        try
        {
            Result<T> result;

            JsonNode node = client.client.req(loc, param, timeout);
            if (node.has("code"))
            {
                int code = node.get("code").intValue();
                if (code == 0)
                {
                    if (node.has("data"))
                        result = Result.success(Json.OM.convertValue(node.get("data"), clazz));
                    else if (node.has("result"))
                        result = Result.success(Json.OM.convertValue(node.get("result"), clazz));
                    else
                        result = Result.success(null);
                }
                else
                {
                    result = Result.fail(node.get("message").asText());
                }

                result.setCode(code);
            }
            else
            {
                result = new Result();
                result.setResult(node.get("result").asText());
                if (result.is(0))
                    result.setContent(Json.OM.convertValue(node.get("content"), clazz));
                else
                {
                    result.setReason(node.get("reason").asText());
                    if (node.has("detail"))
                        result.setDetail(Json.OM.convertValue(node.get("detail"), List.class));
                }
            }

            synchronized (client)
            {
                client.moreFail = 0;
            }

//            if (servers.level == 1)
//                Log.debug("%s => %s/%s => %s", param == null ? null : param.toString(), servers.name, loc, res);

            return result;
        }
        catch (Exception e)
        {
            synchronized (client)
            {
                if (e instanceof java.net.SocketTimeoutException || e instanceof java.net.SocketException)
                    client.moreFail++;
                else //这种一般是地址不对，不是服务错误
                    client.moreFail = 0;
            }

            Log.error("request: <" + servers.name + ">" + client.getUrl() + "/" + loc + " -- " + (param == null ? null : param.toString()) + " -- " + e.getMessage());
            throw new ServiceException("request: <" + servers.name + ">" + client.getUrl() + "/" + loc + " -- " + e.getMessage(), e);
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
    public <T> T ask(String service, String loc, Object param, Class<T> clazz)
    {
        return retry(service, loc, param, clazz, new int[] {10000, 10000, 10000, 10000, 10000});
    }

    private <T> T retry(String service, String loc, Object param, Class<T> clazz, int... sleep)
    {
        try
        {
            Result<T> result = req(service, loc, param, clazz,-1);

            if (result.is("success"))
                return result.getContent();

            //error里面判断过了
            throw new ServiceFeedback(result.getReason());
        }
        catch (ServiceFeedback sf) //非error不重试
        {
            throw sf;
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

                return retry(service, loc, param, clazz, ns);
            }
        }

        throw new ServiceException("服务<" + service + "/" + loc + ">异常，多次重试失败");
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

        if (getServers("secure").clients.length > 0)
        {
            List list = new ArrayList();
            Map v = new HashMap();
            v.put("from", name);
            v.put("index", index);
            v.put("result", "down");
            v.put("type", "service");
            v.put("time", System.currentTimeMillis());
            list.add(v);

            ServiceMgr.this.req("secure", "action.json", list, null);
        }
        else
        {
            Log.alert("无法获取secure服务地址");
        }
    }

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

//            Client[] last = clients;

            clients = new Client[url.length];
            for (int i = 0; i < url.length; i++)
            {
                clients[i] = new Client(this);
                clients[i].index = i;
                clients[i].url = Common.trimStringOf(url[i]);
//                clients[i].client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, clients[i].url);
                clients[i].client = new NetworkClient(name, clients[i].url);
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
                        if (req instanceof Map)
                        {
                            Map m = (Map)req;
                            for (String str : dispatch)
                            {
                                Object o = m.get(str);

                                if (o != null)
                                    return hash(o.toString());
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

    public String uuid()
    {
        return uuid(Integer.parseInt(serviceIndex));
    }

    public String uuid(int index)
    {
        for (;;)
        {
            String uuid = UUID.randomUUID().toString();
            if (hash(uuid) == index)
                return uuid;
        }
    }

    public int hash(Object key)
    {
        return key.hashCode() & 0x7FFFFFFF;
    }

    public static class Client
    {
        Servers servers;

        ServiceClient client;

        String url;

        int index;

        int moreFail; //连续错误
        long restoreTime; //连续错误后停机的恢复时间

        public Client(Servers servers)
        {
            this.servers = servers;
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
        public JsonNode req(String link, Object param, int timeout) throws Exception
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

            return request(url, param, timeout);
        }

        public String toString()
        {
            return name + "@" + addr;
        }

        public JsonNode request(String urlstr, Object param, int timeout) throws Exception
        {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL(urlstr);

                conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setReadTimeout(timeout);
                conn.setConnectTimeout(timeout);
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

                if (conn instanceof HttpsURLConnection)
                {
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    TrustManager[] tm = {new MyX509TrustManager()};
                    sslContext.init(null, tm, new java.security.SecureRandom());;
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    ((HttpsURLConnection) conn).setSSLSocketFactory(ssf);
                }

                if (param != null)
                {
                    try (OutputStream out = conn.getOutputStream())
                    {
                        Json.write(param, out);
                        out.flush();
                    }
                }

                try (InputStream in = conn.getInputStream())
                {
                    return Json.OM.readTree(in);
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (conn != null)
                    conn.disconnect();
            }
        }
    }

    private static class MyX509TrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)  throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    }

    public interface ServicePicker
    {
        public int getIndex(Object req);
    }

    public interface ServiceListener
    {
        public Object onBegin(Client client, String loc, Object param);

        public void onSuccess(Object passport, int time, Object content);

        public default void onFail(Object passport, int time, String reason)
        {
        }

        public default void onError(Object passport, int time, Exception e)
        {
        }
    }
}
