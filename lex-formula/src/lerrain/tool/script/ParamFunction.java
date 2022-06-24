package lerrain.tool.script;

import lerrain.tool.formula.Function;
import lerrain.tool.script.warlock.Code;

/**
 * 这种类型的函数，它的参数是由它自己处理的，也就是不一定先把参数计算出来
 * 如：try(A.B, ++C)
 * 常规函数里，++C一定先被执行，C会累加
 * 但如果自己处理参数部分，就不一定了，try函数如果前者不成立，后者完全不会被执行，也就是C不会被累加
 */
public interface ParamFunction extends Function
{
	Object[] param(Code prt);
}
