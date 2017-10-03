package lerrain.project.insurance.plan;

import lerrain.tool.formula.Factors;

/**
 * 客户、计划、商品的参数表，都有可能缓冲数据以提升效率。
 * 需要设定刷新方法，当一些设定值变化时，提醒参数表清除缓存。
 * 
 * @author lerrain
 */
public interface FactorsSupport extends Factors
{
	/**
	 * 注意：
	 * 这个方法是内部程序自动调用的，外部不需要调用这个方法。
	 * 这个方法是清除缓存，而不是刷新数据，它并不是把数据全部刷新重算，而只是删除计算结果。
	 * 缓存对于提升效率非常有必要，避免了大量重复计算。
	 * 只有在取值的时候才会计算和该值有关的值（只计算必要的，不是全算）。
	 * 
	 * 比如：
	 * 存在3个更改输入项方法：setA、setB、setC，每个方法中都会调用该方法清除缓存。
	 * 由于只是清除，而不是重算，反复清除不会造成效率的损失。所以可以随意反复调用各类setX。
	 */
	public void clearCache();
}
