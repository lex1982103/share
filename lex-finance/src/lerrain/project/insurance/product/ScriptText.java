package lerrain.project.insurance.product;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptText implements Formula
{
    List list = new ArrayList();

    public ScriptText(String text)
    {
        int p1 = 0;
        int p2 = -1;

        int deep = 0;

        boolean quote1 = false;
        boolean quote2 = false;

        char l = 0, c;
        int len = text.length();

        for (int i = 0; i < len; i++)
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

                if (deep == 0)
                {
                    p1 = i;
                    String  s = text.substring(p1-1, p1);
                    if (s.equals("#"))
                    list.add(text.substring(p2 + 1, p1-1));
                }

                deep++;
            }
            else if (c == '}')
            {
                deep--;
                String  s = text.substring(p1-1, p1);
                if (deep == 0 && s.equals("#"))
                {
                    p2 = i;
                    list.add(Script.scriptOf(text.substring(p1 + 1, p2)));
                }
            }
        }

        list.add(text.substring(p2 + 1));
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
