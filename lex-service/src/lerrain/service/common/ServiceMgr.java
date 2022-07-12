package lerrain.service.common;

import lerrain.tool.Common;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.*;

public class ServiceMgr
{
    public static int REQUEST_TIME_OUT = 500;

    @Value("${sys.code:null}")
    String serviceCode;

    @Value("${sys.index:null}")
    String serviceIndex;

    @Resource
    Environment env;

    Map<String, Service> map = new HashMap<>();

    ServiceListener listener;

    public void reset(Map<String, Object> json)
    {
        for (Map.Entry<String, Object> e : json.entrySet())
        {
            Service service = getService(e.getKey());
            service.resetClients(Common.trimStringOf(e.getValue()));
        }
    }

    public void setConfig(Map<String, List> v)
    {
        for (String srvName : v.keySet())
        {
            Service service = map.get(srvName);
            if (service != null)
                service.setConfig(v.get(srvName));
        }
    }

    public boolean hasClients(String serviceName)
    {
        return map.containsKey(serviceName) || env.containsProperty("service." + serviceName);
    }

    public Service getService(String serviceName)
    {
        synchronized (map)
        {
            Service service = map.get(serviceName);
            if (service == null)
            {
                service = new Service(this, serviceName);
                service.resetClients(env.getProperty("service." + serviceName));

                if (env.containsProperty("dispatch." + serviceName))
                {
                    final String[] dispatch = env.getProperty("dispatch." + serviceName).split(",");
                    service.setDispatch(req ->
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
                    });
                }

                map.put(serviceName, service);
            }

            return service;
        }
    }

    public void setServicePicker(String serviceName, ServiceClientPicker picker)
    {
        Service service = getService(serviceName);
        service.picker = picker;
    }

    public void setDefaultClient(String serviceName, int defaultIndex)
    {
        Service service = getService(serviceName);
        service.defaultIndex = defaultIndex;
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
        ServiceClient client = getClient(keys[0], Integer.parseInt(keys[1]));

        Map req = new HashMap();
        req.put("key", keys[3]);
        req.put("param", param);

        return reqVal(client, keys[2], req, REQUEST_TIME_OUT);
    }

    public void setLog(String service, int level)
    {
        getService(service).level = level;
    }

    public ServiceClient[] getAllClient(String str)
    {
        return getService(str).getAllClient();
    }

    public ServiceClient getClient(String str)
    {
        return getService(str).getClient(null);
    }

    public ServiceClient getClient(String str, int index)
    {
        return getService(str).getClient(index);
    }

    public <T> Result<T> req(String service, int index, String loc, Object param)
    {
        return req(service, index, loc, param, -1);
    }

    public <T> Result<T> req(String service, int index, String loc, Object param, int timeout)
    {
        ServiceClient client = getClient(service, index);
        return req(client, loc, param, timeout);
    }

    public <T> Result<T> req(String service, String loc, Object param)
    {
        return req(service, loc, param, -1);
    }

    public <T> Result<T> req(String service, String loc, Object param, int timeout)
    {
        ServiceClient client = this.getService(service).getClient(param);
        return req(client, loc, param, timeout);
    }

    public <T> Result<T> req(ServiceClient client, String loc, Object param, int timeout)
    {
        Object passport = null;
        long t = System.currentTimeMillis();

        if (listener != null)
            passport = listener.onBegin(client, loc, param);

        Result res = null;

        try
        {
            res = call(client, loc, param, timeout);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (listener != null)
                listener.onFinal(passport, res, (int)(System.currentTimeMillis() - t));
        }

        return res;
    }

    public <T> T reqVal(String service, String loc, Object param)
    {
        return reqVal(getClient(service), loc, param, -1);
    }

    public <T> T reqVal(String service, String loc, Object param, int timeout)
    {
        return reqVal(getClient(service), loc, param, timeout);
    }

    public <T> T reqVal(ServiceClient client, String loc, Object param)
    {
        return reqVal(client, loc, param, -1);
    }

    public <T> T reqVal(ServiceClient client, String loc, Object param, int timeout)
    {
        Result<T> res = req(client, loc, param, timeout);

        if (res.success())
            return res.getContent();

        if (res.alert())
            throw new ServiceFeedback(res.getReason(), res.getDetail());

        throw new ServiceException(res.getReason(), res.getDetail());
    }

    public <T> Result<T>[] reqAll(String service, String loc, Object param)
    {
        return reqAll(service, loc, param, -1);
    }

    public <T> Result<T>[] reqAll(String service, String loc, Object param, int timeout)
    {
        ServiceClient[] clients = this.getService(service).getAllClient();
        Result[] res = new Result[clients.length];

        for (int i = 0; i < clients.length; i++)
        {
            try
            {
                res[i] = req(clients[i], loc, param, timeout);
            }
            catch (Exception e)
            {
            }
        }

        return res;
    }

    public <T> Result<T> call(ServiceClient client, String loc, Object param, int timeout)
    {
        Service service = client.getService();

        try
        {
            Result<T> result = client.req(loc, param, timeout);
            client.moreFail = 0;

            return result;
        }
        catch (Exception e)
        {
            if (e instanceof java.net.SocketTimeoutException || e instanceof java.net.SocketException)
                client.moreFail++;
            else //这种一般是地址不对，不是服务错误
                client.moreFail = 0;

            Log.error("request: <" + service.name + ">" + client.getUrl() + "/" + loc + " -- " + (param == null ? null : param.toString()) + " -- " + e.getMessage());
            throw new ServiceException("request: <" + service.name + ">" + client.getUrl() + "/" + loc + " -- " + e.getMessage(), e);
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
    public <T> T ask(String service, String loc, Object param)
    {
        return retry(service, loc, param, new int[] {10000, 10000, 10000, 10000, 10000});
    }

    private <T> T retry(String service, String loc, Object param, int... sleep)
    {
        try
        {
            Result<T> result = req(service, loc, param,-1);

            if (result.success())
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

                return retry(service, loc, param, ns);
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

    public void noValidClient(String name, int index)
    {
        if ("secure".equals(name))
        {
            Log.error("secure is down");
            return;
        }

        if (getService("secure").clients.length > 0)
        {
            List list = new ArrayList();
            Map v = new HashMap();
            v.put("from", name);
            v.put("index", index);
            v.put("result", "down");
            v.put("type", "service");
            v.put("time", System.currentTimeMillis());
            list.add(v);

            ServiceMgr.this.req("secure", "action.json", list);
        }
        else
        {
            Log.alert("无法获取secure服务地址");
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
}
