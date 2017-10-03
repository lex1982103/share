package lerrain.tool.formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Value implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public final static int TYPE_NULL		= 0;
	public final static int TYPE_DECIMAL	= 1;
	public final static int TYPE_STRING		= 2;
	public final static int TYPE_DATE		= 3;
	public final static int TYPE_LIST		= 4;
	public final static int TYPE_MAP		= 5;
	public final static int TYPE_ARRAY		= 6;
	public final static int TYPE_BOOLEAN	= 7;
	public final static int TYPE_FACTORS		= 90;
	public final static int TYPE_OBJECT		= 100;
	
	int type;
	
	Object v;
	
	public Value(int v)
	{
		type = TYPE_DECIMAL;
		this.v = Integer.valueOf(v);
	}
	
	public Value(float v)
	{
		type = TYPE_DECIMAL;
		this.v = Float.valueOf(v);
	}
	
	public Value(double v)
	{
		type = TYPE_DECIMAL;
		this.v = Double.valueOf(v);
	}
	
	public Value(boolean v)
	{
		this(Boolean.valueOf(v));
	}
	
	public Value(Object v)
	{
		if (v == null)
		{
			type = TYPE_NULL;
		}
		else if (v instanceof BigDecimal)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof BigInteger)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof Integer)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof Float)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof Double)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof Long)
		{
			type = TYPE_DECIMAL;
		}
		else if (v instanceof Boolean)
		{
			type = TYPE_BOOLEAN;
		}
		else if (v instanceof List)
		{
			type = TYPE_LIST;
		}
		else if (v instanceof BigDecimal[][])
		{
			type = TYPE_ARRAY;
		}
		else if (v instanceof BigDecimal[])
		{
			type = TYPE_ARRAY;
		}
		else if (v instanceof BigInteger[][])
		{
			type = TYPE_ARRAY;
		}
		else if (v instanceof BigInteger[])
		{
			type = TYPE_ARRAY;
		}
		else if (v instanceof Map)
		{
			type = TYPE_MAP;
		}
		else if (v instanceof String)
		{
			type = TYPE_STRING;
		}
		else if (v instanceof Date)
		{
			type = TYPE_DATE;
		}
		else if (v instanceof Factors)
		{
			type = TYPE_FACTORS;
		}
		else
		{
			type = TYPE_OBJECT;
		}
		
		this.v = v;
	}
	
	public boolean isType(int type)
	{
		return this.type == type;
	}
	
	public boolean isFactors()
	{
		return isType(TYPE_FACTORS);
	}
	
	public boolean isBoolean()
	{
		return isType(TYPE_BOOLEAN);
	}
	
	public boolean isString()
	{
		return isType(TYPE_STRING);
	}
	
	public boolean isDecimal()
	{
		return isType(TYPE_DECIMAL);
	}
	
	public boolean isMap()
	{
		return isType(TYPE_MAP);
	}
	
	public BigDecimal toDecimal()
	{
		return decimalOf(v); 
	}
	
	public Factors toFactors()
	{
		return (Factors)v;
	}
	
	public Map toMap()
	{
		return (Map)v;
	}
	
	public boolean isNull()
	{
		return v == null;
	}
	
	public Object getValue()
	{
		return v;
	}
	
	public String toString()
	{
		return v == null ? null : v.toString();
	}
	
	public int intValue()
	{
		return intOf(v);
	}

	public long longValue()
	{
		return longOf(v);
	}

	/**
	 * @deprecated
	 * @param val
	 * @param i
	 * @return
	 */
	public static double doubleOf(Object val, int i)
	{
		if (val instanceof int[]) //1维数组
		{
			return ((int[])val)[i];
		}
		else if (val instanceof double[]) //1维数组
		{
			return ((double[])val)[i];
		}
		else if (val instanceof Object[])
		{
			return doubleOf(((Object[])val)[i]);
		}
		else if (val instanceof List)
		{
			return doubleOf(((List)val).get(i));
		}
		
		throw new RuntimeException(val + "无法识别为一维数字数组");
	}
	
	/**
	 * @deprecated
	 * @param val
	 * @param i
	 * @param j
	 * @return
	 */
	public static double doubleOf(Object val, int i, int j)
	{
		if (val instanceof int[][])
		{
			return ((int[][])val)[i][j];
		}
		else if (val instanceof double[][])
		{
			return ((double[][])val)[i][j];
		}
		else if (val instanceof Object[][])
		{
			return doubleOf(((Object[][])val)[i][j]);
		}
		else if (val instanceof Object[])
		{
			val = ((Object[])val)[i];
			return doubleOf(val, j);
		}
		else if (val instanceof List)
		{
			val = ((List)val).get(i);
			return doubleOf(((List)val).get(i));
		}
		
		throw new RuntimeException(val + "无法识别为二维数字数组");
	}

	public static int intOf(Object v, int def)
	{
		if (v == null)
			return def;

		if (v instanceof Number)
			return ((Number)v).intValue();

		try
		{
			return Integer.parseInt(v.toString());
		}
		catch (Exception e)
		{
			return def;
		}
	}

	public static int intOf(Object v)
	{
		if (v instanceof Number)
			return ((Number)v).intValue();

		return Integer.parseInt(v.toString());
	}

	public static long longOf(Object v)
	{
		if (v instanceof Number)
			return ((Number)v).longValue();

		return Long.parseLong(v.toString());
	}

	public double doubleValue()
	{
		return doubleOf(v);
	}
	
