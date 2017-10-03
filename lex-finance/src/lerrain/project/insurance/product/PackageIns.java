package lerrain.project.insurance.product;

import lerrain.tool.formula.Formula;

import java.util.*;

/**
 * Created by lerrain on 2017/4/25.
 * @deprecated
 */
public class PackageIns
{
    String id;														//产品id，考虑程序接口简单，采用单键确定险种，公司间不可重复。
    String code;													//产品code
    String name;													//产品名称
    String abbrName;												//产品简称
    String type;
    String vendor;													//供应商

    int sequence						= 1000;						//在产品列表中的排序位置

    Map detail = new LinkedHashMap();

    List input = new ArrayList();

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getSequence()
    {
        return sequence;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    public String getAbbrName()
    {
        return abbrName;
    }

    public void setAbbrName(String abbrName)
    {
        this.abbrName = abbrName;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addInput(InputItem inputItem)
    {
        input.add(inputItem);
    }

    public SubIns getInsurance(String code)
    {
        return (SubIns)detail.get(code);
    }

    public void addInsurance(SubIns ins)
    {
        detail.put(ins.getInsId(), ins);
    }

    public static class SubIns
    {
        String insId;

        SubIns parent;

        Formula condition;

        public Formula getCondition()
        {
            return condition;
        }

        public void setCondition(Formula condition)
        {
            this.condition = condition;
        }

        public String getInsId()
        {
            return insId;
        }

        public void getInsId(String insId)
        {
            this.insId = insId;
        }

        public SubIns getParent()
        {
            return parent;
        }

        public void setParent(SubIns parent)
        {
            this.parent = parent;
        }
    }

    public static class InputItem
    {
        String type;
        String label;
        String name;
        String limit;
        String value;

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

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }
}
