package lerrain.tool.document.typeset.element.sheet;

import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexFont;
import lerrain.tool.document.LexFontFamily;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.element.TypesetText;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;
import lerrain.tool.formula.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypesetSheet extends TypesetElement
{
	Formula header, content;
	Formula colWidth, colAlign;
	
	SheetTheme theme;

	int rowHeight = 60; //行高
	boolean expand = true; //每一行是否固定行高

	boolean nextHeader = true; //到下一页时，是否重画表头
	int headerPadding = 0;
	LexColor headerColor = LexColor.WHITE;
	LexColor headerBgColor = LexColor.DARK_CYAN;
	LexColor headerBorderColor = LexColor.WHITE;

	LexColor innerBorderColor = null;

	int measureMode = 0; //标题和内容平方差、标题平方差、内容平方差、内容最大
	int[] colMargin = new int[4];

	//临时计算的变量，理论上一个模板在多个并发同时进行排版的时候有可能出现冲突，所以build前面要加锁，这个问题是很容易解决的，以后有时间改一下
	DocumentPanel dPanel;

	private void initStyle(TypesetParameters tvs)
	{
		Map style = getStyle();
		if (style != null)
		{
			String str = (String)style.get("line-color");
			if (str != null)
				innerBorderColor = getColor(str);

			str = (String)style.get("header-color");
			if (str != null)
				headerColor = getColor(str);

			str = (String)style.get("header-bgcolor");
			if (str != null)
				headerBgColor = getColor(str);

			str = (String)style.get("header-border-color");
			if (str != null)
				headerBorderColor = getColor(str);

			str = (String)style.get("col-margin");
			if (str != null)
			{
				if (str.indexOf(",") >= 0)
				{
					String[] s = str.split(",");
					for (int i=0;i<s.length;i++)
						colMargin[i] = Integer.parseInt(s[i]);
				}
				else
				{
					for (int i=0;i<4;i++)
						colMargin[i] = Integer.parseInt(str);
				}
			}

			String measure = (String)style.get("measure");
			if ("title".equals(measure))
				measureMode = 1;
			else if ("content".equals(measure))
				measureMode = 2;
			else if ("max".equals(measure))
				measureMode = 3;
			else if ("equal".equals(measure))
				measureMode = 4;
		}
	}

	public synchronized LexElement build(TypesetParameters tvs)
	{
		initStyle(tvs);

		dPanel = new DocumentPanel();
		dPanel.setSplit(this.isSplit());
		dPanel.setBorder(valueOf(getLeftBorder(), tvs), valueOf(getTopBorder(), tvs), valueOf(getRightBorder(), tvs), valueOf(getBottomBorder(), tvs));
		dPanel.setBorderColor(this.getBorderColor());

		if (this.getX() != null)
			dPanel.setX(this.getX().value(tvs));
		else
			dPanel.setX(0);

		if (this.getY() != null)
			dPanel.setY(tvs.getDatum() + this.getY().value(tvs));
		else
			dPanel.setY(tvs.getDatum());

		SheetBlank[][] header = this.header == null ? null : toArray2D(this.header.run(tvs));
		SheetBlank[][] content = this.content == null ? null : toArray2D(this.content.run(tvs));

		double[] colsw = this.colWidth == null ? null : toArrayF(this.colWidth.run(tvs));
		int[] cols = getCols(tvs, content[0].length, colsw);

		LexColor color = this.getColor();
		LexColor bgColor = this.getBgColor();

		if (dPanel.canSplit())
		{
			dPanel.setAdditional("title", headerOf(pack(tvs), header, cols));
			insertTable(pack(tvs), dPanel, 0, content, false, color != null ? color : this.getColor(), bgColor != null ? bgColor : this.getBgColor(), innerBorderColor == null ? -1 : 0, innerBorderColor, cols);
		}
		else
		{
			int y = insertTable(pack(tvs), dPanel, 0, header, true, headerColor, headerBgColor, headerPadding, headerBorderColor, cols);
			insertTable(pack(tvs), dPanel, y, content, false, color != null ? color : this.getColor(), bgColor != null ? bgColor : this.getBgColor(), innerBorderColor == null ? -1 : 0, innerBorderColor, cols);
		}

		if (this.getWidth() != null)
			dPanel.setWidth(this.getWidth().value(tvs));

		if (this.getHeight() != null)
			dPanel.setHeight(this.getHeight().value(tvs));

		resetY(tvs, dPanel);

		return dPanel;
	}

	private int getAlign(int v)
	{
		if (v == 1 || v == 4 || v == 7)
			return DocumentText.ALIGN_LEFT;
		if (v == 3 || v == 6 || v == 9)
			return DocumentText.ALIGN_RIGHT;
		return DocumentText.ALIGN_CENTER;
	}

	private int getVerticalAlign(int v)
	{
		if (v == 1 || v == 2 || v == 3)
			return DocumentText.ALIGN_TOP;
		if (v == 7 || v == 8 || v == 9)
			return DocumentText.ALIGN_BOTTOM;
		return DocumentText.ALIGN_MIDDLE;
	}

	private int[] getCols(TypesetParameters tvs, int len, double[] colsw)
	{
		int max = this.getWidth() != null ? this.getWidth().value(tvs) : tvs.getPaper().getBody().getWidth().value(tvs);
		int[] cols = new int[len];

		if (colsw == null)
		{
			colsw = new double[len];
			for (int i=0;i<len;i++)
				colsw[i] = 1;
		}

		double totalw = 0;
		for (int i=0;i<len;i++)
		{
			totalw += colsw[i];
		}

		int full = 0;
		for (int i=0;i<len-1;i++)
		{
			cols[i] = (int)Math.round(max * colsw[i] / totalw);
			full += cols[i];
		}
		cols[len - 1] = max - full;

		return cols;
	}

	private DocumentPanel headerOf(TypesetParameters tvs, SheetBlank[][] content, int[] cols)
	{
		DocumentPanel dPanel = new DocumentPanel();
		dPanel.setSplit(false);
		insertTable(tvs, dPanel, 0, content, true, headerColor, headerBgColor, headerPadding, headerBorderColor, cols);

		return dPanel;
	}

	private int insertTable(TypesetParameters tvs, DocumentPanel dPanel, int y, SheetBlank[][] content, boolean header, LexColor color, LexColor bgColor, int tableLine, LexColor borderColor, int[] cols)
	{
		if (content == null || content.length == 0)
			return 0;

		int x = 0;
		int width = 0, height = 0;

		TypesetText[][] tt = new TypesetText[content.length][content[0].length];
		for (int i=0;i<content.length;i++)
		{
			int lineH = rowHeight;

			for (int j=0;j<content[i].length;j++)
			{
				SheetBlank t = content[i][j];
				if (t != null && !t.isVirtual())
				{
					int ww = 0;
					int cs = t.getCol();
					int rs = t.getRow();

					LexFont font;
					if (t.getFont() != null || t.getFontSize() > 0)
					{
						if (t.getFont() != null)
						{
							LexFontFamily family = tvs.getTypeset().getFontFamily(t.getFont());
							font = family.derive(t.getFontSize() > 0 ? t.getFontSize() : this.getFont().getSize());
						}
						else
						{
							font = this.getFont().derive(t.getFontSize());
						}
					}
					else
					{
						font = this.getFont();
					}

					for (int k=0;k<cs;k++)
						ww += cols[j + k];

					int[] margin = t.getMargin() == null ? colMargin : t.getMargin();
					if (expand && rs == 1)
					{
						Object[] r = TypesetText.format(t.getText(), font, ww, -1);
						int cy = ((Integer)r[0]).intValue() + margin[1] + margin[3];

						if (lineH < cy)
							lineH = cy;
					}

					TypesetText tx = new TypesetText();
					tx.setMargin(margin);
					tx.setX(x);
					tx.setY(y);
					tx.setWidth(ww);
					tx.setHeight(lineH);
					tx.setValue(FormulaUtil.formulaOf("'" + t.getText() + "'"));

					tx.setFixed(false);
					tx.setSplit(false);
					tx.setColor(color);
					tx.setBgColor(bgColor);
					tx.setBorderColor(borderColor);
					tx.setFont(font);
					tx.setAlign(t.getAlign());
					tx.setVerticalAlign(t.getVerticalAlign());

					if (theme != null)
					{
						if (header)
							theme.styleTitle(this, tx, i, j);
						else
							theme.styleContent(this, tx, i, j);
					}

					tt[i][j] = tx;
				}

				x += cols[j];
				if (x > width)
					width = x;
			}

			if (lineH > rowHeight) //本行拉伸到指定位置
			{
				for (int j=0;j<content[i].length;j++)
				{
					if (tt[i][j] != null)
						tt[i][j].setHeight(lineH);
				}
			}

			y += lineH;
			if (y > height)
				height = y;

			x = 0;
			for (int j=0;j<content[i].length;j++) //处理前面的跨行到本行的格子
			{
				x += cols[j];
				if (x > width)
					width = x;

				if (content[i][j] != null && content[i][j].isVirtual())
				{
					int m = content[i][j].getY();
					int n = content[i][j].getX();
					tt[m][n].setWidth(x - tt[m][n].getX().value(tvs));
					tt[m][n].setHeight(y - tt[m][n].getY().value(tvs));
				}
			}

			x = 0;
		}

		for (int i=0;i<content.length;i++)
		{
			for (int j=0;j<content[i].length;j++)
			{
				if (tt[i][j] != null)
				{
					LexElement dt = tt[i][j].build(tvs);
					if (dt instanceof DocumentPanel)
						dt.setSplit(false);
					dPanel.add(dt);
				}
			}
		}

		dPanel.setWidth(width);
		dPanel.setHeight(height);

		return height;
	}

	public double[] toArrayF(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof double[])
		{
			return (double[])obj;
		}
		else if (obj instanceof float[])
		{
			double[] s = null;
			float[] l1 = (float[])obj;
			if (l1 != null)
			{
				s = new double[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = l1[i];
			}
			return s;
		}
		else if (obj instanceof Object[])
		{
			double[] s = null;
			Object[] l1 = (Object[])obj;
			if (l1 != null)
			{
				s = new double[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = Value.doubleOf(l1[i]);
			}
			return s;
		}

		return null;
	}

	public int[] toArrayInt(Object obj)
	{
		if (obj == null)
			return null;
		if (obj instanceof int[])
		{
			return (int[])obj;
		}
		else if (obj instanceof Object[])
		{
			int[] s = null;
			Object[] l1 = (Object[])obj;
			if (l1 != null)
			{
				s = new int[l1.length];
				for (int i = 0; i < l1.length; i++)
					s[i] = Value.intOf(l1[i]);
			}
			return s;
		}

		return null;
	}

	public SheetBlank toBlank(Object obj)
	{
		SheetBlank sb = new SheetBlank();
		if (obj instanceof Map)
		{
			Map map = (Map)obj;
			sb.setText((String) map.get("text"));
			sb.setCol(Value.intOf(map.get("col"), 1));
			sb.setRow(Value.intOf(map.get("row"), 1));
			sb.setFont((String)map.get("font"));
			sb.setFontSize(Value.intOf(map.get("fontSize"), 0));

			int align = Value.intOf(map.get("align"), 0);
			if (align > 0)
				sb.setAlign(getAlign(align) | getVerticalAlign(align));
			else
				sb.setAlign(this.getAlign() | this.getVerticalAlign());

			Object m = map.get("margin");
			Object[] str = m instanceof List ? ((List)m).toArray() : (Object[])m;
			if (str != null)
			{
				int[] margin = new int[4];
				for (int i=0;i<4 && i<str.length;i++)
					margin[i] = Value.intOf(str[i], 0);
				sb.setMargin(margin);
			}
		}
		else
		{
			sb.setText(obj == null ? null : obj.toString());
			sb.setAlign(this.getAlign() | this.getVerticalAlign());
		}
		return sb;
	}

	public SheetBlank[] toArray(Object obj)
	{
		if (obj == null)
			return null;

		if (obj instanceof String[])
		{
			String[] s = (String[])obj;
			SheetBlank[] res = new SheetBlank[s.length];
			for (int j = 0; j < s.length; j++)
			{
				res[j] = new SheetBlank();
				res[j].setText(s[j]);
			}
			return res;
		}
		if (obj instanceof Object[])
		{
			Object[] r1 = (Object[]) obj;
			SheetBlank[] res = new SheetBlank[r1.length];
			for (int j = 0; j < r1.length; j++)
				res[j] = toBlank(r1[j]);
			return res;
		}
		if (obj instanceof List)
		{
			List l1 = (List)obj;
			if (l1.isEmpty())
				return null;
			SheetBlank[] res = new SheetBlank[l1.size()];
			for (int j=0;j<l1.size();j++)
				res[j] = toBlank(l1.get(j));
			return res;
		}

		return null;
	}

	public SheetBlank[][] toArray2D(Object obj)
	{
		SheetBlank[][] res = null;

		if (obj instanceof String[][])
		{
			String[][] s = (String[][])obj;
			res = new SheetBlank[s.length][];
			for (int i=0;i<s.length;i++)
			{
				res[i] = new SheetBlank[s[i].length];
				for (int j = 0; j < s[i].length; j++)
				{
					res[i][j] = new SheetBlank();
					res[i][j].setText(s[i][j]);
				}
			}
		}
		else if (obj instanceof Object[])
		{
			Object[] r1 = (Object[])obj;
			res = new SheetBlank[r1.length][];
			for (int i=0;i<r1.length;i++)
			{
				res[i] = toArray(r1[i]);
			}
		}
		else if (obj instanceof List)
		{
			List l1 = (List)obj;
			if (l1.isEmpty())
				return null;
			List l2 = new ArrayList();

			int max = 0;
			for (int i=0;i<l1.size();i++)
			{
				SheetBlank[] s2 = toArray(l1.get(i));
				if (s2 == null)
					s2 = new SheetBlank[0];
				if (s2.length > max)
					max = s2.length;
				l2.add(s2);
			}

			res = new SheetBlank[l2.size()][max];
			for (int i=0;i<l2.size();i++)
			{
				SheetBlank[] s2 = (SheetBlank[])l2.get(i);
				for (int j=0;j<s2.length;j++)
					res[i][j] = s2[j];
			}
		}

		for (int i=0;i<res.length;i++)
		{
			for (int j = 0; j < res[i].length; j++)
			{
				if (res[i][j] != null && (res[i][j].getCol() > 1 || res[i][j].getRow() > 1))
				{
					for (int x = j; x < j + res[i][j].getCol(); x++)
					{
						for (int y = i; y < i + res[i][j].getRow(); y++)
						{
							if (x > j || y > i)
							{
								res[y][x] = null;
							}
						}
					}

					int x = j + res[i][j].getCol() - 1;
					int y = i + res[i][j].getRow() - 1;

					res[y][x] = new SheetBlank();
					res[y][x].setX(j);
					res[y][x].setY(i);
				}
			}
		}

		return res;
	}

	public void setHeader(Formula header)
	{
		this.header = header;
	}

	public void setContent(Formula content)
	{
		this.content = content;
	}

	public void setMeasureMode(int measureMode)
	{
		this.measureMode = measureMode;
	}

	public SheetTheme getTheme()
	{
		return theme;
	}

	public void setTheme(SheetTheme theme)
	{
		this.theme = theme;
	}

	public Formula getColWidth()
	{
		return colWidth;
	}

	public void setColWidth(Formula colWidth)
	{
		this.colWidth = colWidth;
	}

	public Formula getColAlign()
	{
		return colAlign;
	}

	public void setColAlign(Formula colAlign)
	{
		this.colAlign = colAlign;
	}
}
