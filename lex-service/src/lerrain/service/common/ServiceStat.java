package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceStat extends PostQueue
{
    @Value("${sys.index}")
    String srvIndex;

    @Value("${sys.code}")
    String srvCode;

    @Autowired
    ServiceMgr serviceMgr;

    public String getSrvIndex()
    {
        return srvIndex;
    }

    public String getSrvCode()
    {
        return srvCode;
    }

    @PostConstruct
    public void start()
    {
        serviceMgr.setListener(new ServiceMgr.ServiceListener()
        {
            @Override
            public Object onBegin(ServiceMgr.Client client, String s, Object req)
            {
                String name = client.getServers().getName();

                if ("secure".equals(name))
                    return null;

                return new Object[] {
                        name, client.getIndex(), s, req
                };
            }

            @Override
            public void onSucc(Object o, int time, Object res)
            {
                if (o != null)
                {
                    Object[] x = (Object[]) o;
                    recServiceSucc((String) x[0], (Integer) x[1], (String) x[2], time, x[3], res);
                }
            }

            @Override
            public void onFail(Object o, int time, Exception e)
            {
                if (o != null)
                {
                    Object[] x = (Object[]) o;
                    recServiceFail((String) x[0], (Integer) x[1], (String) x[2], time, x[3]);
                }
            }
        });

        super.initiate(serviceMgr, 1000, "secure", "action.json");
        super.start();

        Log.info("Service Stat started");
    }

    public void addMsg(Map v)
    {
        v.put("fromSrv", srvCode);
        v.put("fromIndex", srvIndex);

        if (!v.containsKey("time"))
            v.put("time", System.currentTimeMillis());

        super.addMsg(v);
    }

    public void recServiceSucc(String service, int index, String uri, int spend, Object request, Object response)
    {
        if ("secure".equals(service))
            return;

        try
        {
            if (response instanceof String)
                response = JSON.parseObject((String)response);
        }
        catch (Exception e)
        {
        }

        JSONObject v = new JSONObject();
        v.put("service", service);
        v.put("from", service); //废弃，为了兼容
        v.put("index", index);
        v.put("uri", uri);
        v.put("spend", spend);
        v.put("result", "success");
        v.put("type", "service");
        v.put("request", request);
        v.put("response", response);
        v.put("time", System.currentTimeMillis() - spend);

        addMsg(v);
    }

    public void recServiceFail(String service, int index, String uri, int spend, Object request)
    {
        if ("secure".equals(service))
            return;

        JSONObject v = new JSONObject();
        v.put("service", service);
        v.put("from", service); //废弃，为了兼容
        v.put("index", index);
        v.put("uri", uri);
        v.put("spend", spend);
        v.put("result", "fail");
        v.put("type", "service");
        v.put("request", request);
        v.put("time", System.currentTimeMillis() - spend);

        addMsg(v);
    }
}
