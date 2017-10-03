package lerrain.project.insurance.product;

import java.io.Serializable;

import lerrain.tool.formula.Formula;

public class Purchase implements Serializable
{
	private static final long serialVersionUID = 1L;

	/*
	 * 保额的类型描述
	 */
	public final static int QUANTITY 				= 0x1;		//按份数
	public final static int AMOUNT 					= 0x2;		//按保额
	public final static int PREMIUM 				= 0x4;		//按保费
	public final static int RANK 					= 0x8;		//按档次
	public final static int NONE				 	= 0x0;		//无需输入（inputMode专用）
	public final static int PREMIUM_AND_AMOUNT		= 0x6;		//需要输入保费和保额（inputMode专用）
	public final static int RANK_AND_QUANTITY 		= 0x9;		//按档次和份数

	public final static int PREMIUM_OR_AMOUNT		= 0x1006;	//需要输入保费或者保额（inputMode专用）
	
	int purchaseMode 								= AMOUNT;			//保额的类型描述
	int inputMode									= AMOUNT;			//输入方式
	
	Formula amount;
	Formula premium;													//首年基础保费
	Formula quantity;
	Formula premiumFirstYear;											//首年全部保费
	
	public Purchase()
	{
	}
	
	/**
	 * 返回null时代表该险种没有保额计算公式，即保额是需要填写的
	 * @return 保额计算公式
	 */
	public Formula getAmount()
	{
		return amount;
	}

	/**
	 * 返回null时代表该险种没有保费计算公式，即保费是需要填写的
	 * @return 保费计算公式
	 */
	public Formula getPremium()
	{
		return premium;
	}

	public void setAmount(Formula amount)
	{
		this.amount = amount;
	}

	public void setPremium(Formula premium)
	{
		this.premium = premium;
	}

	/**
	 * 主要是用来描述投保计划的“保额/档次/份数”的展示方式（通常投保计划那里是保额/档次/份数三选一，这里决定具体是哪个，在展示投保计划列表时用这个方法判断）
	 * Purchase.QUANTITY份数
	 * Purchase.AMOUNT保额
	 * Purchase.RANK档次
	 * 
	 * @deprecated
	 * @return
	 */
	public int getPurchaseMode()
	{
		return purchaseMode;
	}

	public void setPurchaseType(int purchaseMode)
	{
		this.purchaseMode = purchaseMode;
	}

	public Formula getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Formula quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * 主要用来描述产品的保障金额输入方式（在输入产品参数的时候用这个方法判断，一个普通的险种一般需要输入缴费年期、保障年期、保障金额输入方式（即下面的其中一项））
	 * Purchase.QUANTITY输入份数（根据份数计算保费保额的产品）
	 * Purchase.AMOUNT输入保额（常规产品，根据保额计算保费）
	 * Purchase.RANK选择档次（由档次决定保额，计算保费）
	 * Purchase.PREMIUM输入保费（保费算保额）
	 * Purchase.NONE无需输入（对应于某些捆绑销售的附加险，如果规则要求保额与主险相同，则无需输入）
	 * Purchase.PREMIUM_AND_AMOUNT保费保额都需要输入（如万能险）
	 * 
	 * @deprecated
	 * @return
	 */
	public int getInputMode()
	{
		return inputMode;
	}

	public void setInputMode(int inputMode)
	{
		this.inputMode = inputMode;
	}

	public Formula getPremiumFirstYear()
	{
		return premiumFirstYear;
	}

	public void setPremiumFirstYear(Formula premiumFirstYear)
	{
		this.premiumFirstYear = premiumFirstYear;
	}
}
