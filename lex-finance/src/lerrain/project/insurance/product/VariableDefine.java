package lerrain.project.insurance.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lerrain.tool.formula.Formula;

/**
 * 
 * @author lerrain
 *
 * 修改历史：
 * 
 * 配合数据参数的缓冲部分，所有的缓冲移至那里，这里必须关闭，因为清除缓存步骤只针对那里。
 * - 李新豪 2011/06/02
 * 
 * 增加了自定义类型的处理，目前仅有Map类型，目的是为了实现扩展计算传入参数的多样性。
 * - 李新豪 2011/09/15
 * 
 * 增加了对公式模拟数组时的缓冲，在配置时加入param参数即可。
 * - 李新豪 2011/09/23
 * 
 * 规范了利益中数组、函数的实现方式。
 * ARRAY时返回的就是数组，直接以下表取值即可，不需要param项。
 * FUNCTION时需要定义变量名，必须要定义param项，设定参数名。有两种形式，有缓冲或无缓冲，目前统一采用一种。
 * - 李新豪 2014/06/14
 */

public class VariableDefine implements Serializable
{
	private static final long serialVersionUID = 1L;

	Formula script;
	
	Map vars = new HashMap();
//	List list = new ArrayList();

	public void add(Variable v)
	{
		vars.put(v.getName(), v);
//		list.add(v);
	}
	
	public Variable get(String name)
	{
		return (Variable)vars.get(name);
	}
	
	public void addAll(VariableDefine vd)
	{
		vars.putAll(vd.vars);
//		list.addAll(vd.list);
	}
	
//	public List toList()
//	{
//		return list;
//	}
	
	public Map getAllVars()
	{
		return vars;
	}

	/**
	 * @deprecated
	 */
	public Formula getScript()
	{
		return script;
	}

	/**
	 * interest标签可以带一段脚本，初始化的时候执行，用save方法向IT中写值
	 * @deprecated
	 */
	public void setScript(Formula script)
	{
		this.script = script;
	}
	
//	public Variable get(String name)
//	{
//		return (Variable)super.get(name);
//	}
//	
//	public void add(String name, String formula, String[] param, String desc)
//	{
//		add(new Variable(name, formula, param, desc));
//	}
//	
//	public void add(String name, Formula formula, String[] param, String desc)
//	{
//		add(new Variable(name, formula, param, desc));
//	}
}
