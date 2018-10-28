package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import feign.*;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import lerrain.tool.Common;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

public class ServiceMgr
{
    @Resource
    private Environment env;

    public static final long MAX = 1024L * 1024 * 16;

    Random ran = new Random();

    Map<String, Servers> map = new HashMap<>();

    public void reset(Map<String, Object> json)
    {
        for (Map.Entry<String, Object> e : json.entrySet())
        {
            Servers servers = getServers(e.getKey());
            servers.resetClients(Common.trimStringOf(e.getValue()));
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
                servers = new Servers();
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
        return JSONObject.parseObject(reqStr(service, loc, param));
    }

    public Object reqVal(String service, String loc, JSON param)
    {
        JSONObject res = req(service, loc, param);

        if (!"success".equals(res.getString("result")))
            throw new RuntimeException(res.getString("reason"));

        return res.get("content");
    }

    public String reqStr(String service, String loc, Object param)
    {
        long t = System.currentTimeMillis();

        Servers servers = this.getServers(service);
        Client client = servers.getClient(param);

        try
        {
            client.post++;
            String res = client.client.req(loc, param == null ? null : param.toString());

            long t1 = System.currentTimeMillis() - t;
            client.recTime((int)t1);

            if (servers.level == 1)
                Log.debug("%s => %s/%s(%dms) => %s", param, service, loc, t1, res);
            else if (servers.level == 2)
                Log.info("%s => %s/%s(%dms) => %s", param, service, loc, t1, res);

            return res;
        }
        catch (Exception e)
        {
            client.fail++;

            Log.error("request: " + service + "/" + loc + " -- " + param, e);
            throw e;
        }
    }

    public JSONObject report()
    {
        JSONObject r = new JSONObject();

        for (Map.Entry<String, Servers> e : map.entrySet())
        {
            JSONArray list = new JSONArray();
            for (Client c : e.getValue().clients)
            {
                JSONObject r1 = new JSONObject();
                r1.put("client", c.client.toString());
                r1.put("postTimes", c.post);
                r1.put("failTimes", c.fail);

                int[] t = new int[c.time.length];
                for (int i = 0; i < t.length; i++)
                    t[i] = c.time[(c.pos + t.length - i) % t.length];
                r1.put("time", t);

                list.add(r1);
            }

            r.put(e.getKey(), list);
        }

        return r;
    }

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

    class Servers
    {
        Client[] clients;

        ServicePicker picker;

        int defaultIndex = -1;

        int level = 0;

        public void resetClients(String addrs)
        {
            if (addrs == null)
            {
                clients = new Client[0];
                return;
            }

            String[] url = addrs.split(",");

            clients = new Client[url.length];
            for (int i = 0; i < url.length; i++)
            {
                clients[i] = new Client();
                clients[i].client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, url[i].trim());
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

            return clients[index % clients.length];
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
                                    return s.hashCode();
                            }
                        }

                        return -1;
                    }
                };
            }
        }
    }

    static class Client
    {
        ServiceClient client;

        int post;
        int fail;

        int[] time = new int[1000];
        int pos = time.length - 1;

        public void recTime(int t)
        {
            pos = (pos + 1) % time.length;
            time[pos] = t;
        }
    }

    public static interface ServicePicker
    {
        public int getIndex(Object req);
    }
}
