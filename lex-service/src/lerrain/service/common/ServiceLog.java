package lerrain.service.common;

import lerrain.tool.Common;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceLog
{
    public static boolean OUT_SYSTEM = false;
    public static boolean OUT_FILE = true;

    @Value("${path.log:null}")
    String logPath;

    @Value("${sys.code:null}")
    String serviceCode;

    @Value("${sys.env:null}")
    String env;

    LogOutputStream logOut = new LogOutputStream();

    @PostConstruct
    public synchronized PrintStream start()
    {
        if (logOut.ps != null)
            throw new RuntimeException("already running!");

        System.out.println("intercept system.out, see service log");
        logOut.ps = System.out;

        System.setOut(new PrintStream(logOut));
        logOut.start();

        return logOut.ps;
    }

    public synchronized void stop()
    {
        System.setOut(logOut.ps);
        logOut.ps = null;

        System.out.println("restore system.out");

        logOut.stop();
    }

    public void setOnMessageListener(onMessageListener listener)
    {
        logOut.listener = listener;
    }

    public static interface onMessageListener
    {
        public void onMessage(byte b[], int off, int len);
    }

    private class LogInfo
    {
        byte[] buf;

        int off;
        int len;
    }

    public class LogOutputStream extends OutputStream implements Runnable
    {
        PrintStream ps;

        String path;

        onMessageListener listener;

        Thread thread = new Thread(this);

        List<LogInfo> list = new ArrayList<>();

        public LogOutputStream()
        {
        }

        public void write(int b)
        {
            write(new byte[] {(byte)b}, 0, 1);
        }

        public void write(byte b[], int off, int len)
        {
            if (OUT_SYSTEM && ps != null)
                ps.write(b, off, len);

            synchronized (list)
            {
                if (list.size() > 1000000)
                    list.clear();

                LogInfo log = new LogInfo();
                log.buf = b.clone();
                log.off = off;
                log.len = len;

                list.add(log);
                list.notify();
            }
        }

        public void start()
        {
            if (serviceCode == null)
                serviceCode = "system";

            if (env == null)
                env = "unknown";

            path = Common.pathOf(logPath, serviceCode + "-" + env);

            if (!thread.isAlive())
                thread.start();

            System.out.println("start logging...");
        }

        public void stop()
        {
            thread.interrupt();
        }

        public void run()
        {
            List<LogInfo> pack = new ArrayList<>();

            while (true)
            {
                synchronized (list)
                {
                    pack.addAll(list);
                    list.clear();
                }

                if (pack.size() > 0)
                {
                    if (OUT_FILE)
                    {
                        try (FileOutputStream logFile = new FileOutputStream(path + "-" + Common.getString(new Date()) + ".log", true))
                        {
                            for (LogInfo inf : pack)
                            {
                                logFile.write(inf.buf, inf.off, inf.len);
                                if (listener != null)
                                    listener.onMessage(inf.buf, inf.off, inf.len);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    if (listener != null)
                    {
                        try
                        {
                            for (LogInfo inf : pack)
                            {
                                listener.onMessage(inf.buf, inf.off, inf.len);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                    }
                }
                else synchronized (list)
                {
                    try
                    {
                        if (list.isEmpty())
                            list.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                pack.clear();
            }
        }
    }
}
