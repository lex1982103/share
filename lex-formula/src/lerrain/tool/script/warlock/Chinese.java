package lerrain.tool.script.warlock;

import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.CompileListener;
import lerrain.tool.script.Script;
import lerrain.tool.script.warlock.analyse.Words;

public class Chinese extends Code
{
    public static boolean translateOnCompile = false;

    String text;

    Formula script = null;

    public Chinese(Words ws)
    {
        super(ws);

        this.text = ws.getWord(0);

        if (translateOnCompile)
            translate();
    }

    private synchronized void translate()
    {
        if (script != null)
            return;

        if (Script.compileListener != null)
            script = Script.compileListener.compile(CompileListener.CHINESE, this);
        else
            script = NONE;
    }

    public Object run(Factors factors)
    {
        if (script == null)
            translate();

        return script.run(factors);
    }

    @Override
    public boolean isFixed(int mode)
    {
        return false;
    }

    public String toString()
    {
        return text;
    }

    public String toText(String space, boolean line)
    {
        return text;
    }
}
