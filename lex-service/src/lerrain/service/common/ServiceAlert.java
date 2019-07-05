package lerrain.service.common;

import java.util.List;

public class ServiceAlert extends ServiceException
{
    public ServiceAlert(String msg)
    {
        super(msg);
    }

    public ServiceAlert(String msg, List detail)
    {
        super(msg, detail);
    }
}
