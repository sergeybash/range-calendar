package me.ash.calendar.view;

/**
 * @author Sergey Basheleyshvili
 */
public class Interval {

    Day start = Day.none();
    Day end = Day.none();

    public void set(Day newDay) {
        if (start.isNone()) {
            start = newDay;
        }
        else if (end.isNone() && newDay.isGreaterThan(start)) {
            end = newDay;
        }
        else if (!end.isNone() && newDay.isGreaterThan(start)) {
            start = newDay;
            end = Day.none();
        }
        else {
            start = newDay;
            end = Day.none();
        }
    }


    public boolean contains(Day day) {
        if (start.isNone()) {
            return false;
        }
        else if (end.isNone()) {
            return day.equals(start);
        }
        return day.isGreaterThan(start) && day.isLessThan(end);
    }

    public boolean isEdge(Day day) {
        return start.equals(day) || end.equals(day);
    }
}
