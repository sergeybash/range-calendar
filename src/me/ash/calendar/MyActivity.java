package me.ash.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import me.ash.calendar.R.id;

public class MyActivity extends Activity
{
    private TableLayout calendar;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        calendar = (TableLayout) findViewById(id.calendar);

        for (int i = 1; i < calendar.getChildCount(); i++) {
            TableRow row = (TableRow) calendar.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView day = (TextView) row.getChildAt(j);
//                day.setTextColor(0xFF000000);
            }
        }
    }
}
