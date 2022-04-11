package lerrain.tool.script.warlock;

import lerrain.tool.formula.AutoConstant;
import lerrain.tool.formula.Factors;

/**
 * var AGENT := Env('agent');
 * var CHANNEL := AGENT.CHANNEL;
 * AGENT本身不含有CHANNEL信息
 * 如果不是FastLet，那么this里还没有CHANNEL，会返回null结束
 * 如果是FastLet，this里已经有CHANNEL的快速定义信息，那么AGENT.CHANNEL返回的实际上是this里的CHANNEL（AGENT是通过this构造）
 * 而this里的CHANNEL的值又是AGENT.CHANNEL，去取的时候会造成无限循环，从而StackOverFlow
 *
 * 解决办法：AGENT里面必须要有CHANNEL信息，哪怕是个NULL，有定义就行
 * 屏蔽类似错误办法：AutoCodeConstant里面要有判重手段，历史调用链中，不能有自己
 * 所以
 *   v = r.run(f);
 *   computed = true;
 * 改成了
 *   computed = true;
 *   v = r.run(f);
 */
public class AutoCodeConstant implements AutoConstant
{
    boolean computed = false;

    Code r;
    Factors f;

    Object v;

    public AutoCodeConstant(Code r, Factors f)
    {
        this.r = r;
        this.f = f;
    }

    @Override
    public synchronized Object run()
    {
        if (computed)
            return v;

        computed = true;
        v = r.run(f);

        return v;
    }
}