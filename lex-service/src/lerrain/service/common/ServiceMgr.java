package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import feign.FeignException;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import lerrain.tool.Common;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceMgr
{
    @Resource
    private Environment env;

    Map<String, Servers> map = new HashMap<>();

    Map<String, Integer> prtLog = new HashMap<>();

    Map<String, long[]> idRange = new HashMap<>();

    public void reset(Map<String, Object> json)
    {
        for (Map.Entry<String, Object> e : json.entrySet())
        {
            Servers servers = getServers(e.getKey());
            servers.resetClients(Common.trimStringOf(e.getValue()));
        }
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
        prtLog.put(service, level);
    }

    public ServiceClient getClient(String str)
    {
        return getServers(str).getClient();
    }

    public ServiceClient getClient(String str, int index)
    {
        return getServers(str).getClient(index);
    }

    public JSONObject req(String service, String loc, JSON param)
    {
        return JSONObject.parseObject(reqStr(service, loc, param.toJSONString()));
    }

    public JSONObject reqVal(String service, String loc, JSON param)
    {
        return JSONObject.parseObject(reqStr(service, loc, param.toJSONString())).getJSONObject("content");
    }

    public String reqStr(String service, String loc, String param)
    {
        try
        {
            long t = System.currentTimeMillis();
            String res = this.getClient(service).req(loc, param);

            Integer level = prtLog.get(service);
            if (level != null)
            {
                if (level == 1)
                    Log.debug("%s => %s/%s(%dms) => %s", param, service, loc, System.currentTimeMillis() - t, res);
                else if (level == 2)
                    Log.info("%s => %s/%s(%dms) => %s", param, service, loc, System.currentTimeMillis() - t, res);
            }
            return res;
        }
        catch (Exception e)
        {
            Log.error("request: " + service + "/" + loc + " -- " + param, e);
            throw e;
        }
    }

    public synchronized Long nextId(String code)
    {
        long[] v = idRange.get(code);

        if (v == null)
        {
            v = reqId(code);
            idRange.put(code, v);
        }
        else
        {
            v[0]++;

            if (v[0] > v[1])
            {
                long[] r = reqId(code);
                v[0] = r[0];
                v[1] = r[1];
            }
        }

        return v[0];
    }

    public long[] reqId(String code)
    {
        String[] res = this.reqStr("dict", "id/req", code).split(",");

        long[] r = new long[2];
        r[0] = Long.parseLong(res[0]);
        r[1] = Long.parseLong(res[1]);

        return r;
    }

    class JSONEncoder implements Encoder
    {
        @Override
        public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException
        {
            requestTemplate.header("Content-Type", "application/json;charset=utf-8");
            requestTemplate.body(o.toString());
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
        ServiceClient[] clients;

        ServicePicker picker;

        int defaultIndex = 0;

        public void resetClients(String addrs)
        {
            String[] url = addrs.split(",");

            clients = new ServiceClient[url.length];
            for (int i = 0; i < url.length; i++)
                clients[i] = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, url[i].trim());
        }

        public ServiceClient getClient(int index)
        {
            return clients[index % clients.length];
        }

        public ServiceClient getClient()
        {
            return getClient(picker == null ? defaultIndex : picker.getIndex());
        }
    }

    public static interface ServicePicker
    {
        public int getIndex();
    }
}
