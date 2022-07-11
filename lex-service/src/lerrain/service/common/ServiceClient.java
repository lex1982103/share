package lerrain.service.common;

import com.fasterxml.jackson.databind.JsonNode;

public class ServiceClient
{
    Service service;

    ServiceClientConnector client;

    String url;

    int index;

    int moreFail; //连续错误
    long restoreTime; //连续错误后停机的恢复时间

    public ServiceClient(Service service)
    {
        this.service = service;

        this.client = new SimpleConnector(service.name, url);
    }

    public String getUrl()
    {
        return url;
    }

    public int getIndex()
    {
        return index;
    }

    public Service getService()
    {
        return service;
    }

    public JsonNode req(String link, Object param, int timeout) throws Exception
    {
        if (timeout <= 0)
        {
            Integer time = service.reqTimeout.get(link.startsWith("/") ? link : "/" + link);

            if (time == null)
                timeout = ServiceClientConnector.REQUEST_TIME_OUT;
            else
                timeout = time;
        }

        return client.req(link, param, timeout);
    }
}
