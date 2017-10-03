package lerrain.tool.data;

/**
 * 数据表
 * 
 * 该类实现一个将大型数据源的对应数据段（以险种参数决定）映射为一个简单取值对象（IFormula）的功能。
 * @author 李新豪
 * 
 * 修改历史：
 * 
 * 在险种参数那里加上了全局的缓冲，可以缓冲所有数据计算的结果。
 * 这里的缓冲已经不需要了，关闭掉。
 * - 李新豪 2011/06/02
 * 
 * 打开缓冲计算一个5M的数据文件的红利时速度慢的难以承受，时间非常久，只能先打开。
 * 目前有更换数据不能同步刷新的问题。
 * - 李新豪 2011/05/24
 * 
 * 这里的缓冲机制可以极大的提升运行速度，但是会给修改险种参数之后自动刷新数据带来困难。
 * 由于尚无很好的解决自动刷新的办法，暂时暂停缓冲。
 * - 李新豪 2011/04/19
 */

import java.io.Serializable;

import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Factors;

public class DataTable implements Formula, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String groupName;
	DataSource source;
	
	public DataTable(DataSource source, String groupName)
	{
		this.source = source;
		this.groupName = groupName;
	}
	
	public Object run(Factors factors)
	{
		return source.search(factors, groupName).value();
	}

	public String getGroupName()
	{
		return groupName;
	}
}
