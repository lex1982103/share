package lerrain.tool.script;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;

import java.util.ArrayList;
import java.util.List;

public class ScriptText implements Formula
{
    List list = new ArrayList();

    public ScriptText(String text)
    {
        int p1 = 0;
        int p2 = 0;

        int deep = 0;

        boolean quote1 = false;
        boolean quote2 = false;

        char l = 0, c;

        for (int i=0;i<text.length();i++)
        {
            c = text.charAt(i);

            if (l == '\\')
                continue;

            if (deep > 0)
            {
                if (quote1)
                {
                    if (c == '\"')
                        quote1 = false;
                }
                else if (quote2)
                {
                    if (c == '\'')
                        quote2 = false;
                }
                else
                {
                    if (c == '\"')
                        quote1 = true;
                    else if (c == '\'')
                        quote2 = true;
                }

                if (quote1 || quote2)
                    continue;
            }

            if (c == '{')
            {
                deep++;

                if (deep == 0)
                {
                    p1 = i;
                    list.add(text.substring(p2 + 1, p1));
                }
            }
            else if (c == '}')
            {
                deep--;

                if (deep == 0)
                {
                    p2 = i;
                    list.add(Script.scriptOf(text.substring(p1 + 1, p2)));
                }
            }
        }
    }

    @Override
    public Object run(Factors factors)
    {
        StringBuffer res = new StringBuffer();
        for (Object t : list)
        {
            if (t instanceof String)
                res.append(t);
            else if (t instanceof Formula)
                res.append(((Formula)t).run(factors));
        }

        return res.toString();
    }
}
