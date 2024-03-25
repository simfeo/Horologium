package sim.astronomy.go.Utils;

import static sim.astronomy.go.Utils.Utils.numberToStringAddZeroIfNeeded;

public class AstroMath {
    public static String zoneString(double zone) {

        if (zone != 0) {
//            String sZone = (int) zone + ":" + ((int) (Math.abs(zone) % 1 * 60) < 10 ? "0" + (int) (Math.abs(zone) % 1 * 60) : (int) (Math.abs(zone) % 1 * 60));
            String sZone = (int) zone + ":" + numberToStringAddZeroIfNeeded((int) (Math.abs(zone) % 1 * 60));
            if (zone > 0)
                sZone = "+" + sZone;
            return sZone;
        } else
            return ("" + 0);
    }

    public static double degToRad(int a) {
        return a * (Math.PI / 180.0);
    }

    public static double degToRad(double a) {
        return a * (Math.PI / 180.0);
    }

    public static double radToDeg(int a) {
        return a * (180.0 / Math.PI);
    }

    public static double radToDeg(double a) {
        return a * (180.0 / Math.PI);
    }

    public static double sinSim(double a) {
        return Math.sin(degToRad(a));
    }

    public static double cosSim(double a) {
        return Math.cos(degToRad(a));
    }

    public static double SunRise(int year, int mon, int day, double fi, double lw) ///lat = fi    lon ==lw
    {
        //// zenith:  Sun's zenith for sunrise/sunset
        ///  offical      = 90 degrees 50'
        ///	 civil        = 96 degrees
        ///	 nautical     = 102 degrees
        ///	 astronomical = 108 degrees
        //--------------------------------------------------------
        double zenith = 90 + 50 / 60.0;
        ///step1
        int N1 = 275 * mon / 9;
        int N2 = (mon + 9) / 12;
        int N3 = (1 + ((year - 4 * (year / 4) + 2) / 3));
        int N = N1 - (N2 * N3) + day - 30;
        ///step2
        ///if rising time is desired:
        ///	  t = N + ((6 - lngHour) / 24)
        ///	if setting time is desired:
        ///	  t = N + ((18 - lngHour) / 24)
        double lngHour = lw / 15.0;
        double t = N + ((6 - lngHour) / 24.0);
        ///step3
        double M = (0.9856 * t) - 3.289;
        //step4
        double L = M + (1.916 * Math.sin(Math.toRadians(M)) + (0.020 * Math.sin(Math.toRadians(2 * M)))) + 282.634;
        if (L > 360)
            L -= 360;
        else if (L < 0)
            L += 360;
        if (L > 360)
            L -= 360;
        else if (L < 0)
            L += 360;
        if (L > 360)
            L -= 360;
        else if (L < 0)
            L += 360;
        ///step5a
        double RA = Math.toDegrees(Math.atan(0.91764 * Math.tan(Math.toRadians(L))));
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        ///step5b
        int Lquadrant = (int) (Math.floor(L / 90) * 90);
        int RAquadrant = (int) (Math.floor(RA / 90) * 90);
        RA = RA + (Lquadrant - RAquadrant);
        ///step5c
        RA /= 15;
        ///step6
        double sinDec = 0.39782 * Math.sin(Math.toRadians(L));
        double cosDec = Math.cos(Math.asin(sinDec));
        ///step7a
        double cosH = (Math.cos(Math.toRadians(zenith)) - (sinDec * Math.sin(Math.toRadians(fi)))) /
                (cosDec * Math.cos(Math.toRadians(fi)));
        if (cosH > 1 || cosH < -1)
            return 1000;
        ///step7b
        //if if rising time is desired:
        //	  H = 360 - acos(cosH)
        //	if setting time is desired:
        //	  H = acos(cosH)
        double H = 360 - Math.toDegrees(Math.acos(cosH));
        H /= 15;
        ///step 8
        double T = H + RA - (0.06571 * t) - 6.622;
        ///step9
        double UT = T - lngHour;
        if (UT > 24)
            UT -= 24;
        else if (UT < 0)
            UT += 24;
        if (UT > 24)
            UT -= 24;
        else if (UT < 0)
            UT += 24;
        if (UT > 24)
            UT -= 24;
        else if (UT < 0)
            UT += 24;
        return UT;
    }

