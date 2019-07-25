package lerrain.service.common;

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
        if (e instanceof ServiceException)
        {
            Log.error(e);

            ServiceException se = (ServiceException)e;
            res.put("result", "fail");
            res.put("reason", se.getMessage());
            res.put("detail", se.getDetail());
        }
        else
        {
            Log.error(e);

            res.put("result", "fail");
            res.put("reason", e.getMessage());
        }

        return res;
    }
}
