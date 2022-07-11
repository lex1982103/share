package lerrain.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test
{
	static Random ran = new Random();

	public static void main(String[] s) throws Exception
	{
		Map m1 = new HashMap<>();
		for (int i=0;i<10000;i++)
			m1.put(i, i);

		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				long t1 = System.currentTimeMillis();
				for (int i=10000;i<100000;i++)
					m1.get(ran.nextInt(10000));

				System.out.println(System.currentTimeMillis() - t1);
			}
		};

		for (int i=0;i<100;++i)
		{
			Thread th = new Thread(r);
			th.start();
		}

		Thread th = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long t1 = System.currentTimeMillis();
				for (int i=10000;i<100000;i++)
					m1.put(ran.nextInt(10000), i);

				System.out.println("T:" + (System.currentTimeMillis() - t1));
			}
		});

		th.start();
	}

	public static void main2(String[] s) throws Exception
	{
		int[] p = new int[100];

		int ts = 1000;
		int zz = 0;
		for (int z = 0; z < ts; z++)
		{
			for (int i = 0; i < 100; i++)
				p[i] = i;
			for (int i = 0; i < 100; i++)
			{
				int j = ran.nextInt(100 - i) + i;
				int y = p[i];
				p[i] = p[j];
				p[j] = y;
			}

			if (t(p))
				++zz;
		}

		System.out.println(zz * 100 / ts + "%");
	}

	private static boolean t(int[] p)
	{
		for (int i=0;i<1;i++)
		{
			int m = i;
			int k;
			for (k = 0; k < 50; k++)
			{
				if (p[m] == i)
					break;
				m = p[m];
			}

			if (k >= 50)
				return false;
		}

		return true;
	}
}
