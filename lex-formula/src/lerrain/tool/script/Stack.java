package lerrain.tool.script;

import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.VariableFactors;
import lerrain.tool.script.warlock.Code;

public class Stack implements VariableFactors
{
	public static final int RUNNING				= 0;
	public static final int DEBUG_BREAK_POINT	= 1;
	public static final int DEBUG_STEP_INTO		= 2;
	public static final int DEBUG_STEP_OVER		= 3;

	public static final int EVENT_LOOP_ALERT    = 10001;
	public static final int EVENT_DYNAMIC_FUNCTION_NOT_FOUND    = 19001;

	public static int LOOP_ALERT_TIMES     	    = 0; //大于0时生效，每当循环达到这个次数的时候报警

	public static RuntimeListener runtimeListener;

	public Object ROOT; //可一直向上传递，供各层共享，不设置就是底层的factors，中间可以改

	Factors root;
	
	Map heap;

	BreakListener breakListener;

	int debug = RUNNING;

	Code code;

	public Stack()
	{
	}

	public Stack(Factors root)
	{
		this(root, null);
	}

	public Stack(Factors root, Map heap)
	{
		if (root instanceof Stack)
		{
			debug = ((Stack) root).debug;

			if (debug == DEBUG_STEP_OVER) //进了一层，既然是stepover，新的层里即直接略过
				debug = DEBUG_BREAK_POINT;

			ROOT = ((Stack) root).ROOT;
		}
		else
		{
			ROOT = root;
		}

		this.root = root;
		this.heap = heap;
	}

	public Stack(Map root)
	{
		this.heap = root;
	}
	
	public void declare(String name)
	{
		declare(name, null);
	}

	public void declare(String name, Object val)
	{
		if (heap == null)
			heap = new HashMap();

		heap.put(name, val);
	}
	
	public void set(String name, Object value)
	{
		if (heap != null && heap.containsKey(name))
			heap.put(name, value);
		else if (root instanceof VariableFactors)
			((VariableFactors)root).set(name, value);
		else
            throw new RuntimeException(name + "未定义或者不可修改");
	}
	
	public void setAll(Map map)
	{
		if (heap == null)
			heap = new HashMap();
		
		if (map != null)
			heap.putAll(map);
	}
	
	public boolean hasVar(String name)
	{
		if (heap != null && heap.containsKey(name))
			return true;
		
		if (root instanceof Stack)
			return ((Stack)root).hasVar(name);
		else
			return false;
	}
	
	public Object get(String name)
	{
		if ("this".equals(name))
			return this;
		if ("super".equals(name))
			return root;

		if (heap != null && heap.containsKey(name))
			return heap.get(name);
		
		if (root != null)
			return root.get(name);
		
		return null;
	}
	
	public void remove(String name)
	{
		if (heap != null && heap.containsKey(name))
			heap.remove(name);
	}
	
	public Factors getParent()
	{
		return root;
	}

	public Map getStackMap()
	{
		Map m1 = new HashMap();
		if (heap != null)
			m1.putAll(heap);

		if (root instanceof Stack)
			m1.put("parent", ((Stack) root).getStackMap());

		return m1;
	}

	public String toString()
	{
		return heap == null ? "{}" : heap.toString();
	}

	public Map getHeap()
	{
		return heap;
	}

	public Factors getRoot()
	{
		return root;
	}

	public int getDebug()
	{
		return debug;
	}

	public void setDebug(int debug)
	{
		this.debug = debug;
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public void setBreakListener(BreakListener breakListener)
	{
		this.breakListener = breakListener;
	}

	public BreakListener getBreakListener()
	{
		if (breakListener == null && root instanceof Stack)
			return ((Stack)root).getBreakListener();

		return breakListener;
	}

	public interface BreakListener
	{
		public void onBreak(Code code, Stack s);

		/**
		 * return语句时触发，主要用于定位return的位置
		 * 没有return正常执行结束以最后一行的值返回的情况下不会触发
		 */
		public void onReturn(Code code, Stack s, Object val);
	}

	public interface StackFactory
	{
		Stack newStack(Factors parent, Map heap);
	}

	public static StackFactory STACK_FACTORY;

	public static Stack newStack(Factors parent, Map heap)
	{
		if (STACK_FACTORY == null)
			return new Stack(parent, heap);
		else
			return STACK_FACTORY.newStack(parent, heap);
	}

	public static Stack newStack(Factors parent)
	{
		if (STACK_FACTORY == null)
			return new Stack(parent, null);
		else
			return STACK_FACTORY.newStack(parent, null);
	}
}
