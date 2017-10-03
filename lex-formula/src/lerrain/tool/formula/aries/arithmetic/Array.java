package lerrain.tool.formula.aries.arithmetic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.formula.aries.AssignableFactors;

public class Array implements Formula
{
	Formula arrayValue;
	
	List pos;
	
	public Array(Formula arrayValue, List pos)
	{
		this.arrayValue = arrayValue;
		this.pos = pos;
	}
	
	public int getArraySize()
	{
		return pos.size();
	}
	
	public Formula getArrayIndex(int i)
	{
		return (Formula)pos.get(i);
	}
	
	public Formula getArrayValue()
	{
		return arrayValue;
	}

	public Object run(Factors getter)
	{
		int s = getArraySize();
		
		if (s > 3)
			throw new CalculateException("数组取值只支持一维、二维、三维数组。");
		
//		IProcessor array = null;
//		try
//		{
//			array = (IFormula)arrayValue.getResult(varSet).getValue();//vars.getValue(variableName);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		Formula array = (Formula)arrayValue;
		
		if (s == 3)
		{
			Formula s1 = (Formula)pos.get(0);
			Formula s2 = (Formula)pos.get(1);
			Formula s3 = (Formula)pos.get(2);
			int l1 = Value.valueOf(s1, getter).intValue();
			int l2 = Value.valueOf(s2, getter).intValue();
			int l3 = Value.valueOf(s3, getter).intValue();

			AssignableFactors vsp = new AssignableFactors(getter);
			vsp.set("A1", new Integer(l1));
			vsp.set("A2", new Integer(l2));
			vsp.set("A3", new Integer(l3));

			Object obj = array.run(vsp);
			if (obj instanceof Function)
			{
				return ((Function)obj).run(new Object[] {new Integer(l1), new Integer(l2), new Integer(l3)}, getter);
			}
			else if (obj instanceof BigDecimal)
			{
				return obj;
			}
			
//			System.out.println("ARRAY:" + obj);
//			System.out.println("A1:" + l1);
//			System.out.println("A2:" + l2);
//			System.out.println("A3:" + l3);
		}
		else if (s == 2)
		{
			Formula s1 = (Formula)pos.get(0);
			Formula s2 = (Formula)pos.get(1);
			int l1 = Value.valueOf(s1, getter).intValue();
			int l2 = Value.valueOf(s2, getter).intValue();

			AssignableFactors vsp = new AssignableFactors(getter);
			vsp.set("A1", new Integer(l1));
			vsp.set("A2", new Integer(l2));

			Object obj = array.run(vsp);
			if (obj instanceof Function)
			{
				return ((Function)obj).run(new Object[] {new Integer(l1), new Integer(l2)}, getter);
			}
			else if (obj instanceof BigDecimal)
			{
				return obj;
			}
			//以后考虑弃用下面这种一次成型的传统办法
			//2011/06/12
			//还不能取消这种方式，查询费率出来的一般都是一次成型的2维数组
			//2011/09/15
			else if (obj instanceof BigDecimal[][])
			{
				BigDecimal v = ((BigDecimal[][])obj)[l1][l2];
				return v;
			}
			else if (obj instanceof int[][])
			{
				int v = ((int[][])obj)[l1][l2];
				return new Integer((int)v);
			}
			else if (obj instanceof double[][])
			{
				double v = ((double[][])obj)[l1][l2];
				return new Double((double)v);
			}
			
			System.out.println("ARRAY:" + obj);
			System.out.println("A1:" + l1);
			System.out.println("A2:" + l2);
		}
		else
		{
			Formula s1 = (Formula)pos.get(0);
			int l1 = Value.valueOf(s1, getter).intValue();
			
			AssignableFactors vsp = new AssignableFactors(getter);
			vsp.set("A1", new Integer(l1));

			Value value = Value.valueOf(array, vsp);
			Object obj = value.getValue();
			if (obj instanceof Function)
			{
				return ((Function)obj).run(new Object[] {new Integer(l1)}, getter);
			}
			else if (value.isDecimal())
			{
				return value;
			}
			/**
			 * 这个BigDecimal[][]是处理新华百年好合时增加的一个处理。
			 * 这里的2维数组实际上是通过A1计算出来的，也就是说他已经取过一次数组了，而不是一个现成的2维数组，从中取A1相对的一个1维数组。
			 * 实际上这里是一个3维数组，按照A1的值取出的一个2维数组，与下面那些情况不同，所以可以整体返回。
			 * 所以这里是有问题的，如果现成的一个2维数组中取A1对应的1维数组就和这个完全冲突了，尚没有很好地解决办法。
			 * 不过目前不会导致问题，因为还没有从2维数组中取1维数组的情况。
			 * 2011/09/15 李新豪
			 */
			else if (obj instanceof BigDecimal[][])
			{
				return obj;
			}
			else if (obj instanceof BigDecimal[])
			{
				return (((BigDecimal[])obj)[l1]);
			}
			else if (obj instanceof Map[])
			{
				return ((Map[])obj)[l1];
			}
			else if (obj instanceof String[])
			{
				return ((String[])obj)[l1];
			}
			else if (obj instanceof List)
			{
				return ((List)obj).get(l1);
			}
			else if (obj instanceof int[])
			{
				return new Integer((int)(((int[])obj)[l1]));
			}
			else if (obj instanceof Object[])
			{
				return ((Object[])obj)[l1];
			}
			
			System.out.println("ARRAY:" + obj);
			System.out.println("A1:" + l1);
		}
		
		throw new CalculateException("数组取值参数有误。factors - " + getter.toString());
	}

	public String toText()
	{
		return "数组运算";
	}
}
