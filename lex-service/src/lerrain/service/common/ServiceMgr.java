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
import java.util.HashMap;
import java.util.Map;

public class ServiceMgr
{
    @Resource
    private Environment env;

    Map<String, String> current = new HashMap<>();
    Map<String, ServiceClient> map = new HashMap<>();

    @RequestMapping("/service/reset.json")
    @ResponseBody
    public JSONObject reset(@RequestBody(required=false) JSONObject json)
    {
        synchronized (map)
        {
            for (Map.Entry<String, Object> e : json.entrySet())
            {
                String url = Common.trimStringOf(e.getValue());
                ServiceClient client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, url);

                map.put(e.getKey(), client);
                current.put(e.getKey(), url);
            }
        }

        JSONObject res = new JSONObject();
        res.put("result", "success");
        res.put("content", current);

        return res;
    }

    public ServiceClient getClient(String str)
    {
        if (!map.containsKey(str))
        {
            String url = env.getProperty("service." + str);
            ServiceClient client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, url);

            synchronized (map)
            {
                map.put(str, client);
                current.put(str, url);
            }
        }

        return map.get(str);
    }

    public JSONObject req(String service, String loc, JSON param)
    {
        return JSONObject.parseObject(reqStr(service, loc, param));
    }

    public JSONObject reqVal(String service, String loc, JSON param)
    {
        return JSONObject.parseObject(reqStr(service, loc, param)).getJSONObject("content");
    }

    public String reqStr(String service, String loc, JSON param)
    {
        try
        {
            long t = System.currentTimeMillis();
            String res = this.getClient(service).req(loc, param);

            Log.debug("request: " + service + "/" + loc + " -- " + param + ", response: " + res + " in " + (System.currentTimeMillis() - t) + "ms");
            return res;
        }
        catch (Exception e)
        {
            Log.error("request: " + service + "/" + loc + " -- " + param, e);
            throw e;
        }
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
}
