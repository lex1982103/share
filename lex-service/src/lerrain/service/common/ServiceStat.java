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

public class ServiceStat implements Runnable
{
    @Value("${sys.index}")
    String srvIndex;

    @Value("${sys.code}")
    String srvCode;

    @Autowired
    ServiceMgr serviceMgr;

    List msg = new ArrayList();

    JSONArray retry = new JSONArray();

    Thread thread;

    public String getSrvIndex()
    {
        return srvIndex;
    }

    public String getSrvCode()
    {
        return srvCode;
    }

    public Thread getThread()
    {
        return thread;
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

        if (thread != null)
            thread.interrupt();

        thread = new Thread(this);
        thread.start();

        Log.info("Service Stat started");
    }

    public void addMsg(Map v)
    {
        v.put("fromSrv", srvCode);
        v.put("fromIndex", srvIndex);

        if (!v.containsKey("time"))
            v.put("time", System.currentTimeMillis());

        synchronized (msg)
        {
            if (msg.size() > 1000)
                msg.clear();

            msg.add(v);
            msg.notify();
        }
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

    public void run()
    {
        while (true)
        {
            JSONArray list = new JSONArray();

            synchronized (msg)
            {
                if (!msg.isEmpty())
                {
                    list.addAll(msg);
                    msg.clear();
                }
            }

            if (list.size() > 0)
            {
                try
                {
                    serviceMgr.req("secure", "action.json", list);
                }
                catch (Exception e)
                {
                    store(list);
                }
            }

            try
            {
                Thread.sleep(100);

                synchronized (msg)
                {
                    if (msg.isEmpty())
                        msg.wait();
                }
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }

    private void store(JSONArray more)
    {
        boolean invoke;

        synchronized (retry)
        {
            invoke = retry.isEmpty();
            retry.addAll(more);
        }

        if (invoke)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        retry(10000, 60000, 600000, 1800000);
                    }
                    catch (Exception e)
                    {
                        Log.alert(e.getMessage());
                    }
                }

            }).start();
        }
    }

    private void retry(int... sleep) throws InterruptedException
    {
        try
        {
            synchronized (retry)
            {
                serviceMgr.req("secure", "action.json", retry);
                retry.clear();
            }
        }
        catch (Exception e3)
        {
            Log.error("发送至secure服务失败 -> ", e3);

            if (sleep.length > 0)
            {
                Log.info("等待" + sleep[0] + "ms继续发送");

                try
                {
                    Thread.sleep(sleep[0]);
                }
                catch (InterruptedException i3)
                {
                    throw i3;
                }

                int[] ns = new int[sleep.length - 1];
                for (int i = 0; i < ns.length; i++)
                    ns[i] = sleep[i + 1];

                retry(ns);
            }
        }
    }
}
