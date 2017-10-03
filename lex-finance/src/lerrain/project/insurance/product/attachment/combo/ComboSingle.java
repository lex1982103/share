package lerrain.project.insurance.product.attachment.combo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Formula;

public class ComboSingle
{
	Combo selfTable;
	
	List itemList = new ArrayList();
	List textList = new ArrayList();
	
	Map textMap = new HashMap();
	
	public void addItem(ComboItem item)
	{
		itemList.add(item);
	}
	
	public void addText(String code)
	{
		textList.add(code);
	}

	public List getItemList()
	{
		return itemList;
	}

	public void setItemList(List itemList)
	{
		this.itemList = itemList;
	}
	
	public void addComboText(String code, ComboText text)
	{
		textMap.put(code, text);
	}
	
	public ComboText getComboText(String code)
	{
		return (ComboText)textMap.get(code);
	}

	public List getTextList()
	{
		return textList;
	}

	public void setTextList(List textList)
	{
		this.textList = textList;
	}

	public Combo getSelfTable()
	{
		return selfTable;
	}

	public void setSelfTable(Combo selfTable)
	{
		this.selfTable = selfTable;
	}
}
