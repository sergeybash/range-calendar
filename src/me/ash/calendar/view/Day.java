package me.ash.calendar.view;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * @author Sergey Basheleyshvili
 */
public class Day implements Comparable<Day> {

    public static final Day NONE = new Day();

    public static Day none() {
        return NONE;
    }

    public boolean isNone() {
        return equals(NONE);
    }

    public boolean isGreaterThan(Day start) {
        return compareTo(start) >= 0;
    }

    public boolean isLessThan(Day end) {
        return compareTo(end) <= 0;
    }


    public int compareTo(Day day) {
        if (year > day.year) return 1;
        else if (year < day.year) return -1;
        else {
            if (monthInYear > day.monthInYear) return 1;
            else if (monthInYear < day.monthInYear) return -1;
            else {
                if (dayOfMonth > day.dayOfMonth) return 1;
                else if (dayOfMonth < day.dayOfMonth) return -1;
                else return 0;
            }
        }
    }

    public final int year;
    public final int monthInYear;
    public final int dayOfMonth;

    public Day(final Calendar calendar) {
        year = calendar.get(YEAR);
        monthInYear = calendar.get(MONTH);
        dayOfMonth = calendar.get(DAY_OF_MONTH);
    }

    private Day() {
        year = -1;
        monthInYear = -1;
        dayOfMonth = -1;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date=" + dayOfMonth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (dayOfMonth != day.dayOfMonth) return false;
        if (monthInYear != day.monthInYear) return false;
        if (year != day.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + monthInYear;
        result = 31 * result + dayOfMonth;
        return result;
    }
}
