package lerrain.tool.script.warlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class Wrap
{
	List values = new ArrayList();
	
	public Wrap()
	{
	}
	
	public Wrap(Object r)
	{
		if (r instanceof Wrap)
			values.addAll(((Wrap)r).values);

		values.add(r);
	}
	
	public Wrap(Object v1, Object v2)
	{
		if (v1 instanceof Wrap)
			values.addAll(((Wrap)v1).values);
		else
			values.add(v1);
		
		if (v2 instanceof Wrap)
			values.addAll(((Wrap)v2).values);
		else
			values.add(v2);
	}
	
	public List toList()
	{
		return values;
	}
	
//	public Object[] toValues(Factors p)
//	{
//		if (r == null)
//		{
//			r = new Object[values.size()];
//			int i = 0;
//			for (Iterator iter = values.iterator(); iter.hasNext();)
//			{
//				r[i++] = ((Formula)iter.next()).run(p);
//			}
//		}
//		
//		return r;
//	}
	
	public int size()
	{
		return values.size();
	}
	
//	public Formula get(int index)
//	{
//		return (Formula)values.get(index);
//	}
	
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		for (Iterator iter = values.iterator(); iter.hasNext();)
		{
			Object v = iter.next();
			s.append(v + ", ");
		}
		
		return s.toString();
	}

//	public static Object[] valueOf(Formula code, Factors factors)
//	{
//		if (code == null)
//			return new Object[0];
//		
//		Object r = code.run(factors);
//		if (r instanceof Wrap)
//			return ((Wrap)r).toValues(factors);
//		else
//			return new Object[] { r };
//	}
	
	public Object[] toArray()
	{
		return values.toArray();
	}
	
	/**
	 * 下面的已经作废，现在是都自动计算了
	 * 
	 * 如果只有一个参数，那么直接就计算了
	 * 如果是多个参数，那么直接把计算公式打包返回，不计算
	 * 
	 * @author lerrain
	 *
	 */
	public static Wrap wrapOf(Formula code, Factors p)
	{
		if (code == null)
			return new Wrap();
		
		Object r = code.run(p);
		if (r instanceof Wrap)
			return (Wrap)r;
		else
			return new Wrap(r);
	}
	
	public static Object[] arrayOf(Formula code, Factors p)
	{
		return wrapOf(code, p).toArray();
	}
	
	public static double[] doubleArrayOf(Formula code, Factors p)
	{
		Wrap wrap = wrapOf(code, p);
		
		double[] r = new double[wrap.size()];
		for (int i = 0; i < r.length; i++)
			r[i] = Value.doubleOf(wrap.values.get(i));
		
		return r;
	}
}
