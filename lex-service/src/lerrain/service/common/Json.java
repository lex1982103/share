package lerrain.service.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lerrain.tool.Common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Json
{
    static ObjectMapper OM = new ObjectMapper();

    static
    {
        OM.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OM.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static Map parse(String str) throws Exception
    {
        if (Common.isEmpty(str))
            return null;

        return OM.readValue(str, Map.class);
    }

    public static Map parseNull(String str)
    {
        try
        {
            return parse(str);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> T parse(String str, Class<T> clazz) throws Exception
    {
        if (Common.isEmpty(str))
            return null;

        return OM.readValue(str, clazz);
    }

    public static <T> T parseNull(String str, Class<T> clazz)
    {
        try
        {
            return parse(str, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String write(Object val) throws Exception
    {
        if (val == null)
            return null;

        return OM.writeValueAsString(val);
    }

    public static String writeNull(Object val)
    {
        try
        {
            return write(val);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void write(Object val, OutputStream os) throws IOException
    {
        OM.writeValue(os, val);
    }

    public static <T> T read(InputStream is, Class<T> clazz) throws IOException
    {
        return OM.readValue(is, clazz);
    }
}
