package lerrain.tool.document.export;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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

import lerrain.tool.document.typeset.TypesetUtil;

public class PdfPainterNDF implements Painter
{
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
			Object imageDst = null;

			if (dImage.hasImage(DocumentImage.TYPE_FILE))
			{
				imageDst = dImage.getImage(DocumentImage.TYPE_FILE);
			}
			else if (dImage.hasImage(DocumentImage.TYPE_SRC))
			{
				imageDst = new File((String)dImage.getImage(DocumentImage.TYPE_SRC));
			}
			else if (dImage.hasImage(DocumentImage.TYPE_PATH))
			{
				imageDst = new File((String)dImage.getImage(DocumentImage.TYPE_PATH));
			}
			else if (dImage.hasImage(DocumentImage.TYPE_BIN))
			{
				imageDst = dImage.getImage(DocumentImage.TYPE_BIN);
			}
			else if (dImage.hasImage(DocumentImage.TYPE_BASE64))
			{
				imageDst = Base64.getDecoder().decode((String)dImage.getImage(DocumentImage.TYPE_BASE64));
			}
			else if (dImage.hasImage(DocumentImage.TYPE_QRCODE))
			{
				BarcodeQRCode qrcode = new BarcodeQRCode((String)dImage.getImage(DocumentImage.TYPE_QRCODE), 1, 1, null);
				imageDst = qrcode.getImage();
			}

			Image image = null;

			if (imageDst instanceof Image)
			{
				image = (Image)imageDst;
			}
			else if (imageDst instanceof File)
			{
				File imageFile = (File)imageDst;
				image = (Image)appendMap.get("image:" + imageFile.getAbsolutePath());
				if (image == null)
				{
					image = Image.getInstance(imageFile.getAbsolutePath());
					appendMap.put("image:" + imageFile.getAbsolutePath(), image);
				}
			}
			else if (imageDst instanceof byte[])
			{
				byte[] b = (byte[])imageDst;
				image = Image.getInstance(b);
			}

