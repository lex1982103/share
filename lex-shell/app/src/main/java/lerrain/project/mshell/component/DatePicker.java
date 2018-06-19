package lerrain.project.mshell.component;

import android.app.DatePickerDialog;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

import lerrain.project.mshell.Common;

/**
 * Created by lerrain on 2018/6/17.
 */

public class DatePicker
{
    View root;

    DatePickerDialog dpd;

    OnSelectListener listener;

    public DatePicker(final View view, Date begin, Date end, Date now)
    {
        this.root = view;

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        dpd = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                final Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                root.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String time = Common.getString(cal.getTime());
                        listener.select(time);
                    }
                });

                dpd.dismiss();
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    public void show(OnSelectListener listener)
    {
        this.listener = listener;

        dpd.show();
    }
}
