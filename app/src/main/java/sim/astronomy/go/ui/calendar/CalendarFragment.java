package sim.astronomy.go.ui.calendar;


import static sim.astronomy.go.Utils.AstroMath.JD;
import static sim.astronomy.go.Utils.AstroMath.getWeekNumberFromDate;
import static sim.astronomy.go.Utils.AstroMath.getDayOfWeekAndMonthByYearAndDayNumber;
import static sim.astronomy.go.Utils.AstroMath.getDayOfWeek;
import static sim.astronomy.go.Utils.AstroMath.getDayNum;
import static sim.astronomy.go.Utils.AstroMath.isLeapYear;
import static sim.astronomy.go.Utils.AstroMath.JDtoDay;
import static sim.astronomy.go.Utils.AstroMath.JDtoMon;
import static sim.astronomy.go.Utils.AstroMath.JDtoYear;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;
import java.util.Vector;

import sim.astronomy.go.R;
import sim.astronomy.go.databinding.HomeBinding;

public class CalendarFragment extends Fragment {
    private TextView calendCurrentDate, calendCurrentWeekDay, calendCurrentWeekNumber,
            calendCurrentDayNumber, calendCurrentDayRemain, settedMon, settedYear;
    private Vector<TextView> vCalendWeekNumbersTextViews;
    private Vector<TextView> vCalendDatesTextViews;
    static int iYear, iMon, iDay;
    static int iYearF, iMonF, iDayF;
    private Resources res;
    private View view;
    private HomeBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel CalendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = HomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        res = getResources();


        saveCalendarWeekNumberToVector();
        saveCalendarDatesToVector();
        settedMon = view.findViewById(R.id.settedMon);
        settedYear = view.findViewById(R.id.settedYear);

