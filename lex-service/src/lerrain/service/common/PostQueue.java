package lerrain.service.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostQueue implements Runnable
{
    int max;

    ServiceMgr serviceMgr;
    String service, uri;

    List msg = new ArrayList();
    List retry = new ArrayList();

    Thread thread;

    public void initiate(ServiceMgr serviceMgr, int max, String service, String uri)
    {
        this.serviceMgr = serviceMgr;
        this.max = max;
        this.service = service;
        this.uri = uri;
    }

    public void addMsg(Map v)
    {
        synchronized (msg)
        {
            if (msg.size() > max)
            {
                msg.clear();
                Log.error("发送至" + service + "/" + uri + "时过多数据阻塞，已被清空");
            }

            msg.add(v);
            msg.notify();
        }
    }

    public void start()
    {
        if (thread != null)
            thread.interrupt();

        thread = new Thread(this);
        thread.start();
    }

    public Thread getThread()
    {
        return thread;
    }

    public void stop()
    {
        thread.interrupt();
    }

    public void run()
    {
        while (true)
        {
            List list = new ArrayList();

            synchronized (msg)
            {
                if (!msg.isEmpty())
                {
                    list.addAll(msg);
                    msg.clear();
                }
            }

            if (list.size() > 0)
            {
                try
                {
                    serviceMgr.req(service, uri, list, null);
                }
                catch (Exception e)
                {
                    if (e instanceof IOException || e.getCause() instanceof IOException) //IOException才会被重试
                        store(list);
                    else
                        Log.error(e);
                }
            }

            try
            {
                Thread.sleep(100);

                synchronized (msg)
                {
                    if (msg.isEmpty())
                        msg.wait();
                }
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }

    private void store(List more)
    {
        boolean invoke;

        synchronized (retry)
        {
            invoke = retry.isEmpty();

            if (retry.size() > max)
                retry.clear();

            retry.addAll(more);
        }

        if (invoke)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        retry(10000, 60000, 600000, 1800000);
                    }
                    catch (Exception e)
                    {
                        Log.alert(e.getMessage());
                    }
                }

            }).start();
        }
    }

    private void retry(int... sleep) throws InterruptedException
    {
        if (sleep.length > 0)
        {
            Log.info("等待" + sleep[0] + "ms继续发送");

            try
            {
                Thread.sleep(sleep[0]);
            }
            catch (InterruptedException i3)
            {
                throw i3;
            }

            int[] ns = new int[sleep.length - 1];
            for (int i = 0; i < ns.length; i++)
                ns[i] = sleep[i + 1];

            try
            {
                synchronized (retry)
                {
                    serviceMgr.req(service, uri, retry, null);
                    retry.clear();
                }
            }
            catch (Exception e3)
            {
                Log.error("发送至" + uri + "服务失败 -> ", e3);

                retry(ns);
            }
        }
    }
}