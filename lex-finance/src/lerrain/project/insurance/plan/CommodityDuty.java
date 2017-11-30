package lerrain.project.insurance.plan;

import lerrain.project.insurance.product.Duty;
import lerrain.tool.formula.Factors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lerrain on 2017/11/24.
 */
public class CommodityDuty extends ArrayList
{
    List dutyList;

    Factors factors;

    public CommodityDuty(List dutyList, Factors factors)
    {
        this.factors = factors;
        this.dutyList = dutyList;

        this.clear();
    }

    public Object get(int index)
    {
        Map map = (Map)super.get(index);
        if (map == null)
        {
            Duty duty = (Duty)dutyList.get(index);

            map = new HashMap();
            map.put("NAME", duty.getName());
            map.put("CODE", duty.getCode());

            if (duty.getAmount() != null)
                map.put("AMOUNT", duty.getAmount().run(factors));
            if (duty.getPremium() != null)
                map.put("PREMIUM", duty.getPremium().run(factors));

            super.set(index, map);
        }

        return map;
    }

    public void clear()
    {
        super.clear();

        for (int i=0;i<dutyList.size();i++)
            super.add(null);
    }
}
