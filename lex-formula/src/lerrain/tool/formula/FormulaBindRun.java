package lerrain.tool.formula;

/**
 * AGENT.ZJ_SCORE
 * 这种情况，如果AGENT是个Factors，那会存在使用AGENT这个环境还是当前环境计算的问题
 * BindRun使用AGENT计算，AutoRun使用当前环境
 */
public interface FormulaBindRun extends FormulaAutoRun
{
}
