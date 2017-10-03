package lerrain.tool.document.typeset.element;

import java.util.HashMap;
import java.util.Map;

import lerrain.tool.document.element.DocumentImage;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.formula.Formula;

public class TypesetImage extends TypesetElement
{
	boolean sign = false;
	
	Map srcMap = new HashMap();
	
	public TypesetImage()
	{
	}
	
	public void addImageSource(Formula src, int type)
	{
		srcMap.put(new Integer(type), src);
	}
	
	public boolean hasType(int type)
	{
		return srcMap.containsKey(new Integer(type));
	}
	
	public Formula getImageSource(int type)
	{
		return (Formula)srcMap.get(new Integer(type));
	}
	
	public LexElement build(TypesetParameters tvs)
	{
		DocumentImage dImage = new DocumentImage();
		dImage.setSign(sign);
		
		try
		{
			if (hasType(DocumentImage.TYPE_URL))
				dImage.addImageSource(getImageSource(DocumentImage.TYPE_URL).run(tvs), DocumentImage.TYPE_URL);
			if (hasType(DocumentImage.TYPE_PATH))
				dImage.addImageSource(getImageSource(DocumentImage.TYPE_PATH).run(tvs), DocumentImage.TYPE_PATH);
			if (hasType(DocumentImage.TYPE_SRC))
				dImage.addImageSource(getImageSource(DocumentImage.TYPE_SRC).run(tvs), DocumentImage.TYPE_SRC);
			if (hasType(DocumentImage.TYPE_FILE))
				dImage.addImageSource(getImageSource(DocumentImage.TYPE_FILE).run(tvs), DocumentImage.TYPE_FILE);
			if (hasType(DocumentImage.TYPE_OTHER))
				dImage.addImageSource(getImageSource(DocumentImage.TYPE_OTHER).run(tvs), DocumentImage.TYPE_OTHER);
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

	public boolean isSign()
	{
		return sign;
	}

	public void setSign(boolean sign)
	{
		this.sign = sign;
	}
}
