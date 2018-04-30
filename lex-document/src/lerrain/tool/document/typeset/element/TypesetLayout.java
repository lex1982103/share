package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;

public class TypesetLayout extends TypesetPanel
{
	public static final int MODE_TEXT			= 1;
	
	public static final int BASELINE_TOP		= 1;
	public static final int BASELINE_CENTER		= 2;
	public static final int BASELINE_BOTTOM		= 3;
	
	int mode = MODE_TEXT; //
	int baseLine = BASELINE_BOTTOM; //3 底部 2 中部 1 上部，即不同大小的文字怎么对齐 
	
	public TypesetLayout(int mode)
	{
		this.mode = mode;
	}

	public LexElement build(TypesetParameters tvs)
	{
		boolean fixed = isFixed();
		int bodyWidth = tvs.getPaper().getBody().getWidth().value(tvs);
		
		int width = this.getWidth() == null ? 0 : this.getWidth().value(tvs);
		int height = this.getHeight() == null ? 0 : this.getHeight().value(tvs);

		DocumentPanel dPanel = new DocumentPanel();
		dPanel.setType(1);
//		dPanel.setAdditional("type", "layout");
		dPanel.setColor(color);
		dPanel.setBgColor(this.getBgColor());
		dPanel.setX(this.getX() == null ? 0 : this.getX().value(tvs));
		dPanel.setY(tvs.getDatum() + (this.getY() == null ? 0 : this.getY().value(tvs)));
		dPanel.setHorizontalAlign(this.getAlign());
		dPanel.setVerticalAlign(this.getVerticalAlign());
		dPanel.setBorder(valueOf(getLeftBorder(), tvs), valueOf(getTopBorder(), tvs), valueOf(getRightBorder(), tvs), valueOf(getBottomBorder(), tvs));
		dPanel.setBorderColor(this.getBorderColor());

		TypesetParameters tvs2 = pack(tvs);
		tvs2.setStreamY(tvs.getStreamY() + dPanel.getY());

		int ww = this.getWidth().value(tvs);
		
		int x = 0, y = 0, h = 0, p = 0;
		for (int i = 0; i < elementList.size(); i++)
		{
			TypesetElement iye = (TypesetElement)elementList.get(i);
			if (iye.getFont() == null)
				iye.setFont(this.getFont());
			if (iye.getColor() == null)
				iye.setColor(this.getColor());

			tvs2.set("text_x", new Integer(x));
			tvs2.set("text_y", new Integer(y));
			tvs2.set("max", ww);

			LexElement ile = iye.build(tvs2);
			
			if (ile == null)
				continue;

			LexElement[] list;

			if (iye.getWidth() != null || !(ile instanceof DocumentPanel))
			{
				list = new LexElement[1];
				list[0] = ile;
			}
			else
			{
				DocumentPanel dp = (DocumentPanel) ile;
				int s = dp.getElementCount();
				list = new LexElement[s];
				for (int j = 0; j < s; j++)
					list[j] = dp.getElement(j);
			}

			for (LexElement e : list)
			{
//				System.out.print(e.getX() + " ");
//				System.out.print(e.getY() + " ");
//				System.out.print(e.getWidth() + " ");
//				System.out.print(e.getHeight() + " ");
//
//				if (e instanceof DocumentText)
//					System.out.print(((DocumentText)e).getText());
//				else
//					System.out.print(e);
//
//				System.out.println();

				if (x + e.getWidth() > width || x + e.getWidth() > bodyWidth)
				{
					resetLastLine(dPanel, y, h);

					y += h;
					e.setLocation(0, y);

					x = e.getWidth();
					h = e.getHeight();
					if (p < x)
						p = x;
				}
				else
				{
					e.setLocation(x, y);

					x += e.getWidth();
					if (p < x)
						p = x;
					if (h < e.getHeight())
						h = e.getHeight();
				}
				dPanel.add(e);
			}
		}

		resetLastLine(dPanel, y, h);
		y += h;
		
		//基准坐标可以作为推移画板的手段
		if (height < tvs2.getDatum())
			height = tvs2.getDatum();
			
		if (!fixed)
		{
			if (width < p)
				width = p;
			if (height < y)
				height = y;
		}

		int s = dPanel.getElementCount();
		for (int i=0;i<s;i++)
		{
			LexElement e = dPanel.getElement(i);
			
			if (this.getAlign() == LexElement.ALIGN_CENTER)
			{
				e.setX((width - p) / 2 + e.getX());
			}
			else if (this.getAlign() == LexElement.ALIGN_RIGHT)
			{
				e.setX(width - p + e.getX());
			}

			if (this.getVerticalAlign() == LexElement.ALIGN_MIDDLE)
			{
				e.setY((height - y) / 2 + e.getY());
			}
			else if (this.getAlign() == LexElement.ALIGN_BOTTOM)
			{
				e.setY(height - y + e.getY());
			}
		}
		
		dPanel.setSize(width, height);
		resetY(tvs, dPanel);

		return dPanel;
	}
	
	private void resetLastLine(DocumentPanel dPanel, int y, int h)
	{
		if (baseLine == TypesetLayout.BASELINE_BOTTOM)
		{
			for (int k=dPanel.getElementCount()-1;k>=0;k--) //下对齐
			{
				LexElement l = dPanel.getElement(k);
				if (l.getY() == y)
					l.setY(y + h - l.getHeight());
				else
					break;
			}
		}
		else if (baseLine == TypesetLayout.BASELINE_CENTER)
		{
			for (int k=dPanel.getElementCount()-1;k>=0;k--) //下对齐
			{
				LexElement l = dPanel.getElement(k);
				if (l.getY() == y)
					l.setY(y + (h - l.getHeight()) / 2);
				else
					break;
			}
		}
	}

	public int getBaseLine()
	{
		return baseLine;
	}

	public void setBaseLine(int baseLine)
	{
		this.baseLine = baseLine;
	}
}
