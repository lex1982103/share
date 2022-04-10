package lerrain.service.common;

public class Result
{
    String result;
    Object content;

    String reason;
    Object detail;

    public static Result success(Object content)
    {
        Result res = new Result();
        res.result = "success";
        res.content = content;

        return res;
    }

    public static Result fail(String reason)
    {
        Result res = new Result();
        res.result = "fail";
        res.reason = reason;

        return res;
    }

    public static Result error(String reason)
    {
        Result res = new Result();
        res.result = "error";
        res.reason = reason;

        return res;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public Object getContent()
    {
        return content;
    }

    public void setContent(Object content)
    {
        this.content = content;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
