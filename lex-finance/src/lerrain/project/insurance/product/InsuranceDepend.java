package lerrain.project.insurance.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 险种的数据关联
 * 清除一个产品的缓存时，通过该类判断相关的类，然后将相关类的缓存也一并清除
 * 
 * @author lerrain
 *
 */
public class InsuranceDepend
{
	boolean rider = false;
	boolean parent = false;
	
	List productIdList = new ArrayList();
	
	public InsuranceDepend()
	{
	}
	
	public void addProduct(String productId)
	{
		if ("riders".equalsIgnoreCase(productId))
			rider = true;
		else if ("parent".equalsIgnoreCase(productId))
			parent = true;
		else
			productIdList.add(productId);
	}
	
	public boolean isAllRidersMatched()
	{
		return rider;
	}
	
	public boolean isParentMatched()
	{
		return parent;
	}

	public boolean isMatched(String productId)
	{
		return productIdList.contains(productId);
	}
}
