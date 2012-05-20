package me.ash.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import me.ash.calendar.R;
import me.ash.calendar.R.color;
import me.ash.calendar.R.layout;

/**
 * @author Sergey Basheleyshvili
 */
public class MyCalendar extends LinearLayout implements OnClickListener {


    private TextView monthText;
    private TableLayout calendarTable;
    private Month month;


    public MyCalendar(Context context) {
        super(context);
        inflate();
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate();
    }

    private void inflate() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layout.calendar, this);

        monthText = (TextView) findViewById(R.id.month);
        calendarTable = (TableLayout) findViewById(R.id.calendar);
        Button prevMonth = (Button) findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                month.prevMonth();
                drawCalendar();
            }
        });
        Button nextMonth = (Button) findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                month.nextMonth();
                drawCalendar();
            }
        });

        initCalendar();
    }

    private void initCalendar() {
        month = new Month();

        TableRow weekDaysRow = (TableRow) calendarTable.getChildAt(0);
        for (int i = 0; i < weekDaysRow.getChildCount(); i++) {
            TextView weekDay = (TextView) weekDaysRow.getChildAt(i);
            weekDay.setText(month.getWeekDayName(i));
        }

        drawCalendar();

    }

    private void drawCalendar() {
        for (int i = 0; i < calendarTable.getChildCount() - 1; i++) {
            TableRow row = (TableRow) calendarTable.getChildAt(i+1);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView dayView = (TextView) row.getChildAt(j);
                Day day = month.getDay(i, j);

                switch (month.getKind(day)) {
                    case SelectedEdge:
                        dayView.setBackgroundColor(getColor(color.calendarSelectionEdgeBackground));
                        dayView.setTextColor(getColor(color.calendarSelectionEdgeText));
                        break;
                    case SelectedInner:
                        dayView.setBackgroundColor(getColor(color.calendarSelectionBackground));
                        dayView.setTextColor(getColor(color.calendarSelectionText));
                        break;
                    case CurrentMonth:
                        dayView.setBackgroundColor(getColor(color.calendarBackground));
                        dayView.setTextColor(getColor(color.calendarRegularText));
                        break;
                    case AnotherMonth:
                        dayView.setBackgroundColor(getColor(color.calendarBackground));
                        dayView.setTextColor(getColor(color.calendarShadowedText));
                        break;
                }

                dayView.setText(String.valueOf(day.dayOfMonth));
                dayView.setOnClickListener(this);
                dayView.setTag(day);
            }
        }

        String monthName = getResources().getStringArray(R.array.monthNames)[month.getMonthIndex()];
        String year = String.valueOf(month.getYear());
        monthText.setText(monthName + " " + year);
    }

    private int getColor(int calendarRegularText) {
        return getResources().getColor(calendarRegularText);
    }

    public void onClick(View view) {
        Day day = (Day) view.getTag();
        month.selectDay(day);
        drawCalendar();
    }
}
