package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Function;
import lerrain.tool.script.ScriptRuntimeException;

import java.util.List;
import java.util.Map;

/**
 * 空值判断并返回第一个非空值（至少需要一个参数）
 * nvl(v1, v2, v3, v4, ...);
 * 根据参数表依次找到第一个非空值数据并返回
 */
public class FunctionNvl implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if(v != null && v.length > 0) {
			for(int k = 0; k < v.length; k++) {
				Object o = v[k];

				if(o == null) {
					continue;
				}

				if(o instanceof String && "".equals(o)) {
					continue;
				}
				if(o instanceof Map && ((Map) o).size() <= 0) {
					continue;
				}
				if(o instanceof List && ((List) o).size() <= 0) {
					continue;
				}
				if(o instanceof Object[] && ((Object[]) o).length <= 0) {
					continue;
				}

				return o;
			}

			return null;
		}

		throw new ScriptRuntimeException("错误的nvl运算");
	}
}
