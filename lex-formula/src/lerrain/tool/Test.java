package lerrain.tool;

import java.util.Random;

public class Test
{
	static Random ran = new Random();

	public static void main(String[] s) throws Exception
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
