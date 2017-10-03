package lerrain.project.insurance.product;

import lerrain.tool.formula.Formula;

/**
 * Created by lerrain on 2017/4/26.
 */
public class Field
{
    String type;
    String label;
    String name;
    String limit;
    String widget;
    String regex; //正则表达式校验

    boolean req;

    Object value; //默认值

    Formula options;

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLimit()
    {
        return limit;
    }

    public void setLimit(String limit)
    {
        this.limit = limit;
    }

    public String getWidget()
    {
        return widget;
    }

    public void setWidget(String widget)
    {
        this.widget = widget;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRegex()
    {
        return regex;
    }

    public void setRegex(String regex)
    {
        this.regex = regex;
    }

    public boolean isReq()
    {
        return req;
    }

    public void setReq(boolean req)
    {
        this.req = req;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Formula getOptions()
    {
        return options;
    }

    public void setOptions(Formula options)
    {
        this.options = options;
    }
}