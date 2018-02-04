package lerrain.project.insurance.product.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.project.insurance.plan.UnstableList;
import lerrain.project.insurance.product.Insurance;
import lerrain.project.insurance.product.Company;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.FormulaUtil;

public class RuleUtil
{
	/**
	 * 校验产品的客户级产品规则
	 * 在此产品被添加到投保计划前的预检测，检测规则较少，只检查和人有关的属性，比如年龄超标无法购买的情况。
	 * 需要投保计划里的投保人和被保险人
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
		List ruleList = commodity.getProduct().getRuleList();
		UnstableList children = commodity.getChildren();
		if (children != null && children.size() > 0 && (ruleList == null || ruleList.isEmpty()))
		{
			List r = new ArrayList();

			for (Commodity c : (List<Commodity>)children.toList())
			{
				List cl = check(c);
				if (cl != null)
					r.addAll(cl);
			}

			return r;
		}
		else
		{
			List list = new ArrayList();

			for (int i = 0; ruleList != null && i < ruleList.size(); i++)
			{
				Rule rule = (Rule) ruleList.get(i);

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
				for (int i = 0; i < clist.size(); i++)
				{
					Rule rule = (Rule) clist.get(i);

					if (idList != null && idList.indexOf(rule.getId()) < 0)
						list.add(rule);
				}
			}

			clist = company.getRuleList(Rule.TYPE_PRODUCT);
			if (clist != null)
			{
				for (int i = 0; i < clist.size(); i++)
				{
					Rule rule = (Rule) clist.get(i);

					if (idList == null || idList.indexOf(rule.getId()) < 0)
						list.add(rule);
				}
			}

			return checkRules(list, commodity.getFactors());
		}
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

	public static void addRules(Insurance ins, List rs)
	{
		for (int i = 0; i < rs.size(); i++)
		{
			Map rp = (Map)rs.get(i);

			List rules = (List)rp.get("rules");
			if (rules == null)
			{
				ins.addRule(ruleOf(rp));
			}
			else
			{
				String group = (String) rp.get("group");
				for (int j = 0; j < rules.size(); j++)
				{
					Map rj = (Map) rules.get(j);
					ins.addRule(ruleOf(group, rj));
				}
			}
		}
	}

	public static Rule ruleOf(Map rj)
	{
		return ruleOf(null, rj);
	}

	public static Rule ruleOf(String group, Map rj)
	{
		Rule rule = new Rule();

		String id = (String)rj.get("id");
		if (id != null)
			rule.setId(id);

		String condition = (String)rj.get("condition");
		if (condition != null && !"".equals(condition))
		{
			String str = group == null ? condition : "(" + group + ") and (" + condition + ")";
			rule.setCondition(FormulaUtil.formulaOf(str));

			String desc = (String)rj.get("desc");
			if (desc != null)
				rule.setDesc(FormulaUtil.formulaOf(desc));
			else
				rule.setDesc((String)rj.get("text"));
		}
		else if (group == null) //自由型规则，不支持分类
		{
			rule.setCondition(FormulaUtil.formulaOf((String)rj.get("script")));
		}

		String level = (String)rj.get("level");
		if ("alert".equalsIgnoreCase(level))
			rule.setLevel(Rule.LEVEL_ALERT);

		String type = (String)rj.get("type");
		if ("customer".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_CUSTOMER);
		else if ("product".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PRODUCT);
		else if ("plan".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PLAN);
		else if ("proposal".equalsIgnoreCase(type))
			rule.setType(Rule.TYPE_PROPOSAL);

		String alert = (String)rj.get("alert");
		if (alert != null)
		{
			for (String alertCol : alert.split(","))
				rule.addAlert(alertCol);
		}

		return rule;
	}
}
