package sim.horologium.app.Utils;

import java.util.Locale;

// Capital of the user's language (or country, for English and Portuguese), shown until they
// save their own location.
public final class DefaultLocation {
    private DefaultLocation() {
    }

    public static LocationData forLocale(Locale locale) {
        String lang = locale.getLanguage();
        String country = locale.getCountry();
        switch (lang) {
            case "en":
                switch (country) {
                    case "US": return city("Washington", "-05:00", LocationData.DayLightState.UsCanada, 38, 54, -77, 2);
                    case "CA": return city("Ottawa", "-05:00", LocationData.DayLightState.UsCanada, 45, 25, -75, 42);
                    case "AU": return city("Canberra", "+10:00", LocationData.DayLightState.off, -35, 17, 149, 8);
                    case "IN": return city("New Delhi", "+05:30", LocationData.DayLightState.off, 28, 37, 77, 13);
                    case "SG": return city("Singapore", "+08:00", LocationData.DayLightState.off, 1, 17, 103, 51);
                    case "ZA": return city("Pretoria", "+02:00", LocationData.DayLightState.off, -25, 45, 28, 11);
                    case "IE": return city("Dublin", "00:00", LocationData.DayLightState.Eu, 53, 21, -6, 16);
                    default: return city("London", "00:00", LocationData.DayLightState.Eu, 51, 30, 0, -8);
                }
            case "pt":
                if ("BR".equals(country)) {
                    return city("Brasília", "-03:00", LocationData.DayLightState.off, -15, 48, -47, 53);
                }
                return city("Lisboa", "00:00", LocationData.DayLightState.Eu, 38, 43, -9, 8);
            case "cs": return city("Praha", "+01:00", LocationData.DayLightState.Eu, 50, 5, 14, 25);
            case "da": return city("København", "+01:00", LocationData.DayLightState.Eu, 55, 41, 12, 34);
            case "de": return city("Berlin", "+01:00", LocationData.DayLightState.Eu, 52, 31, 13, 24);
            case "el": return city("Αθήνα", "+02:00", LocationData.DayLightState.Eu, 37, 59, 23, 44);
            case "es": return city("Madrid", "+01:00", LocationData.DayLightState.Eu, 40, 25, -3, 42);
            case "et": return city("Tallinn", "+02:00", LocationData.DayLightState.Eu, 59, 26, 24, 45);
            case "fi": return city("Helsinki", "+02:00", LocationData.DayLightState.Eu, 60, 10, 24, 56);
            case "fr": return city("Paris", "+01:00", LocationData.DayLightState.Eu, 48, 51, 2, 21);
            case "hr": return city("Zagreb", "+01:00", LocationData.DayLightState.Eu, 45, 49, 15, 59);
            case "hu": return city("Budapest", "+01:00", LocationData.DayLightState.Eu, 47, 30, 19, 3);
            case "hy": return city("Երևան", "+04:00", LocationData.DayLightState.off, 40, 11, 44, 31);
            case "it": return city("Roma", "+01:00", LocationData.DayLightState.Eu, 41, 54, 12, 30);
            case "ja": return city("東京", "+09:00", LocationData.DayLightState.off, 35, 41, 139, 41);
            case "ko": return city("서울", "+09:00", LocationData.DayLightState.off, 37, 34, 126, 59);
            case "nb":
            case "nn":
            case "no": return city("Oslo", "+01:00", LocationData.DayLightState.Eu, 59, 55, 10, 45);
            case "nl": return city("Amsterdam", "+01:00", LocationData.DayLightState.Eu, 52, 22, 4, 54);
            case "pl": return city("Warszawa", "+01:00", LocationData.DayLightState.Eu, 52, 14, 21, 1);
            case "ro": return city("București", "+02:00", LocationData.DayLightState.Eu, 44, 26, 26, 6);
            case "sk": return city("Bratislava", "+01:00", LocationData.DayLightState.Eu, 48, 9, 17, 7);
            case "sv": return city("Stockholm", "+01:00", LocationData.DayLightState.Eu, 59, 20, 18, 4);
            case "uk": return city("Київ", "+02:00", LocationData.DayLightState.Eu, 50, 27, 30, 30);
            case "vi": return city("Hà Nội", "+07:00", LocationData.DayLightState.off, 21, 2, 105, 51);
            default: return city("London", "00:00", LocationData.DayLightState.Eu, 51, 30, 0, -8);
        }
    }

    // A negative degree or minute value means south / west (minutes carry the sign at 0 degrees).
    private static LocationData city(String name, String gmt, LocationData.DayLightState dst,
                                     int latDeg, int latMin, int lonDeg, int lonMin) {
        LocationData data = LocationData.CreateEmpty();
        data.cityName = name;
        data.gmt = gmt;
        data.daylightSavingEnabled = dst;
        data.latitude.isPlus = latDeg >= 0 && latMin >= 0;
        data.latitude.degrees = Math.abs(latDeg);
        data.latitude.minutes = Math.abs(latMin);
        data.longitude.isPlus = lonDeg >= 0 && lonMin >= 0;
        data.longitude.degrees = Math.abs(lonDeg);
        data.longitude.minutes = Math.abs(lonMin);
        data.isPlaceholder = true;
        return data;
    }
}
