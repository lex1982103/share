package lerrain.service.script;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;

import java.util.*;

public class DebugUtil
{
    public static Recorder RECORDER;

    static ObjectOperate opt;

    static Map<Long, Script> temp = new HashMap<>();

    public static void initiate(Recorder recorder, ObjectOperate opt)
    {
        DebugUtil.RECORDER = recorder;
        DebugUtil.opt = opt;
    }

    public static Object copy(Object val)
    {
        return opt.copy(val);
    }

    public static boolean isScript(int type)
    {
        return opt.isScript(type);
    }

    public static Object snapshot(Object val)
    {
        if (val instanceof Stack)
            return opt.snapshot((Stack)val);

        return val;
    }

    public static Object snapshot(ReqHistory rh)
    {
        JSONObject r = new JSONObject();
        r.put("id", rh.id);
        r.put("type", rh.type);
        r.put("target", rh.target);
        r.put("request", DebugUtil.snapshot(rh.request));
        r.put("response", DebugUtil.snapshot(rh.response));
        r.put("result", rh.result);
        r.put("time", new Date(rh.time));
        r.put("spend", rh.spend);
        r.put("name", rh.name);

        if (rh.detail != null)
        {
            List list = new ArrayList();
            for (ReqHistory c : rh.detail)
                list.add(snapshot(c));

            r.put("detail", list);
        }

        return r;
    }

    public static Object snapshot(ReqReplay.Current current)
    {
        JSONObject m = new JSONObject();
        m.put("range", current.range);
        m.put("count", current.count);
        m.put("result", current.result);
        m.put("error", current.error);
        m.put("stack", current.stack);
        m.put("script", current.script);

        return m;
    }

    public static Script getScript(ReqHistory rh)
    {
        synchronized (temp)
        {
            if (temp.containsKey(rh.getId()))
                return temp.get(rh.getId());
        }

        Script script = opt.getScript(rh);

        synchronized (temp)
        {
            temp.put(rh.getId(), script);
        }

        return script;
    }

    public static Object resume(int type, Object val)
    {
        return opt.resume(type, val);
    }

    public static ReqHistory reqHistoryOf(JSONObject rs)
    {
        ReqHistory req = new ReqHistory();
        req.setId(rs.getLong("id"));
        req.setType(rs.getIntValue("type"));
        req.setTarget(rs.getString("target"));

        if (isScript(req.getType()))
            req.setRequest(DebugUtil.resume(10, rs.getJSONObject("request")));
        else
            req.setRequest(rs.get("request"));

        req.setResponse(rs.get("response"));
        req.setResult(rs.getIntValue("result"));
        req.setSpend(rs.getIntValue("spend"));
        req.setTime(rs.getDate("time").getTime());
        req.setName(rs.getString("name"));

        JSONArray ja = rs.getJSONArray("detail");
        if (ja != null)
        {
            for (int i = 0; i < ja.size(); i++)
                req.add(reqHistoryOf(ja.getJSONObject(i)));
        }

        return req;
    }

    /**
     * @param recorder
     * @param functionId
     * @param script
     * @param stack
     * @return
     */
    public static Object debug(Recorder recorder, String functionId, Formula script, Factors stack)
    {
        if (recorder.isDebugging())
            return recorder.debug(functionId);

        ReqHistory reqHistory = null;

        if (reqHistory == null)
            reqHistory = recorder.newHistory(ReqHistory.TYPE_FUNCTION);

        if (reqHistory != null)
        {
            reqHistory.setTarget(functionId);
            reqHistory.setName(reqHistory.getTarget() + "@?");
        }

        if (reqHistory != null)
            reqHistory.setRequest(stack);

        try
        {
            Object res = script.run(stack);

            if (reqHistory != null)
                reqHistory.setResponse(res);

            return res;
        }
        catch (Exception e)
        {
            if (reqHistory != null)
                reqHistory.setError(e.getMessage());

            throw e;
        }
        finally
        {
            if (reqHistory != null)
                reqHistory.finish();
        }
    }

    public interface ObjectOperate
    {
        public Object copy(Object val);

        public Object snapshot(Stack stack);

        public Object resume(int type, Object val);

        public Script getScript(ReqHistory reqHistory);

        public boolean isScript(int type);
    }
}
