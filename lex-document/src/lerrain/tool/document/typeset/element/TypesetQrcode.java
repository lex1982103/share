package lerrain.tool.document.typeset.element;

import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.formula.Value;

public class TypesetQrcode extends TypesetElement
{
	public LexElement build(TypesetParameters tvs)
	{
		DocumentImage dImage = new DocumentImage();
		
		try
		{
			String url = this.getValue().run(tvs).toString();
			if (url != null)
				dImage.addImageSource(url, DocumentImage.TYPE_QRCODE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		dImage.setSize(this.getWidth().value(tvs), this.getHeight().value(tvs));
		dImage.setLocation(this.getX().value(tvs), tvs.getDatum() + this.getY().value(tvs));
		
		resetY(tvs, dImage);

		if (this.getLink() != null)
		{
			try
			{
				dImage.setLink(this.getLink().run(tvs).toString());
			}
			catch (Exception e)
			{
				System.out.println("ERROR: TypesetQrcode Link - " + e.toString());
			}
		}

		return dImage;
	}
}
