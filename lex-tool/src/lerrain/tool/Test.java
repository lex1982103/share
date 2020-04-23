package lerrain.tool;

import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void test()
    {
        Thread th = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.out.println("XXXXX");
                }
            }
        });


        th.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        th.interrupt();
    }

    public static void main(String[] s)
    {
        test();


//        Network.request("http://iyunbao-broker-iyb-facilities.test.za-tech.net/open/neptuneDisable/user/queryProduct?accountId=1");


       System.out.println ((int)(System.currentTimeMillis() - 3600000*2 - 0));

        System.out.println("2017/6\\\\9".replaceAll("[\\\\][\\\\]", "\\\\"));
        System.out.println(Common.dateOf("2017-6-9"));
        System.out.println(Common.dateOf("2017/6/9"));


        Object r = new Object[] {3, 5, 7};

        Object l = new ArrayList();
        ((List)l).add("ss");
        ((List)l).add("zzz");

        Object[] o1, o2;

        if (l instanceof List)
            o1 = ((List)l).toArray();
        else
            o1 = (Object[])l;

        if (r instanceof List)
            o2 = ((List)r).toArray();
        else
            o2 = (Object[])r;

        Object[] o3 = new Object[o1.length + o2.length];
        for (int i = 0; i < o1.length; i++)
            o3[i] = o1[i];
        for (int i = 0; i < o2.length; i++)
            o3[i + o1.length] = o2[i];

        System.arraycopy(o1, 0, o3, 0, o1.length);
        System.arraycopy(o2, 0, o3, o1.length, o2.length);

        for (int i=0;i<o3.length;i++)
            System.out.println(o3[i]);
    }
}
