package lerrain.tool.script.warlock;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.warlock.analyse.Words;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class CodeImpl implements Code
{
	Words words;

	int pos1, pos2;

	public CodeImpl(Words words)
	{
		this(words, 0, words.size() - 1);
	}

	public CodeImpl(Words words, int pos)
	{
		this(words, pos, pos);
	}

	public CodeImpl(Words words, int pos1, int pos2)
	{
		this.words = words;

		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public String toText(String space)
	{
		return words.toString();
	}

//	private void print(Object ps, String c)
//	{
//		if (ps instanceof PrintStream)
//			((PrintStream)ps).print(c);
//		if (ps instanceof PrintWriter)
//			((PrintWriter)ps).print(c);
//	}
//
//	private void println(Object ps, String c)
//	{
//		print(ps, c);
//		print(ps, "\n");
//	}

	public void printAll(PrintStream ps, String msg)
	{
		String s = words.getScript();
		if (!s.endsWith("\n"))
			s += "\n";
		s = s.replaceAll("\r", " ");

		int p1 = words.getLocation(pos1);
		int p2 = pos2 + 1 >= words.size() ? s.length() : words.getLocation(pos2 + 1);

		int len = s.length();
		int last = 0;

		for (int i=0;i<len;i++)
		{
			char c = s.charAt(i);
			if (c == '\n')
			{
				ps.println(s.substring(last, i));

				if (p1 >= 0 && i > p1)
				{
					for (int j = last; j < p1; j++)
					{
						char c1 = s.charAt(j);
						ps.print(c1 == '\t' ? "\t" : " ");
					}

					for (int j = p1; j < p2; j++)
						ps.print("|");

					ps.print(" ==> ");
					ps.println(msg);

					p1 = -1;
				}
				last = i + 1;
			}
		}
	}

	public boolean isInt(Object v)
	{
		return (v instanceof Integer) || (v instanceof Long) || (v instanceof Character);
	}

	public boolean isLong(Object v)
	{
		return (v instanceof Long);
	}

	public boolean isFloat(Object v)
	{
		return !isInt(v) && !isLong(v);
	}
}
