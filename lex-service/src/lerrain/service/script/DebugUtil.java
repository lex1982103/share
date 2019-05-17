package lerrain.service.script;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.Script;
import lerrain.tool.script.Stack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebugUtil
{
    static ObjectOperate opt;

    public static void initiate(ObjectOperate opt)
    {
        DebugUtil.opt = opt;
    }

    public static Object copy(Object val)
    {
        return opt.copy(val);
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

        return m;
    }

    public static Script getScript(ReqHistory rh)
    {
        return opt.getScript(rh);
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

        if (req.getType() == ReqHistory.TYPE_FUNCTION)
            req.setRequest(DebugUtil.resume(10, rs.getJSONObject("request")));
        else
            req.setRequest(rs.get("request"));

        req.setResponse(rs.get("response"));
        req.setResult(rs.getIntValue("result"));
        req.setSpend(rs.getIntValue("spend"));
        req.setTime(rs.getDate("time").getTime());
        req.setName(req.getTarget());

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
    }
}
