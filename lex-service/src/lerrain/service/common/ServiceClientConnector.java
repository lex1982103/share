package lerrain.service.common;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by lerrain on 2017/8/3.
 */
public interface ServiceClientConnector
{
    public static int REQUEST_TIME_OUT = 500;

    JsonNode req(String link, Object param, int timeout) throws Exception;
}
