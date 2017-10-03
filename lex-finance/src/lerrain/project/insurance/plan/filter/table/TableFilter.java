package lerrain.project.insurance.plan.filter.table;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.project.insurance.plan.filter.FilterCommodity;
import lerrain.project.insurance.plan.filter.StaticText;
import lerrain.project.insurance.product.attachment.document.DynamicText;
import lerrain.project.insurance.product.attachment.table.TableBlank;
import lerrain.project.insurance.product.attachment.table.TableBlankSupply;
import lerrain.project.insurance.product.attachment.table.TableDef;
import lerrain.project.insurance.product.attachment.table.TableLoop;
import lerrain.project.insurance.product.attachment.table.TableReset;
import lerrain.project.insurance.product.attachment.table.TableRow;
import lerrain.project.insurance.product.attachment.table.TableSupply;
import lerrain.project.insurance.product.attachment.table.TableTextDef;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;


public class TableFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;

	public TableFilter()
	{
	}
	
	private Map findSupply(Commodity c, String attachmentName)
	{
		Map supply = new HashMap();
		
		Plan p = c.getPlan();
		for (int i = 0; i < p.size(); i++)
		{
			Commodity r = p.getCommodity(i);
			Stack stack = new Stack(r.getFactors());
			
			List tableList = (List)r.getProduct().getAttachment(attachmentName);
			if (tableList == null)
				continue;
			
			for(int j=0;j<tableList.size();j++)
			{
				Object line = tableList.get(j);
				if (line instanceof TableSupply)
				{
					TableSupply ts = (TableSupply)line;
					List list = ts.getBlankSupplyList();
					for(int k=0;k<list.size();k++)
					{
						TableBlankSupply tbs = (TableBlankSupply)list.get(k);
						String code = ts.getCode() + "/" + tbs.getCode();
						if (!supply.containsKey(code))
						{
							List l = new ArrayList();
							l.add(new Object[] {tbs, stack});
							supply.put(code, l);
						}
						else
						{
							List l = (List)supply.get(code);
							l.add(new Object[] {tbs, stack});
						}
					}
				}
			}
		}
		
		return supply.isEmpty() ? null : supply;
	}

	public Object filtrate(Commodity product, String attachmentName)
	{
		List tableList = (List)product.getProduct().getAttachment(attachmentName);
		if (tableList == null)
			return null;
		
		Map supply = findSupply(product, attachmentName);

		List result = new ArrayList();
		Factors varSet = product.getFactors();
		
		for(int i=0;i<tableList.size();i++)
		{
			Object line = tableList.get(i);
			
			if (line instanceof TableDef)
			{
				TableDef table = (TableDef)line;
				Table dt = filterTable(table, varSet, supply);
				if (dt != null)
					result.add(dt);
			}
			else if (line instanceof DynamicText)
			{
				DynamicText dt = (DynamicText)line;
				if (dt.getCondition() == null || Value.booleanOf(dt.getCondition(), varSet))
					result.add(StaticText.textOf(dt, varSet));
			}
			else if (line instanceof TableReset)
			{
				result.add(line);
			}
		}
		
		return result;
	}
	
	public static Table filterTable(TableDef table, Factors param, Map supply)
	{
		try
		{
			Formula condition = (Formula)table.getAdditional("condition");
			if (condition != null && !Value.booleanOf(condition, param))
				return null;
			
			Table grid = new Table();
			grid.setName(table.getName());
			
			Map map = table.getAdditional();
			if (map != null)
			{
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext())
				{
					String key = (String)iter.next();
					grid.setAdditional(key, table.getAdditional(key));
				}
			}
			
			int ty = 0, y = 0;
			for(int j=0;j<table.size();j++)
			{
				Object obj = table.getElement(j);
				if (obj instanceof TableRow)
				{
					TableRow row = (TableRow)obj;
					
					if (row.getCondition() != null && !Value.booleanOf(row.getCondition(), param))
						continue;
					
					int x = 0;
					for(int k=0;k<row.getSize();k++)
					{
						TableBlank blank = (TableBlank)row.getBlank(k);
						
						if (blank.getCondition() != null && !Value.booleanOf(blank.getCondition(), param))
							continue;

						int vr = Value.intOf(blank.getRowspan(), param);
						int vc = Value.intOf(blank.getColspan(), param);
						
						if (row.getType() == TableRow.TYPE_TITLE)
						{
							while (grid.getTitleBlank(ty, x) != null && grid.getTitleBlank(ty, x).getType() == Blank.TYPE_NULL)
							{
								x++;
							}
							grid.setTitleBlank(ty, x, vr, vc, Value.stringOf(blank.getContent(), param));

							if (blank.getWidthScale() > 0)
								grid.getBlank(ty, x).setWidthScale(blank.getWidthScale());
						}
						else
						{
							while (grid.getBlank(y, x) != null && grid.getBlank(y, x).getType() == Blank.TYPE_NULL)
							{
								x++;
							}
							grid.setBlank(y, x, vr, vc, format(blank.getContent(), 0, param, blank.getStyle()), blank.getAlign());

							if (blank.getWidthScale() > 0)
								grid.getBlank(y, x).setWidthScale(blank.getWidthScale());
						}
						
						x++;
					}
					
					if (row.getType() == TableRow.TYPE_TITLE)
						ty++;
					else
						y++;
				}
				else if (obj instanceof TableLoop)
				{
					TableLoop loop = (TableLoop)obj;

					int st = Value.intOf(loop.getStart(), param);
					int ed = Value.intOf(loop.getEnd(), param);
					int sp = Value.intOf(loop.getStep(), param);

					Stack pe = new Stack(param);

					for (int n = st; n <= ed; n = n + sp)
					{
						pe.set(loop.getLoopVar(), new Integer(n));
						for (int m = 0; m < loop.getElementNum(); m++)
						{
							Object lObj = loop.getElement(m);
							if (lObj instanceof TableRow)
							{
								TableRow row = (TableRow) lObj;
								if (row.getCondition() != null && !Value.booleanOf(row.getCondition(), pe))
									continue;

								for (int k = 0, x = 0; k < row.getSize(); k++)
								{
									TableBlank blank = (TableBlank) row.getBlank(k);
									
									if (blank.getCondition() != null && !Value.booleanOf(blank.getCondition(), pe))
										continue;

									int vr = Value.intOf(blank.getRowspan(), pe);
									int vc = Value.intOf(blank.getColspan(), pe);

									try
									{
										double t = 0;
										if (supply != null && table.getCode() != null && blank.getCode() != null) //该列有code，其他的险种可能会对这列的数据有补充（相加、累加）
										{
											List l = (List)supply.get(table.getCode() + "/" + blank.getCode());
											for (int i=0;i<l.size();i++)
											{
												Object[] objs = (Object[])l.get(i);
												TableBlankSupply tbs = (TableBlankSupply)objs[0];
												Stack stack = (Stack)objs[1];
												stack.set(loop.getLoopVar(), new Integer(n));
												t += Value.doubleOf(tbs.getFormula(), stack);
											}
										}
										
										grid.setBlank(y, x, vr, vc, format(blank.getContent(), t, pe, blank.getStyle()), blank.getAlign());
									}
									catch (Exception e)
									{
										System.out.println("FORMULA:" + blank.getContent());
										throw e;
									}
									
									x++;
								}
								y++;
							}
						}
					}
				}
			}
			
			return grid;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		return null;
	}
	
	private static String format(Formula cf, double supply, Factors p, String style)
	{
		if (cf == null)
		{
			return "";
		}
		else if (style == null)
		{
			Object v = cf.run(p);
			return v == null ? "" : v.toString();
		}
		else
		{
			Value v = Value.valueOf(cf, p);
			if (v.isDecimal())
				return format(v.doubleValue() + supply, style);
			else if (v.isString())
				return v.toString();
			else
				return "<ERROR>";
		}
	}
	
	private static String format(double v, String style)
	{
		if (style == null)
			return v + "";
		
		DecimalFormat df = new DecimalFormat(style);
//		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(v + 0.0000001);
	}
}
