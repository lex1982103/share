package lerrain.service.script;

import lerrain.tool.Common;
import lerrain.tool.formula.Factors;
import lerrain.tool.formula.Formula;
import lerrain.tool.script.Script;

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
        return opt.snapshot(val);
    }

    public static Object snapshot(ReqHistory rh)
    {
        Map r = new HashMap();
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
        Map m = new HashMap();
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

    public static ReqHistory reqHistoryOf(Map rs)
    {
        ReqHistory req = new ReqHistory();
        req.setId(Common.toLong(rs.get("id")));
        req.setType(Common.intOf(rs.get("type")));
        req.setTarget((String)rs.get("target"));

        if (isScript(req.getType()))
            req.setRequest(DebugUtil.resume(10, rs.get("request")));
        else
            req.setRequest(rs.get("request"));

        req.setResponse(rs.get("response"));
        req.setResult(Common.intOf(rs.get("result")));
        req.setSpend(Common.intOf(rs.get("spend")));
        req.setTime(Common.longOf(rs.get("time")));
        req.setName((String)rs.get("name"));

        List ja = (List)rs.get("detail");
        if (ja != null)
        {
            for (int i = 0; i < ja.size(); i++)
                req.add(reqHistoryOf((Map)ja.get(i)));
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

        public Object snapshot(Object stack);

        public Object resume(int type, Object val);

        public Script getScript(ReqHistory reqHistory);

        public boolean isScript(int type);
    }
}
