package lerrain.tool.document.typeset.element;

import java.util.ArrayList;
import java.util.List;

import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetNumber;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.formula.Formula;

public class TypesetPanel extends TypesetElement
{
	List elementList = new ArrayList();
	
	public TypesetPanel(Formula x, Formula y, Formula width, Formula height)
	{
		super.setX(x);
		super.setY(y);
		super.setWidth(width);
		super.setHeight(height);
	}
	
	public TypesetPanel(int x, int y, int width, int height)
	{
		super.setX(x);
		super.setY(y);
		super.setWidth(width);
		super.setHeight(height);
	}
	
	public TypesetPanel(String x, String y, String width, String height)
	{
		super.setX(x);
		super.setY(y);
		super.setWidth(width);
		super.setHeight(height);
	}
	
	public TypesetPanel()
	{
	}
	
	public void add(TypesetElement element)
	{
		elementList.add(element);
	}
	
	public int getElementNum()
	{
		return elementList.size();
	}
	
	public TypesetElement getElement(int index)
	{
		return (TypesetElement)elementList.get(index);
	}

	public LexElement build(TypesetParameters tvs)
	{
		DocumentPanel dPanel = new DocumentPanel();
		
		if (this.getX() != null)
			dPanel.setX(this.getX().value(tvs));
		else
			dPanel.setX(0);
		
		if (this.getY() != null)
			dPanel.setY(tvs.getDatum() + this.getY().value(tvs));
		else
			dPanel.setY(tvs.getDatum());

		TypesetParameters tvs2 = pack(tvs);
		tvs2.setStreamY(tvs.getStreamY() + dPanel.getY());
		
		int width = 0, height = 0;
		for (int i=0;i<elementList.size();i++)
		{
			TypesetElement iye = (TypesetElement)elementList.get(i);
			LexElement ile = iye.isShow(tvs2) ? iye.build(tvs2) : null;
			
			if (ile != null)
			{
				dPanel.add(ile);
				
				if (ile.getX() + ile.getWidth() > width)
					width = ile.getX() + ile.getWidth();
				if (ile.getY() + ile.getHeight() > height)
					height = ile.getY() + ile.getHeight();
			}
		}
		
		//基准坐标可以作为推移画板的手段
		if (height < tvs2.getDatum())
			height = tvs2.getDatum();
			
		if (this.getWidth() != null)
			dPanel.setWidth(this.getWidth().value(tvs));
		else
			dPanel.setWidth(width);
		
		if (this.getHeight() != null)
			dPanel.setHeight(this.getHeight().value(tvs));
		else
			dPanel.setHeight(height);
		
		resetY(tvs, dPanel);
		
		return dPanel;
	}
}
