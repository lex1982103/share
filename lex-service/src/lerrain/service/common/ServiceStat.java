package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
        serviceMgr.setListener(new ServiceListener()
        {
            @Override
            public Object onBegin(ServiceClient client, String s, Object req)
            {
                String name = client.getService().getName();

                if ("secure".equals(name))
                    return null;

                return new Object[] {
                        name, client.getIndex(), s, req
                };
            }

            @Override
            public void onFinal(Object passport, Result result, int time)
            {
                if (passport != null)
                {
                    Object[] x = (Object[]) passport;
                    recService((String) x[0], (Integer) x[1], (String) x[2], time, result.getCode(), result.reqBytes, result.resBytes, x[3], result.getContent(), result.getReason());
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

    public void recService(String service, int index, String uri, int spend, int code, int reqBytes, int resBytes, Object request, Object response, String alert)
    {
        if (reqBytes > 1024) //debug的报文巨大
            request = null;
        if (resBytes > 1024) //debug的报文巨大
            response = null;

        if (code >= 0 && spend <= 100) //太多了，减少压力，成功且快速的先不传了
        {
            request = null;
            response = null;
        }

//        if (response instanceof String)
//            response = Json.parseNull((String)response);

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
        v.put("requestBytes", reqBytes);
        v.put("responseBytes", resBytes);
        v.put("time", System.currentTimeMillis() - spend);
        v.put("alert", alert);

        addMsg(v);
    }

//    @Deprecated
//    public void recServiceFail(String service, int index, String uri, int spend, int reqBytes, Object request)
//    {
//        recServiceFail(service, index, uri, spend, reqBytes, request, null);
//    }
//
//    public void recServiceFail(String service, int index, String uri, int spend, int reqBytes, Object request, Exception e)
//    {
//        if (reqBytes > 1024) //debug的报文巨大
//            request = null;
//
//        Map v = new HashMap();
//        v.put("service", service);
//        v.put("from", service); //废弃，为了兼容
//        v.put("index", index);
//        v.put("uri", uri);
//        v.put("spend", spend);
//        v.put("result", "fail");
//        v.put("type", "service");
//        v.put("request", request);
//        v.put("time", System.currentTimeMillis() - spend);
//
//        if (e != null)
//            v.put("exc", log(new ArrayList(), e));
//
//        addMsg(v);
//    }
//
//    private List<String> log(List<String> list, Throwable e)
//    {
//        if (e != null)
//        {
//            StackTraceElement[] ste = e.getStackTrace();
//            if (ste != null)
//                for (StackTraceElement st : ste)
//                    if (st != null)
//                        list.add(st.toString());
//
//            log(list, e.getCause());
//        }
//
//        return list;
//    }
}
