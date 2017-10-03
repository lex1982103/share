package lerrain.tool.data;

/**
 * 
 * DataHub 多个DataSource合成为一个DataHub，可以同时集中各类的数据源，集中他们的数据表
 * DataParser 数据解析器（类似于数据库中的driver）
 * DataSource 数据文件（带有对应数据解析器，类似于一个数据库实例），包含多张数据表
 * DataTable 数据表
 * DataRecord 一次查询返回的数据片段
 * 
 * 修改历史：
 * 
 * 2013/03/21 李新豪
 * 由于这个级别上的缓冲关闭，会导致多次查询，影响效率。
 * 由于缓冲是建立在产品层上的，也就是保费、保额、交费、保障等层面的缓冲，一个产品显示时，假如保额、交费、保障需要
 * 计算，那么这里就会被查询3次。而第二次显示产品时，产品层面的缓冲会直接显示结果不会再次查询。
 * 而且一次进来就要查询全部的数据，显得有些多余，产品列表时的修改多数本不涉及量最大的年度数据集。
 * 考虑到以上两个问题，将来要对这里做出修改。
 * 
 * 2011/06/02 李新豪
 * 配合数据参数的缓冲部分，所有的缓冲移至那里，这里必须关闭，因为清除缓存步骤只针对那里。
 * 同时规范getResult方法，取值步骤所有值都实例化。
 * 
 */

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSet implements Factors, Serializable
{
	private static final long serialVersionUID = 1L;

	Factors f;

	Map temp = new HashMap();

	public DataSet(Factors f)
	{
		this.f = f;
	}

	public void addDataTable(DataTable dt)
	{
		temp.put("@" + dt.getGroupName(), dt);
	}

	@Override
	public Object get(String name)
	{
		if (!temp.containsKey(name))
		{
			DataTable dt = (DataTable) temp.get("@" + name);
			temp.put(name, dt.run(f));
		}

		return temp.get(name);
	}
}
