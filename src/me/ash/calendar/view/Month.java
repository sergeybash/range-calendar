package me.ash.calendar.view;

import java.util.*;
import java.util.Map.Entry;

import static java.util.Calendar.*;

/**
 * @author Sergey Basheleyshvili
 */
public class Month {

    public Day getFirstSelectedDay() {
        return interval.start;
    }

    public Day getLastSelectedDay() {
        return interval.end;
    }

    public enum DayKind {CurrentMonth, AnotherMonth, SelectedInner, SelectedEdge}

    public DayKind getKind(Day day) {
        int currentMonth = calendar.get(MONTH);
        if (interval.isEdge(day)) {
            return DayKind.SelectedEdge;
        }
        else if (interval.contains(day)) {
            return DayKind.SelectedInner;
        }
        else if (day.monthInYear == currentMonth) {
            return DayKind.CurrentMonth;
        }
        else {
            return DayKind.AnotherMonth;
        }

    }

    private final Interval interval = new Interval();

    public Day getDay(int i, int j) {
        return  field[i * 7 + j];
    }

    private final Day[] field = new Day[42];

    private final Calendar calendar;

    public Month() {
        this(Calendar.getInstance());
    }

    public Month(Calendar calendar) {
        this.calendar = (Calendar) calendar.clone();
        fillField();
    }

    public Month(int year, int month) {
        this(new GregorianCalendar(year, month, 1));
    }

    private void fillField() {
        Calendar workCalendar = (Calendar) this.calendar.clone();
        workCalendar.set(DAY_OF_MONTH, 1);
        while (workCalendar.get(DAY_OF_WEEK) != workCalendar.getFirstDayOfWeek()) {
            workCalendar.add(DAY_OF_YEAR, -1);
        }
        for (int i = 0; i < field.length; i++) {
            field[i] = new Day(workCalendar);
            workCalendar.add(DAY_OF_YEAR, 1);
        }
    }


    private int indexToWeekDay(int i) {
        return (i + calendar.getFirstDayOfWeek() - 1) % 7 + 1;
    }

    public String getWeekDayName(int index) {
        final Map<String, Integer> namesToNumbers = calendar.getDisplayNames(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        Map<Integer, String> numbersToNames = invertMap(namesToNumbers);
        return numbersToNames.get(indexToWeekDay(index));
    }

    private static <K,V> Map<V,K> invertMap(Map<K, V> source) {
            Map<V,K> ret = new HashMap<V, K>(source.size());
            for (Entry<K, V> entry : source.entrySet()) {
                ret.put(entry.getValue(), entry.getKey());
            }
            return ret;
        }


    public int getMonth() {
        return calendar.get(MONTH);
    }

    public int getYear() {
        return calendar.get(YEAR);
    }

    public void selectDay(Day day) {
        interval.set(day);
    }

    public boolean isRangeSelected() {
        return interval.isSelected();
    }

    public void prevMonth() {
        calendar.add(MONTH, -1);
        fillField();
    }

    public void nextMonth() {
        calendar.add(MONTH, 1);
        fillField();
    }
}
