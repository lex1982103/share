package lerrain.project.insurance.product.attachment.table;

import java.util.ArrayList;
import java.util.List;

/**
 * 为主表提供附加的数据，比如主副险合表的时候，附险可以只提供补充数据，不提供表格。
 * 这个方式并不是很好的实现方式，暂时先不要使用它
 * 
 * @author lerrain
 * @deprecated
 *
 */
public class TableSupply
{
	String code;
	
	List list = new ArrayList();
	
	public TableSupply(String code)
	{
		this.code = code;
	}
	
	public void addBlankSupply(TableBlankSupply tbs)
	{
		list.add(tbs);
	}
	
	public List getBlankSupplyList()
	{
		return list;
	}

	public String getCode()
	{
		return code;
	}
}
