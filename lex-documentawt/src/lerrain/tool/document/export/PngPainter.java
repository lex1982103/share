package lerrain.tool.document.export;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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

public class PngPainter implements Painter
{
	float scale = 0.5f;
	
	public PngPainter()
	{
	}
	
	public PngPainter(float scale)
	{
		this.scale = scale;
	}
	
	protected int scale(int v)
	{
		return Math.round(v * scale);
	}
	
	protected int scale(float v)
	{
		return Math.round(v * scale);
	}
	
	protected Shape clip(Graphics g, int sx, int sy, int sw, int sh)
	{
		Shape s = g.getClip();
		if (s != null)
		{
			if (s.getBounds().getX() > sx)
				sx = (int)s.getBounds().getX();
			if (s.getBounds().getY() > sy)
				sy = (int)s.getBounds().getY();
			if (s.getBounds().getX() + s.getBounds().getWidth() < sx + sw)
				sw = (int)(s.getBounds().getX() + s.getBounds().getWidth() - sx);
			if (s.getBounds().getY() + s.getBounds().getHeight() < sy + sh)
				sh = (int)(s.getBounds().getY() + s.getBounds().getHeight() - sy);
		}
		g.setClip(sx, sy, sw, sh);
		
		return s;
	}
	
	protected int getImageType()
	{
		return BufferedImage.TYPE_3BYTE_BGR;
	}
	
