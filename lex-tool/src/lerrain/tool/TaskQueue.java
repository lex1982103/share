package lerrain.tool;

import java.util.HashMap;
import java.util.Map;

public abstract class TaskQueue implements Runnable
{
	public static final int MAX	= 100000;

	Map<String, Runnable> list = new HashMap<>();

	Thread thread = new Thread(this);

	public void add(String key, Runnable runnable)
	{
		synchronized (list)
		{
			if (key == null || list.containsKey(key))
				return;

			if (list.size() < MAX)
				list.put(key, runnable);

			list.notify();
		}
	}

	public void start()
	{
		if (!thread.isAlive())
			thread.start();
	}

	public void run()
	{
		Map<String, Runnable> pack = new HashMap<>();

		while (true)
		{
			synchronized (list)
			{
				pack.putAll(list);
				list.clear();
			}

			if (pack.size() > 0)
			{
				for (Runnable runnable : pack.values())
				{
					try
					{
						runnable.run();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				try
				{
					Thread.sleep(10000);
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
