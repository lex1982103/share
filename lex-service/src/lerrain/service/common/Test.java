package lerrain.service.common;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by lerrain on 2017/8/3.
 */
public class Test
{
    public static void main(String[] arg)
    {
        ServiceMgr cm = new ServiceMgr();
        ServiceClient client = cm.getClient("http://localhost:7773");

        JSONObject p = new JSONObject();
        p.put("name", "marriage");
        p.put("company", "dawn");

        Object val = client.req("dict/view.json", p);
        Log.info(val.toString());
    }
}
