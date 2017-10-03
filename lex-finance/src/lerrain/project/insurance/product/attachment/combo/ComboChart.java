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
public class ComboChart implements Serializable
{
	private static final long serialVersionUID = 1L;

	Map colMap = new HashMap();
	
	List colList = new ArrayList();
	
	List hide;
	
	public void addCol(ComboChartCol col)
	{
		colList.add(col);
		
		if (col.getCode() != null)
			colMap.put(col.getCode(), col);
	}
	
	public List getColList()
	{
		return colList;
	}
	
	public ComboChartCol getCol(String code)
	{
		return (ComboChartCol)colMap.get(code);
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
