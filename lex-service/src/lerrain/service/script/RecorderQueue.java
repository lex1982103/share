package lerrain.service.script;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.service.common.ServiceMgr;

import java.util.ArrayList;
import java.util.List;

public class RecorderQueue implements Runnable
{
    public static final int MAX	= 10000;

    ServiceMgr sv;

    List msg = new ArrayList();

    Thread thread;

    public RecorderQueue(ServiceMgr sv)
    {
        this.sv = sv;
    }

    public void add(JSONObject v)
    {
        synchronized (msg)
        {
            if (msg.size() > MAX)
                msg.clear();

            msg.add(v);
            msg.notify();
        }
    }

    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    public void stop()
    {
        thread.interrupt();
    }

    public void run()
    {
        while (true)
        {
            JSONArray list = new JSONArray();

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
                send(list, 10000, 60000);
            }
            else synchronized (msg)
            {
                try
                {
                    if (msg.isEmpty())
                        msg.wait();
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    private void send(JSON req, int... sleep)
    {
        try
        {
            sv.req("develop", "script/record.json", req);
        }
        catch (Exception e3)
        {
            if (sleep.length > 0)
            {
                try
                {
                    Thread.sleep(sleep[0]);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }

                int[] ns = new int[sleep.length - 1];
                for (int i = 0; i < ns.length; i++)
                    ns[i] = sleep[i + 1];

                send(req, ns);
            }
        }
    }
}