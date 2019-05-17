package lerrain.service.script;

import java.util.ArrayList;
import java.util.List;

public class ReqHistory
{
    public static final int TYPE_SERVICE    = 1;
    public static final int TYPE_URL        = 2;
    public static final int TYPE_FUNCTION   = 3;

    public static final int RESTYPE_SUCCESS = 1;
    public static final int RESTYPE_FAIL    = 2;

    Long id;
    String name;

    int type;
    String target;

    Object request, response;

    long time = 0;
    int spend = -1;

    int result = RESTYPE_SUCCESS;

    List<ReqHistory> detail;

    public Object getRequest()
    {
        return request;
    }

    /**
     * 必须copy
     * @param request
     */
    public void setRequest(Object request)
    {
        this.request = DebugUtil.copy(request);
    }

    public Object getResponse()
    {
        return response;
    }

    /**
     * 必须copy
     * @param response
     */
    public void setResponse(Object response)
    {
        this.result = RESTYPE_SUCCESS;
        this.response = DebugUtil.copy(response);
    }

    public void setError(Object error)
    {
        this.result = RESTYPE_FAIL;
        this.response = DebugUtil.copy(error);;
    }

    public int getSpend()
    {
        return spend;
    }

    public void finish()
    {
        this.spend = (int)(System.currentTimeMillis() - time);
    }

    public boolean isFinished()
    {
        return this.spend >= 0;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public List<ReqHistory> getDetail()
    {
        return detail;
    }

    public void setDetail(List<ReqHistory> detail)
    {
        this.detail = detail;
    }

    public void add(ReqHistory r)
    {
        if (detail == null)
            detail = new ArrayList<>();

        detail.add(r);
    }

    public void setSpend(int spend)
    {
        this.spend = spend;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}