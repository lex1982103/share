package lerrain.tool.document.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.*;
import lerrain.tool.document.*;
import lerrain.tool.document.element.*;
import lerrain.tool.document.size.Paper;
import lerrain.tool.document.typeset.TypesetUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PdfPainterSign2 implements Painter
{
	String tempDir;

	PrivateKey pk;
	Certificate[] chain;

	String scope, reason, location;

	public PdfPainterSign2(PrivateKey pk, Certificate[] chain, String scope, String reason, String location, String tempDir)
	{
		this.tempDir = tempDir;
		this.scope = scope;
		this.reason = reason;
		this.location = location;

		this.pk = pk;
		this.chain = chain;
	}

	public void paint(LexDocument doc, Object canvas, int canvasType)
	{
		String filePath = tempDir + UUID.randomUUID().toString() + ".pdf";

		Map map = new HashMap();

		try (FileOutputStream fos = new FileOutputStream(new File(filePath)))
		{
			draw(doc, fos, map);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		if (canvasType == Painter.AUTO)
		{
			if (canvas instanceof File)
				canvasType = Painter.FILE;
			else if (canvas instanceof String)
				canvasType = Painter.FILEPATH;
			else if (canvas instanceof OutputStream)
				canvasType = Painter.STREAM;
		}

		Image image = (Image)map.get("sign/image");
		Rectangle rect = (Rectangle)map.get("sign/rect");
		Integer page = (Integer)map.get("sign/page");

		if (page == null)
			page = 0;

		if (canvasType == Painter.STREAM)
		{
			try
			{
				sign(filePath, (OutputStream) canvas, image, rect, page, scope, chain, pk, DigestAlgorithms.SHA1, MakeSignature.CryptoStandard.CMS, reason, location);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		else if (canvasType == Painter.FILE || canvasType == Painter.FILEPATH)
		{
			File file = canvasType == Painter.FILE ? (File)canvas : new File((String)canvas);
			try (FileOutputStream fos = new FileOutputStream(file))
			{
				sign(filePath, fos, image, rect, page, scope, chain, pk, DigestAlgorithms.SHA1, MakeSignature.CryptoStandard.CMS, reason, location);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		else
		{
			throw new DocumentExportException("不支持的导出格式");
		}
	}
	
	public static void copy(String file, OutputStream fos)
	{
		System.out.println("WARN: no sign image");
		
		try (FileInputStream is = new FileInputStream(new File(file)))
		{
			byte[] b = new byte[4096];
			int c = 0;
			while (c >= 0)
			{
				c = is.read(b);
				if (c > 0)
					fos.write(b, 0, c);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void draw(LexDocument doc, OutputStream os, Map appendMap) throws DocumentExportException
	{
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
					translateElement(pdfContentByte, document, i, page.getPaper(), 0, 0, element, appendMap);
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
	
	private void translateElement(PdfContentByte pdf, Document document, int pageNum, Paper paper, int x, int y, LexElement element, Map appendMap) throws DocumentException, MalformedURLException, IOException
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
				if (dImage.isSign())
				{
					appendMap.put("sign/image", image);
					appendMap.put("sign/rect", new Rectangle(sx, sy, sx + sw, sy + sh));
					appendMap.put("sign/page", pageNum);
				}
				else
				{
					image.setAbsolutePosition(sx, sy);
					image.scaleAbsolute(sw, sh);

					if (dImage.getLink() != null)
						pdf.setAction(new PdfAction(dImage.getLink()), sx, sy + sh, sx + sw, sy);

					pdf.addImage(image);
				}
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
			int align = dText.getHorizontalAlign() == DocumentText.ALIGN_CENTER ? Element.ALIGN_CENTER : dText.getHorizontalAlign() == DocumentText.ALIGN_RIGHT ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT;
			colText.setAlignment(align);
			colText.setSimpleColumn(new Phrase(text, font), sx, sy, sx + sw, sy + sh, (sh - (y1 - y2)) / 2 + y1, align);
			colText.go();

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
				translateElement(pdf, document, pageNum, paper, x + dPanel.getX(), y + dPanel.getY(), dPanel.getElement(i), appendMap);

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

	public void sign(String src, OutputStream os, Image image, Rectangle rect, int page, String scope, Certificate[] chain, PrivateKey pk, String digestAlgorithm, MakeSignature.CryptoStandard subfilter, String reason, String location)
			throws GeneralSecurityException, IOException, DocumentException
	{
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);

		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setReason(reason);
		appearance.setLocation(location);
		appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
		appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

		if (image != null)
		{
			appearance.setVisibleSignature(rect, page + 1, scope);
			appearance.setSignatureGraphic(image);
		}

		ExternalDigest digest = new BouncyCastleDigest();
		ExternalSignature signature = new PrivateKeySignature(pk, digestAlgorithm, null);
		MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, subfilter);
	}
}