	public void build(LexDocument doc, File dir)
	{
		int pageNum = doc.size();
		
		for (int i = 0; i < pageNum; i++)
		{
			LexPage page = doc.getPage(i);
			
			Paper paper = page.getPaper();

			int sw = scale(paper.getWidth());
			int sh = scale(paper.getHeight());

			BufferedImage image = new BufferedImage(sw, sh, getImageType());
			Graphics2D g = (Graphics2D)image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, sw, sh);
			
			int eNum = page.getElementNum();
			for (int j = 0; j < eNum; j++)
			{
				LexElement element = page.getElement(j);
				translateElement(g, 0, 0, element);
			}
			
			write(image, dir, i);
		}
	}
	
	protected void write(BufferedImage image, File dir, int i)
	{
		try
		{
			File f = new File(dir.getAbsolutePath() + File.separator + (i + 1) + ".png");
			ImageIO.write(image, "PNG", f);
		} 
		catch (IOException e)
		{
			throw new DocumentExportException("图片写入异常", e);
		}
	}
	
	protected void translateElement(Graphics g, int x, int y, LexElement element)
	{
		int sx = scale(x + element.getX());
		int sy = scale(y + element.getY());
		int sw = scale(element.getWidth());
		int sh = scale(element.getHeight());

		if (element instanceof DocumentRect)
		{
			DocumentRect dRect = (DocumentRect)element;
			g.setColor(translate(dRect.getColor()));
			g.fillRect(sx, sy, sw, sh);
		}
		else if (element instanceof DocumentLine)
		{
			DocumentLine dLine = (DocumentLine)element;
			g.setColor(translate(dLine.getColor()));
			//g.drawLine(scale(x + dLine.getX()), scale(y + dLine.getY()), scale(x + dLine.getX() + dLine.getWidth()), scale(y + dLine.getY() + dLine.getHeight()));
			g.drawLine(sx, sy, sx + sw, sy + sh);
		}
		else if (element instanceof DocumentImage)
		{
			DocumentImage dImage = (DocumentImage)element;
			
			if (dImage.hasImage(DocumentImage.TYPE_FILE))
			{
				try
				{
					Image image = ImageIO.read((File)dImage.getImage(DocumentImage.TYPE_FILE));
					g.drawImage(image, sx, sy, sx + sw, sy + sh, 0, 0, image.getWidth(null), image.getHeight(null), null);
				} 
				catch (IOException e)
				{
					System.out.println("Typeset - WARN: image(" + dImage.getImage(DocumentImage.TYPE_FILE) + ") load failed.");
				}
			}
			else if (dImage.hasImage(DocumentImage.TYPE_SRC))
			{
				try
				{
					Image image = ImageIO.read(new File((String)dImage.getImage(DocumentImage.TYPE_SRC)));
					g.drawImage(image, sx, sy, sx + sw, sy + sh, 0, 0, image.getWidth(null), image.getHeight(null), null);
				} 
				catch (IOException e)
				{
					System.out.println("Typeset - WARN: image(" + dImage.getImage(DocumentImage.TYPE_SRC) + ") load failed.");
				}
			}
			else if (dImage.hasImage(DocumentImage.TYPE_PATH))
			{
				try
				{
					Image image = ImageIO.read(new File((String)dImage.getImage(DocumentImage.TYPE_PATH)));
					g.drawImage(image, sx, sy, sx + sw, sy + sh, 0, 0, image.getWidth(null), image.getHeight(null), null);
				} 
				catch (IOException e)
				{
					System.out.println("Typeset - WARN: image(" + dImage.getImage(DocumentImage.TYPE_PATH) + ") load failed.");
				}
			}
			else
			{
				g.setColor(Color.RED);
				g.fillRect(sx, sy, sw, sh);
			}
		}
		else if (element instanceof DocumentText)
		{
			DocumentText dText = (DocumentText)element;

			Shape s = clip(g, sx, sy, sw, sh);
			
			if (dText.getBgColor() != null)
			{
				g.setColor(translate(dText.getBgColor()));
				g.fillRect(sx, sy, sw, sh);
			}
			
//			if (dText.getBorderColor() != null)
//			{
//				g.setColor(translate(dText.getBorderColor()));
//				if (dText.getTopBorder() >= 0)
//				{
//					int b = scale(dText.getTopBorder());
//					if (b < 1) b = 1;
//					g.fillRect(sx, sy, sw, b);
//				}
//				if (dText.getBottomBorder() >= 0)
//				{
//					int b = scale(dText.getBottomBorder());
//					if (b < 1) b = 1;
//					g.fillRect(sx, sy + sh - b, sw, b);
//				}
//				if (dText.getLeftBorder() >= 0)
//				{
//					int b = scale(dText.getLeftBorder());
//					if (b < 1) b = 1;
//					g.fillRect(sx, sy, b, sh);			
//				}
//				if (dText.getRightBorder() >= 0)
//				{
//					int b = scale(dText.getRightBorder());
//					if (b < 1) b = 1;
//					g.fillRect(sx + sw - b, sy, b, sh);
//				}
//			}
			
			if (dText.getText() != null)
			{	
				g.setColor(translate(dText.getColor()));
				
				String path = null;
				float size = 32f;
				
				if (dText.getFont() != null)
				{
					if (dText.getFont().getFamily() != null)
						path = dText.getFont().getFamily().getPath();
					
					size = dText.getFont().getSize();
				}
				
				//System.out.println(name);
				
				Font font = getFont(path, scale(size));
				g.setFont(font);
				
				String[] t = dText.getText().split("\n");
				int lineY = 0;
				int wx = 0, wy = 0;
				
				for (int i=0;i<t.length;i++)
				{
					Graphics2D g2D = (Graphics2D)g;
					FontRenderContext frc = g2D.getFontRenderContext();
					Rectangle2D rect = font.getStringBounds(t[i], frc);
					
					if (i == 0)
					{
						if (dText.getVerticalAlign() == DocumentText.ALIGN_MIDDLE)
							wy = (int)Math.round((sh - rect.getHeight() * t.length) / 2);
						else if (dText.getVerticalAlign() == DocumentText.ALIGN_BOTTOM)
							wy = (int)Math.round(sh - rect.getHeight() * t.length);
					}
					
					wx = 0;
					if (dText.getHorizontalAlign() == DocumentText.ALIGN_CENTER)
						wx = (int)Math.round((sw - rect.getWidth()) / 2);
					else if (dText.getHorizontalAlign() == DocumentText.ALIGN_RIGHT)
						wx = (int)Math.round(sw - rect.getWidth());
					
					g.drawString(t[i], sx + wx, sy + wy + lineY - (int)rect.getY());
					
					lineY += Math.round(scale(dText.getLineHeight()));
				}
			}

			g.setClip(s);
		}
		else if (element instanceof DocumentPanel)
		{
			DocumentPanel dPanel = (DocumentPanel)element;

			if (dPanel.getBgColor() != null)
			{
				g.setColor(translate(dPanel.getBgColor()));
				g.fillRect(sx, sy, sw, sh);
			}
			
			if (dPanel.getBorderColor() != null)
			{
				g.setColor(translate(dPanel.getBorderColor()));
				if (dPanel.getTopBorder() >= 0)
				{
					int b = scale(dPanel.getTopBorder());
					if (b < 1) b = 1;
					g.fillRect(sx, sy, sw, b);
				}
				if (dPanel.getBottomBorder() >= 0)
				{
					int b = scale(dPanel.getBottomBorder());
					if (b < 1) b = 1;
					g.fillRect(sx, sy + sh - b, sw, b);
				}
				if (dPanel.getLeftBorder() >= 0)
				{
					int b = scale(dPanel.getLeftBorder());
					if (b < 1) b = 1;
					g.fillRect(sx, sy, b, sh);			
				}
				if (dPanel.getRightBorder() >= 0)
				{
					int b = scale(dPanel.getRightBorder());
					if (b < 1) b = 1;
					g.fillRect(sx + sw - b, sy, b, sh);
				}
			}
			
			int count = dPanel.getElementCount();
			for (int i = 0; i < count; i++)
				translateElement(g, x + dPanel.getX(), y + dPanel.getY(), dPanel.getElement(i));
		}
	}
	
	public Color translate(LexColor color)
	{
		if (color == LexColor.BLACK)
			return Color.BLACK;
		else if (color == LexColor.BLUE)
			return Color.BLUE;
		else if (color == LexColor.WHITE)
			return Color.WHITE;
		else if (color == LexColor.CYAN)
			return Color.CYAN;
		else if (color == LexColor.GRAY)
			return Color.GRAY;
		else if (color != null)
			return new Color(color.getRed(), color.getGreen(), color.getBlue());
		
		return Color.WHITE;
	}
	
	static Map fontMap = new HashMap();
	
	public static Font getFont(String path, float size)
	{
		Font font = (Font)fontMap.get(path);
		if (font == null)
		{
			try
			{
				File file = new File(path);
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

	public void paint(LexDocument doc, Object canvas, int canvasType)
	{
		if (canvasType == Painter.AUTO)
		{
			if (canvas instanceof File)
				canvasType = Painter.DIRECTORY;
		}
		
		if (canvasType != Painter.DIRECTORY)
			throw new DocumentExportException("png输出器不支持的输出格式");
		
		build(doc, (File)canvas);
	}
}
