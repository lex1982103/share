package lerrain.tool.script.warlock;

/**
 * 用异常来处理return、continue、break
 * 代码会比较清楚
 * （暂时先没用）
 * 
 * @author lerrain
 * @deprecated
 */
public class InterruptExc extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public static class Continue extends InterruptExc
	{
		private static final long serialVersionUID = 1L;
	}

	public static class Break extends InterruptExc
	{
		private static final long serialVersionUID = 1L;
	}

	public static class Return extends InterruptExc
	{
		private static final long serialVersionUID = 1L;
		
		Object v;
		
		public Return(Object v)
		{
			this.v = v;
		}
		
		public Object getValue()
		{
			return v;
		}
	}

	public static class Throw extends InterruptExc
	{
		private static final long serialVersionUID = 1L;
		
		Object v;
		
		public Throw(Object v)
		{
			this.v = v;
		}
		
		public Object getValue()
		{
			return v;
		}
	}
}
