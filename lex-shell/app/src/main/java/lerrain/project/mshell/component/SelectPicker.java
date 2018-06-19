package lerrain.project.mshell.component;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by lerrain on 2018/6/17.
 */

public class SelectPicker
{
    AlertDialog.Builder builder;

    View view;

    public SelectPicker(View view)
    {
        this.view = view;

        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("列表");
    }

    public void show(String[] items, final OnSelectListener onSelect)
    {
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, final int which)
            {
                view.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onSelect.select(which);
                    }
                });

                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
