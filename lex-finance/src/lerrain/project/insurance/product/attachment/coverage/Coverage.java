package lerrain.project.insurance.product.attachment.coverage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coverage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	List paraList = new ArrayList();
	
	public int getParagraphCount()
	{
		return paraList.size();
	}
	
	public CoverageParagraph getParagraph(int index)
	{
		return (CoverageParagraph)paraList.get(index);
	}

	public CoverageParagraph newParagraph(String title)
	{
		CoverageParagraph cp = new CoverageParagraph();
		cp.setTitle(title);
		
		paraList.add(cp);
		
		return cp;
	}
	
	public void addParagrpah(CoverageParagraph cp)
	{
		paraList.add(cp);
	}
}
