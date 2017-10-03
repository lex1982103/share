package lerrain.tool.document.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.DocumentExportException;
import lerrain.tool.document.LexColor;
import lerrain.tool.document.LexDocument;
import lerrain.tool.document.LexFont;
import lerrain.tool.document.LexPage;
import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.DocumentLine;
import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.DocumentRect;
import lerrain.tool.document.element.DocumentText;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.size.Paper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfPainter implements Painter
{
	public PdfPainter()
	{
	}

	public void paint(LexDocument doc, Object canvas, int canvasType)
	{
		if (canvasType == Painter.AUTO)
		{
			if (canvas instanceof File)
				canvasType = Painter.FILE;
			else if (canvas instanceof String)
				canvasType = Painter.FILEPATH;
			else if (canvas instanceof OutputStream)
				canvasType = Painter.STREAM;
		}
		
		if (canvasType == Painter.STREAM)
		{
			draw(doc, (OutputStream)canvas);
		}
		else if (canvasType == Painter.FILE || canvasType == Painter.FILEPATH)
		{
			File file = canvasType == Painter.FILE ? (File)canvas : new File((String)canvas);
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(file);
				draw(doc, fos);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (fos != null)
						fos.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		else
		{
			throw new DocumentExportException("不支持的导出格式");
		}
	}

	private void draw(LexDocument doc, OutputStream os) throws DocumentExportException
	{
		Map appendMap = new HashMap();
				
		PdfContentByte pdfContentByte;
		Document document = new Document(PageSize.A4);
		
		try
		{
			PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
			
			document.open();

			BaseFont song = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			//BaseFont textFont = BaseFont.createFont("fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			//BaseFont hei = BaseFont.createFont("STHeiti-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			appendMap.put("font:default", song);
			
			int pageNum = doc.size();
			for (int i = 0; i < pageNum; i++)
			{
				LexPage page = doc.getPage(i);

				if ("A4".equals(page.getPaper().getName()))
					document.setPageSize(PageSize.A4);
				else
					document.setPageSize(PageSize.A4.rotate());
				document.newPage();
				
				pdfContentByte = pdfWriter.getDirectContent();
				
				int eNum = page.getElementNum();
				for (int j = 0; j < eNum; j++)
				{
					LexElement element = page.getElement(j);
					translateElement(pdfContentByte, document, page.getPaper(), 0, 0, element, appendMap);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			document.close();
		}
	}
	
	private static float scale(float mm10)
	{
		return (float)(72f * mm10 / 100 / 2.54f);
	}
	
	private void translateElement(PdfContentByte pdf, Document document, Paper paper, int x, int y, LexElement element, Map appendMap) throws DocumentException, MalformedURLException, IOException
	{
		float sx = scale(x + element.getX());
		float sy = scale(paper.getHeight() - y - element.getY() - element.getHeight());
		float sw = scale(element.getWidth());
		float sh = scale(element.getHeight());
		
		if (element instanceof DocumentRect)
		{
			DocumentRect dRect = (DocumentRect)element;  
			
			pdf.setColorFill(translate(dRect.getColor()));
			pdf.rectangle(sx, sy, sw, sh);
			pdf.fill();
		}
		else if (element instanceof DocumentLine)
		{
			DocumentLine dLine = (DocumentLine)element;
			
			pdf.setColorStroke(translate(dLine.getColor()));
			pdf.moveTo(sx, sy);
			pdf.lineTo(sx + sw, sy + sh);
			pdf.stroke();
		}
		else if (element instanceof DocumentImage)
		{
			DocumentImage dImage = (DocumentImage)element;
			File imageFile = null;
			
			if (dImage.hasImage(DocumentImage.TYPE_FILE))
			{
				imageFile = (File)dImage.getImage(DocumentImage.TYPE_FILE);
			}
			else if (dImage.hasImage(DocumentImage.TYPE_SRC))
			{
				imageFile = new File((String)dImage.getImage(DocumentImage.TYPE_SRC));
			}
			else if (dImage.hasImage(DocumentImage.TYPE_PATH))
			{
				imageFile = new File((String)dImage.getImage(DocumentImage.TYPE_PATH));
			}
			else
			{
			}
			
			if (imageFile != null)
			{
				Image image = (Image)appendMap.get("image:" + imageFile.getAbsolutePath());
				if (image == null)
				{
					image = Image.getInstance(imageFile.getAbsolutePath());
					appendMap.put("image:" + imageFile.getAbsolutePath(), image);
				}
				
				image.setAbsolutePosition(sx, sy);
				image.scaleAbsolute(sw, sh);
				
				document.add(image);
			}
		}
		else if (element instanceof DocumentText)
		{
			DocumentText dText = (DocumentText)element;
			
			BaseFont textFont;
			
			LexFont f = dText.getFont();
			if (f == null)
				textFont = (BaseFont)appendMap.get("font:default");
			else
			{
				textFont = (BaseFont)appendMap.get("font:" + f.getName());
				if (textFont == null)
				{
					if (f.getFamily() == null || f.getFamily().getPath() == null)
						textFont = (BaseFont)appendMap.get("font:default");
					else
					{
						try
						{
							textFont = BaseFont.createFont(f.getFamily().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
						}
						catch (Exception e)
						{
							e.printStackTrace();
							textFont = (BaseFont)appendMap.get("font:default");
						}
					}
					
					appendMap.put("font:" + f.getName(), textFont);
				}
			}

			float fontSize = dText.getFont() == null ? 32 : dText.getFont().getSize();
			Font font = new Font(textFont, scale(fontSize), Font.NORMAL, translate(dText.getColor()));
			
			String text = dText.getText();
			if (text == null)
				text = "";
			
//			System.out.println(sh + " - " + font.getBaseFont().getDescentPoint(text, fontSize) + " - " + text);
			
//			dText.setBgColor(LexColor.DARK_GREEN);
			if (!LexColor.WHITE.equals(dText.getBgColor()) && dText.getBgColor() != null)
			{
//				pdf.setColorStroke(translate(dText.getBgColor()));
				pdf.setColorFill(translate(dText.getBgColor()));
				pdf.rectangle(sx, sy, sw, sh);
//				pdf.stroke();
				pdf.fill();
			}
			
			float y1 = font.getBaseFont().getAscentPoint(text, scale(fontSize));
			float y2 = font.getBaseFont().getDescentPoint(text, scale(fontSize));
			
			ColumnText colText = new ColumnText(pdf);
			colText.setAlignment(Element.ALIGN_MIDDLE);
			colText.setSimpleColumn(new Phrase(text, font), sx, sy, sx + sw, sy + sh, (sh - (y1 - y2)) / 2 + y1, Element.ALIGN_CENTER);
			colText.go();
			
			if (dText.getUnderline() != null)
			{
				pdf.setColorStroke(translate(dText.getColor()));
				pdf.setLineWidth(scale(1));
				pdf.moveTo(sx, sy + 1);
				pdf.lineTo(sx + sw, sy + 1);
				pdf.stroke();
			}
		}
		else if (element instanceof DocumentPanel)
		{
			DocumentPanel dPanel = (DocumentPanel)element;

			if (!LexColor.WHITE.equals(dPanel.getBgColor()) && dPanel.getBgColor() != null)
			{
				pdf.setColorFill(translate(dPanel.getBgColor()));
				pdf.rectangle(sx, sy, sw, sh);
				pdf.fill();
			}
			
			pdf.setColorStroke(translate(dPanel.getBorderColor()));

			if (dPanel.getLeftBorder() >= 0)
			{
				pdf.setLineWidth(scale(dPanel.getLeftBorder()));
				pdf.moveTo(sx, sy);
				pdf.lineTo(sx, sy + sh);
				pdf.stroke();
			}
			
			if (dPanel.getRightBorder() >= 0)
			{
				pdf.setLineWidth(scale(dPanel.getRightBorder()));
				pdf.moveTo(sx + sw, sy);
				pdf.lineTo(sx + sw, sy + sh);
				pdf.stroke();
			}
			
			if (dPanel.getTopBorder() >= 0)
			{
				pdf.setLineWidth(scale(dPanel.getTopBorder()));
				pdf.moveTo(sx, sy + sh);
				pdf.lineTo(sx + sw, sy + sh);
				pdf.stroke();
			}
			
			if (dPanel.getBottomBorder() >= 0)
			{
				pdf.setLineWidth(scale(dPanel.getBottomBorder()));
				pdf.moveTo(sx, sy);
				pdf.lineTo(sx + sw, sy);
				pdf.stroke();
			}

			int count = dPanel.getElementCount();
			for (int i = 0; i < count; i++)
				translateElement(pdf, document, paper, x + dPanel.getX(), y + dPanel.getY(), dPanel.getElement(i), appendMap);
		}
	}
	
	public BaseColor translate(LexColor color)
	{
//		if (LexColor.BLACK.equals(color))
//			return Color.BLACK;
//		else if (LexColor.BLUE.equals(color))
//			return Color.BLUE;
//		else if (LexColor.WHITE.equals(color))
//			return Color.WHITE;
//		else if (LexColor.CYAN.equals(color))
//			return Color.CYAN;
//		else if (LexColor.GRAY.equals(color))
//			return Color.GRAY;
//		else if (LexColor.RED.equals(color))
//			return Color.RED;

		if (color != null)
			return new BaseColor(color.getRed(), color.getGreen(), color.getBlue());
		
		return BaseColor.WHITE;
	}
}
