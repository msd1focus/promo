package app.fpp.bean.promoproposal;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConversion {
    
    public static String PATTERN = "yyyyMMdd";


    static class Measure {
        private int month;
        private int year;
        private Calendar calendar;

        public Measure() {
            init();
        }

        private void init() {
            calendar = Calendar.getInstance();
            calendar.set(year, month, 1);
        }

        public String min() {
            return format(calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        }

        public String max() {
            return format(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        private Date date(GregorianCalendar c) {
            return new java.util.Date(c.getTime().getTime());

        }

        private GregorianCalendar gc(int day) {
            return new GregorianCalendar(year, month, day);
        }


        private String format(int day) {

            SimpleDateFormat format = new SimpleDateFormat(PATTERN);
            return format.format(date(gc(day)));
        }

        public Measure month(int month) {
            this.month = month - 1;
            return this;
        }

        public Measure year(int year) {
            this.year = year;
            return this;
        }
    }
}
