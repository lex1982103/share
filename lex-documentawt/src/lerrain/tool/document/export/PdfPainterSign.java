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
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.*;

public class PdfPainterSign extends PdfPainterBase implements Painter
{
	String tempDir;

	PrivateKey pk;
	Certificate[] chain;

	String scope, reason, location;

	public PdfPainterSign(String keystore, String password, String scope, String reason, String location, String tempDir)
	{
		this.tempDir = tempDir;
		this.scope = scope;
		this.reason = reason;
		this.location = location;

		char[] pwd = password.toCharArray();

		try (InputStream keyIs = new FileInputStream(keystore))
		{
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(keyIs, pwd);
			String alias = ks.aliases().nextElement();
			pk = (PrivateKey) ks.getKey(alias, pwd);
			chain = ks.getCertificateChain(alias);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
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
