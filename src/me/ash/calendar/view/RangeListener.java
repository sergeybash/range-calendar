package me.ash.calendar.view;

/**
 * @author Sergey Basheleyshvili
 */
public interface RangeListener {

    public static final RangeListener EMPTY = new RangeListener() {
        public void onRangeSelected(Day first, Day last) {}
    };

    public void onRangeSelected(Day first, Day last);

}
