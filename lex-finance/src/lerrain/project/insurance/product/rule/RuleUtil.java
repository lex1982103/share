package lerrain.project.insurance.product.rule;

import java.util.ArrayList;
import java.util.List;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.Company;
import lerrain.tool.formula.Factors;

public class RuleUtil
{
	/**
	 * 校验产品的客户级产品规则
	 * 在此产品被添加到投保计划前的预检测，检测规则较少，只检查和人有关的属性，比如年龄超标无法购买的情况。
	 * 需要投保计划里的投保人和被保险人
	 * @param product
	 * @param insurant
	 * @return
	 */
	public static List precheck(Insurance product, Plan plan)
	{
		List list = new ArrayList();

		List ruleList = product.getRuleList();
		for (int i = 0; i < ruleList.size(); i++)
		{
			Rule rule = (Rule) ruleList.get(i);

			if (rule.getType() == Rule.TYPE_CUSTOMER)
				list.add(rule);
		}

		Company company = (Company)product.getCompany();

		if (company.getRuleList(Rule.TYPE_CUSTOMER) != null)
			list.addAll(company.getRuleList(Rule.TYPE_CUSTOMER));

		return checkRules(list, plan.getFactors());
	}

	/**
	 * 校验产品的投保规则
	 * 通则、客户级投保规则也会被校验，但不包括计划级通则
	 * @param commodity
	 * @return
	 */
	public static List check(Commodity commodity)
	{
		List list = new ArrayList();
		
		List ruleList = commodity.getProduct().getRuleList();
		for (int i = 0; ruleList != null && i < ruleList.size(); i++)
		{
			Rule rule = (Rule)ruleList.get(i);
			
			//if (rule.getType() == Rule.TYPE_PRODUCT)
			{
				list.add(rule);
			}
		}
		
		Company company = commodity.getCompany();
		List idList = commodity.getProduct().getRuleSkippedIdList();
		
		List clist = company.getRuleList(Rule.TYPE_CUSTOMER);
		if (clist != null)
		{
			for (int i=0;i<clist.size();i++)
			{
				Rule rule = (Rule)clist.get(i);
				
				if (idList != null && idList.indexOf(rule.getId()) < 0)
					list.add(rule);
			}
		}
		
		clist = company.getRuleList(Rule.TYPE_PRODUCT);
		if (clist != null)
		{
			for (int i=0;i<clist.size();i++)
			{
				Rule rule = (Rule)clist.get(i);
				
				if (idList == null || idList.indexOf(rule.getId()) < 0)
					list.add(rule);
			}
		}
		
		return checkRules(list, commodity.getFactors());
	}

	/**
	 * 校验计划级通则
	 * @param plan
	 * @return
	 */
	public static List check(Plan plan)
	{
		if (plan != null && !plan.isEmpty())
		{
			Commodity commodity = plan.getCommodity(0);
			Company company = commodity.getCompany();
			
			if (company.getRuleList(Rule.TYPE_PLAN) != null)
				return checkRules(company.getRuleList(Rule.TYPE_PLAN), plan.getFactors());
		}
		return null;
	}
	
	/**
	 * 从返回的未通过的投保规则List中，分离出等级是alert的规则。
	 * @param list 校验后返回的规则列表
	 * @return 如果没有等级是alert的规则，返回null，不会返回size为0的List。
	 */
	public static List findAlert(List list)
	{
		return getRule(list, Rule.LEVEL_ALERT);
	}
	
	/**
	 * 从返回的未通过的投保规则List中，分离出等级是fail的规则。
	 * @param list 校验后返回的规则列表
	 * @return 如果没有等级是fail的规则，返回null，不会返回size为0的List。
	 */
	public static List findFail(List list)
	{
		return getRule(list, Rule.LEVEL_FAIL);
	}
	
	private static List getRule(List list, int level)
	{
		if (list == null)
			return null;
		
		List result = null;

		for (int i = 0; i < list.size(); i++)
		{
			Rule rule = (Rule) list.get(i);
			if (rule.getLevel() == level)
			{
				if (result == null)
					result = new ArrayList();
				result.add(rule);
			}
		}
		
		return result;
	}

	private static List checkRules(List list, Factors factors)
	{
		if (list == null)
			return null;

		List result = null;
		for (int i = 0; i < list.size(); i++)
		{
			Rule rule = (Rule) list.get(i);

			Rule catched = rule.isCatched(factors);
			if (catched != null)
			{
				if (result == null)
					result = new ArrayList();
				result.add(catched);
			}
		}

		return result;
	}
}
