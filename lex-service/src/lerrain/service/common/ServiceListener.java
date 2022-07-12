package lerrain.service.common;

public interface ServiceListener
{
    public Object onBegin(ServiceClient client, String loc, Object param);

    public default void onFinal(Object passport, Result res, int time)
    {
    }
}

