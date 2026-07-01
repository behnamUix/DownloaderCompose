package com.behnamuix.persianM3Calendar.utils

/**
 * Returns the number of days in a specific month of the Persian (Jalali) calendar.
 *
 * @param year The Jalali year
 * @param month The Jalali month (1 to 12)
 * @return Number of days in the month
 */
fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        in 1..6 -> 31
        in 7..11 -> 30
        12 -> if (isPersianLeapYear(year)) 30 else 29
        else -> 0
    }
}

fun generateCalendarCells(startDay: Int, daysInMonth: Int): List<Int?> {
    val result = mutableListOf<Int?>()
    repeat(startDay) {
        result.add(null)
    }
    for (day in 1..daysInMonth) {
        result.add(day)
    }
    while (result.size < 42) {
        result.add(null)
    }
    return result

}



/**
 * Checks if a given Jalali year is a leap year.
 */
fun isPersianLeapYear(year: Int): Boolean {
    val commonLeapRemainders = intArrayOf(1, 5, 9, 13, 17, 22, 26, 30)
    return commonLeapRemainders.contains(year % 33)
}