        binding.calendarNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarNextButClick(v);
            }
        });

        binding.calendarPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarPrevButClick(v);
            }
        });

        {
            String dt = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US).format(java.util.Calendar.getInstance().getTime());

            String[] days = dt.split("-");

            int day = Integer.parseInt(days[0]);
            int mon = Integer.parseInt(days[1]);
            int year = Integer.parseInt(days[2]);

            iYear = year;
            iMon = mon;
            iDay = day;

            iYearF = year;
            iMonF = mon;
            iDayF = day;


            CalendarViewFillData(year, mon, day);

            int[] daysInMonth = {31, 28 + isLeapYear(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int dayOfWeek = getDayOfWeek(JD(year, mon, 1));

            int startMon = getStartMon(mon, year, daysInMonth, dayOfWeek);
            int daysInYear = getDaysInYear(year, startMon);

            calendCurrentDate = view.findViewById(R.id.calendCurrentDate);
            calendCurrentWeekDay = view.findViewById(R.id.calendCurrentWeekDay);
            calendCurrentWeekNumber = view.findViewById(R.id.calendCurrentWeekNumber);
            calendCurrentDayNumber = view.findViewById(R.id.calendCurrentDayNumber);
            calendCurrentDayRemain = view.findViewById(R.id.calendCurrentDayRemain);
            calendCurrentDate.setText(day + "-" + mon + "-" + year)
            ;
            String[] weekdaysNamesArray = res.getStringArray(R.array.Weekday);
            calendCurrentWeekDay.setText(weekdaysNamesArray[getDayOfWeek(JD(year, mon, day))]);

            calendCurrentWeekNumber.setText(res.getString(R.string.Weeknumber) + "  " + getWeekNumberFromDate(year, mon, day));
            calendCurrentDayNumber.setText(res.getString(R.string.Dayspass) + "  " + getDayNum(year, mon, day));
            calendCurrentDayRemain.setText(res.getString(R.string.Daylast) + "  " + (daysInYear - getDayNum(year, mon, day)));

        }


        return view;
    }

    private static int getDaysInYear(int year, int startMon) {
        int daysInYear;
        if (startMon == 12) {
            daysInYear = 365 + isLeapYear(year - 1);
        }
        else {
            daysInYear = 365 + isLeapYear(year);
        }
        return daysInYear;
    }

    private static int getStartMon(int mon, int year, int[] mes, int dayOfWeek) {
        int dayNum;
        int startDay;
        int startMon;
        if (dayOfWeek != 1) {
            do {
                if (mon == 1) {
                    dayNum = getDayNum(year - 1, 12, 31);
                    for (; ; ) {
                        int[] dayAndMonth = getDayOfWeekAndMonthByYearAndDayNumber(year - 1, dayNum);
                        startDay = dayAndMonth[1];
                        startMon = dayAndMonth[0];
                        dayOfWeek = getDayOfWeek(JD(year - 1, startMon, startDay));
                        if (dayOfWeek != 1) {
                            dayNum = dayNum - 1;
                        }
                        else {
                            break;
                        }
                    }
                } else {
                    dayNum = getDayNum(year, mon - 1, mes[mon - 2]);
                    for (; ; ) {
                        int[] dayAndMonth = getDayOfWeekAndMonthByYearAndDayNumber(year, dayNum);
                        startDay = dayAndMonth[1];
                        startMon = dayAndMonth[0];
                        dayOfWeek = getDayOfWeek(JD(year, startMon, startDay));
                        if (dayOfWeek != 1) {
                            dayNum = dayNum - 1;
                        }
                        else {
                            break;
                        }
                    }
                }


            }
            while (dayOfWeek != 1);
        } else {
            startMon = mon;
        }
        return startMon;
    }

    private void saveCalendarDatesToVector() {
        vCalendDatesTextViews = new Vector<TextView>(42);

        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek1_Day7));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek2_Day7));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek3_Day7));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek4_Day7));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek5_Day7));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day1));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day2));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day3));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day4));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day5));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day6));
        vCalendDatesTextViews.add(view.findViewById(R.id.calendWeek6_Day7));
    }

    private void saveCalendarWeekNumberToVector() {
        vCalendWeekNumbersTextViews = new Vector<TextView>(6);
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek1));
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek2));
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek3));
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek4));
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek5));
        vCalendWeekNumbersTextViews.add(view.findViewById(R.id.calendWeek6));
    }


    public  void textBackgroundBold(TextView tView, double d) {
        tView.setTypeface(null, Typeface.BOLD);
        int day, mon, year;
        day = JDtoDay(d);
        mon = JDtoMon(d);
        year = JDtoYear(d);
        if (day != iDayF || (day == iDayF && (mon != iMonF || year != iYearF))) {
            tView.setBackgroundColor(res.getColor(R.color.month_background,null));
        } else {
            tView.setBackgroundColor(res.getColor(R.color.day_background,null));
        }

    }

    public static void resetTextViewFormatting(TextView la) {
        la.setTypeface(null, Typeface.NORMAL);
        la.setBackgroundColor(0);
    }

    public void ClearClalendar() {
        vCalendDatesTextViews.forEach((v) -> resetTextViewFormatting(v));
    }

    public void CalendarViewFillData(int year, int mon, int day) {
        int[] mes = {31, 28 + isLeapYear(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int dayOfWeek = getDayOfWeek(JD(year, mon, 1));
        int dayNum, startDay, startMon;

        if (dayOfWeek != 1) {
            do {
                if (mon == 1) {
                    dayNum = getDayNum(year - 1, 12, 31);
                    for (; ; ) {
                        int[] dayAndMonth = getDayOfWeekAndMonthByYearAndDayNumber(year - 1, dayNum);
                        startDay = dayAndMonth[1];
                        startMon = dayAndMonth[0];
                        dayOfWeek = getDayOfWeek(JD(year - 1, startMon, startDay));
                        if (dayOfWeek != 1) dayNum = dayNum - 1;
                        else break;
                    }
                } else {
                    dayNum = getDayNum(year, mon - 1, mes[mon - 2]);
                    for (; ; ) {
                        int[] dayAndMonth = getDayOfWeekAndMonthByYearAndDayNumber(year, dayNum);
                        startDay = dayAndMonth[1];
                        startMon = dayAndMonth[0];
                        dayOfWeek = getDayOfWeek(JD(year, startMon, startDay));
                        if (dayOfWeek != 1) dayNum = dayNum - 1;
                        else break;
                    }
                }


            }
            while (dayOfWeek != 1);
        } else {
            startDay = 1;
            startMon = mon;
        }

        double jd1 = JD(((startMon == 12) ? year - 1 : year), startMon, startDay);


        for (int i = 0; i < vCalendDatesTextViews.size(); ++i) {
            TextView v = vCalendDatesTextViews.get(i);
            v.setText(String.valueOf(JDtoDay(jd1 + i)));
            if (JDtoMon(jd1 + i) == mon) {
                textBackgroundBold(v, jd1 + i);
            }
        }

        for (int i = 0; i < vCalendWeekNumbersTextViews.size(); ++i) {
            vCalendWeekNumbersTextViews.get(i).setText(String.valueOf(getWeekNumberFromDate(JDtoYear(jd1 + i * 7), JDtoMon(jd1 + i * 7), JDtoDay(jd1 + i * 7))));
        }

        String[] sMonths = res.getStringArray(R.array.Months);
        settedMon.setText(" " + sMonths[mon] + "  ");
        settedYear.setText("  " + year + "  ");

    }

    ///////////////////////////////////////////////////////////

    public void CalendarPrevButClick(View v) {
        if (iYear >= 2005) {
            if (iMon == 1) {
                iMon = 12;
                --iYear;
            } else {
                --iMon;
            }
            ClearClalendar();
            CalendarViewFillData(iYear, iMon, iDay);
        }
    }

    public void CalendarNextButClick(View v) {
        if (iYear <= 2050) {
            if (iMon == 12) {
                iMon = 1;
                ++iYear;
            } else {
                ++iMon;
            }

            ClearClalendar();
            CalendarViewFillData(iYear, iMon, iDay);
        }
    }
}
