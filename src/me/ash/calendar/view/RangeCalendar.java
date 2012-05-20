package me.ash.calendar.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
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
public class RangeCalendar extends LinearLayout implements OnClickListener {


    private TextView monthText;
    private TableLayout calendarTable;
    private Month month;


    public RangeCalendar(Context context) {
        super(context);
        inflate();
    }

    public RangeCalendar(Context context, AttributeSet attrs) {
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
            TableRow row = (TableRow) calendarTable.getChildAt(i + 1);
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

        String monthName = getResources().getStringArray(R.array.monthNames)[month.getMonth()];
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

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.year = this.month.getYear();
        ss.month = this.month.getMonth();
        ss.firstDay = this.month.getFirstSelectedDay();
        ss.lastDay = this.month.getLastSelectedDay();

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        int year = ss.year;
        int month = ss.month;
        Day firstDay = ss.firstDay;
        Day lastDay = ss.lastDay;
        this.month = new Month(year, month);
        this.month.selectDay(firstDay);
        this.month.selectDay(lastDay);
        drawCalendar();
    }

    static class SavedState extends BaseSavedState {
        int year;
        int month;
        Day firstDay;
        Day lastDay;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            year = in.readInt();
            month = in.readInt();
            firstDay = in.readParcelable(Day.class.getClassLoader());
            lastDay = in.readParcelable(Day.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.year);
            out.writeInt(this.month);
            out.writeParcelable(firstDay, 0);
            out.writeParcelable(lastDay, 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
