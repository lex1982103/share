package lerrain.service.common;

import java.util.List;

public class ServiceException extends RuntimeException
{
    List detail;

    public ServiceException(Exception e, String msg)
    {
        this(e, msg, null);
    }

    public ServiceException(Exception e, String msg, List detail)
    {
        super(msg, e);

        this.detail = detail;
    }

    public ServiceException(String msg, List detail)
    {
        this(null, msg, detail);
    }

    public ServiceException(String msg)
    {
        this(null, msg, null);
    }

    public List getDetail()
    {
        return detail;
    }

    public void setDetail(List detail)
    {
        this.detail = detail;
    }
}
