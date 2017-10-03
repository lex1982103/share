package lerrain.tool.document.typeset;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.parser.ParserXml;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;
import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TypesetQrcode extends TypesetElement implements TypesetElementFactory
{
	public TypesetQrcode()
	{
	}
	
	public LexElement build(TypesetParameters tvs)
	{
		DocumentImage dImage = new DocumentImage();
		
		try
		{
			String url = Value.stringOf(this.getValue(), tvs);
			if (url != null)
			{
				try
				{
					Map hints = new HashMap();
					hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

					MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
					BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);

					ByteArrayOutputStream os = new ByteArrayOutputStream();
					MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
					os.close();

					dImage.addImageSource(os.toByteArray(), DocumentImage.TYPE_BIN);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		dImage.setSize(this.getWidth().value(tvs), this.getHeight().value(tvs));
		dImage.setLocation(this.getX().value(tvs), tvs.getDatum() + this.getY().value(tvs));
		
		resetY(tvs, dImage);
		
		return dImage;
	}

	@Override
	public TypesetElement parse(Typeset typeset, Object node)
	{
		TypesetQrcode qrcode = new TypesetQrcode();
		ParserXml.parseTypesetBase(typeset, (Element)node, qrcode);

		return qrcode;
	}
}
