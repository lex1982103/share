package lerrain.service.script;

import lerrain.service.common.Param;
import lerrain.service.common.PostQueue;
import lerrain.service.common.Result;
import lerrain.service.common.ServiceMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
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

    PostQueue rq;

    Map<Long, ReqHistory> temp = new HashMap<>();

    @PostConstruct
    public void initiate()
    {
        if (rq != null)
            rq.stop();

        rq = new PostQueue();
        rq.initiate(serviceMgr, 128, "develop", "script/record.json");
        rq.start();
    }

    public void clear()
    {
        temp.clear();
    }

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

    public void save(List<ReqHistory> history, int type, String scriptId, String url, String ip, Object req, int result, Object res, boolean overwrite)
    {
        save(history, type, scriptId, url, ip, req, result, res, overwrite);
    }

    public void save(List<ReqHistory> history, int type, String scriptId, String url, String ip, Object req, int result, Object res, boolean overwrite, String seekKey)
    {
        if (history == null || history.isEmpty())
            return;

        ReqHistory rh = history.get(0);

        Date date = new Date(rh.getTime());
        int spend = (int)(System.currentTimeMillis() - date.getTime());

        store(type, scriptId, url, ip, req, rh, result, res, date, spend, overwrite, seekKey);
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

    public ReqHistory newBuildInHistory(String name)
    {
        ReqHistory rh = newHistory(ReqHistory.TYPE_BUILD_IN);

        if (rh != null)
        {
            rh.setName(name);
            rh.setTarget(name);
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
        Map req = new HashMap();
        req.put("reqId", reqId);

        Map res = serviceMgr.reqVal("develop", "script/view_history.json", req, Map.class);
        return DebugUtil.reqHistoryOf(res);
    }

    private void store(int bizType, String scriptId, String url, String ip, Object req, ReqHistory rh, int result, Object response, Date date, int spend, boolean overwrite, String seekKey)
    {
        Map r = new HashMap();
        r.put("key", seekKey);
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
        r.put("overwrite", overwrite);

        rq.addMsg(r);
    }

    public Map load(Long reqId)
    {
        Map req = new HashMap();
        req.put("reqId", reqId);

        return (Map) serviceMgr.reqVal("develop","script/load.json", req, Map.class);
    }

    public Map load(String reqKey)
    {
        Map req = new HashMap();
        req.put("reqKey", reqKey);

        return (Map) serviceMgr.reqVal("develop","script/load.json", req, Map.class);
    }

    public Object query(Map condition)
    {
        return serviceMgr.reqVal("develop","script/query.json", condition, null);
    }

    @RequestMapping("/debug/prepare.json")
    @ResponseBody
    @CrossOrigin
    public Object prepare(@RequestBody Param req)
    {
        ReqHistory rh = DebugUtil.reqHistoryOf(req);

        synchronized (temp)
        {
            temp.put(rh.getId(), rh);
        }

        Map map = (Map)DebugUtil.snapshot(rh);
        return Result.success(map);
    }

    @RequestMapping("/debug/debug.json")
    @ResponseBody
    @CrossOrigin
    public Object debug(@RequestBody Param req)
    {
        ReqHistory rh;

        synchronized (temp)
        {
            rh = temp.get(req.getLong("reqId"));
        }

        ReqReplay rs = new ReqReplay(rh);

        Long historyId = req.getLong("historyId");
        int pos = req.getIntVal("pos");

        ReqReplay.Current current = this.replay(rs, historyId, pos);
        return Result.success(DebugUtil.snapshot(current));
    }
}
