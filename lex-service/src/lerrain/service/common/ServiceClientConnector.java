package lerrain.service.common;

/**
 * Created by lerrain on 2017/8/3.
 */
public interface ServiceClientConnector
{
    <T> Result<T> req(String link, Object param, int timeout, Class<T> clazz) throws Exception;
}
