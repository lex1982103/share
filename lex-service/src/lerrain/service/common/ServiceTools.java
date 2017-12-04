package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
            v = reqId(code);
            map.put(code, v);
        }
        else
        {
            v[0]++;

            if (v[0] > v[1])
            {
                long[] r = reqId(code);
                v[0] = r[0];
                v[1] = r[1];
            }
        }

        return v[0];
    }

    public long[] reqId(String code)
    {
        String[] res = serviceMgr.reqStr("dict", "id/req", code).split(",");

        long[] r = new long[2];
        r[0] = Long.parseLong(res[0]);
        r[1] = Long.parseLong(res[1]);

        return r;
    }
}
