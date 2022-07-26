package lerrain.service.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lerrain.tool.Common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Json
{
    public static ObjectMapper OM = new ObjectMapper();

    static
    {
        OM.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OM.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static Map parse(String str)
    {
        if (Common.isEmpty(str))
            return null;

        try
        {
            return OM.readValue(str, Map.class);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
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

    public static <T> T parse(String str, Class<T> clazz)
    {
        if (Common.isEmpty(str))
            return null;

        try
        {
            return OM.readValue(str, clazz);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(InputStream is, Class<T> clazz) throws IOException
    {
        try
        {
            return OM.readValue(is, clazz);
        }
        catch (JsonParseException e)
        {
            throw new RuntimeException(e);
        }
        catch (JsonMappingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseNull(InputStream is, Class<T> clazz)
    {
        try
        {
            return parse(is, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
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

    public static String write(Object val)
    {
        if (val == null)
            return null;

        try
        {
            return OM.writeValueAsString(val);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
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
