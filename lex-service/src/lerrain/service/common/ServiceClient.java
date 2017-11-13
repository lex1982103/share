package lerrain.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Param;
import feign.RequestLine;

/**
 * Created by lerrain on 2017/8/3.
 */
public interface ServiceClient
{
    @RequestLine("POST /{link}")
    public String req(@Param("link") String link, JSON param);
}
