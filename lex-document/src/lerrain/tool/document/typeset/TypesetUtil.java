package lerrain.tool.document.typeset;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.typeset.element.TypesetText;
import lerrain.tool.document.typeset.environment.TextDimension;
import lerrain.tool.document.typeset.parser.ParserXml;
import lerrain.tool.document.typeset.parser.TypesetParser;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.FormulaUtil;

public class TypesetUtil
{
	public static final int MODE_ALWAYS			= 1;
	public static final int MODE_FAIL			= 2;
	
	private static TypesetParser parser;
	
	private static int mode						= MODE_ALWAYS;
	
	private static String resourcePath			= "";

	private static String fontPath				= "";

	private static Map<String, TypesetElementFactory> fs = new HashMap<>();

	static
	{
		parser = new ParserXml();
	}

	public static void addElementFactory(String label, TypesetElementFactory tef)
	{
		fs.put(label, tef);
	}

	public static TypesetElementFactory getElementFactory(String label)
	{
		return fs.get(label);
	}

	public static TypesetParser getTypesetParser()
	{
		return parser;
	}

	public static void setTypesetParser(TypesetParser parser)
	{
		TypesetUtil.parser = parser;
	}
	
	public static Typeset parseTypeset(String fileName)
	{
		return parser.parse(new File(fileName));
	}
	
//	public static Typeset parseTypeset(InputStream is)
//	{
//		return parser.parse(is);
//	}
	
	public static void setTextDimension(TextDimension textDimension)
	{
		TypesetText.setTextDimension(textDimension);
	}
	
	public static void setResourcePath(String path)
	{
		TypesetUtil.resourcePath = path;
	}
	
	public static String getResourcePath()
	{
		return resourcePath;
	}

	public static int getMode()
	{
		return mode;
	}

	public static void setMode(int mode)
	{
		TypesetUtil.mode = mode;
	}
	
	public static Formula formulaOf(String formula)
	{
		return FormulaUtil.formulaOf(formula);
	}

	public static String getFontPath()
	{
		return fontPath;
	}

	public static void setFontPath(String fontPath)
	{
		TypesetUtil.fontPath = fontPath;
	}
}