    public static double SunSet(int year, int mon, int day, double fi, double lw) ///lat = fi    lon ==lw
    {
        ////zenith:  Sun's zenith for sunrise/sunset
        ///  offical      = 90 degrees 50'
        ///		  civil        = 96 degrees
        ///		  nautical     = 102 degrees
        ///		  astronomical = 108 degrees
        //--------------------------------------------------------
        double zenith = 90 + 50 / 60.0;
        ///step1
        int N1 = 275 * mon / 9;
        int N2 = (mon + 9) / 12;
        int N3 = (1 + ((year - 4 * (year / 4) + 2) / 3));
        int N = N1 - (N2 * N3) + day - 30;
        ///step2
        ///if rising time is desired:
        ///	  t = N + ((6 - lngHour) / 24)
        ///	if setting time is desired:
        ///	  t = N + ((18 - lngHour) / 24)
        double lngHour = lw / 15.0;
        double t1 = N + ((18 - lngHour) / 24.0);
        ///step3
        double M1 = (0.9856 * t1) - 3.289;
        //step4
        double L1 = M1 + (1.916 * Math.sin(Math.toRadians(M1)) + (0.020 * Math.sin(Math.toRadians(2 * M1)))) + 282.634;
        if (L1 > 360)
            L1 -= 360;
        else if (L1 < 0)
            L1 += 360;
        if (L1 > 360)
            L1 -= 360;
        else if (L1 < 0)
            L1 += 360;
        if (L1 > 360)
            L1 -= 360;
        else if (L1 < 0)
            L1 += 360;
        ///step5a
        double RA = Math.toDegrees(Math.atan(0.91764 * Math.tan(Math.toRadians(L1))));
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        if (RA > 360)
            RA -= 360;
        else if (RA < -360)
            RA += 360;
        ///step5b
        int Lquadrant1 = (int) (Math.floor(L1 / 90) * 90);
        int RAquadrant = (int) (Math.floor(RA / 90) * 90);
        double RA1 = RA + (Lquadrant1 - RAquadrant);
        ///step5c
        RA1 /= 15;
        ///step6
        double sinDec1 = 0.39782 * Math.sin(Math.toRadians(L1));
        double cosDec = Math.cos(Math.asin(sinDec1));
        ///step7a
        double cosH1 = (Math.cos(Math.toRadians(zenith)) - (sinDec1 * Math.sin(Math.toRadians(fi)))) /
                (cosDec * Math.cos(Math.toRadians(fi)));
        if (cosH1 > 1 || cosH1 < -1)
            return 1000;
        ///step7b
        //if if rising time is desired:
        //	  H = 360 - acos(cosH)
        //	if setting time is desired:
        //	  H = acos(cosH)
        double H1 = Math.toDegrees(Math.acos(cosH1));
        H1 /= 15;
        ///step 8
        double T1 = H1 + RA1 - (0.06571 * t1) - 6.622;
        ///step9
        double UT1 = T1 - lngHour;
        if (UT1 > 24)
            UT1 -= 24;
        else if (UT1 < 0)
            UT1 += 24;
        if (UT1 > 24)
            UT1 -= 24;
        else if (UT1 < 0)
            UT1 += 24;
        if (UT1 > 24)
            UT1 -= 24;
        else if (UT1 < 0)
            UT1 += 24;

        return UT1;
    }


    public static int SummerTimeStartEu(int year) {
        return (31 - ((((5 * year) / 4) + 4) % 7));
    }

    public static int WinterTimeStartEu(int year) {

        return (31 - ((((5 * year) / 4) + 1) % 7));
    }

    public static boolean IsSummerTimeEu(int year, int mon, int day) {
        if (mon > 3 && mon < 11)
            return true;
        if (mon == 3 && day >= SummerTimeStartEu(year))
            return true;
        else return mon == 11 && day < WinterTimeStartEu(year);
    }

    public static int SummerTimeStartUsaCanada(int year) {
        int latstSundayInMarch = SummerTimeStartEu(year);
        int firstSundayInMarch = latstSundayInMarch % 7;
        if (firstSundayInMarch == 0)
        {
            firstSundayInMarch = 7;
        }
        return firstSundayInMarch + 7;
    }

    public static int WinterTimeStartUsaCanada(int year) {
        int lastSundayInNovember = WinterTimeStartEu(year);
        int firstSundayInNovember = lastSundayInNovember % 7;
        if (firstSundayInNovember == 0)
        {
            firstSundayInNovember = 7;
        }
        return firstSundayInNovember;
    }

