package lerrain.tool.formula;

public interface VariableFactors extends Factors
{
    public void set(String name, Object value);

    public boolean hasVar(String name);
}
