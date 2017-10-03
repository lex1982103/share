package lerrain.project.insurance.plan.filter.chart;

import java.util.ArrayList;
import java.util.List;

import lerrain.project.insurance.plan.Commodity;
import lerrain.project.insurance.plan.filter.FilterCommodity;
import lerrain.project.insurance.plan.filter.FilterException;
import lerrain.project.insurance.product.attachment.chart.ChartDef;
import lerrain.project.insurance.product.attachment.chart.ChartItem;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;
import lerrain.tool.script.Stack;

public class ChartFilter implements FilterCommodity
{
	private static final long serialVersionUID = 1L;

	private Chart filterChart(ChartDef chartDef, Factors factors)
	{
		try
		{
			Chart chart = new Chart();
			
			int st = Value.intOf(chartDef.getStart(), factors); // 循环开始
			int ed = Value.intOf(chartDef.getEnd(), factors); // 循环结束
			int sp = 1; // 循环步长
			double max = 0, min = -1;

			chart.setStart(st);
			chart.setEnd(ed);
			chart.setStep(sp);

			Stack pe = new Stack(factors);
			pe.declare(chartDef.getVarName());
			
			for (int m = 0; m < chartDef.size(); m++)
			{
				ChartItem item = chartDef.getItem(m);
				ChartLine line = new ChartLine(item.getType());
				line.setName(item.getName());
				line.setColor(colorOf(item.getColor()));
				
				for (int n = st; n <= ed; n = n + sp)
				{
					pe.set(chartDef.getVarName(), new Integer(n));
					double v = Value.doubleOf(item.getFormula(), pe);
					if (v > max)
						max = v;
					if (v < min || min < 0)
						min = v;
					
					line.setData(n, v);
				}
				
				chart.addLine(line);
			}

			chart.setMax(max);
			chart.setMax(min);

			return chart;
		}
		catch (Exception e)
		{
			throw new FilterException(e);
		}
	}

	public Object filtrate(Commodity product, String attachmentName)
	{
		List result = null;

		List chartList = (List) product.getProduct().getAttachment(attachmentName);
		if (chartList != null)
		{
			result = new ArrayList();
			Factors factors = product.getFactors();

			for (int i = 0; i < chartList.size(); i++)
			{
				Object line = chartList.get(i);

				if (line instanceof ChartDef)
				{
					ChartDef chart = (ChartDef) line;
					Chart dt = filterChart(chart, factors);
					if (dt != null)
						result.add(dt);
				}
			}
		}

		return result;
	}
	
	private int colorOf(String color)
	{
		if ("red".equals(color))
			return 0xFFFF0000;
		if ("black".equals(color))
			return 0xFF000000;
		if ("blue".equals(color))
			return 0xFF0000FF;
		if ("cyan".equals(color))
			return 0xFF00CCAA;
		if ("green".equals(color))
			return 0xFF009900;
		
		try
		{
			if (color != null)
			{
				String c = color;
				if (c.startsWith("[#]"))
				{
					c = c.substring(1).trim();
				}
				else
				{
					c = c.trim();
				}
				
				if (c.length() > 6)
					c = c.substring(c.length() - 6);
				return Integer.parseInt(c, 16) | 0xFF000000;
			}
		}
		catch (Exception e)
		{
			System.out.println("无法解析的颜色：" + color + "，默认为黑色。");
		}
		
		return 0xFF000000;
	}
}
