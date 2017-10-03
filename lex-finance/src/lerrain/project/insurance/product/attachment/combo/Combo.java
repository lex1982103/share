package lerrain.project.insurance.product.attachment.combo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利益合并
 * @author lerrain
 *
 */
public class Combo implements Serializable
{
	private static final long serialVersionUID = 1L;

	Map colMap = new HashMap();
	
	List textSorted = new ArrayList();
	Map textMap = new HashMap();
	
	List colList = new ArrayList();
	
	List hide;
	
	public void addCol(ComboCol col)
	{
		colList.add(col);
		
		if (col.getCode() != null)
			colMap.put(col.getCode(), col);
		
		if (col.hasSubCol())
		{
			List list = col.getSubCol();
			for (int i=0;i<list.size();i++)
			{
				ComboCol subCol = (ComboCol)list.get(i);
				if (subCol.getCode() != null)
					colMap.put(subCol.getCode(), subCol);
				
				if (subCol.hasSubCol())
				{
					List list2 = subCol.getSubCol();
					for (int j=0;j<list2.size();j++)
					{
						ComboCol subCol2 = (ComboCol)list2.get(j);
						if (subCol2.getCode() != null)
							colMap.put(subCol2.getCode(), subCol2);
					}
				}
			}
		}
	}
	
	public List getColList()
	{
		return colList;
	}
	
	public ComboCol getCol(String code)
	{
		return (ComboCol)colMap.get(code);
	}
	
	public void addComboText(String code, ComboText c)
	{
		textMap.put(code, c);
		textSorted.add(code);
	}
	
	public List getTextCodeSortedList()
	{
		return textSorted;
	}
	
	public ComboText getComboText(String code)
	{
		return (ComboText)textMap.get(code);
	}

	public List getHide()
	{
		return hide;
	}

	public void addHide(String hideCol)
	{
		if (hideCol == null || "".equals(hideCol.trim()))
			return;
		
		if (hide == null)
			hide = new ArrayList();
		
		if (hide.indexOf(hideCol) < 0)
			hide.add(hideCol);
	}
}