			if (image != null)
			{
				if (dImage.getScale() == DocumentImage.SCALE_FULL)
				{
				}
				else if (dImage.getScale() == DocumentImage.SCALE_FIT)
				{
					float s1 = image.getWidth() / image.getHeight();
					float s2 = sw / sh;

					if (s1 > s2)
					{
						float t = image.getHeight() * sw / image.getWidth();
						if (dImage.getVerticalAlign() == DocumentImage.ALIGN_MIDDLE)
							sy = sy - (sh - t) / 2;
						else if (dImage.getVerticalAlign() == DocumentImage.ALIGN_BOTTOM)
							sy = sy - (sh - t);
						sh = t;
					}
					else
					{
						float t = image.getWidth() * sh / image.getHeight();
						if (dImage.getHorizontalAlign() == DocumentImage.ALIGN_CENTER)
							sx = sx + (sw - t) / 2;
						else if (dImage.getHorizontalAlign() == DocumentImage.ALIGN_RIGHT)
							sx = sx + (sw - t);
						sw = t;
					}
				}
				else if (dImage.getScale() == DocumentImage.SCALE_OUTFIT_RESIZE)
				{
					float s1 = image.getWidth() / image.getHeight();
					float s2 = sw / sh;

					if (s1 > s2)
					{
						float t = image.getWidth() * sh / image.getHeight();
						if (dImage.getHorizontalAlign() == DocumentImage.ALIGN_CENTER)
							sx = sx + (sw - t) / 2;
						else if (dImage.getHorizontalAlign() == DocumentImage.ALIGN_RIGHT)
							sx = sx + (sw - t);
						sw = t;
					}
					else
					{
						float t = image.getHeight() * sw / image.getWidth();
						if (dImage.getVerticalAlign() == DocumentImage.ALIGN_MIDDLE)
							sy = sy - (sh - t) / 2;
						else if (dImage.getVerticalAlign() == DocumentImage.ALIGN_BOTTOM)
							sy = sy - (sh - t);
						sh = t;
					}
				}

				image.setAbsolutePosition(sx, sy);
				image.scaleAbsolute(sw, sh);

				if (dImage.getLink() != null)
					pdf.setAction(new PdfAction(dImage.getLink()), sx, sy + sh, sx + sw, sy);

				pdf.addImage(image);
			}
		}
		else if (element instanceof DocumentText)
		{
			DocumentText dText = (DocumentText)element;
			
			BaseFont textFont = null;
			
			LexFont f = dText.getFont();
			if (f != null)
			{
				if (appendMap.containsKey("font:" + f.getName()))
				{
					textFont = (BaseFont)appendMap.get("font:" + f.getName());
				}
				else
				{
					try
					{
						textFont = BaseFont.createFont(TypesetUtil.getFontPath() + f.getFamily().getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					appendMap.put("font:" + f.getName(), textFont);
				}
			}

			float fontSize = dText.getFont() == null ? 32 : dText.getFont().getSize();
			boolean bold = dText.getFont() == null ? false : dText.getFont().getBold() > 0;
			Font font = new Font(textFont, scale(fontSize), bold ? Font.BOLD : Font.NORMAL, translate(dText.getColor()));
			
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
			
			float y1 = textFont.getAscentPoint(text, scale(fontSize));
			float y2 = textFont.getDescentPoint(text, scale(fontSize));

//			System.out.println(text + " - " + (sh - (y1 - y2)) / 2);
			int align = dText.getHorizontalAlign() == DocumentText.ALIGN_CENTER ? Element.ALIGN_CENTER : dText.getHorizontalAlign() == DocumentText.ALIGN_RIGHT ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT;

			ColumnText colText = new ColumnText(pdf);
			colText.setAlignment(align);
			colText.setSimpleColumn(new Phrase(text, font), sx, sy, sx + sw, sy + sh, (sh - (y1 - y2)) / 2 + y1, align);
			colText.go();

//			pdf.setColorFill(translate(dText.getColor()));
//			pdf.setFontAndSize(textFont, scale(fontSize));
//			if (align == Element.ALIGN_LEFT)
//				pdf.showTextAligned(align, text, sx, sy + (sh - (y1 - y2)) / 2, 0);
//			else if (align == Element.ALIGN_RIGHT)
//				pdf.showTextAligned(align, text, sx + sw, sy + (sh - (y1 - y2)) / 2, 0);
//			else if (align == Element.ALIGN_CENTER)
//				pdf.showTextAligned(align, text, sx + sw / 2, sy + (sh - (y1 - y2)) / 2, 0);

			if (dText.getUnderline() != null)
			{
				pdf.setColorStroke(translate(dText.getColor()));
				pdf.setLineWidth(scale(1));
				pdf.moveTo(sx, sy + 1);
				pdf.lineTo(sx + sw, sy + 1);
				pdf.stroke();
			}

			if (dText.getLink() != null)
				pdf.setAction(new PdfAction(dText.getLink()), sx, sy + sh, sx + sw, sy);
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
			pdf.setLineWidth(scale(1));

			if (dPanel.getLeftBorder() == 0)
			{
				pdf.moveTo(sx, sy);
				pdf.lineTo(sx, sy + sh);
				pdf.stroke();
			}
			else if (dPanel.getLeftBorder() > 0)
			{
				float w = scale(dPanel.getLeftBorder());
				pdf.setColorFill(translate(dPanel.getBorderColor()));
				pdf.rectangle(sx, sy, w, sh);
				pdf.fill();
			}
			
			if (dPanel.getRightBorder() == 0)
			{
				pdf.moveTo(sx + sw, sy);
				pdf.lineTo(sx + sw, sy + sh);
				pdf.stroke();
			}
			else if (dPanel.getRightBorder() > 0)
			{
				float w = scale(dPanel.getLeftBorder());
				pdf.setColorFill(translate(dPanel.getBorderColor()));
				pdf.rectangle(sx + sw - w, sy, w, sh);
				pdf.fill();
			}

			if (dPanel.getTopBorder() == 0)
			{
				pdf.moveTo(sx, sy + sh);
				pdf.lineTo(sx + sw, sy + sh);
				pdf.stroke();
			}
			else if (dPanel.getTopBorder() > 0)
			{
				float w = scale(dPanel.getLeftBorder());
				pdf.setColorFill(translate(dPanel.getBorderColor()));
				pdf.rectangle(sx, sy + sh - w, sw, w);
				pdf.fill();
			}

			if (dPanel.getBottomBorder() == 0)
			{
				pdf.moveTo(sx, sy);
				pdf.lineTo(sx + sw, sy);
				pdf.stroke();
			}
			else if (dPanel.getBottomBorder() > 0)
			{
				float w = scale(dPanel.getLeftBorder());
				pdf.setColorFill(translate(dPanel.getBorderColor()));
				pdf.rectangle(sx, sy, sw, w);
				pdf.fill();
			}

			int count = dPanel.getElementCount();
			for (int i = 0; i < count; i++)
				translateElement(pdf, document, paper, x + dPanel.getX(), y + dPanel.getY(), dPanel.getElement(i), appendMap);

			if (dPanel.getLink() != null)
				pdf.setAction(new PdfAction(dPanel.getLink()), sx, sy + sh, sx + sw, sy);
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
