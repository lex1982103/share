package lerrain.tool.document;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.document.export.Painter;
import lerrain.tool.document.size.Paper;
import lerrain.tool.document.size.PaperA4;
import lerrain.tool.document.size.PaperA4Rotate;


public class DocumentUtil
{
	static Map exporterMap = new HashMap();
	
	static List paperList = new ArrayList();
	
	static Map fontMap = new HashMap();
	
	static
	{
		paperList.add(new PaperA4());
		paperList.add(new PaperA4Rotate());
		
//		DocumentUtil.addDocumentPainter("png", new PngPainter());
//		DocumentUtil.addDocumentPainter(new SwingPainter());
//		DocumentUtil.addDocumentPainter(new PngFiles());
	}
	
	/**
	 * 按照名称获得纸张规格
	 * @param name 纸张规格名称A4/A3/...
	 * @return 纸张规格定义
	 */
	public static Paper getPaperSize(String name)
	{
		for (int i = 0; i < paperList.size(); i++)
		{
			Paper paperSize = (Paper) paperList.get(i);
			if (paperSize.getName().equalsIgnoreCase(name)) 
			{
				return paperSize;
			}
		}
		
		return null;
	}
	
	public static void addFont(String fontName, String fontPath)
	{
		fontMap.put(fontName, new LexFontFamily(fontName, fontPath));
	}
	
	public static LexFontFamily getFont(String fontName)
	{
		return (LexFontFamily)fontMap.get(fontName);
	}
	
	public static Painter getPainter(String name)
	{
		return (Painter)exporterMap.get(name);
	}
	
	public static void addDocumentPainter(String name, Painter painter)
	{
		exporterMap.put(name, painter);
	}
	
	public static void paint(LexDocument doc, String name, File directory)
	{
		getPainter(name).paint(doc, directory, Painter.DIRECTORY);
	}

	public static void paint(LexDocument doc, String name, OutputStream os)
	{
		getPainter(name).paint(doc, os, Painter.STREAM);
	}
	
	public static void paint(LexDocument doc, Painter painter, OutputStream os)
	{
		painter.paint(doc, os, Painter.STREAM);
	}
	
	public static void paint(LexDocument doc, Painter painter, File directory)
	{
		painter.paint(doc, directory, Painter.DIRECTORY);
	}
	
	public static void paint(LexDocument doc, Painter painter, Object resObject)
	{
		painter.paint(doc, resObject, Painter.OBJECT);
	}
}
