package lerrain.tool.script.warlock.function;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;

/**
 * 有的函数需要根据参数的个数来判断是否可以优化，所以需要使用带有参数代码的是否可优化函数
 * 比如time()不可优化，time(datestr)如果datestr是固定的，就可以优化
 */
public interface OptimizedFunction extends Function
{
    public default boolean isFixed(int mode, Code code)
    {
        return true;
    }
}
