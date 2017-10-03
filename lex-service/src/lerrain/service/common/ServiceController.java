package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServiceController
{
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject exc(RuntimeException e)
    {
        JSONObject res = new JSONObject();
        res.put("result", "fail");
        res.put("reason", e.getMessage());

        return res;
    }
}
