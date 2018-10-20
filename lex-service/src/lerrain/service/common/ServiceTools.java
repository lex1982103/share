package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ServiceTools
{
    @Autowired
    ServiceMgr serviceMgr;

    Map<String, long[]> map = new HashMap<>();

    public synchronized Long nextId(String code)
    {
        long[] v = map.get(code);

        if (v == null)
        {
            v = reqId(code, new long[2]);
            map.put(code, v);
        }
        else
        {
            v[0]++;

            if (v[0] > v[1])
                reqId(code, v);
        }

        return v[0];
    }

    private long[] reqId(String code, long[] r)
    {
        String[] res = serviceMgr.reqStr("tools", "id/req", code).split(",");

        r[0] = Long.parseLong(res[0]);
        r[1] = Long.parseLong(res[1]);

        return r;
    }
}
