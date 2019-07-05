package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;

public class ServiceResponse
{
    int result = 0;

    Object value;

    String reason;

    public ServiceResponse(JSONObject rsp)
    {
        if (rsp != null)
        {
            String res = rsp.getString("result");

            if ("success".equalsIgnoreCase(res))
                result = 1;
            else if ("alert".equalsIgnoreCase(res))
                result = 2;

            if (result == 1)
                value = rsp.get("content");
            else
                reason = rsp.getString("reason");
        }
    }

    public boolean isSuccess()
    {
        return result == 1;
    }

    public boolean isAlert()
    {
        return result == 2;
    }

    public boolean isFail()
    {
        return result == 0;
    }

    public Object getValue()
    {
        return value;
    }

    public JSONObject getJsonVal()
    {
        return (JSONObject)value;
    }

    public String getReason()
    {
        return reason;
    }
}
