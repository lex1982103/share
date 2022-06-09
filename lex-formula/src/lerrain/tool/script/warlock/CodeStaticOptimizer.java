package lerrain.tool.script.warlock;

import lerrain.tool.script.warlock.statement.Constant;

/**
 * 静态优化器
 */
@Deprecated
public class CodeStaticOptimizer implements Code.ErgodicListener
{
    int mode;

    CodeStaticOptimizer(int mode)
    {
        this.mode = mode;
    }

    @Override
    public void begin(Code code)
    {

    }

    @Override
    public boolean each(Code parent, int i, Code code)
    {
        if (code.isFixed(mode))
        {
            parent.replaceChild(i, new Constant(code.getWords(), code.run(null)));
            return false;
        }

        return true;
    }
}
