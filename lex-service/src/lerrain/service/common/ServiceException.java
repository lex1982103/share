package lerrain.service.common;

import java.util.List;

public class ServiceException extends RuntimeException
{
    List detail;

    public ServiceException(String msg)
    {
        super(msg);
    }

    public ServiceException(String msg, Exception e)
    {
        super(msg, e);
    }

    public ServiceException(String msg, List detail)
    {
        super(msg);

        this.detail = detail;
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
