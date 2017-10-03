package lerrain.tool.document.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.DocumentExportException;
import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexDocument;
import lerrain.tool.document.LexPage;
import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.DocumentLine;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentRect;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.size.Paper;

public class ObjectPainter implements Painter
{
	public ObjectPainter()
	{
	}
	
	public void build(LexDocument doc, List pages)
	{
		int pageNum = doc.size();
		
		for (int i = 0; i < pageNum; i++)
		{
			Map m = new HashMap();
			
			LexPage page = doc.getPage(i);
			Paper paper = page.getPaper();

			m.put("w", new Integer(paper.getWidth()));
			m.put("h", new Integer(paper.getHeight()));
			m.put("bgcolor", "FFFFFF");
			
			m.put("image", new ArrayList());
			m.put("line", new ArrayList());
			m.put("rect", new ArrayList());
			m.put("text", new ArrayList());
			m.put("panel", new ArrayList());

			int eNum = page.getElementNum();
			for (int j = 0; j < eNum; j++)
			{
				LexElement element = page.getElement(j);
				translateElement(m, 0, 0, element);
			}
			
			pages.add(m);
		}
	}
	
	private Map addElement(Map m, String type, int x, int y, LexElement element)
	{
		int sx = x + element.getX();
		int sy = y + element.getY();
		int sw = element.getWidth();
		int sh = element.getHeight();
		
		Map r = new HashMap();
		r.put("x", new Integer(sx));
		r.put("y", new Integer(sy));
		r.put("w", new Integer(sw));
		r.put("h", new Integer(sh));
		r.put("c", translate(element.getColor()));
		r.put("bg", translate(element.getBgColor()));
		r.put("bdc", translate(element.getBorderColor()));
		r.put("bd", new int[] {element.getLeftBorder(), element.getTopBorder(), element.getRightBorder(), element.getBottomBorder()});
		
		List list = (List)m.get(type);
		list.add(r);
		
		return r;
	}
	
	protected void translateElement(Map m, int x, int y, LexElement element)
	{
		if (element instanceof DocumentRect)
		{
			addElement(m, "rect", x, y, element);
		}
		else if (element instanceof DocumentLine)
		{
			addElement(m, "line", x, y, element);
		}
		else if (element instanceof DocumentImage)
		{
			DocumentImage dImage = (DocumentImage)element;
			Map r = addElement(m, "image", x, y, dImage);
			
			if (dImage.hasImage(DocumentImage.TYPE_URL))
			{
				r.put("url", dImage.getImage(DocumentImage.TYPE_URL));
			}
			if (dImage.hasImage(DocumentImage.TYPE_PATH))
			{
				r.put("path", dImage.getImage(DocumentImage.TYPE_PATH));
			}
		}
		else if (element instanceof DocumentText)
		{
			DocumentText dText = (DocumentText)element;
			Map r = addElement(m, "text", x, y, dText);
			
			r.put("v", dText.getText());
			
			if (dText.getFont() != null)
			{
				r.put("f", dText.getFont().getFamily().getName());
				r.put("fs", new Float(dText.getFont().getSize()));
			}
			else
			{
				r.put("f", "song");
				r.put("fs", new Integer(30));
			}
			
			r.put("lh", new Integer(dText.getLineHeight()));
			r.put("va", dText.getVerticalAlign() == DocumentText.ALIGN_MIDDLE ? new Integer(0) : dText.getVerticalAlign() == DocumentText.ALIGN_BOTTOM ? new Integer(1) : new Integer(-1));
			r.put("ha", dText.getHorizontalAlign() == DocumentText.ALIGN_CENTER ? new Integer(0) : dText.getHorizontalAlign() == DocumentText.ALIGN_RIGHT ? new Integer(1) : new Integer(-1));
		}
		else if (element instanceof DocumentPanel)
		{
			addElement(m, "panel", x, y, element);

			DocumentPanel dPanel = (DocumentPanel)element;
			int count = dPanel.getElementCount();
			
			for (int i = 0; i < count; i++)
				translateElement(m, x + dPanel.getX(), y + dPanel.getY(), dPanel.getElement(i));
		}
	}
	
	public String translate(LexColor color)
	{
		if (color != null)
		{
			String r = (color.getRed() < 16 ? "0" : "") + Integer.toHexString(color.getRed());
			String g = (color.getGreen() < 16 ? "0" : "") + Integer.toHexString(color.getGreen());
			String b = (color.getBlue() < 16 ? "0" : "") + Integer.toHexString(color.getBlue());
			
			return r + g + b;
		}
		
		return null;
	}

	public void paint(LexDocument doc, Object canvas, int canvasType)
	{
		if (canvasType != Painter.OBJECT)
			throw new DocumentExportException("object输出器不支持的输出格式");
		
		build(doc, (List)canvas);
	}
}
