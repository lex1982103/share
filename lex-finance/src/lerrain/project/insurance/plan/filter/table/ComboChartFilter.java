package lerrain.project.insurance.plan.filter.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.project.insurance.plan.filter.FilterPlan;
import lerrain.project.insurance.plan.filter.chart.Chart;
import lerrain.project.insurance.plan.filter.chart.ChartLine;
import lerrain.project.insurance.product.Company;
import lerrain.project.insurance.product.attachment.combo.ComboChart;
import lerrain.project.insurance.product.attachment.combo.ComboChartCol;
import lerrain.project.insurance.product.attachment.combo.ComboCol;
import lerrain.project.insurance.product.attachment.combo.ComboItem;
import lerrain.project.insurance.product.attachment.combo.ComboSingle;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;


public class ComboChartFilter implements FilterPlan
{
	private static final long serialVersionUID = 1L;

	public ComboChartFilter()
	{
	}
	
	public Object filtrate(Plan plan, String attachmentName)
	{
		Chart chart = new Chart();
		
		if (plan.primaryCommodity() != null)
		{
			double max = 0, min = -1;
			
			Company c = (Company)plan.primaryCommodity().getProduct().getCompany();
			ComboChart bc = (ComboChart)c.getAttachment(attachmentName);
			if (bc != null && bc.getColList() != null)
			{
				Map colMap = new HashMap();
				List colList = new ArrayList(); //存在的数据列
				List colHide = new ArrayList(); //设定为隐藏的列

				Map temp = new HashMap();
				int from = -1, to = -1;
				
				for (int i=0;i<plan.size();i++)
				{
					Commodity product = plan.getCommodity(i);
					
					if (product.getInsurantId() != null && !product.getInsurantId().equals(plan.getInsurant().getId()))
						continue;
					
					ComboSingle bcs = (ComboSingle)product.getProduct().getAttachment("benefit_combo");
					if (bcs == null)
						continue;
					if (bcs.getSelfTable() != null && bcs.getSelfTable().getHide() != null)
						colHide.addAll(bcs.getSelfTable().getHide());
					
					//利益合并
					List itemList = bcs.getItemList();
					for(int j=0;j<itemList.size();j++)
					{
						FactorsExt vse = new FactorsExt(product.getFactors());
						ComboItem bci = (ComboItem)itemList.get(j);
						
						String code = bci.getCode();

						try
						{
							Formula visible = bci.getVisible();
							if (visible != null)
							{
								if (!Value.booleanOf(visible, vse))
									continue;
							}

							int bfrom = Value.intOf(bci.getFrom(), vse);
							int bto = Value.intOf(bci.getTo(), vse);
							
							if (from < 0 || bfrom < from)
								from = bfrom;
							if (to < 0 || bto > to)
								to = bto;
							
							double[] v = (double[])temp.get(code);
							if (v == null)
							{
								v = new double[200];
								temp.put(code, v);
							}
							
							ComboChartCol col = (ComboChartCol)colMap.get(code);
							if (col == null)
								col = bc.getCol(code);
							if (col == null)
								continue;
							
							if (!colMap.containsKey(code))
								colMap.put(code,  col);
							
							int x = colList.indexOf(col);
							if (x < 0)
							{
								colList.add(col);
							}
							
							int mode = col.getMode();

							//模式：相加、累加、覆盖
							if (mode == ComboCol.MODE_ADD)
							{
								for (int k = bfrom; k <= bto; k++)
								{
									vse.set(bci.getVarName(), new Integer(k));
									
									if (v[k] <= 0)
										v[k] = Value.doubleOf(bci.getValue(), vse);
									else
										v[k] = v[k] + Value.doubleOf(bci.getValue(), vse);
								}
							}
							else if (mode == ComboCol.MODE_ACCUMULATE)
							{
								double lastv = 0;
								for (int k = bfrom; k <= bto; k++)
								{
									vse.set(bci.getVarName(), new Integer(k));
									
									lastv = Value.doubleOf(bci.getValue(), vse);
									
									if (v[k] <= 0)
										v[k] = lastv;
									else
										v[k] = v[k] + lastv;
								}
								for (int k = bto + 1; k < 200; k++)
								{
									if (v[k] <= 0)
										v[k] = v[bto];
									else
										v[k] = lastv <= 0 ? v[bto] : (v[k] + lastv);
								}
							}
							else if (mode == ComboCol.MODE_COVER)
							{
								for (int k = bfrom; k <= bto; k++)
								{
									if (v[k] <= 0)
									{
										vse.set(bci.getVarName(), Integer.valueOf(k));
										v[k] = Value.doubleOf(bci.getValue(), vse);
									}
								}
							}
						}
						catch (Exception e)
						{
							System.out.println("数据列合并异常 - " + product.getProduct().getName() + " / " + code);
						}
					}
				}
			
				//数据
				for (int i=0;i<colList.size();i++)
				{
					ComboChartCol bcc = (ComboChartCol)colList.get(i);
					if (hasCol(colHide, bcc))
					{
						double[] v = (double[])temp.get(bcc.getCode());
						if (v != null)
						{
							if (bcc.getMode() != ComboCol.MODE_COVER && bcc.getAddCol() != null) //加入其他列add="XXXX"
							{
								double[] v1 = (double[])temp.get(bcc.getAddCol());
								if (v1 != null)
								{
									for (int k = 0; k < 200; k++)
									{
										v[k] = v[k] + v1[k];
									}
								}
							}
							
							ChartLine line = new ChartLine(bcc.getType());
							line.setName(Value.stringOf(bcc.getName(), plan.getFactors()));
							
							for (int k = from; k <= to; k++)
							{
								if (v[k] > max)
									max = v[k];
								if (v[k] < min || min < 0)
									min = v[k];
								line.setData(k, v[k]);
							}
							
							chart.addLine(line);
						}
					}
				}
				
				chart.setMax(max);
				chart.setMin(min);
				chart.setStart(from);
				chart.setEnd(to);
				chart.setStep(1);
			}
		}
		
		return chart;
	}
	
	private boolean hasCol(List colHide, ComboChartCol col)
	{
		return colHide.indexOf(col.getCode()) < 0;
	}
	
	private class FactorsExt implements Factors
	{
		Factors f;
		
		String name;
		Object value;
		
		public FactorsExt(Factors f)
		{
			this.f = f;
		}
		
		public void set(String name, Object value)
		{
			this.name = name;
			this.value = value;
		}
		
		public Object get(String n)
		{
			if (n != null && n.equals(name))
				return value;
			
			return f.get(n);
		}
		
	}
}
