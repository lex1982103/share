package lerrain.tool;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class TaskQueue implements Runnable
{
	public static final int MAX	= 100000;

	Map<String, Runnable> list = new LinkedHashMap<>();

	Thread thread = new Thread(this);

	public void add(Runnable runnable)
	{
		add(null, runnable);
	}

	public void add(String key, Runnable runnable)
	{
		if (key == null)
			key = UUID.randomUUID().toString();

		synchronized (list)
		{
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
		Map<String, Runnable> pack = new LinkedHashMap<>();

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
