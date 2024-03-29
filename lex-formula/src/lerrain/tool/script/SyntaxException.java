package lerrain.tool.script;

import lerrain.tool.script.warlock.analyse.Words;

import java.util.ArrayList;
import java.util.List;

public class SyntaxException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	Words ws;
	String script;

	int pos = -1;

	public SyntaxException(Words ws, int pos, String descr)
	{
		super(descr);

		this.ws = ws;
		this.pos = pos;
	}

	public SyntaxException(Words ws, String descr)
	{
		super(descr);

		this.ws = ws;
	}

	public SyntaxException(String script, int pos, String descr)
	{
		super(descr);

		this.script = script;
		this.pos = pos;
	}

	public int getPosition()
	{
		if (ws != null)
		{
			if (pos >= 0)
				return ws.getLocation(pos);

			return ws.getLocation(0);
		}

		if (pos > 0)
			return pos;

		return 0;
	}

	public int[] getRange()
	{
		if (ws != null)
			return ws.range();

		if (pos >= 0)
			return new int[] {pos, pos + 1};

		return new int[] {0, 1};
	}

	public String getMistakeScript()
	{
		if (ws != null)
		{
			if (pos >= 0)
				return ws.getWord(pos);

			return ws.getCurrentScript();
		}

		return script.substring(pos, pos + 1);
	}

	public String getMessage()
	{
		if (ws == null && script == null)
			return super.getMessage();

		if (pos < 0 && ws != null)
		{
			int p1 = ws.getLocation(0);
			int p2 = ws.getLocation(ws.size() - 1);

			if (p1 < 0 || p2 <= p1)
			{
				return super.getMessage() + " - " + ws.getScript().substring(0, Math.min(100, ws.getScript().length()));
			}
			else
			{
				String script = ws.getScript();

				p1 -= 10;
				p2 += 10;

				if (p1 < 0)
					p1 = 0;
				if (p2 > script.length())
					p2 = script.length();

				String wss = "";
				for (int i = 0; i < 5 && i < ws.size(); i++)
				{
					wss += ws.getWord(i);
					wss += " ";
				}

				return super.getMessage() + " for " + wss + "at " + script.substring(p1, p2);
			}
		}

		if (pos < 0 && script != null)
			return super.getMessage() + " - " + script.substring(0, Math.min(100, script.length()));

		if (script != null)
		{
			int p1 = Math.max(0, pos - 100);
			String alert = String.format("%" + Math.max(0, pos - 1 - p1) + "s%s%s", "", "| ==> ", super.getMessage());

			return "\n" + script.substring(p1, Math.min(script.length(), pos + 100)).replaceAll("\n", " ") + "\n" + alert;
		}

		String s = ws.getScript();
		if (!s.endsWith("\n"))
			s += "\n";
		s = s.replaceAll("\r", " ");

		int p1 = ws.getLocation(pos);

		int len = s.length();
		int last = 0;
		int max = 10;

		StringBuffer sb = new StringBuffer("\n");
		List<String> lines = new ArrayList<>();

		for (int i = 0, line = 1; i < len; i++)
		{
			char c = s.charAt(i);
			if (c == '\n')
			{
				String code = String.format("%04d: ", line++) + s.substring(last, i);

				if (lines == null)
				{
					sb.append(code);
					sb.append("\n");
					max--;

					if (max <= 0)
						break;
				}
				else
				{
					lines.add(code);
				}

				if (p1 >= 0 && i > p1)
				{
					for (int f = Math.max(0, lines.size() - max); f < lines.size(); f++)
					{
						sb.append(lines.get(f));
						sb.append("\n");
					}

					sb.append("      ");
					for (int j = last; j < p1; j++)
					{
						char c1 = s.charAt(j);
						sb.append(c1 == '\t' ? "\t" : " ");
					}

					sb.append("|");
					sb.append(" ==> ");
					sb.append(super.getMessage());
					sb.append("\n");

					p1 = -1;
					lines = null;
				}

				last = i + 1;
			}
		}

		return sb.toString();
	}
}
