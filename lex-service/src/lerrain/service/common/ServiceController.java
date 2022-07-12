package lerrain.service.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Result exc(Exception e)
    {
        if (e instanceof ServiceFeedback) //手工抛出的异常，不算做错误
        {
            Log.alert(e.getMessage());
            return Result.alert(e.getMessage());
        }
        else //其他异常都算作失败
        {
            Log.error(e);
            return Result.fail(e.toString());
        }
    }

//    private JSONArray getExceptionStack(Throwable e)
//    {
//        JSONArray list = new JSONArray();
//        list.add(e.getMessage());
//
//        StackTraceElement[] trace = e.getStackTrace();
//        for (StackTraceElement traceElement : trace)
//            list.add("\tat " + traceElement);
//
//        for (Throwable se : e.getSuppressed())
//            list.addAll(getExceptionStack(se));
//
//        Throwable ourCause = e.getCause();
//        if (ourCause != null)
//            list.addAll(getExceptionStack(e.getCause()));
//
//        return list;
//    }
}
