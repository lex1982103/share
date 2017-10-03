package lerrain.project.insurance.product.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Value;


public class Rule implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public final static int LEVEL_ALERT 		= 101;				//规则不通过时仅提示警告
	public final static int LEVEL_FAIL 			= 102;				//规则不通过时产品在当前参数选择下禁止购买

	public final static int TYPE_CUSTOMER		= 1;				//
	public final static int TYPE_PRODUCT		= 2;				//
	public final static int TYPE_PLAN			= 3;				//
	public final static int TYPE_PROPOSAL		= 4;				//
	public final static int TYPE_OUTSIDE		= 5;				//

	public final static int EXCEPT_FAIL			= 1;				//
	public final static int EXCEPT_ALERT		= 2;				//
	public final static int EXCEPT_PASS			= 3;				//

	String id;
	String desc;													//错误提示信息
	Formula descFormula;
	
	List alertList;													//出错元素提示

	Formula condition;												//规则运算公式

	int level									= LEVEL_FAIL;		//错误等级，默认为LEVEL_FAIL
	int type									= TYPE_PRODUCT;
	int except									= EXCEPT_FAIL;

	public Rule isCatched(Factors p)
	{
		Rule r = null;
		
		try
		{
			Object v = condition.run(p);
			if (v != null)
			{
				if (v instanceof String) //返回null为通过，返回String为不通过
				{
					r = copyOf((String)v);
				}
				else if (Value.booleanOf(v))
				{
					r = copyOf(descFormula != null ? Value.stringOf(descFormula, p) : desc);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(this.getCondition() + " - " + e.getMessage());
			e.printStackTrace();

			//规则计算出现异常，视为不过
			r = copyOf(descFormula != null ? Value.stringOf(descFormula, p) : desc);
		}
		
		return r;
	}
	
	private Rule copyOf(String text)
	{
		Rule r = new Rule();
		r.condition = condition;
		r.level = level;
		r.type = type;
		r.except = except;
		r.alertList = alertList;
		r.desc = text;
		
		return r;
	}
	
	public List getAlert()
	{
		return alertList;
	}

	public void addAlert(String alert)
	{
		if (alertList == null)
			alertList = new ArrayList();
		
		alertList.add(alert);
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	
	public void setDesc(Formula desc)
	{
		this.descFormula = desc;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
	
	public void setCondition(String condition)
	{
		setCondition(FormulaUtil.formulaOf(condition));
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * 当规则条件计算异常时的处理方案
	 * @param except
	 * Rule.EXCEPT_FAIL 视为不通过
	 * Rule.EXCEPT_ALERT 仅提示
	 * Rule.EXCEPT_PASS 视为通过
	 */
	public void setExcept(int except)
	{
		this.except = except;
	}
}
