package lerrain.service.common;

import lerrain.tool.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Service
{
    private static final Random ran = new Random();

    String name;

    ServiceClient[] clients;

    ServiceClientPicker picker;

    ServiceMgr serviceMgr;

    int defaultIndex = -1;

    int level = 0;

    Map<String, Integer> reqTimeout;

    protected Service(ServiceMgr serviceMgr, String name)
    {
        this.serviceMgr = serviceMgr;
        this.name = name;
    }

    public void resetClients(String addrs)
    {
        if (addrs == null)
        {
            clients = new ServiceClient[0];
            return;
        }

        String[] url = addrs.split(",");

//            Client[] last = clients;

        clients = new ServiceClient[url.length];
        for (int i = 0; i < url.length; i++)
        {
            clients[i] = new ServiceClient(this);
            clients[i].index = i;
            clients[i].url = Common.trimStringOf(url[i]);
//                clients[i].client = Feign.builder().encoder(new JSONEncoder()).decoder(new JSONDecoder()).target(ServiceClient.class, clients[i].url);
//                clients[i].client = new NetworkClient(name, clients[i].url);
        }
    }

    public ServiceClient[] getAllClient()
    {
        return clients;
    }

    public ServiceClient getClient(int index)
    {
        if (index < 0)
            index = ran.nextInt(clients.length);

        ServiceClient client = clients[index % clients.length];

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
                serviceMgr.noValidClient(name, index);
            }
            else //处于无效期，这里用else，而不是另起if（上面的结果可能已经要求停机了），是考虑每次标记失败都要先请求一次，判断是不是好了
            {
                ServiceClient res = null;

                if (clients.length > 1) //多台机器才有调整停机的空间一台机器只能挂了
                {
                    for (ServiceClient c : clients)
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

    public ServiceClient getClient(Object req)
    {
        return getClient(picker == null ? defaultIndex : picker.getIndex(req));
    }

    public void setDispatch(ServiceClientPicker picker)
    {
        this.picker = picker;
    }

    public String getName()
    {
        return name;
    }

    public void setConfig(List list)
    {
        reqTimeout = new HashMap<>();

        for (int i = 0; i < list.size(); i++)
        {
            Map rs = (Map)list.get(i);
            String uri = (String)rs.get("uri");
            int timeout = Common.intOf(rs.get("timeout"), -1);

            if (uri != null && timeout > 0)
            {
                String reqKey = uri.startsWith("/") ? uri : "/" + uri;
                reqTimeout.put(reqKey, timeout);
            }
        }
    }
}
