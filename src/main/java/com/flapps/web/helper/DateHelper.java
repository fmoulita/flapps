package com.flapps.web.helper;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {

    public static int getDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        if (date != null) c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static Calendar calendar(Date date) {
        Calendar c = new GregorianCalendar();
        if (date != null) c.setTime(date);
        return c;
    }

    public static Calendar calendar(Date date, TimeZone timeZone) {
        Calendar c = new GregorianCalendar();
        GregorianCalendar cal = new GregorianCalendar(timeZone);
        cal.setTime(date == null ? new Date() : date);
        c.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        c.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, cal.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
        return c;
    }

    public static Date date000000(int year, int month, int day) {
        Calendar c = new GregorianCalendar(year, month, day, 0, 0, 0);
        return c.getTime();
    }

    public static Date date240000(int year, int month, int day) {
        Calendar c = new GregorianCalendar(year, month, day, 23, 59, 59);
        c.set(Calendar.MILLISECOND, 999);
        c.add(Calendar.MILLISECOND, 1);
        return c.getTime();
    }

    public static Date date000000(Date date) {
        return dateBegin(date, Calendar.DAY_OF_MONTH);
    }

    public static Date date240000(Date date) {
        return dateEnd(date, Calendar.DAY_OF_MONTH);
    }

    public static Date dateBegin(Date date, int level) {
        if (date == null) return null;
        Calendar cal = calendar(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        switch (level) {
            case Calendar.YEAR:
                cal.set(Calendar.MONTH, 0);
            case Calendar.MONTH:
                cal.set(Calendar.DAY_OF_MONTH, 1);
            case Calendar.DAY_OF_MONTH:
                cal.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                cal.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                cal.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                cal.set(Calendar.MILLISECOND, 0);
                break;
            default:
                throw new IllegalStateException("DateUtil.dateBegin level");
        }
        return cal.getTime();
    }

    public static Date dateEnd(Date date, int level) {
        if (date == null) return null;

        switch (level) {
            case Calendar.YEAR:
                return new DateTime(date).plusYears(1).withMonthOfYear(1).withDayOfMonth(1).withTimeAtStartOfDay().toDate();

            case Calendar.MONTH:
                return new DateTime(date).plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay().toDate();

            case Calendar.DAY_OF_MONTH:
                return new DateTime(date).plusDays(1).withTimeAtStartOfDay().toDate();

            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                return new DateTime(date).plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            case Calendar.MINUTE:
                return new DateTime(date).plusMinutes(1).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            case Calendar.SECOND:
                return new DateTime(date).plusSeconds(1).withMillisOfSecond(0).toDate();

            case Calendar.DAY_OF_WEEK:
            case Calendar.DAY_OF_WEEK_IN_MONTH:
                return new DateTime(date).plusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay().toDate();

        }
        throw new IllegalStateException("DateUtil.dateEnd level");
    }

    public static Date minusMillisecond(Date date) {
        return new DateTime(date).minusMillis(1).toDate();
    }

    public static boolean sameDate(Date date1, Date date2, int level) {


        if (date1 == null || date2 == null) return false;
        Calendar cal1 = calendar(date1);
        Calendar cal2 = calendar(date2);
        switch (level) {
            case Calendar.SECOND:
                if (cal1.get(Calendar.SECOND) != cal2.get(Calendar.SECOND)) return false;
            case Calendar.MINUTE:
                if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE)) return false;
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY)) return false;
            case Calendar.DAY_OF_MONTH:
                if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)) return false;
            case Calendar.WEEK_OF_YEAR:
                if (cal1.get(Calendar.WEEK_OF_YEAR) != cal2.get(Calendar.WEEK_OF_YEAR)) return false;
            case Calendar.MONTH:
                if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH)) return false;
            case Calendar.YEAR:
                if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) return false;
                break;
            default:
                throw new IllegalStateException("DateUtil.dateEnd level");
        }
        return true;
    }

    public static long dateDiff(Date date1, Date date2, int level) {
        if (date1 == null || date2 == null) return 0;

        double div = 1;
        switch (level) {
            case Calendar.DAY_OF_MONTH:
                return Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
//				div *= 24;
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
//				div *= 60;
                return Hours.hoursBetween(new DateTime(date1), new DateTime(date2)).getHours();
            case Calendar.MINUTE:
//				div *= 60;
                return Minutes.minutesBetween(new DateTime(date1), new DateTime(date2)).getMinutes();
            case Calendar.SECOND:
//				div *= 1000;
                return Seconds.secondsBetween(new DateTime(date1), new DateTime(date2)).getSeconds();
            case Calendar.MILLISECOND:
//				break;
                return date2.getTime() - date1.getTime();
            default:
                throw new IllegalStateException("DateUtil.dateEnd level");
        }
//		return Double.valueOf(Math.ceil((date2.getTime() - date1.getTime()) / div)).longValue();
    }

    public static Date beginingOfMonth(Date date) {
        return dateBegin(date, Calendar.MONTH);
    }

    public static Date endOfMonth(Date date) {
        return dateEnd(date, Calendar.MONTH);
    }

    public static Date beginingOfDay(Date date) {
        if (date == null) return null;
        Calendar cal = calendar(null);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date beginingOfWeek(Date date) {
        if (date == null) return null;
        Calendar cal = calendar(null);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date endOfWeek(Date date) {
        return dateEnd(date, Calendar.DAY_OF_WEEK);
//		if(date == null) return null;
//		Calendar cal = calendar(null);
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.setTime(date);
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		cal.set(Calendar.HOUR_OF_DAY, 23);
//		cal.set(Calendar.MINUTE, 59);
//		cal.set(Calendar.SECOND, 59);
//		cal.set(Calendar.MILLISECOND, 999);
//		return cal.getTime();
    }

    public static Date beginingOfYear(Date date) {
        return dateBegin(date, Calendar.YEAR);
    }

    public static Date endOfYear(Date date) {
        return dateEnd(date, Calendar.YEAR);
    }

    public static Date dateAddDays(Date date, int days) {
        return dateAdd(date, days, Calendar.DAY_OF_MONTH);
    }

    public static Date dateAddMonths(Date date, int months) {
        return dateAdd(date, months, Calendar.MONTH);
    }

    public static Date dateAdd(Date date, int count, int field) {
        if (date == null) return null;
        Calendar cal = calendar(date);
        cal.add(field, count);
        return cal.getTime();
    }

    public static boolean isWorkingDay(Date date, List<Date> holidays) {
        return isWorkingDay(calendar(date), holidays);
    }

    public static boolean isWorkingDay(Calendar cal, List<Date> holidays) {
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        return !(weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY || (holidays != null && holidays.contains(cal.getTime())));
    }

    public static boolean isWeekend(Date date) {
        return isWeekend(calendar(date));
    }

    public static boolean isWeekend(Calendar cal) {
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        return (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY);
    }

    public static int countWorkingDays(Date dateFrom, Date dateTo, List<Date> holidays) {
        int days = 0;
        if (dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
            Calendar cal = calendar(dateFrom);
            while (cal.getTime().before(dateTo) || cal.getTime().equals(dateTo)) {
                if (isWorkingDay(cal, holidays)) days++;
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return days;
    }

    /**
     * @param first
     * @param second
     * @return true if first date is strictly before second, true if first is
     * not null and second is null, false otherwise
     */
    public static boolean before(Date first, Date second) {
        // null is bigger
        boolean result = false;

        if (first != null && second != null) {
            result = first.before(second);
        } else {
            result = (first != null && second == null);
        }

        return result;
    }

    /**
     * @param first
     * @param second
     * @return true if first date is strictly after second, true if first is
     * null and second is not null, false otherwise
     */
    public static boolean after(Date first, Date second) {
        // null is bigger
        boolean result = false;

        if (first != null && second != null) {
            result = first.after(second);
        } else {
            result = (first == null && second != null);
        }

        return result;
    }

    public static boolean between(Date akt, Date dateFrom, Date dateTo) {
        if (akt == null) return false;
        if (dateFrom != null && !dateFrom.equals(akt) && dateFrom.after(akt)) return false;
        if (dateTo != null && !dateTo.equals(akt) && dateTo.before(akt)) return false;
        return true;
    }

    /**
     * @return true if intervals are crossing
     */
    public static boolean between(Date dateFrom1, Date dateTo1, Date dateFrom, Date dateTo) {
        if (dateFrom1 == null && dateTo1 == null) return false;
        if (dateFrom == null && dateTo == null) return false;
        if (dateFrom1 != null && dateTo != null && !dateTo.equals(dateFrom1) && dateTo.before(dateFrom1)) return false;
        if (dateTo1 != null && dateFrom != null && !dateTo1.equals(dateFrom) && dateTo1.before(dateFrom)) return false;
        return true;
    }

    public static Date dateFromRC(String rc) {
        // vrati datum z rodneho cisla
        if (rc == null) return null;

        rc = rc.trim().replaceAll("/", "");

        if (rc.length() < 9 || rc.length() > 10) return null;

        int year = Integer.parseInt(rc.substring(0, 2));
        int month = Integer.parseInt(rc.substring(2, 4));
        int day = Integer.parseInt(rc.substring(4, 6));

        if (rc.length() == 9) {
            // rok do 1954
            year += 1900;
        } else {
            // 1954, 1955, ...
            if (year < 54) {
                year += 2000;
            } else {
                year += 1900;
            }
        }

        if (month > 50) month -= 50;

        return date000000(year, month - 1, day);
    }

    /**
     * return Calendar DAY OF MONTH of first saturday of month
     * if 1st of month is sunday, return 0
     *
     * @param date
     */
    public static int getFirstWeekend(Date date) {
        Date firstDay = DateHelper.beginingOfMonth(date);
        Date sunday = DateHelper.endOfWeek(firstDay);
        Calendar s = DateHelper.calendar(sunday);
        s.add(Calendar.MILLISECOND, -1);
        return s.get(Calendar.DAY_OF_MONTH) - 1;
    }

    public static int diffDays(Date start, Date end) {
        if (start == null) return 0;
        if (end == null) return 0;

        DateTime dtStart = new DateTime(start).withTimeAtStartOfDay();
        DateTime dtEnd = new DateTime(end).withTimeAtStartOfDay();

        return Days.daysBetween(dtStart, dtEnd).getDays();
    }

    public static Date parseSimpleDateFormat(String pattern, String value) {
        return parseSimpleDateFormat(pattern, value, false);
    }

    public static Date parseSimpleDateFormat(String pattern, String value, boolean rethrow) {
        if (value == null) return null;
        if (value.trim().length() == 0) return null;
        if (pattern == null) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(value);
        } catch (Exception e) {

            if (rethrow) {
                if (e instanceof RuntimeException) throw (RuntimeException) e;
                throw new IllegalStateException(e);
            }
        }
        return null;

    }

    public static int getDurationInMinutes(Date start, Date end) {
        return getDurationInMinutes(start == null ? null : new DateTime(start), end == null ? null : new DateTime(end));
    }

    public static int getDurationInMinutes(DateTime start, DateTime end) {
        try {
            if (start == null) return 0;
            if (end == null) end = new DateTime();
            int duration = Minutes.minutesBetween(start.withSecondOfMinute(0).withMillisOfSecond(0), end.withSecondOfMinute(0).withMillisOfSecond(0)).getMinutes();
            if (duration < 0) duration = 0;
            return duration;
        } catch (Exception e) {
        }
        return 0;
    }


    public static Date[] getDatesByDuration(String periodType, Date dateFrom, Date dateTo) {
        return getDatesByDuration(NumberUtils.toInt(periodType, 0), dateFrom, dateTo);
    }

    public static Date[] getDatesByDuration(Integer periodType, Date dateFrom, Date dateTo) {
        return getDatesByDuration(periodType, dateFrom, dateTo, null);
    }

    public static Date[] getDatesByDuration(Integer periodType, Long dateFrom, Long dateTo, DateTime maxDate) {
        return getDatesByDuration(periodType, dateFrom == null ? null : new DateTime(dateFrom).toDate(), dateTo == null ? null : new DateTime(dateTo).toDate(), maxDate);
    }

    public static Date[] getDatesByDuration(Integer periodType, Date dateFrom, Date dateTo, DateTime maxDate) {
        DateTime dt;

        if (periodType == null) periodType = 1;

        switch (periodType) {
            case 1:    // today
                dt = new DateTime().withTimeAtStartOfDay();
                dateFrom = dt.toDate();
                dateTo = dt.plusDays(1).minusMillis(1).toDate();
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                break;
            case 2: // yesterday
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.plusDays(-1);
                dateFrom = dt.toDate();
                dateTo = dt.plusDays(1).minusMillis(1).toDate();
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                break;
            case 3: // this week
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.withDayOfWeek(1);
                dateFrom = dt.toDate();
                dt = dt.plusWeeks(1).minusMillis(1);
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                dateTo = dt.toDate();
                break;
            case 4: // last week
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.withDayOfWeek(1).plusWeeks(-1);
                dateFrom = dt.toDate();
                dt = dt.plusWeeks(1).minusMillis(1);
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                dateTo = dt.toDate();
                break;
            case 5: // this month
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.withDayOfMonth(1);
                dateFrom = dt.toDate();
                dt = dt.plusMonths(1).minusMillis(1);
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                dateTo = dt.toDate();
                break;
            case 6: // last month
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.withDayOfMonth(1).minusMonths(1);
                dateFrom = dt.toDate();
                dt = dt.plusMonths(1).minusMillis(1);
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                dateTo = dt.toDate();
                break;
            case 7: // this year
                dt = new DateTime().withTimeAtStartOfDay();
                dt = dt.withDayOfMonth(1).withMonthOfYear(1);
                dateFrom = dt.toDate();
                dt = dt.plusYears(1).minusMillis(1);
                if (maxDate != null && dt.isAfter(maxDate)) {
                    dt = maxDate;
                }
                dateTo = dt.toDate();
                break;
            default:
                if (dateFrom != null) dateFrom = DateHelper.date000000(dateFrom);
                if (dateTo != null) dateTo = new DateTime(dateTo).withTimeAtStartOfDay().plusDays(1).minusMillis(1).toDate();
        }

        return new Date[]{dateFrom, dateTo};
    }
}
