package lerrain.service.common;

import java.util.List;

public class Result<T>
{
    public static Result SUCCESS = success(null);

    int code = -9;

    String result;
    T content;

    String reason;
    List detail;

    public static <T> Result success(T content)
    {
        Result res = new Result();
        res.result = "success";
        res.code = 0;
        res.content = content;

        return res;
    }

    public static Result fail(String reason)
    {
        Result res = new Result();
        res.result = "fail";
        res.code = 1;
        res.reason = reason;

        return res;
    }

    public static Result error(String reason)
    {
        Result res = new Result();
        res.result = "error";
        res.code = -1;
        res.reason = reason;

        return res;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;

        if ("success".equals(result))
            code = 0;
        else if ("fail".equals(result))
            code = 1;
        else
            code = -1;
    }

    public T getContent()
    {
        return content;
    }

    public void setContent(T content)
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

    public boolean is(String r)
    {
        return r.equals(result);
    }

    public boolean is(int code)
    {
        return this.code == code;
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
