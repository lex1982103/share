package lerrain.project.mshell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

import lerrain.project.mshell.component.DatePicker;
import lerrain.project.mshell.component.OnSelectListener;
import lerrain.project.mshell.component.SelectPicker;

/**
 * Created by lerrain on 2018/6/13.
 */

public class JsBridge
{
    Layer layer;

    public JsBridge(Layer layer)
    {
        this.layer = layer;
    }

    @JavascriptInterface
    public String env(String key)
    {
        return layer.getRoot().env.get(key);
    }

    @JavascriptInterface
    public void setEnv(String key, String val)
    {
        layer.getRoot().env.put(key, val);
    }

    @JavascriptInterface
    public void setTitle(final String title)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                layer.setTitle(title);
            }
        });
    }

    @JavascriptInterface
    public void direct(final String url)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                layer.openLocal(url);
            }
        });
    }

    @JavascriptInterface
    public void navi(final String url)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                Layer newLayer = new PageLayer(layer.window);
                layer.getRoot().addLayout(newLayer);

                newLayer.openLocal(url);
            }
        });
    }

    @JavascriptInterface
    public void pop(final String url, final int percent)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                Layer newLayer = new PopLayer(layer.window, Common.intOf(percent, 75));
                layer.getRoot().addLayout(newLayer);

                newLayer.openLocal(url);
            }
        });
    }


    @JavascriptInterface
    public void back(final String val)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                layer.getRoot().back(val);
            }
        });
    }

    @JavascriptInterface
    public void pick(String type, String json)
    {
        if ("select".equals(type))
        {
            Object[] keys;
            String[] items;

            Log.i("mshell", json);

            try
            {
                JSONObject obj = JSON.parseObject(json);

                keys = new Object[obj.size()];
                items = new String[obj.size()];

                int i = 0;
                for (String key : obj.keySet())
                {
                    keys[i] = key;
                    items[i] = Common.trimStringOf(obj.get(key));
                    i++;
                }
            }
            catch (Exception e)
            {
                JSONArray list = JSON.parseArray(json);

                keys = new Object[list.size()];
                items = new String[list.size()];

                for (int i=0;i<list.size();i++)
                {
                    Object val = list.get(i);
                    if (val instanceof JSONArray)
                    {
                        JSONArray ja = (JSONArray)val;
                        keys[i] = ja.get(0);
                        items[i] = Common.trimStringOf(ja.get(1));
                    }
                    else if (val instanceof JSONObject)
                    {
                        JSONObject ja = (JSONObject)val;
                        keys[i] = ja.getString("code");
                        items[i] = ja.getString("text");
                    }
                    else
                    {
                        keys[i] = i;
                        items[i] = Common.trimStringOf(list.get(i));
                    }
                }
            }

            final Object[] kkk = keys;

            SelectPicker sp = new SelectPicker(layer);
            sp.show(items, new OnSelectListener()
            {
                @Override
                public void select(Object item)
                {
                    Object select = kkk[(int)item];
                    if (!(select instanceof Number))
                        select = "\"" + Common.trimStringOf(select) + "\"";

                    Log.i("mshell", "select " + select);
                    layer.runJs("APP.callback(" + select + ")");
                }
            });
        }
        else if ("date".equals(type))
        {
            Date begin = null;
            Date end = null;
            Date now = new Date();

            try
            {
                JSONObject obj = JSON.parseObject(json);
                begin = obj.getDate("begin");
                end = obj.getDate("end");
                now = obj.getDate("now");
            }
            catch (Exception e)
            {
            }

            DatePicker dpw = new DatePicker(layer, begin, end, now);
            dpw.show(new OnSelectListener()
            {
                @Override
                public void select(Object time)
                {
                    Log.i("mshell", "select " + time);
                    layer.runJs("APP.callback(\"" + time + "\")");
                }
            });
        }
    }

    @JavascriptInterface
    public void alert(String title, String text, String left, String right)
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(layer.window);
        builder.setTitle(title);
        builder.setMessage(text);

        if (left != null) builder.setPositiveButton(left, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                layer.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        layer.runJs("APP.callback(true)");
                    }
                });
            }
        });

        if (right != null) builder.setNegativeButton(right, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                layer.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        layer.runJs("APP.callback(false)");
                    }
                });
            }
        });

        builder.show();
    }

}
