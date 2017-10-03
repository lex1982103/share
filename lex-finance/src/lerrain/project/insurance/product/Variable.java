package lerrain.project.insurance.product;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;

/**
 * 不考虑在变量层做缓冲，这样过于离散，清除缓冲时非常麻烦。
 * 缓冲统一在参数表中做。
 * 
 * @author lerrain
 *
 */
public class Variable implements Formula
{
	String name;
	
	Formula formula;
//	String reference;
	
	String[] param;
	
	String desc;
	
	boolean cache = false;
	
	public Variable(String name, Formula formula, String[] param, boolean cache, String desc)
	{
		this.name = name;
		this.formula = formula;
		this.param = param;
		this.desc = desc;
		this.cache = cache;
	}
	
//	public Variable(String name, String reference, String[] param, String desc)
//	{
//		this.name = name;
//		this.reference = reference;
//		this.param = param;
//		this.desc = desc;
//	}

	public String getName()
	{
		return name;
	}

	public Formula getFormula()
	{
		return formula;
	}
	
//	public String getReference()
//	{
//		return reference;
//	}

	public String[] getParam()
	{
		return param;
	}

	public Object run(Factors factors)
	{
		if (param != null)
		{
			/*
			 * 每次new一个，带上factors固定参数环境
			 * 
			 * IT.ABC(A1,A2)，返回函数指针IT.ABC的值，由WL完成函数计算
			 * 如果设定了参数表，那么就用设定的参数表计算，不再使用当前产品的参数表
			 * 
			 * 之所以这么做，是因为这里无法返回计算结果，这里公式引擎以规范的角度考虑，只询问表达式的计算结果（其结果是函数指针）
			 * 也就是说公式引擎认为这里只是计算函数的指针（IT.ABC的值），而不是计算整个函数（IT.ABC(A1,A2)的值）。
			 * 因此不会在此将函数的参数（A1,A2）传入，也就是这里无法自己完成计算然后返回结果，只能把函数指针传回，由公式引擎计算。
			 * 公式引擎计算的时候还会引用本次计算的全局环境（在finance中，是有多个全局环境并存的，计划的每个产品都有一个）
			 * 如果是附加险通过PARENT.IT.ABC(A1,A2)的方式调用，那么就会使用附加险的全局环境计算这个函数，结果就错了。
			 * 
			 * 如果约定特别参数如_A1、_A2等，在这里运算也是能解决这个问题的，但是这种做法会导致公式引擎本身的不规范。
			 */
			return new VariableArray(formula, param, factors, cache);
		}
		else
		{
			//IT.ABC，直接计算结果返回，返回结果为Formula时，POINTKEY运算并不会自动计算他的值。
			return this.getFormula().run(factors);
		}
	}
}
