package lerrain.project.insurance.plan.filter.axachart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @date 2014-7-17 下午6:58:42
 */
public class YearData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String MODE_YEAR = "year";
	public static final String MODE_ADD = "add";

	List itemList = new ArrayList();
	String code;

	public void addItem(YearDataItem item) {
		itemList.add(item);
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}

	public List getItemList(String mode) {
		List result = new ArrayList();
		for (int i = 0; i < itemList.size(); i++) {
			YearDataItem item = (YearDataItem) itemList.get(i);
			if (mode.equals(item.getMode())) {
				result.add(item);
			}
		}
		return result;
	}

	public List getItemList() {
		return itemList;
	}
}
