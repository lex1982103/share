package lerrain.project.insurance.product;

import lerrain.tool.formula.Formula;

/**
 * Created by lerrain on 2017/11/24.
 */
public class Duty
{
    String code; // 保司责任code
    String name; // 责任描述

    Formula premium; // 责任保费
    Formula amount; // 责任描述

    Formula condition; // 触发条件

    Formula desc; // 责任描述
    Formula extra ;
    String iybCode; // iyb责任code
    String type; // 责任类型
    String prefix;// 描述前缀
    String suffix; // 描述后缀
    String optional; // 是否可选
    String iybName; // 责任描述


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

    public Formula getCondition()
    {
        return condition;
    }

    public void setCondition(Formula condition)
    {
        this.condition = condition;
    }

    public Formula getDesc() {
        return desc;
    }

    public void setDesc(Formula desc) {
        this.desc = desc;
    }

    public Formula getExtra() {
        return extra;
    }

    public void setExtra(Formula extra) {
        this.extra = extra;
    }

    public String getIybCode() {
        return iybCode;
    }

    public void setIybCode(String iybCode) {
        this.iybCode = iybCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getIybName() {
        return iybName;
    }

    public void setIybName(String iybName) {
        this.iybName = iybName;
    }
}
