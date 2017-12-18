package lerrain.project.insurance.product;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;

/**
 * 对于公式模拟数组的缓冲处理，主要是针对递归形式。
 * 递归形式会有大量重复计算，加上缓冲可以避免，同时正序计算时也可以避免递归层数过深，低版本android下递归层数200即出错，这个是必要的。
 * 如果没有大量递归，效率提升有限，也不会有低版本android下深层递归的溢出问题，没有必要开缓冲。
 * @author lerrain
 */
public class VariableArray implements Function, Serializable
{
	private static final long serialVersionUID = 1L;

	Map temp;
	
	Formula f;
	String[] param;
	
	Stack p;
	
	/*
	 * 假如由程序自己设定了factors，那么公式引擎传入的factors将不会起作用
	 * 这个是处理PARENT.IT.ABC(A1,A2)这种运算的，直接这样写无法将参数表换为主险的。
	 * 比如PARENT.IT.ABC得到的函数为PREMIUM*1.05，这个公式将会用附加险的参数表计算，得到的结果不是期望值
	 * 函数写为IT.ABC(A1,A2)，设定factors为主险来处理这个问题
	 */
	Factors factors; 
	
	public VariableArray(Formula f, String[] param)
	{
		this.f = f;
		this.param = param;
	}
	
	public VariableArray(Formula f, String[] param, Factors factors, boolean cached)
	{
		this.f = f;
		this.param = param;
		this.factors = factors;
		
		if (cached)
			temp = new HashMap();
	}

	public Object run(Object[] v, Factors factors)
	{
		if (this.factors != null)
			factors = this.factors;
		
		p = new Stack(factors);

		if (temp == null)
		{
			for (int i = 0; i < param.length && v != null && i < v.length; i++)
				p.declare(param[i], v[i]);
			
			return f.run(p);
		}
		else
		{
			Object r = null;
			
			long c = 0;
			for (int i = 0; i < param.length && v != null && i < v.length; i++)
				c = c * 1000 + Value.valueOf(v[i]).intValue();
			
			Long key = new Long(c);
				
			r = temp.get(key);
			if (r == null)
			{
				for (int i = 0; i < param.length; i++)
					p.declare(param[i], v[i]); //必须要声明一下，不然数组嵌套的时候会把上一级的数组的同名值（通常是A1,A2）给改了

				r = f.run(p);
				temp.put(key, r);
			}
			
			return r;
		}
		
	}
	
//	public Object run(Factors factors)
//	{
//		int num = Value.valueOf(factors.get("#0")).intValue();
//		Object[] v = new Object[num];
//		for (int i=0;i<num;i++)
//			v[i] = factors.get("#" + (i + 1));
//		
//		return run(v, factors);
//	}
}