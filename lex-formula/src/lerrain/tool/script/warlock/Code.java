package lerrain.tool.script.warlock;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.analyse.Words;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Code implements Formula
{
	Words words;

	int pos1, pos2;

	boolean breakPoint = false;

	public Code(Words words)
	{
		this(words, 0, words.size() - 1);
	}

	public Code(Words words, int pos)
	{
		this(words, pos, pos);
	}

	public Code(Words words, int pos1, int pos2)
	{
		this.words = words;

		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public boolean isPointOn(int pos)
	{
		return words.isInWords(pos);
	}

	public int[] getRange()
	{
		return words.range();
	}

	public Code markBreakPoint(int pos)
	{
		if (isPointOn(pos))
		{
			setBreakPoint(true);
			return this;
		}

		return null;
	}

	public void clearBreakPoints()
	{
		breakPoint = false;
	}

	//能debug的一定是stack
	public void debug(Factors factors)
	{
		if (!(factors instanceof Stack))
			return;

		Stack stack = (Stack)factors;
		if (stack.getDebug() != Stack.RUNNING)
		{
			Stack.BreakListener listener = stack.getBreakListener();

			if (listener != null && (this.isBreakPoint() || stack.getDebug() != Stack.DEBUG_BREAK_POINT))
				listener.onBreak(this, stack);
		}
	}

	public void setBreakPoint(boolean p)
	{
		this.breakPoint = p;
	}

	public boolean isBreakPoint()
	{
		return breakPoint;
	}

	public String toText(String space, boolean line)
	{
		return words.toString();
	}

	public Object getScriptTag()
	{
		return words.getScriptTag();
	}

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
		int max = 5;

		List<String> lines = new ArrayList<>();

		for (int i=0,line=1;i<len;i++)
		{
			char c = s.charAt(i);
			if (c == '\n')
			{
				String code = String.format("%04d: ", line++) + s.substring(last, i);

				if (lines == null)
				{
					ps.println(code);
					max--;

					if (max <= 0)
						return;
				}
				else
				{
					lines.add(code);
				}

				if (p1 >= 0 && i > p1)
				{
					for (int f = Math.max(0, lines.size() - max); f < lines.size(); f++)
						ps.println(lines.get(f));

					ps.print("      ");
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
					lines = null;
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
