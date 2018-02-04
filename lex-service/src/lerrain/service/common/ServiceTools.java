package lerrain.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class ServiceTools
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    Map<String, long[]> map = new HashMap<>();

    int skip = -1;

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

        if (skip < 0)
        {
            int num = jdbcTemplate.queryForObject("select count(*) from s_sequence where code = ?", Integer.class, code);
            if (num == 0)
                jdbcTemplate.update("insert into s_sequence(code, value, step) values(?, 0, 100)", code);

            skip = jdbcTemplate.queryForObject("select step from s_sequence where code = ?", Integer.class, code);
        }

        r[1] = jdbcTemplate.queryForObject("select s_next_seq(?) from dual", Long.class, code);
        r[0] = r[1] - skip + 1;

        return r;
    }
}
