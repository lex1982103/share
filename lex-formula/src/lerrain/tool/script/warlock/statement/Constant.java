package lerrain.tool.script.warlock.statement;

import lerrain.tool.formula.Factors;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;

public class Constant extends Code
{
	Object v;

	public Constant(Words ws)
	{
		super(ws);

		int type = ws.getType(0);
		String text = ws.getWord(0);

		if (type == Words.NULL)
		{
			v = null;
		}
		else if (type == Words.TRUE)
		{
			v = Boolean.TRUE;
		}
		else if (type == Words.FALSE)
		{
			v = Boolean.FALSE;
		}
		else if (type == Words.NUMBER)
		{
			if (text.indexOf(".") >= 0)
			{
				v = new BigDecimal(text);
			}
			else
			{
				try
				{
					long v1 = Long.valueOf(text);
					if (v1 >= Integer.MIN_VALUE && v1 <= Integer.MAX_VALUE)
						v = (int)v1;
					else
						v = v1;
				}
				catch (Exception e)
				{
					v = new BigDecimal(text);
				}
			}
		}
		else if (type == Words.STRING)
		{
			v = text.substring(1, text.length() - 1);
		}
		else
		{
			throw new RuntimeException("无法识别的常量：" + v);
		}

//		if (Script.compileListener != null)
//			Script.compileListener.compile(CompileListener.CONST, this);
	}

	/**
	 * 用于替换fixed的表达式
	 * @param ws
	 * @param v
	 * @return
	 */
	public Constant(Words ws, Object v)
	{
		super(ws);
		this.v = v;
	}
	
	public Object run(Factors factors)
	{
		return v;
	}

	public String toText(String space, boolean line)
	{
		if (v == null)
			return "NULL";
		
		return v instanceof String ? "\"" + v + "\"" : v.toString();
	}

	public Object getValue()
	{
		return v;
	}
	
	public String toString()
	{
		return v == null ? null : v.toString();
	}

//	public double calculate(Factors p)
//	{
//		return vd;
//	}

	@Override
	public boolean isFixed(int mode)
	{
		return true;
	}
}
