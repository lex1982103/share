package lerrain.project.insurance.product;

import lerrain.tool.formula.Formula;

/**
 * Created by lerrain on 2017/11/24.
 */
public class Duty
{
    String code;
    String name;

    Formula premium;
    Formula amount;

    public Formula getAmount()
    {
        return amount;
    }

    public void setAmount(Formula amount)
    {
        this.amount = amount;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Formula getPremium()
    {
        return premium;
    }

    public void setPremium(Formula premium)
    {
        this.premium = premium;
    }
}
