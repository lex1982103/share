package lerrain.tool.formula.aries.arithmetic;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.CalculateException;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

public class ArithmeticPoint extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticPoint()
	{
		super.addSign(".");
		super.setPriority(600);
		super.setFuntion(false);
	}
	
	public ArithmeticPoint(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		//System.out.println(super.getParameter(0) + " " + super.getParameter(1));
		
		/*
		 * 2011/09/22
		 * 取消这个功能，point嵌套太多，会造成溢出。
		 * 2011/09/10
		 * 关键字varset代表其本身，需要将其作为函数的传入参数的时候使用。
		 */
//		VarSetExpand vs = new VarSetExpand(vs2);
//		vs.setValue("varset", vs);
		
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object value;
		
		if (v1.isType(Value.TYPE_MAP) && v2.isType(Value.TYPE_STRING))
		{
			Map map = (Map)v1.getValue();
			value = map.get((String)v2.getValue());
			//result = new LexValue(map.get((String)v2.getValue()));
		}
		else if (!v1.isType(Value.TYPE_MAP) && v2.isType(Value.TYPE_LIST))
		{
			List list = (List)v2.getValue();
			String methodStr = (String)list.get(0);
			
			Object object = v1.getValue();
			
			/*J2C INSERT
			JArray * paramArray = [[JArray alloc] _init_ : [JArray TYPE_CLASS] : [list count] - 1];
            for (int i = 0; i < [list count] - 1; i++)
            {
                [(JArray *)paramArray set : (i) : [list objectAtIndex : i + 1]];
                methodStr = [NSString stringWithFormat: @"%@:", methodStr];
            }

			SEL selector = NSSelectorFromString(methodStr);
			value = [object performSelector : selector withObjects : paramArray];
			*/
			//J2C R-BEGIN
			try
			{
				Class[] classArray = new Class[list.size() - 1];
				Object[] paramArray = new Object[list.size() - 1];
				
				for (int i=0;i<list.size()-1;i++)
				{
					paramArray[i] = list.get(i+1);
					
					if (paramArray[i] != null)
						classArray[i] = paramArray[i].getClass();
				}
				
				Method method = null;
				try
				{
					method = object.getClass().getDeclaredMethod(methodStr, classArray);
				}
				catch (NoSuchMethodException e)
				{
					//上面的方法，如果参数某个参数是传入参数的父类，则会找不到该方法。这里对这种情况做一下处理。
					Method[] m = object.getClass().getMethods();
					for (int i=0;i<m.length;i++)
					{
						if (methodStr.equals(m[i].getName()))
						{
							Class[] c = m[i].getParameterTypes();
							if (c.length == classArray.length)
							{
								boolean pass = true;
								for(int j=0;j<c.length;j++)
								{
									//传入参数是null，除了int(double boolean等先不考虑了)以外的其他都适合，只找第一个。
									if (paramArray[j] == null && !c[j].equals(Integer.TYPE))
									{
									}
									//int
									else if (c[j].equals(Integer.TYPE) && paramArray[j] instanceof Integer)
									{
									}
									else if (c[j].equals(Integer.TYPE) && paramArray[j] instanceof BigDecimal)
									{
										paramArray[j] = new Integer(((BigDecimal)paramArray[j]).intValue());
									}
									//boolean
									else if (c[j].equals(Boolean.TYPE) && paramArray[j] instanceof Boolean)
									{
									}
									//double
									else if (c[j].equals(Double.TYPE) && paramArray[j] instanceof Double)
									{
									}
									else if (c[j].equals(Double.TYPE) && paramArray[j] instanceof Integer)
									{
										paramArray[j] = new Double(((Integer)paramArray[j]).doubleValue());
									}
									else if (c[j].equals(Double.TYPE) && paramArray[j] instanceof Float)
									{
										paramArray[j] = new Double(((Float)paramArray[j]).doubleValue());
									}
									else if (c[j].equals(Double.TYPE) && paramArray[j] instanceof BigDecimal)
									{
										paramArray[j] = new Double(((BigDecimal)paramArray[j]).doubleValue());
									}
									//float
									else if (c[j].equals(Float.TYPE) && paramArray[j] instanceof Integer)
									{
										paramArray[j] = new Float(((Integer)paramArray[j]).floatValue());
									}
									else if (c[j].equals(Float.TYPE) && paramArray[j] instanceof Float)
									{
									}
									else if (c[j].equals(Float.TYPE) && paramArray[j] instanceof Double)
									{
										paramArray[j] = new Float(((Float)paramArray[j]).floatValue());
									}
									else if (c[j].equals(Float.TYPE) && paramArray[j] instanceof BigDecimal)
									{
										paramArray[j] = new Float(((BigDecimal)paramArray[j]).floatValue());
									}
									else if (!c[j].isInstance(paramArray[j]))
									{
										pass = false;
										break;
									}
								}
								
								if (pass)
								{
									method = m[i];
									break;
								}
							}
							
						}
					}
				}
				
				if (method == null)
					throw new NoSuchMethodException();
				
				value = method.invoke(object, paramArray);
			}
			catch (NoSuchMethodException e)
			{
				String str = "";
				if (v2.getValue() instanceof List)
				{
					List lt = (List)v2.getValue();
					int len = lt.size();
					for (int i=0;i<len;i++)
					{
						Object obj = lt.get(i);
						str += obj == null ? "NULL" : obj.getClass().toString();
						str += ",";
					}
				}
				
				throw new CalculateException("point运算取值失败，没有该方法 -- 对象：" + v1.getValue() + "，方法名: " + v2.getValue() + "<" + str + ">", e);
			}
			catch (Exception e)
			{
				throw new CalculateException("point运算取值失败，方法内部错误 -- 对象：" + v1.getValue() + "，方法名: " + v2.getValue(), e);
			}
			//J2C R-END
		}
		else if (!v1.isType(Value.TYPE_MAP) && v2.isType(Value.TYPE_STRING))
		{
			/*
			 * 1。判定v1object是不是一个IVarSet，是的话从里面直接取。
			 * 2。1不成立的话，取v2string的mapping函数，将v1object作为this传入计算。
			 * 3。1和2都不成立的话，将v2string作为方法名，直接读取v1object的该方法，方法不存在就出错退出。
			 */
			value = null;

			Object object = v1.getValue();
			String methodStr = (String)v2.getValue();
			
			if (object instanceof Factors)
			{
				value = ((Factors)object).get(methodStr);
			}
		}
		else
		{
			throw new CalculateException("point运算取值失败 -- value: " + v1.getValue() + ", key: " + v2.getValue());
		}
		
		if (value instanceof Formula)
			value = ((Formula)value).run(getter);
		
		return value;
	}
}
