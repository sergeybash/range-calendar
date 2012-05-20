package me.ash.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import me.ash.calendar.R.id;
import me.ash.calendar.view.Day;
import me.ash.calendar.view.RangeCalendar;
import me.ash.calendar.view.RangeListener;

public class MainActivity extends Activity implements RangeListener
{
    RangeCalendar calendar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        calendar = (RangeCalendar) findViewById(id.rangeCalendar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        calendar.setRangeListener(this);
    }

    @Override
    protected void onPause() {
        calendar.removeRangeListener();
        super.onPause();
    }

    public void onRangeSelected(Day first, Day last) {
        Toast.makeText(this, first.toString() + " Ñ " + last.toString(), Toast.LENGTH_SHORT).show();
    }
}
