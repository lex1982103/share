package lerrain.tool.script.warlock;

public class Interrupt extends RuntimeException
{
	public static class Break extends Interrupt
	{
		int l;

		public Break(int l)
		{
			this.l = l;
		}

		public int popOut()
		{
			return --l;
		}
	}

	public static class Continue extends Interrupt
	{
	}

	public static class Return extends Interrupt
	{
		Object value;

		public Return(Object value)
		{
			this.value = value;
		}

		public Object getValue()
		{
			return value;
		}

		public void setValue(Object value)
		{
			this.value = value;
		}

	}
}
