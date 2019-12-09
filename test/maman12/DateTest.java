package maman12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Adjusted to task requirements and limitations.
 *
 * @author George Merenzon
 * @version 2020a
 */
class DateTest {
    final Date DEFAULT_DATE = setupDate(1, 1, 2000);

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14})
    void whenInvalidDay_dayDoesntChange(int day) {
        assertAll("day should not change given invalid value",
                () -> assertDoesNotSetInvalidDay(setupDate(day, 1, 2019), 32),
                () -> assertDoesNotSetInvalidDay(setupDate(day, 4, 2018), 0)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,})
    void whenInvalidMonth_monthDoesntChange(int month) {
        assertAll("month should not change given invalid value",
                () -> assertDoesNotSetInvalidMonth(setupDate(1, month, 2019), 13),
                () -> assertDoesNotSetInvalidMonth(setupDate(1, month, 2019), 0),
                () -> assertDoesNotSetInvalidMonth(setupDate(1, month, 2019), -1),
                () -> assertEquals(1, setupDate(1, 17, 2013).getMonth())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 9, 11})
    void whenThirtyDayMonthSetMaximumDaysLower(int thirtyDayMonth) {
        assertAll("given months should not exceed 30 days",
                () -> assertEquals(30, setupDate(30, thirtyDayMonth, 2003).getDay()),
                () -> assertEquals(1, setupDate(31, thirtyDayMonth, 2003).getDay()),
                () -> assertEquals(31, setupDate(31, 1, 2003).getDay())
        );
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, 10_000, 2})
    void whenInvalidYear_yearDoesNotChange(int year) {
        assertAll("year should not change on invalid value",
                () -> assertDoesNotSetInvalidYear(setupDate(1, 1, 1900), year),
                () -> assertDoesNotSetInvalidYear(setupDate(15, 3, 2020), year)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32, 100})
    void whenInvalidDay_setDefaultDate(int invalidDay) {
        assertAll("should set default date on invalid day",
                () -> assertEqualDates(DEFAULT_DATE, setupDate(invalidDay, 1, 2012))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 13, 100})
    void whenInvalidMonth_setDefaultDate(int invalidMonth) {
        assertAll("should set default date on invalid month",
                () -> assertEqualDates(DEFAULT_DATE, setupDate(3, invalidMonth, 2012)),
                () -> assertEqualDates(DEFAULT_DATE, setupDate(15, invalidMonth, 1998))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 999, 10_000})
    void whenInvalidYear_setDefaultDate(int invalidYear) {
        assertAll("should set default date on invalid year",
                () -> assertEqualDates(DEFAULT_DATE, setupDate(3, 12, invalidYear)),
                () -> assertEqualDates(DEFAULT_DATE, setupDate(15, 1, invalidYear))
        );
    }

    @Test
    void whenInvalidDate_createDefaultDate() {
        assertAll("should create default date on invalid ",
                () -> assertEqualDates(DEFAULT_DATE, setupDate(29, 2, 2017)),
                () -> assertEqualDates(DEFAULT_DATE, setupDate(30, 2, 2020)),
                () -> assertEqualDates(DEFAULT_DATE, setupDate(31, 6, 2003))
        );
    }

    @Test
    void whenDayExceeds_cannotSetFebruary() {
        assertAll("cannot set month to February when days exceed",
                () -> assertMonthDoesNotSetToFebruary(3, setupDate(31, 3, 2018)),
                () -> assertMonthDoesNotSetToFebruary(2, setupDate(28, 4, 2019)),
                () -> assertMonthDoesNotSetToFebruary(2, setupDate(29, 3, 2020))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {29, 30, 31})
    void whenFebruary_cannotSetDaysExceeding(int day) {
        assertAll("when month is February cannot set days above max",
                () -> assertWhenFebruaryDayDoesNotExceed(setupDate(29, 2, 2016), day),
                () -> assertWhenFebruaryDayDoesNotExceed(setupDate(28, 2, 2017), day),
                () -> assertWhenFebruaryDayDoesNotExceed(setupDate(28, 2, 2017), day)
        );
    }

    @Test
    void dateCanCompareValues() {
        assertAll("should return true on equal values",
                () -> assertTrue(DEFAULT_DATE.equals(DEFAULT_DATE)),
                () -> assertFalse(DEFAULT_DATE.equals(setupDate(3, 4, 2019))),
                () -> assertTrue(setupDate(39, 2, 2019).equals(DEFAULT_DATE))
        );
    }

    @Test
    void dateCanDetermineBefore() {
        assertAll("compare if this date occurs before given date",
                () -> assertTrue(DEFAULT_DATE.before(setupDate(2, 1, 2000))),
                () -> assertFalse(DEFAULT_DATE.before(DEFAULT_DATE)),
                () -> assertFalse(DEFAULT_DATE.before(setupDate(1, 1, 1999))),
                () -> assertTrue(setupDate(31, 12, 1999).before(setupDate(1, 1, 2000)))
        );
    }

    @Test
    void dateCanDetermineAfter() {
        assertAll("compare if this date occurs after given date",
                () -> assertFalse(DEFAULT_DATE.after(setupDate(2, 1, 2000))),
                () -> assertFalse(DEFAULT_DATE.after(DEFAULT_DATE)),
                () -> assertTrue(DEFAULT_DATE.after(setupDate(1, 1, 1999))),
                () -> assertFalse(setupDate(31, 12, 1999).after(setupDate(1, 1, 2000)))
        );
    }

    @Test
    void dateReturnNonNegativeDayDifference() {
        assertAll("should return positive number of days difference between current and give date",
                () -> assertDayDifference(1, DEFAULT_DATE, setupDate(2, 1, 2000)),
                () -> assertDayDifference(1, setupDate(2, 1, 2000), DEFAULT_DATE),
                () -> assertDayDifference(31, DEFAULT_DATE, setupDate(1, 2, 2000)),
                () -> assertDayDifference(0, DEFAULT_DATE, DEFAULT_DATE)
        );
    }

    @Test
    void dateToStringReturnsExpectedValue() {
        assertAll("should return string value in given format",
                () -> assertEquals("12/05/2019", setupDate(12, 5, 2019).toString()),
                () -> assertEquals("01/02/2020", setupDate(1, 2, 2020).toString()),
                () -> assertEquals("09/07/2053", setupDate(9, 7, 2053).toString())
        );
    }

    @Test
    void returnNewDateTomorrow() {
        assertAll("should return new date for tomorrow given monthly limitations",
                () -> assertDateTomorrow(setupDate(1, 2, 2012), setupDate(31, 1, 2012)),
                () -> assertDateTomorrow(setupDate(15, 12, 2019), setupDate(14, 12, 2019)),
                () -> assertDateTomorrow(DEFAULT_DATE, setupDate(31, 12, 1999)),
                () -> assertDateTomorrow(setupDate(1, 3, 2000), setupDate(29, 2, 2000)),
                () -> assertDateTomorrow(setupDate(1, 3, 2001), setupDate(28, 2, 2001)),
                () -> assertDateTomorrow(setupDate(1, 3, 2021), setupDate(28, 2, 2021)),
                () -> assertDateTomorrow(setupDate(1, 5, 2001), setupDate(30, 4, 2001))
        );
    }

    @Test
    void dateDeterminesDayOfTheWeek() {
        assertAll("should return 0-6 values for day of the week",
                () -> assertEquals(5, setupDate(15, 1, 2009).dayInWeek()),
                () -> assertEquals(0, setupDate(7, 12, 2019).dayInWeek()),
                () -> assertEquals(0, setupDate(8, 11, 2008).dayInWeek()),
                () -> assertEquals(2, setupDate(19, 11, 1973).dayInWeek())
        );
    }

    private Date setupDate(int day, int month, int year) {
        return new Date(day, month, year);
    }

    private void assertDoesNotSetInvalidMonth(Date date, int newMonth) {
        int expectedMonth = date.getMonth();
        date.setMonth(newMonth);
        assertEquals(expectedMonth, date.getMonth());
    }

    private void assertDoesNotSetInvalidDay(Date date, int newDay) {
        int expectedDay = date.getDay();
        date.setDay(newDay);
        assertEquals(expectedDay, date.getDay());
    }

    private void assertDoesNotSetInvalidYear(Date date, int yearToSet) {
        int expectedYear = date.getYear();
        date.setYear(yearToSet);
        assertEquals(expectedYear, date.getYear());
    }

    private void assertEqualDates(Date expected, Date actual) {
        assertEquals(expected.getDay(), actual.getDay());
        assertEquals(expected.getMonth(), actual.getMonth());
        assertEquals(expected.getYear(), actual.getYear());
    }

    private void assertMonthDoesNotSetToFebruary(int expectedMonth, Date date) {
        date.setMonth(2);
        assertEquals(expectedMonth, date.getMonth());
    }

    private void assertWhenFebruaryDayDoesNotExceed(Date date, int toSet) {
        int expected = date.getDay();
        date.setDay(toSet);
        assertEquals(expected, date.getDay());
    }

    private void assertDayDifference(int expectedDifference, Date thisYear, Date otherYear) {
        int actualDifference = thisYear.difference(otherYear);
        assertEquals(expectedDifference, actualDifference);
    }

    private void assertDateTomorrow(Date expected, Date given) {
        assertEqualDates(expected, given.tomorrow());
    }
}
