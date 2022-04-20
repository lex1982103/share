package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
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
            public void onError(Object o, int time, Exception e)
            {
                if (o != null)
                {
                    Object[] x = (Object[]) o;
                    recServiceFail((String) x[0], (Integer) x[1], (String) x[2], time, x[3], e);
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
        if ("develop".equals(service)) //debug的报文巨大
            request = null;

        if (spend <= 100) //太多了，减少压力，成功且快速的先不传了
        {
            request = null;
            response = null;
        }

        if (response instanceof String)
            response = Json.parseNull((String)response);

        Map v = new HashMap();
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

    @Deprecated
    public void recServiceFail(String service, int index, String uri, int spend, Object request)
    {
        recServiceFail(service, index, uri, spend, request, null);
    }

    public void recServiceFail(String service, int index, String uri, int spend, Object request, Exception e)
    {
        if ("develop".equals(service)) //debug的报文巨大
            request = null;

        Map v = new HashMap();
        v.put("service", service);
        v.put("from", service); //废弃，为了兼容
        v.put("index", index);
        v.put("uri", uri);
        v.put("spend", spend);
        v.put("result", "fail");
        v.put("type", "service");
        v.put("request", request);
        v.put("time", System.currentTimeMillis() - spend);

        if (e != null)
            v.put("exc", log(new ArrayList(), e));

        addMsg(v);
    }

    private List<String> log(List<String> list, Throwable e)
    {
        if (e != null)
        {
            StackTraceElement[] ste = e.getStackTrace();
            if (ste != null)
                for (StackTraceElement st : ste)
                    if (st != null)
                        list.add(st.toString());

            log(list, e.getCause());
        }

        return list;
    }
}
