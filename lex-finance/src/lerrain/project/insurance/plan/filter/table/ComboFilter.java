package lerrain.project.insurance.plan.filter.table;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.Plan;
import lerrain.project.insurance.plan.filter.FilterPlan;
import lerrain.project.insurance.product.Company;
import lerrain.project.insurance.product.attachment.combo.Combo;
import lerrain.project.insurance.product.attachment.combo.ComboCol;
import lerrain.project.insurance.product.attachment.combo.ComboItem;
import lerrain.project.insurance.product.attachment.combo.ComboSingle;
import lerrain.project.insurance.product.attachment.combo.ComboText;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;


public class ComboFilter implements FilterPlan
{
	private static final long serialVersionUID = 1L;

	public ComboFilter()
	{
	}
	
	public Object filtrate(Plan plan, String attachmentName)
	{
		List result = null;
		
		Table grid = new Table();
		if (plan.primaryCommodity() != null)
		{
			Company c = (Company)plan.primaryCommodity().getProduct().getCompany();
			Combo bc = (Combo)c.getAttachment(attachmentName);
			if (bc != null && bc.getColList() != null)
			{
				Map innerText = new HashMap();
				
				Map colMap = new HashMap();
				List colList = new ArrayList(); //存在的数据列
				List colHide = new ArrayList(); //设定为隐藏的列

				Map temp = new HashMap();
				int from = -1, to = -1;
				
				List textCodeList = new ArrayList();
				
				for (int i=0;i<plan.size();i++)
				{
					Commodity product = plan.getCommodity(i);
					
					if (product.getInsurantId() != null && !product.getInsurantId().equals(plan.getInsurant().getId()))
						continue;
					
					ComboSingle bcs = (ComboSingle)product.getProduct().getAttachment(attachmentName);
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
							
							ComboCol col = (ComboCol)colMap.get(code);
							if (col == null && bcs.getSelfTable() != null)
								col = bcs.getSelfTable().getCol(code);
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
							else //有的话需要比较优先级，高的需要替代低的
							{
								ComboCol cc = (ComboCol)colList.get(x);
								if (cc.getPriority() < col.getPriority())
									colList.set(x, col);
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
					
					//重要提示合并
					List tcList = bcs.getTextList();
					for (int j=0; tcList != null && j < tcList.size(); j++)
					{
						String code = (String)tcList.get(j);
						if (textCodeList.indexOf(code) < 0)
							textCodeList.add(code);
						
						ComboText ct = bcs.getComboText(code);
						if (ct != null)
							innerText.put(code, ct.toTableText(product.getFactors()));
					}
				}
			
				int count = 0, titleHeight = 1;
				
				//数据
				for (int i=0;i<colList.size();i++)
				{
					ComboCol bcc = (ComboCol)colList.get(i);
//					if (bcc.hasSubCol())
//					{
//						int count2 = count;
//						
//						List subCol = bcc.getSubCol();
//						for (int j=0;j<subCol.size();j++)
//						{
//							ComboCol col = (ComboCol)subCol.get(j);
//							if (hasCol(colHide, col))
//							{
//								double[] v = (double[])temp.get(col.getCode());
//								if (v != null)
//								{
//									for (int k = from; k <= to; k++)
//										grid.setBlank(k, count, format(v[k], col.getStyle()));
//									count++;
//								}
//							}
//						}
//
//						if (count > count2)
//							titleHeight = 2;
//					}
//					else 
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
							
							if (bcc.getParent() != null)
								titleHeight = 2;
							
							for (int k = from; k <= to; k++)
								grid.setBlank(k, count, format(v[k], k, bcc.getStyle(), bcc.getValue(), plan.getFactors()));
							count++;
						}
					}
				}
				
				//求标题最高层的合并栏
				List colList2 = new ArrayList();
				for (int i=0;i<colList.size();i++)
				{
					ComboCol bcc = (ComboCol)colList.get(i);
					if (bcc.getParent() == null)
					{
						colList2.add(bcc);
					}
					else
					{
						if (colList2.indexOf(bcc.getParent()) < 0)
							colList2.add(bcc.getParent());
					}
				}
				
				//标题
				count = 0;
				for (int i=0;i<colList2.size();i++)
				{
					ComboCol bcc = (ComboCol)colList2.get(i);
					if (bcc.hasSubCol())
					{
						int colspan = 0;
						
						List subCol = bcc.getSubCol();
						for (int j=0;j<subCol.size();j++)
						{
							ComboCol col = (ComboCol)subCol.get(j);
							
							if (hasCol(colHide, col))
							{
								grid.setTitleBlank(bcc.getRow() < 0 ? 1 : bcc.getRow(), count + colspan, col.getRow() < 0 ? 1 : col.getRow(), 1, Value.stringOf(col.getName(), plan.getFactors()));
								colspan++;
							}
						}

						if (colspan > 0)
						{
							grid.setTitleBlank(0, count, bcc.getRow() < 0 ? 1 : bcc.getRow(), colspan, Value.stringOf(bcc.getName(), plan.getFactors()));
							count += colspan;
						}
					}
					else if (hasCol(colHide, bcc))
					{
						grid.setTitleBlank(0, count++, bcc.getRow() < 0 ? titleHeight : bcc.getRow(), 1, Value.stringOf(bcc.getName(), plan.getFactors()));
					}
				}
				
				result = new ArrayList();
				result.add(grid);

				//重要提示
				List sortedList = bc.getTextCodeSortedList();
				for (int i=0; textCodeList != null && i<textCodeList.size(); i++)
				{
					String code = (String)textCodeList.get(i);
					if (sortedList.indexOf(code) < 0)
					{
						TableText tt = (TableText)innerText.get(code);
						if (tt != null)
							result.add(tt);
					}
					else
					{
						ComboText text = bc.getComboText(code);
						if (text != null)
							result.add(text.toTableText(plan.getFactors()));
					}
				}
			}
		}
		
		return result;
	}
	
	private boolean hasCol(List colHide, ComboCol col)
	{
		return colHide.indexOf(col.getCode()) < 0;
	}
	
//	private int buildBlankCombo(Table grid, Map temp, ComboCol col, int from, int to, int colNum)
//	{
//		double[] v = (double[])temp.get(col.getCode());
//		if (v == null)
//			return colNum;
//		
//		for (int k = from; k <= to; k++)
//			grid.setBlank(k, colNum, format(v[k], col.getStyle()));
//		
//		return colNum + 1;
//	}

	private static String format(double v, int k, String style, Formula value, Factors f)
	{
		if (value != null)
		{
			Stack s = new Stack(f);
			s.set("this", new Double(v));
			s.set("I", new Integer(k));
			s.set("INDEX", new Integer(k));
			Object res = value.run(s);
			if (res instanceof String)
				return (String)res;
			
			v = Value.doubleOf(res);
		}
		
		if (style == null)
			return v + "";
		
		DecimalFormat df = new DecimalFormat(style);
//		style.setRoundingMode(RoundingMode.HALF_UP);
//		return df.format(v.add(new BigDecimal("0.0000001")));
		return df.format(v);
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
