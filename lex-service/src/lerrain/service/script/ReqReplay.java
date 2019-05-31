package lerrain.service.script;

import lerrain.tool.script.Script;
import lerrain.tool.script.ScriptRuntimeException;
import lerrain.tool.script.Stack;
import lerrain.tool.script.warlock.Code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReqReplay
{
    ReqHistory req;

    LinkedList<ReqHistory> history;

    public ReqReplay(ReqHistory req)
    {
        this.req = req;
    }

    public ReqHistory getReq()
    {
        return req;
    }

    public void setReq(ReqHistory req)
    {
        this.req = req;
    }

    public boolean isMatch(Long reqId)
    {
        return req.getId().equals(reqId);
    }

    private LinkedList setParam(LinkedList list, List<ReqHistory> rlist)
    {
        if (rlist != null)
        {
            for (ReqHistory rh : rlist)
            {
                if (rh.getType() == ReqHistory.TYPE_SERVICE || rh.getType() == ReqHistory.TYPE_URL || rh.getType() == ReqHistory.TYPE_FUNCTION)
                    list.add(rh);

                if (rh.getType() == ReqHistory.TYPE_FUNCTION)
                    if (rh.getRequest() == null)
                        setParam(list, rh.getDetail());
            }
        }

        return list;
    }

    public LinkedList<ReqHistory> getCurrentSavePoints()
    {
        return history;
    }

    private ReqHistory find(ReqHistory reqHistory, Long historyId)
    {
        if (reqHistory.getId().equals(historyId))
            return reqHistory;

        ReqHistory res = null;
        if (reqHistory.getDetail() != null)
        {
            for (ReqHistory child : reqHistory.getDetail())
            {
                res = find(child, historyId);
                if (res != null)
                    break;
            }
        }

        return res;
    }

    public Current jumpTo(Long historyId, int pos)
    {
        final ReqHistory reqHistory = find(req, historyId);

        Script script = DebugUtil.getScript(reqHistory);

        script.clearBreakPoints();
        Code code = script.markBreakPoint(pos);

        final Current r = new Current();
        r.script = script.getFullScriptString();

        if (code != null)
            r.range = code.getRange();

        //不能拿内存里的跑，跑完原版就被改了
        final Stack stack = (Stack)DebugUtil.copy(reqHistory.getRequest());
        stack.setDebug(Stack.DEBUG_BREAK_POINT);

        stack.setBreakListener(new Stack.BreakListener()
        {
            @Override
            public void onBreak(Code code, Stack stack)
            {
                if (r.stack == null)
                    r.stack = DebugUtil.snapshot(stack);

                r.count++;
            }

            @Override
            public void onReturn(Code code, Stack stack, Object val)
            {
                if (reqHistory.getId().equals(code.getScriptTag()))
                    r.setResult(code.getRange(), val);
            }
        });

        history = setParam(new LinkedList<>(), reqHistory.getDetail());

        try
        {
            script.run(stack);
        }
        catch (ScriptRuntimeException e)
        {
            r.setError(e.getCode() == null ? null : e.getCode().getRange(), e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();

            r.setError(null, e.getMessage());
        }

        return r;
    }

    public static class Current
    {
        int[] range;
        int count;

        Object stack;

        Object result;
        Object error;

        String script;

        public void setResult(int[] range, Object val)
        {
            Map result = new HashMap();
            result.put("range", range);
            result.put("value", val);

            this.result = result;
        }

        public void setError(int[] range, Object msg)
        {
            Map error = new HashMap();
            error.put("range", range);
            error.put("msg", msg);

            this.error = error;
        }
    }

}