    public static boolean IsSummerTimeUsaCanada(int year, int mon, int day) {
        if (mon > 3 && mon < 11)
            return true;
        if (mon == 3 && day >= SummerTimeStartUsaCanada(year))
            return true;
        else return mon == 11 && day < WinterTimeStartUsaCanada(year);
    }


    //Get Julian date from y-m-d
    public static double JD(int year, int mon, int day) {
        return (367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013.5);
    }

    public static int getDayOfWeek(double aaa) {
        return (int) (aaa + 1.5) % 7;
    }

    public static int getDayNum(int y, int m, int d) {
        int leap = isLeapYear(y);
        return (m * 275 / 9) - ((2 - leap) * (m + 9) / 12) + d - 30;
    }

    //return int: 1 for leap year, and 0 for regular
    public static int isLeapYear(int year) {
        int leap = 0;

        if (year % 4 == 0 && year % 100 != 0) {
            leap = 1;
        } else if (year % 400 == 0) {
            leap = 1;
        }

        return leap;
    }


    public static int[] getDayOfWeekAndMonthByYearAndDayNumber(int year, int num) {
        int leap = isLeapYear(year);

        int a = 1889;
        if (leap == 1) a = 1523;
        int b = (int) ((num + a - 122.1) / 365.25);
        int c = num + a - (int) (365.25 * b);
        int e = (int) (c / 30.6001);
        int m = (e > 13.5) ? e - 13 : e - 1;
        int d = c - (int) (30.6001 * e);
        int[] aa = new int[2];
        aa[0] = m;
        aa[1] = d;
        return aa;
    }

    public static int JDtoDay(double jd) {
        int z = (int) (jd + 0.5);

        int alf = (int) ((z - 1867216.25) / 36524.25);
        double a = z + 1 + alf - (alf / 4);
        a = (z < 2299161) ? z : a;
        double b = a + 1524;
        int c = (int) ((b - 122.1) / 365.25);
        int d = (int) (365.25 * c);
        int e = (int) ((b - d) / 30.6001);
        return (int) (b - d - (int) (30.6001 * e));
    }

    public static int JDtoMon(double jd) {
        int z = (int) (jd + 0.5);

        int alf = (int) ((z - 1867216.25) / 36524.25);
        double a = z + 1 + alf - (alf / 4);
        a = (z < 2299161) ? z : a;
        double b = a + 1524;
        int c = (int) ((b - 122.1) / 365.25);
        int d = (int) (365.25 * c);
        int e = (int) ((b - d) / 30.6001);
        return (e < 13.5) ? e - 1 : e - 13;
    }


    public static int JDtoYear(double jd) {
        int z = (int) (jd + 0.5);

        int alf = (int) ((z - 1867216.25) / 36524.25);
        double a = z + 1 + alf - (alf / 4);
        a = (z < 2299161) ? z : a;
        double b = a + 1524;
        int c = (int) ((b - 122.1) / 365.25);
        int d = (int) (365.25 * c);
        int e = (int) ((b - d) / 30.6001);
        int m = (e < 13.5) ? e - 1 : e - 13;
        return (m > 2.5) ? c - 4716 : c - 4715;
    }

    public static int getWeekNumberFromDate(int year, int mon, int day) {
        int x = getDayOfWeek(JD(year, mon, day)); //from 0 to 6

        if (x == 0) {
            x = 7;
        }
        return (getDayNum(year, mon, day) - x + 10) / 7;

    }

    public static double normalize(double a) {
        a = a - Math.floor(a);
        if (a < 0) {
            a = a + 1;
        }
        return a;
    }

    public static double round2(double a) {
        return (Math.round(a * 100) / 100.0);
    }

    static int EssenUTCCalc(boolean east, int lat1, int lat2, int lat3) {
        int toRet = (int) Math.round((lat1 + lat2 / 60.0 + lat3 / 3600.0) / 15);

        return east ? toRet : -1 * toRet;
    }

