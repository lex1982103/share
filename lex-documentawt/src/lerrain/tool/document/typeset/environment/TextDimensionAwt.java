package lerrain.tool.document.typeset.environment;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.peer.LightweightPeer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.LexFont;
import lerrain.tool.document.LexFontFamily;
import lerrain.tool.document.typeset.TypesetCoord;
import lerrain.tool.document.typeset.TypesetUtil;
import sun.font.FontManager;
import sun.font.FontManagerFactory;
import sun.font.SunFontManager;

import javax.swing.*;

public class TextDimensionAwt implements TextDimension
{
	Map fontMap = new HashMap();
	
//	Graphics2D g2D = (Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
	
	public synchronized TypesetCoord getSize(LexFont font, String text)
	{
		if (text == null || "".equals(text))
			return new TypesetCoord(0, 0);

		Font f = getFont(font.getFamily().getPath(), font.getSize());
		FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);

		int w = fm.stringWidth(text);
		int h = fm.getHeight();

		return new TypesetCoord(w + 2, h + 2);

//		g2D.setFont(f);
//		FontRenderContext frc = g2D.getFontRenderContext();
//		Rectangle2D rect = f.getStringBounds(text, frc);
//
//		return new TypesetCoord((int)Math.round(rect.getWidth() + 2), (int)Math.round(rect.getHeight() + 2));
	}

	private Font getFont(String path, float size)
	{
		Font font = (Font)fontMap.get(path);
		if (font == null)
		{
			try
			{
				File file = new File(TypesetUtil.getFontPath() + path);
				InputStream is;
				is = new FileInputStream(file);
				font = Font.createFont(Font.TRUETYPE_FONT, is);
				fontMap.put(path, font);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if (font == null)
		{
			font = new Font("黑体", Font.PLAIN, (int)size);
			fontMap.put(path, font);
		}
		else
		{
			font = font.deriveFont(size);
		}
		
		return font;
	}

	public static void main(String[] arg)
	{
		LexFontFamily ff = new LexFontFamily("hei", "X:\\FZFSJW.TTF");
		LexFont font = new LexFont(ff, 16);

		TextDimensionAwt tw = new TextDimensionAwt();
		TypesetCoord tc = tw.getSize(font, "啊啊啊are you ok ?");

		System.out.println(tc.x);
		System.out.println(tc.y);
	}
}