//	public boolean isEqualTo(double v)
//	{
//		return this.toDecimal().doubleValue() == v;
//	}
	
//	public static double round(Object v, int scale)
//	{
//		return round(doubleOf(v), scale);
//	}
//	
//	public static double round(double v, int scale)
//	{
//		return BigDecimal.valueOf(v + 0.00000001f).setScale(scale, RoundingMode.HALF_UP).doubleValue();
//	}
	
	public static double doubleOf(Object v)
	{
		if (v instanceof Number)
			return ((Number)v).doubleValue();
		else
			return Double.parseDouble(v.toString());
	}
	
	public boolean booleanValue()
	{
		return booleanOf(v);
	}
	
	public static boolean booleanOf(Object v)
	{
		if (v instanceof Boolean)
			return ((Boolean)v).booleanValue();
		else
			return intOf(v) != 0;
	}
	
	public boolean isEqualTo(Object v)
	{
		return equals(new Value(v));
	}
	
	public boolean equals(Value v)
	{
		if (this.isDecimal() && v.isDecimal())
		{
			return this.toDecimal().compareTo(v.toDecimal()) == 0;
		}
		else //if (this.isType(TYPE_STRING) && v.isType(TYPE_STRING))
		{
			return this.getValue().equals(v.getValue());
		}
		
		//return false;
	}

	public int getType()
	{
		return type;
	}
	
	public static BigDecimal decimalOf(Object v)
	{
		if (v instanceof BigDecimal)
			return (BigDecimal)v;
		else if (v instanceof Integer)
			return decimalOf(((Integer)v).intValue());
		else if (v instanceof Float)
			return decimalOf(((Float)v).floatValue());
		else if (v instanceof Double)
			return decimalOf(((Double)v).doubleValue());
		else if (v instanceof BigInteger)
			return new BigDecimal((BigInteger)v);
		else if (v instanceof Long)
			return new BigDecimal((Long)v);
		else
			return new BigDecimal(v.toString()); //错误类型直接异常
	}
	
	public static BigDecimal decimalOf(int i)
	{
		return new BigDecimal(i);
	}
	
	public static BigDecimal decimalOf(float f)
	{
		return BigDecimal.valueOf(f);
	}

	public static BigDecimal decimalOf(double d)
	{
		return BigDecimal.valueOf(d);
	}
	
	public static Value valueOf(Object obj)
	{
		return new Value(obj);
	}
	
	public static Value valueOf(Formula function, Factors factors)
	{
		return new Value(function.run(factors));
	}
	
	public static Value valueOf(Formula function, Factors factors, Object dv)
	{
		if (function == null)
			return new Value(dv);
		
		try
		{
			return new Value(function.run(factors));
		}
		catch (Exception e)
		{
			return new Value(dv);
		}
	}
	
	public static BigDecimal decimalOf(Formula function, Factors factors)
	{
		return valueOf(function, factors).toDecimal();
	}
	
	public static String stringOf(Formula function, Factors factors)
	{
		return valueOf(function, factors).toString();
	}
	
	public static int intOf(Formula function, Factors factors, int dv)
	{
		return valueOf(function, factors, new Integer(dv)).intValue();
	}
	
	public static int intOf(Formula function, Factors factors)
	{
		return intOf(function.run(factors));
	}

	public static double doubleOf(Formula function, Factors factors)
	{
		return doubleOf(function.run(factors));
	}
	
	public static boolean booleanOf(Formula function, Factors factors)
	{
		return booleanOf(function.run(factors));
	}
}
