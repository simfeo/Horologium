package sim.horologium.app.Utils;

import android.text.format.DateFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

// Month, weekday and date text from the platform locale data, so these never need translating.
public final class LocalizedText {
    private LocalizedText() {
    }

    private static Locale locale() {
        return Locale.getDefault();
    }

    // Several locales (cs, pl, uk, fr, ...) return lower-case names; they start a line here.
    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase(locale()) + s.substring(1);
    }

    public static String longDate(int year, int month, int day) {
        return LocalDate.of(year, month, day)
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale()));
    }

    public static String dateTime(int year, int month, int day, int hour, int minute) {
        return LocalDateTime.of(year, month, day, hour, minute)
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(locale()));
    }

    public static String weekdayName(int year, int month, int day) {
        return capitalize(LocalDate.of(year, month, day).getDayOfWeek().getDisplayName(TextStyle.FULL, locale()));
    }

    public static String weekdayShort(DayOfWeek dayOfWeek) {
        return capitalize(dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, locale()));
    }

    // Stand-alone form: Slavic languages use a different case for a month shown on its own.
    public static String monthName(int month) {
        return capitalize(Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, locale()));
    }

    // "21 March" or "March 21" or "21. Maerz": the order comes from the locale.
    public static String dayAndMonth(int month, int day) {
        String pattern = DateFormat.getBestDateTimePattern(locale(), "dMMMM");
        return LocalDate.of(2000, month, day).format(DateTimeFormatter.ofPattern(pattern, locale()));
    }
}
