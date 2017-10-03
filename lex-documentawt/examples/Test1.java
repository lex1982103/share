import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.DocumentUtil;
import lerrain.tool.document.export.PngPainter;
import lerrain.tool.document.typeset.Typeset;
import lerrain.tool.document.typeset.TypesetDocument;
import lerrain.tool.document.typeset.TypesetUtil;
import lerrain.tool.document.typeset.environment.TextDimensionAwt;
import lerrain.tool.formula.Factors;


public class Test1 
{
	public static void main(String[] arg)
	{
		Map v = new HashMap();
		v.put("t1", new Integer(1));
		v.put("t2", "sasa");
		System.out.println(v);
		
//		DocumentUtil.addDocumentPainter("png", new PngPainter());
//		DocumentUtil.addFont("kai", "c:/windows/fonts/STKAITI.TTF");
//		DocumentUtil.addFont("hei", "c:/windows/fonts/simhei.ttf");
//		DocumentUtil.addFont("song", "c:/windows/fonts/simsunb.ttf");
//
//		TypesetUtil.setTextDimension(new TextDimensionAwt());
//		
//		Typeset t = TypesetUtil.parseTypeset("examples/simple.xml");
//		TypesetDocument doc = new TypesetDocument(t);
//		
//		doc.build(new Parameters() 
//		{
//			public Object get(String name) 
//			{
//				if ("FONT_EXT1".equals(name))
//					return "c:/windows/fonts/STXINGKA.TTF";
//				
//				return null;
//			}
//		}); 
//		
//		DocumentUtil.paint(doc, "png", new File("C:/Users/lerrain/Desktop/temp"));
	}
}
