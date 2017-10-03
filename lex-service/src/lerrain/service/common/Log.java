package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;
import lerrain.tool.Common;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lerrain on 2017/8/2.
 */
public class Log
{
    static String LOG_DIR = "log/";

    static boolean write = false;

    static String fmt = "%tF %tT <%s> %s.%s: %s";

    static List<String> buffer = new ArrayList<>();

    public static String getLogFile(long time)
    {
        return LOG_DIR + String.format("%tF", time) + ".log";
    }

    public static void stopWrite()
    {
        write = false;
    }

    public static void startWrite(String path)
    {
        LOG_DIR = path;

        new File(LOG_DIR).mkdirs();

        write = true;

        while (write)
        {
            synchronized (buffer)
            {
                try (FileOutputStream f = new FileOutputStream(new File(Log.getLogFile(System.currentTimeMillis())), true))
                {
                    for (String line : buffer)
                    {
                        f.write(line.getBytes());
                        f.write("\n".getBytes());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                buffer.clear();

                try
                {
                    buffer.wait();
                }
                catch (InterruptedException e)
                {
                }
            }
        }
    }

    private static void print(String inf, Object str, Exception e)
    {
        if (e != null) try
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            PrintWriter pw = new PrintWriter(os);
            e.printStackTrace(pw);
            pw.close();

            str = (str == null ? "" : str) + "\n" + os.toString();
        }
        catch (Exception e1)
        {
        }

        long t = System.currentTimeMillis();
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String log = String.format(fmt, t, t, inf, ste[3].getClassName(), ste[3].getMethodName(), str);

        System.out.println(log);

        if (write) synchronized (buffer)
        {
            buffer.add(log);
            buffer.notify();
        }
    }

    public static void info(Object str, Exception e)
    {
        print("INFO", str, e);
    }

    public static void info(Object str)
    {
        print("INFO", str, null);
    }

    public static void info(Exception e)
    {
        print("INFO", null, e);
    }

    public static void debug(Object str, Exception e)
    {
        print("DEBUG", str, e);
    }

    public static void debug(Object str)
    {
        print("DEBUG", str, null);
    }

    public static void debug(Exception e)
    {
        print("DEBUG", null, e);
    }

    public static void alert(Object str, Exception e)
    {
        print("ALERT", str, e);
    }

    public static void alert(Object str)
    {
        print("ALERT", str, null);
    }

    public static void alert(Exception e)
    {
        print("ALERT", null, e);
    }

    public static void error(Object str, Exception e)
    {
        print("ERROR", str, e);
    }

    public static void error(Object str)
    {
        print("ERROR", str, null);
    }

    public static void error(Exception e)
    {
        print("ERROR", null, e);
    }
}
