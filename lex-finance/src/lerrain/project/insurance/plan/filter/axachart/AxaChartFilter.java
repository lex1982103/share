package lerrain.project.insurance.plan.filter.axachart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.FilterCommodity;
import lerrain.project.insurance.product.attachment.axachart.AppendAxis;
import lerrain.project.insurance.product.attachment.axachart.AppendItem;
import lerrain.project.insurance.product.attachment.axachart.AppendYearData;
import lerrain.project.insurance.product.attachment.axachart.AppendYearItem;
import lerrain.project.insurance.product.attachment.axachart.BenefitChart;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;

public class AxaChartFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;

	Factors varSet;

	int appendValue;

	String appendName;

	public Object filtrate(Commodity product, String attachmentName)
	{
		List result = null;

			List charList = (List) product.getProduct().getAttachment(attachmentName);
			if (charList != null) {
				result = new ArrayList();
				Factors varSet = product.getFactors();

				List defaultYearData = null;
				for (int i = 0; i < charList.size(); i++) {
					Object line = charList.get(i);

					if (line instanceof BenefitChart) {
						BenefitChart chart = (BenefitChart) line;
						DataChart dt = filterChart(chart, varSet);
						if (dt != null)
							result.add(dt);
					}

					if (line instanceof AppendYearData) {
						if (defaultYearData == null)
							defaultYearData = new ArrayList();
						YearData data = filterYearData((AppendYearData) line, varSet);
						if (data != null)
							defaultYearData.add(data);
					}
				}

				if (defaultYearData != null) {
					for (int i = 0; i < result.size(); i++) {
						DataChart dt = (DataChart) result.get(i);
						if (!dt.hasYearData()){
							dt.setYearDataList(transYearDataList(dt.getCode(), defaultYearData));
						}
//							dt.setYearDataList(defaultYearData);
					}
				}
			}

		return result;
	}

	// 根据code获取所属当年数据
	public static List transYearDataList(String code, List defaultYearData) {
		if (code == null)
			return defaultYearData;
		List list = new ArrayList();
		try {
			for (int i = 0; defaultYearData != null && i < defaultYearData.size(); i++) {
				YearData yearData = (YearData) defaultYearData.get(i);
				YearData newYearData = new YearData();
				newYearData.setCode(yearData.getCode());

				List itemList = yearData.getItemList();
				for (int j = 0 ; itemList != null && j < itemList.size(); j++) 
				{
					YearDataItem item = (YearDataItem) itemList.get(j);
					if (code.equals(item.getCode())) 
						newYearData.addItem(item);
				}
				
				list.add(newYearData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static YearData filterYearData(AppendYearData appendYearData, Factors varSet) {
		YearData result = null;
		if (appendYearData != null) {
			result = new YearData();
			result.setCode(appendYearData.getCode());
			int from = Value.intOf(appendYearData.getStart(), varSet);
			int to = Value.intOf(appendYearData.getEnd(), varSet);
			int step = Value.intOf(appendYearData.getStep(), varSet);
			Formula condition = appendYearData.getCondition();
			if (condition != null && !Value.booleanOf(condition, varSet))
				return null;

			for (int i = 0; i < appendYearData.size(); i++) {
				AppendYearItem item = appendYearData.getItem(i);
				YearDataItem dataIem = new YearDataItem(item.getTitle(), item.getDesc(), item.getMode());
				dataIem.setCode(item.getCode());
				if (item.getFormula() != null) {
					Stack nVars = new Stack(varSet);
					for (int n = from; n <= to; n = n + step) {
						nVars.set(appendYearData.getLoopVar(), new Integer(n));
						BigDecimal res = Value.decimalOf(item.getFormula(), nVars);
						dataIem.setItem(n, res);
//						System.out.println(item.getTitle() + "," + item.getFormula() + "," + n + " = " + res);
					}
				}
				result.addItem(dataIem);
			}

		}
		return result;
	}

	public static DataChart filterChart(BenefitChart chart, Factors varSet) {
		try {
			Formula condition = (Formula) chart.getAdditional("condition");
			if (condition != null && !Value.booleanOf(condition, varSet))
				return null;

			DataChart data = new DataChart();
			data.setCode(chart.getCode());
			Map map = chart.getAdditional();
			if (map != null) {
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					data.setAdditional(key, chart.getAdditional(key));
				}
			}

			if (chart.hasyearData()) {
				for (int i = 0; i < chart.getYearCount(); i++) {
					data.addYearData(filterYearData(chart.getYearData(i), varSet));
				}
			}

			Object obj = chart.getAxis();
			if (obj != null && obj instanceof AppendAxis) {
				AppendAxis loop = (AppendAxis) obj;
				int st = Value.intOf(loop.getStart(), varSet); // 循环开始
				int ed = Value.intOf(loop.getEnd(), varSet); // 循环结束
				int sp = Value.intOf(loop.getStep(), varSet); // 循环步长

				data.setStart(st);
				data.setEnd(ed);
				data.setStep(sp);
				data.setSize(loop.getElementNum());
				Stack nVars = new Stack(varSet);
				for (int n = st; n <= ed; n = n + sp) {
					nVars.set(loop.getAxisVar(), new Integer(n));
					for (int m = 0; m < loop.getElementNum(); m++) {
						Object lObj = loop.getElement(m);
						if (lObj instanceof AppendItem) {
							AppendItem item = (AppendItem) lObj;

							int subST = item.getStart() == null ? st : Value.intOf(item.getStart(), varSet);
							int subED = item.getEnd() == null ? ed : Value.intOf(item.getEnd(), varSet);

							if (n >= subST && n <= subED) // 判断是否在当前区间内
							{
								String name = item.getName();
								String type = item.getType();
								String color = item.getColor();

								Value v = Value.valueOf(item.getContent(), nVars);

								data.setItem(m, n, subST, subED, type, name, color,
										v == null || v.getValue() == null || !v.isDecimal() ? null : v.toDecimal());
							}
						}
					}
				}
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getName() {
		return "chart@axa";
	}
}
