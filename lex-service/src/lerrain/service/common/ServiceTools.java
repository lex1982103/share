package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ServiceTools
{
//    @Autowired
//    ServiceMgr serviceMgr;

    @Autowired
    JdbcTemplate jdbc;

    String sql = "select s_next_seq(?) from dual";

    int skip = -1;

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
        long[] r = new long[2];

//        JSONObject res = serviceMgr.req("dict", "id/range.do?code=" + code, null);
//        JSONObject re1 = res.getJSONObject("content");
//
//        r[0] = re1.getLongValue("begin");
//        r[1] = re1.getLongValue("end");

        if (skip < 0)
        {
            int num = jdbc.queryForObject("select count(*) from s_sequence where code = ?", Integer.class, code);
            if (num == 0)
                jdbc.update("insert into s_sequence(code, value, step) values(?, 0, 100)", code);

            skip = jdbc.queryForObject("select step from s_sequence where code = ?", Integer.class, code);
        }

        r[1] = jdbc.queryForObject(sql, Long.class, code);
        r[0] = r[1] - skip + 1;

        return r;
    }
}
