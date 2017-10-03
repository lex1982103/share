package lerrain.tool.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单事务
 * @author lerrain
 *
 */
public class Transaction implements Behavior
{
	List<Behavior> behaviorList = new ArrayList<Behavior>();
	
	int pos = -1;
	
	public void add(Behavior behavior)
	{
		behaviorList.add(behavior);
	}
	
	public boolean perform()
	{
		pos = 0;
		
		for (Behavior behavior : behaviorList)
		{
			if (!behavior.perform())
				return false;

			pos++;
		}
		
		return true;
	}

	@Override
	public void rollback()
	{
		/*
		 * 当前出错的行为回滚需要在自己的peform中进行
		 * 通常当前行为失败的时候该行为并没有执行，该行为不需要回滚。
		 * 即使已经执行了一部分，事务管理器也无法预知每个行为内部的错误点，需要在perform内部处理。
		 */
		for (; pos >= 0; pos--)
		{
			Behavior behavior = (Behavior)behaviorList.get(pos);
			behavior.rollback();
		}
	}

	@Override
	public void commit()
	{
		if (pos == behaviorList.size())
		{
			for (Behavior behavior : behaviorList)
				behavior.commit();
		
			pos = -1;
		}
	}
}
