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
        Log.error(e);

        JSONObject res = new JSONObject();
        res.put("result", "fail");
        res.put("reason", e.getMessage());

        return res;
    }
}
