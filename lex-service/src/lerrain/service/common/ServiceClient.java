package lerrain.service.common;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Param;
import feign.RequestLine;

/**
 * Created by lerrain on 2017/8/3.
 */
public interface ServiceClient
{
    @RequestLine("POST /{link}")
    public JsonNode req(@Param("link") String link, Object param, int timeout) throws Exception;
}
