package lerrain.service.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Controller
@ControllerAdvice
public class ServiceController
{
    @RequestMapping("/health")
    @ResponseBody
    @CrossOrigin
    public String health()
    {
        return "success";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONObject exc(Exception e)
    {
        JSONObject res = new JSONObject();
        res.put("reason", e.getMessage());
        res.put("detail", getExceptionStack(e));

        if (e instanceof ServiceFeedback) //手工抛出的异常，不算做错误
        {
            Log.alert(e.getMessage());
            res.put("result", "fail");
        }
        else //其他异常都算作失败
        {
            Log.error(e);
            res.put("result", "error");
        }

        return res;
    }

    private JSONArray getExceptionStack(Throwable e)
    {
        JSONArray list = new JSONArray();
        list.add(e.getMessage());

        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement traceElement : trace)
            list.add("\tat " + traceElement);

        for (Throwable se : e.getSuppressed())
            list.addAll(getExceptionStack(se));

        Throwable ourCause = e.getCause();
        if (ourCause != null)
            list.addAll(getExceptionStack(e.getCause()));

        return list;
    }
}
