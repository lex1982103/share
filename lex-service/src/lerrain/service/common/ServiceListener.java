package lerrain.service.common;

public interface ServiceListener
{
    public Object onBegin(ServiceClient client, String loc, Object param);

    public void onSuccess(Object passport, int time, Object content);

    public default void onFail(Object passport, int time, String reason)
    {
    }

    public default void onError(Object passport, int time, Exception e)
    {
    }
}

