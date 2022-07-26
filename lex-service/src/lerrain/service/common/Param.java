package lerrain.service.common;

import lerrain.tool.Common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class Param extends HashMap
{
    public Param getMap(String field)
    {
        Map map = (Map)get(field);
        if (map == null)
            return null;

        Param param = new Param();
        param.putAll(map);
        return param;
    }

    public Long getLong(String field)
    {
        return Common.toLong(get(field));
    }

    public long getLongVal(String field)
    {
        return Common.longOf(get(field));
    }

    public long getLongVal(String field, long mis)
    {
        return Common.longOf(get(field), mis);
    }

    public Integer getInteger(String field)
    {
        return Common.toInteger(get(field));
    }

    public int getIntVal(String field)
    {
        return Common.intOf(get(field));
    }

    public int getIntVal(String field, int mis)
    {
        return Common.intOf(get(field), mis);
    }

    public List getList(String field)
    {
        return (List)get(field);
    }

    public Object[] getArray(String field)
    {
        List list = (List)get(field);
        if (list == null)
            return null;

        return list.toArray();
    }

    public String getString(String field)
    {
        Object s = get(field);
        return s == null ? null : s.toString();
    }

    public String[] getStringArray(String field)
    {
        List<String> list = getList(field);
        if (list == null)
            return null;

        return list.toArray(new String[list.size()]);
    }

    public long[] getLongArray(String field)
    {
        List list = getList(field);
        if (list == null)
            return null;

        long[] r = new long[list.size()];
        for (int i=0;i<r.length;++i)
            r[i] = Common.longOf(list.get(i));

        return r;
    }

    public int[] getIntArray(String field)
    {
        List list = getList(field);
        if (list == null)
            return null;

        int[] r = new int[list.size()];
        for (int i=0;i<r.length;++i)
            r[i] = Common.intOf(list.get(i));

        return r;
    }

    public BigDecimal getDecimal(String field)
    {
        return Common.decimalOf(get(field));
    }

    public Double getDouble(String field)
    {
        return Common.toDouble(get(field));
    }

    public double getDoubleVal(String field)
    {
        return Common.doubleOf(get(field));
    }

    public double getDoubleVal(String field, double mis)
    {
        return Common.doubleOf(get(field), mis);
    }

    public Boolean getBoolean(String field)
    {
        return Common.toBoolean(get(field));
    }

    public boolean getBoolVal(String field)
    {
        return Common.boolOf(get(field));
    }

    public boolean getBoolVal(String field, boolean mis)
    {
        return Common.boolOf(get(field), mis);
    }

    public Date getDate(String field)
    {
        return Common.dateOf(get(field));
    }
}
