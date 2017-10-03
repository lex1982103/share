package lerrain.tool.document.typeset;

import lerrain.tool.document.element.DocumentPanel;
import lerrain.tool.document.element.LexElement;
import lerrain.tool.document.typeset.TypesetBuildException;
import lerrain.tool.document.typeset.TypesetParameters;
import lerrain.tool.document.typeset.TypesetUtil;
import lerrain.tool.document.typeset.element.TypesetElement;
import lerrain.tool.document.typeset.element.TypesetPanel;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.formula.Value;

import javax.swing.text.html.HTMLDocument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TypesetParagraphLoop
{
	private static final long serialVersionUID = 1L;

	Formula from;
	Formula to;
	Formula step;

	Formula value;

	String name;
	String[] vars;

	int loopValue;
	Factors varSet;

	List paragraphs = new ArrayList();

	public TypesetParagraphLoop(Formula from, Formula to, Formula step, String name)
	{
		this.from = from;
		this.to = to;
		this.step = step;

		this.name = name == null ? "I" : name;
	}

	public TypesetParagraphLoop(Formula value, String name)
	{
		this.name = name == null ? "I" : name;
		this.value = value;
	}

	public void addParagraph(TypesetParagraph tp)
	{
		paragraphs.add(tp);
	}

	public void build(TypesetParameters tvs, TypesetDocument td)
	{
		try
		{
			TypesetParameters tvs2 = new TypesetParameters(tvs);
			tvs2.setPaper(tvs.getPaper());

			Object forValue = value == null ? null : value.run(tvs);
			if (value != null && forValue == null) //有循环的list value，不过是空，那么直接跳过
			{
			}
			else if (forValue instanceof Map) // value = "..."
			{
				tvs2.declare(name);

				Map map = (Map)forValue;
				for (Iterator iter = map.keySet().iterator(); iter.hasNext();)
				{
					tvs2.set(name, iter.next());

					int num = paragraphs.size();
					for (int j = 0; j < num; j++)
						td.buildParagraph((TypesetParagraph)paragraphs.get(j), tvs2);
				}
			}
			else if (forValue != null) // value = "..."
			{
				int s = 0;
				if (forValue instanceof List)
					s = ((List)forValue).size();
				else if (forValue instanceof Object[])
					s = ((Object[])forValue).length;

				tvs2.declare(name);
				for (int i = 0; i < s; i++)
				{
					if (forValue instanceof List)
						tvs2.set(name, ((List)forValue).get(i));
					else if (forValue instanceof Object[])
						tvs2.set(name, ((Object[])forValue)[i]);
					
					int num = paragraphs.size();
					for (int j = 0; j < num; j++)
						td.buildParagraph((TypesetParagraph)paragraphs.get(j), tvs2);
				}
			}
			else // from="..." to="..." step="..."
			{
				int f = 0, t = 2, s = 1;
				try
				{
					f = Value.intOf(from, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<from>'s formula: " + value, e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						f = 0;
						System.out.println("exception in loop<from> formula: " + value + ", set it 0 instead.");
					}
				}
				
				try
				{
					t = Value.intOf(to, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<to>'s formula: " + value, e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						t = 1;
						System.out.println("exception in loop<to> formula: " + value + ", set it 1 instead.");
					}
				}
				
				try
				{
					s = step == null ? 1 : Value.intOf(step, tvs);
				}
				catch (Exception e)
				{
					if (TypesetUtil.getMode() == TypesetUtil.MODE_FAIL)
					{
						throw new TypesetBuildException("exception in loop<step>'s formula: " + value, e);
					}
					else if (TypesetUtil.getMode() == TypesetUtil.MODE_ALWAYS)
					{
						s = 1;
						System.out.println("exception in loop<step> formula: " + value + ", set it 1 instead.");
					}
				}
				
				tvs2.declare(name);
				for (int i = f; i <= t; i += s)
				{
					tvs2.set(name, new Integer(i));

					int num = paragraphs.size();
					for (int j = 0; j < num; j++)
						td.buildParagraph((TypesetParagraph)paragraphs.get(i), tvs2);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
