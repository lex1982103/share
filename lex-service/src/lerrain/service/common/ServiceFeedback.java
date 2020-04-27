package lerrain.service.common;

import java.util.List;

public class ServiceFeedback extends RuntimeException
{
    List detail;

    public ServiceFeedback(Exception e, String msg)
    {
        this(e, msg, null);
    }

    public ServiceFeedback(Exception e, String msg, List detail)
    {
        super(msg, e);

        this.detail = detail;
    }

    public ServiceFeedback(String msg, List detail)
    {
        this(null, msg, detail);
    }

    public ServiceFeedback(String msg)
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
