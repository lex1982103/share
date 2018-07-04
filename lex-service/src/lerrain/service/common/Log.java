package lerrain.service.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lerrain on 2017/8/2.
 */
public class Log
{
    public static boolean EXCEPTION_STACK = true;

    static String LOG_DIR = "log/";

    static boolean write = false;

    static boolean write_info = true;
    static boolean write_debug = true;
    static boolean write_alert = true;
    static boolean write_error = true;

    static String fmt = "%tF %tT <%s> %s.%s: %s";

    static List<String> buffer = new ArrayList<>();

    public static String getLogFile(long time)
    {
        return LOG_DIR + String.format("%tF", time) + ".log";
    }

    /**
     * 设置日志输出级别
     * @param levels 从（info,debug,alert,error）中选择一个或多个日志输出级别','(英文逗号)隔开，为空则关闭所有
     */
    public static void resetWriteLevel(String levels)
    {
        if(levels == null || "".equals(levels.trim()))
        {
            write_alert = false;
            write_debug = false;
            write_error = false;
            write_info = false;
        }
        else
        {
            levels = ","+levels.replaceAll(" ","").toLowerCase()+",";
            write_alert = levels.indexOf(",alert,") >= 0;
            write_debug = levels.indexOf(",debug,") >= 0;
            write_info = levels.indexOf(",info,") >= 0;
            write_error = levels.indexOf(",error,") >= 0;
        }
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
        if (e != null)
        {
            if (EXCEPTION_STACK)
            {
                try (ByteArrayOutputStream os = new ByteArrayOutputStream())
                {
                    PrintWriter pw = new PrintWriter(os);
                    e.printStackTrace(pw);
                    pw.close();

                    str = (str == null ? "" : str) + "\n" + os.toString();
                }
                catch (Exception e1)
                {
                }
            }
            else
            {
                str = (str == null ? "" : str) + "\n" + e.toString();
            }
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

    public static void info(String fmt, Object... obj)
    {
        if(write_info)
            print("INFO", String.format(fmt, obj), null);
    }

    public static void info(Object str, Exception e)
    {
        if(write_info)
            print("INFO", str, e);
    }

    public static void info(Object str)
    {
        if(write_info)
            print("INFO", str, null);
    }

    public static void info(Exception e)
    {
        if(write_info)
            print("INFO", null, e);
    }

    public static void debug(String fmt, Object... obj)
    {
        if(write_debug)
            print("DEBUG", String.format(fmt, obj), null);
    }

    public static void debug(Object str, Exception e)
    {
        if(write_debug)
            print("DEBUG", str, e);
    }

    public static void debug(Object str)
    {
        if(write_debug)
            print("DEBUG", str, null);
    }

    public static void debug(Exception e)
    {
        if(write_debug)
            print("DEBUG", null, e);
    }

    public static void alert(String fmt, Object... obj)
    {
        if(write_alert)
            print("ALERT", String.format(fmt, obj), null);
    }

    public static void alert(Object str, Exception e)
    {
        if(write_alert)
            print("ALERT", str, e);
    }

    public static void alert(Object str)
    {
        if(write_alert)
            print("ALERT", str, null);
    }

    public static void alert(Exception e)
    {
        if(write_alert)
            print("ALERT", null, e);
    }

    public static void error(String fmt, Object... obj)
    {
        if(write_error)
            print("ERROR", String.format(fmt, obj), null);
    }

    public static void error(Object str, Exception e)
    {
        if(write_error)
            print("ERROR", str, e);
    }

    public static void error(Object str)
    {
        if(write_error)
            print("ERROR", str, null);
    }

    public static void error(Exception e)
    {
        if(write_error)
            print("ERROR", null, e);
    }

    public static void stat(String str)
    {
        print("STAT", str, null);
    }
}
