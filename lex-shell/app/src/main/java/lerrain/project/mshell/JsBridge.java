package lerrain.project.mshell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lerrain.tool.Common;

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
    public void setTitle(final String title)
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                layer.title.setText(title);
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
                Layer newLayer = new Layer(layer.window);
                newLayer.openLocal(url);

                layer.getRoot().addLayout(newLayer);
            }
        });
    }

    @JavascriptInterface
    public void back()
    {
        layer.post(new Runnable()
        {
            @Override
            public void run()
            {
                layer.getRoot().back();
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
                    keys[i] = i;
                    items[i] = Common.trimStringOf(list.get(i));
                }
            }

            final Object[] kkk = keys;

            AlertDialog.Builder builder = new AlertDialog.Builder(layer.getContext(), 3);
            builder.setTitle("列表");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, final int which)
                {
                    layer.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Object select = kkk[which];
                            if (!(select instanceof Number))
                                select = "\"" + Common.trimStringOf(select) + "\"";

                            Log.i("mshell", "select " + select);
                            layer.runJs("APP.callback(" + select + ")");
                        }
                    });

                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    public void toast()
    {

    }
}
