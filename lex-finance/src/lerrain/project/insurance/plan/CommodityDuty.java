package lerrain.project.insurance.plan;

import lerrain.project.insurance.product.Duty;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lerrain on 2017/11/24.
 */
public class CommodityDuty extends ArrayList
{
    List<Duty> dutyList;

    Factors factors;

    boolean inited = false;

    public CommodityDuty(List dutyList, Factors factors)
    {
        this.factors = factors;
        this.dutyList = dutyList;

        this.clear();
    }

    public Object get(int index)
    {
        if (!inited)
            this.init();

        return super.get(index);
    }

    private synchronized void init()
    {
        if (dutyList != null) for (Duty duty : dutyList)
        {
            if (duty.getCondition() != null && !Value.booleanOf(duty.getCondition(), factors))
                continue;

            Map map = new HashMap();
            map.put("NAME", duty.getName());
            map.put("CODE", duty.getCode());

            if (duty.getAmount() != null)
                map.put("AMOUNT", duty.getAmount().run(factors));
            if (duty.getPremium() != null)
                map.put("PREMIUM", duty.getPremium().run(factors));

            super.add(map);
        }
    }

    public int size()
    {
        if (!inited)
            this.init();

        return super.size();
    }

    public void clear()
    {
        super.clear();

        inited = false;
    }
}
