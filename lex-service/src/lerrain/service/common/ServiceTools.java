package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServiceTools
{
    @Value("${sys.code:null}")
    String serviceCode;

    @Value("${sys.index:null}")
    String serviceIndex;

    @Autowired
    ServiceMgr serviceMgr;

    Map<String, long[]> map = new HashMap<>();

    Random ran = new Random();

    public synchronized Long nextId(String code)
    {
        long[] v = map.get(code);

        if (v == null)
        {
            v = reqId(code, new long[3], 5000, 10000, 30000);
            map.put(code, v);
        }
        else
        {
            if (v[2] <= 1)
                v[0]++;
            else
                v[0] += ran.nextInt((int)v[2] - 1) + 1;

            if (v[0] > v[1])
                reqId(code, v, 5000, 10000, 30000);
        }

        return v[0];
    }

    private long[] reqId(String code, long[] r, int... sleep)
    {
        try
        {
            String[] res = serviceMgr.reqStr("tools", "id/req", code).split(",");

            r[0] = Long.parseLong(res[0]);
            r[1] = Long.parseLong(res[1]);
            r[2] = Long.parseLong(res[2]);

            if (r[2] > 1)
                r[0] += ran.nextInt((int)r[2]);

            return r;
        }
        catch (Exception e3)
        {
            if (sleep != null && sleep.length > 0)
            {
                try
                {
                    Thread.sleep(sleep[0]);
                }
                catch (InterruptedException i3)
                {
                    throw new RuntimeException(i3);
                }

                int[] ns = new int[sleep.length - 1];
                for (int i = 0; i < ns.length; i++)
                    ns[i] = sleep[i + 1];

                return reqId(code, r, ns);
            }
        }

        throw new RuntimeException("获取id失败，tools服务异常");
    }

    public static JSONObject success(Object val)
    {
        JSONObject res = new JSONObject();
        res.put("result", "success");
        res.put("content", val);

        return res;
    }

    public static JSONObject fail(Object val)
    {
        JSONObject res = new JSONObject();
        res.put("result", "fail");
        res.put("content", val);

        return res;
    }

    /**
     * synchronized对controller的每个方法来加锁效率比较高，并不需要对这个方法加锁，这里是简单化处理
     * @param param
     */
    public synchronized void idempotent(JSONObject param)
    {
        String key = param.getString("idempotent");
        if (key == null)
            throw new RuntimeException("no idempotent key");

        JSONObject req = new JSONObject();
        req.put("service", serviceCode);
        req.put("key", "idempotent/" + key);

        String res = (String)serviceMgr.reqVal("cache", "load.json", req);
        if (res != null)
            throw new RuntimeException("重复的请求");

        req.put("value", "Y");
        req.put("timeout", 3600000L * 24 * 30); //30天内幂等

        serviceMgr.req("cache", "save.json", req);
    }

    /**
     * 获取当前应用编排编号
     * @return
     */
    public String getServiceIndex(){
        return serviceIndex;
    }
}
