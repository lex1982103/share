package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.formula.Value;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.warlock.Code;
import lerrain.tool.script.warlock.CodeImpl;
import lerrain.tool.script.warlock.analyse.Expression;
import lerrain.tool.script.warlock.analyse.Words;
import lerrain.tool.script.warlock.statement.ArithmeticApprox;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FunctionContains implements Function
{
	@Override
	public Object run(Object[] objects, Factors factors)
	{
		Value v1 = Value.valueOf(objects[0]);
		Value v2 = Value.valueOf(objects[1]);

		if (v1.isNull() && v2.isNull())
			return true;

		if (v1.isNull() || v2.isNull())
			return v2.isNull();

		if (v1.isString() && v2.isString())
			return v1.toString().indexOf(v2.toString()) >= 0;

		if (v1.isMap() && v2.isMap())
			return contains(v1.toMap(), v2.toMap());

		throw new RuntimeException("无效的包含操作");
	}

	private static boolean contains(Map m1, Map m2)
	{
		Map<String, Object> t1 = new HashMap();

		for (Map.Entry e : (Set<Map.Entry>)m1.entrySet())
		{
			Object key = e.getKey();
			Object val = e.getValue();

			if (val != null && key != null)
				t1.put(key.toString(), val);
		}

		for (Map.Entry e : (Set<Map.Entry>)m2.entrySet())
		{
			if (e.getKey() != null && e.getValue() != null)
			{
				String key = e.getKey().toString();
				if (!ArithmeticApprox.compare(Value.valueOf(t1.get(key)), Value.valueOf(e.getValue())))
					return false;
			}
		}

		return true;
	}
}