    public static double getFiForLocation(int year, int mon, int day, int latDegree, int latMinutes, int latSeconds)
    {
        //  double Jd=367*year-7*(year+(mon+9)/12)/4+275*mon/9+day+1721013.5;
        double Jd = JD(year, mon, day);

        //	System.out.println ("daynum " +daynum);

        double T = (Jd - 2415020.0) / 36525.0;
        double L = 279.6968 + 36000.76892 * T + 0.000325 * T * T;
        double M = 358.47583 + 35999.04975 * T - 0.000150 * T * T - 0.0000033 * T * T * T;
        double C = (1.919460 - 0.004789 * T - 0.000014 * T * T) * sinSim(M)
                + (0.020094 - 0.000100 * T) * sinSim(2 * M)
                + 0.000193 * sinSim(3 * M);
        double SOL = L + C;
        double EPS = 23.452294 - 0.0130125 * T
                - 0.00000164 * T * T + 0.000000503 * T * T * T;
        // double Delta = Math.asin(sinSim(EPS) * sinSim(SOL));


        //example for kyiv
        //        double fi=50+27/60.0+0/3600.0;
        //        double fiRad=degToRad(fi);
        //        double lw=(30+30/60.0+0/3600.0);

        double fi = latDegree + latMinutes / 60.0 + latSeconds / 3600.0;
        return fi;
    }

    public static double getLwForLocation(int lonDegrees, int lonMinutes, int lonSecons, boolean isEast) {
        double lw = lonDegrees + lonMinutes / 60.0 + lonSecons / 3600.0;
        lw = isEast ? lw : -1 * lw;
        return lw;
    }

    public static double getMoonAgeInRadians(double jd) {
        return normalize((jd - 2451550.1) / 29.530588853);
    }
    public static double getMoonAge(int year, int mon, int day, double jd) {
        double IP = getMoonAgeInRadians(jd);
        return IP * 29.53;
    }

    public static double[] getFullNullMoonDates(double jd, double ageInDays) {
        double JDFull, JDNew;
        if (ageInDays > 0 && ageInDays < 29.53 / 2.0) {
//                JDFull = (367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013) + 0.5 + 29.53 / 2 - ageInDays + 1;
//                JDNew = (367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013) + 0.5 + 29.53 - ageInDays + 1;
            JDFull = jd + 29.53 / 2 - ageInDays + 1;
            JDNew = jd + 29.53 - ageInDays + 1;
        } else {
//                JDNew = (367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013) + 0.5 + 29.53 - ageInDays + 1;
            JDNew = jd + 29.53 - ageInDays + 1;
            JDFull = JDNew + 29.53 / 2 + 1;
        }
        double [] result = new double[2];
        result[0] = JDFull;
        result[1] = JDNew;
        return result;
    }

    //return values [0] = ecliptic orbit
    // [1] = distance, distance * 6342 is distance in kilometers
    public static double[] getLunarEclipticOrbitInDegreesAndDistance(double Jd) {
        double IP = getMoonAgeInRadians(Jd) * 2 * Math.PI;                      // Convert phase to radians

        // calculate moon's distance
        double DP = 2 * Math.PI * normalize((Jd - 2451562.2) / 27.55454988);
        double DI = 60.4 - 3.3 * Math.cos(DP) - 0.6 * Math.cos(2 * IP - DP) - 0.5 * Math.cos(2 * IP);

        // calculate moon's ecliptic latitude
        // double NP = 2 * Math.PI * normalize((Jd - 2451565.2) / 27.212220817);
        // double LA = 5.1 * Math.sin(NP);

        // calculate moon's ecliptic longitude
        double RP = normalize((Jd - 2451555.8) / 27.321582241);
        double LO = 360 * RP + 6.3 * Math.sin(DP) + 1.3 * Math.sin(2 * IP - DP) + 0.7 * Math.sin(2 * IP);
        if (LO > 360) LO = LO - 360;
        double[] res = new double[2];
        res[0] = LO;
        res[1] = DI;
        return res;
    }

    public static int getMoonVisibilityPercent(double jde) {
        double tt = (jde - 2451545 + 0.5) / 36525; //v originale bez 0.5
        double dd = 297.8502042 + 445267.1115168 * tt - 0.0016300 * tt * tt + tt * tt * tt / 545868.0 - tt * tt * tt * tt / 113065000.0;
        double mm = 357.5291092 + 35999.0502909 * tt - 0.0001536 * tt * tt + tt * tt * tt / 24490000;
        double mmM = 134.9634114 + 447198.8676314 * tt + 0.0089970 * tt * tt + tt * tt * tt / 69699 - tt * tt * tt * tt / 14712000;
        double ii = 180 - dd - 6.289 * sinSim(mmM) + 2.100 * sinSim(mm) - 1.274 * sinSim(2 * dd - mmM) - 0.658 * sinSim(2 * dd) - 0.214 * sinSim(mmM) - 0.110 * sinSim(dd);
        int visibitlity = (int) Math.round((1 + Math.cos(Math.toRadians(ii))) * 100) / 2;
        return visibitlity;
    }
}
