package lerrain.service.script;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lerrain.service.common.Log;
import lerrain.service.common.ServiceMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@RequestMapping("/script")
public class Recorder
{
    @Value("${sys.code:null}")
    String serviceCode;

    @Value("${sys.index:null}")
    String serviceIndex;

    @Autowired
    ServiceMgr serviceMgr;

    Map<Long, ReqHistory> temp = new HashMap<>();

    //总记录点的数量需要限制，单个记录点的长短也需要限制
    private static final ThreadLocal<LinkedList<ReqHistory>> threadLocal = new ThreadLocal<>();

    private static final ThreadLocal<ReqReplay> threadRuntime = new ThreadLocal<>();

    public boolean isDebugging()
    {
        return threadRuntime.get() != null;
    }

    public void start()
    {
        threadLocal.set(new LinkedList<ReqHistory>());
    }

    public List<ReqHistory> stop()
    {
        List<ReqHistory> history = threadLocal.get();
        threadLocal.remove();

        return history;
    }

    public void save(List<ReqHistory> history, int type, String scriptId, String url, String ip, Object req, int result, Object res)
    {
        if (history == null || history.isEmpty())
            return;

        ReqHistory rh = history.get(0);

        Date date = new Date(rh.getTime());
        int spend = (int)(System.currentTimeMillis() - date.getTime());

        store(type, scriptId, url, ip, req, rh, result, res, date, spend);
    }

    public ReqHistory newHistory(int type)
    {
        LinkedList<ReqHistory> history = threadLocal.get();
        if (history == null)
            return null;

        if (threadRuntime.get() != null)
            return null;

        ReqHistory req = new ReqHistory();
        req.setType(type);
        req.setTime(System.currentTimeMillis());

        ReqHistory r1 = null;
        do
        {
            if (r1 != null)
                history.removeLast();

            if (history.isEmpty())
                break;

            r1 = history.getLast();
        }
        while (r1.isFinished());

        if (r1 != null)
            r1.add(req);

        history.add(req);

        return req;
    }

    public ReqHistory newFunctionHistory(String name)
    {
        return newFunctionHistory(name, name);
    }

    public ReqHistory newFunctionHistory(String name, String target)
    {
        ReqHistory rh = newHistory(ReqHistory.TYPE_FUNCTION);

        if (rh != null)
        {
            rh.setName(name);
            rh.setTarget(target);
        }

        return rh;
    }

    public ReqHistory newBuildInHistory(String name, String target)
    {
        ReqHistory rh = newHistory(ReqHistory.TYPE_BUILD_IN);

        if (rh != null)
        {
            rh.setName(name);
            rh.setTarget(target);
        }

        return rh;
    }

    public ReqReplay.Current replay(ReqReplay rs, Long historyId, int pos)
    {
        threadRuntime.set(rs);

        try
        {
            return rs.jumpTo(historyId, pos);
        }
        finally
        {
            threadRuntime.remove();
        }
    }

    public Object debug(String key)
    {
        //DEBUG
        LinkedList<ReqHistory> history = threadRuntime.get().getCurrentSavePoints();
        ReqHistory rh = history.removeFirst();

        if (rh.getType() == ReqHistory.TYPE_URL)
        {
            if (!rh.getTarget().equals(key))
                throw new RuntimeException("history not match");
        }
        else if (rh.getType() == ReqHistory.TYPE_FUNCTION)
        {
            if (!rh.getTarget().equals(key))
                throw new RuntimeException("history not match");

            //function可以存了，不需要这样特殊处理了
//            if (rh.getRequest() == null)
//                return rh;
        }
        else if (rh.getType() == ReqHistory.TYPE_SERVICE)
        {
            //不能用request的hash来判断，重构的map可能内容相同，但是内部实体不同导致的hash值有差异
            if (!rh.getTarget().equals(key))
                throw new RuntimeException("history not match");
        }

        if (rh.getResult() == ReqHistory.RESTYPE_FAIL)
            throw new RuntimeException(rh.getResponse().toString());

        return DebugUtil.copy(rh.getResponse()); //必须复制一份，不然后面的脚本会改了里面的值
    }

    public ReqHistory findHistory(Long reqId)
    {
        JSONObject req = new JSONObject();
        req.put("reqId", reqId);

        JSONObject res = (JSONObject)serviceMgr.reqVal("develop", "script/view_history.json", req);
        return DebugUtil.reqHistoryOf(res);
    }

    private void store(int bizType, String scriptId, String url, String ip, Object req, ReqHistory rh, int result, Object response, Date date, int spend)
    {
        JSONObject r = new JSONObject();
        r.put("service", serviceCode);
        r.put("instance", serviceIndex);
        r.put("type", bizType);
        r.put("scriptId", scriptId);
        r.put("url", url);
        r.put("ip", ip);
        r.put("request", req);
        r.put("history", DebugUtil.snapshot(rh));
        r.put("result", result);
        r.put("response", response);
        r.put("time", date);
        r.put("spend", spend);

        try
        {
            serviceMgr.req("develop", "script/record.json", r);
        }
        catch (Exception e)
        {
            Log.alert(e);
            Log.alert("url: " + url + ", response: " + response);
            Log.alert(e);
        }
    }

    public JSONObject load(Long reqId)
    {
        JSONObject req = new JSONObject();
        req.put("reqId", reqId);

        return (JSONObject) JSON.toJSON(serviceMgr.reqVal("develop","script/load.json", req));
    }

    public Object query(JSONObject condition)
    {
        return serviceMgr.reqVal("develop","script/query.json", condition);
    }

    @RequestMapping("/debug/prepare.json")
    @ResponseBody
    @CrossOrigin
    public JSONObject prepare(@RequestBody JSONObject req)
    {
        ReqHistory rh = DebugUtil.reqHistoryOf(req);

        synchronized (temp)
        {
            temp.put(rh.getId(), rh);
        }

        Map map = (Map)DebugUtil.snapshot(rh);

        JSONObject res = new JSONObject();
        res.put("result", "success");
        res.put("content", map);

        return res;
    }

    @RequestMapping("/debug/debug.json")
    @ResponseBody
    @CrossOrigin
    public JSONObject debug(@RequestBody JSONObject req)
    {
        ReqHistory rh;

        synchronized (temp)
        {
            rh = temp.get(req.getLong("reqId"));
        }

        ReqReplay rs = new ReqReplay(rh);

        Long historyId = req.getLong("historyId");
        int pos = req.getIntValue("pos");

        ReqReplay.Current current = this.replay(rs, historyId, pos);

        JSONObject res = new JSONObject();
        res.put("result", "success");
        res.put("content", DebugUtil.snapshot(current));

        return res;
    }
}
