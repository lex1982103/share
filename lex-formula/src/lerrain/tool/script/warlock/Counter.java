package lerrain.tool.script.warlock;

/**
 * 目前只给计数器用，提高效率
 */
public class Counter extends Number
{
    int v;

    public Counter()
    {
    }

    public Counter(int v)
    {
        this.v = v;
    }

    public int getAndAdd()
    {
        return v++;
    }

    public int addAndGet()
    {
        return ++v;
    }

    @Override
    public int intValue()
    {
        return v;
    }

    @Override
    public long longValue()
    {
        return v;
    }

    @Override
    public float floatValue()
    {
        return v;
    }

    @Override
    public double doubleValue()
    {
        return v;
    }
}
