range-calendar
==============

## Initial specification
1. As a calendar day is pressed, it gets selected
2. When second day is pressed:
    1. If second day's date is greater, the interval is selected
    2. Otherwise, interval is discarded, selected day becomes new first day

## Notes
* I've adjusted this spec in a following way:
    * When there's a selected interval and third day is pressed,
    the interval gets discarded, selected day becomes new first day
* Week days' names are taken from locale. Initially tried to do the same with months, but russian names
are messed up.
* About arrows (next/prev month). I didn't want to bother creating png's and there is no way to
draw a triangle via Shapes, so instead of graphics I've used '<' and '>' characters
for arrows.
* Added persistent state for the calendar
* Tested on Nexus One, looks fine in both landscape and portrait mode.
* **TODO**: Add selectors for 'next/prev month' buttons
* **TODO**: Support touch gestures for selecting date range and switching between months

Ideally, I need more context to develop the control further. How it is supposed to be used, etc.
For now activity that uses it, subscribes for range selection callbacks and dispays toasts.
