package sim.horologium.app.Utils;

public class LocationData {

    public enum DayLightState
    {
        off,
        Eu,
        UsCanada
    }
    //    Kyiv +2 on north 50 27 00 east 30 30 00"
    public String cityName = "City";
    public String gmt = "00:00";
    public DayLightState daylightSavingEnabled = DayLightState.off;

    public Coords latitude;
    public Coords longitude;

    public static LocationData CreateKyiv() {
        // String defaultString = "Kyiv +2 on north 50 27 00 east 30 30 00";
        LocationData locationData = LocationData.CreateEmpty();
        locationData.cityName = "Kyiv";
        locationData.gmt = "+02:00";
        locationData.daylightSavingEnabled = DayLightState.Eu;
        locationData.latitude.isPlus = true;
        locationData.latitude.degrees = 50;
        locationData.latitude.minutes = 27;
        locationData.latitude.seconds = 0;
        locationData.longitude.isPlus = true;
        locationData.longitude.degrees = 30;
        locationData.longitude.minutes = 30;
        locationData.longitude.seconds = 0;
        return locationData;
    }

    public static LocationData CreateDefault() {
        LocationData locationData = LocationData.CreateEmpty();
        locationData.cityName = "ChangeCity";
        locationData.gmt = "00:00";
        locationData.daylightSavingEnabled = DayLightState.off;
        locationData.latitude.isPlus = true;
        locationData.latitude.degrees = 0;
        locationData.latitude.minutes = 0;
        locationData.latitude.seconds = 0;
        locationData.longitude.isPlus = true;
        locationData.longitude.degrees = 0;
        locationData.longitude.minutes = 0;
        locationData.longitude.seconds = 0;
        return locationData;
    }


    public static LocationData CreateEmpty() {
        LocationData locationData = new LocationData();
        locationData.longitude = new LocationData.Coords();
        locationData.latitude = new LocationData.Coords();
        return locationData;
    }

    public static class Coords {
        // true if North, false if South
        // true if East, false if West
        public boolean isPlus = true;
        public int degrees = 0;
        public int minutes = 0;
        public int seconds = 0;
    }

    public boolean isEast() {
        return longitude.isPlus;
    }

    public boolean isNorth() {
        return latitude.isPlus;
    }

    public double getGmtAsDouble() {
        return gmtToDouble(gmt);
    }

    public static double gmtToDouble(String gmt)
    {
        try {
            if (gmt.contains(":")) {
                String[] zoneHoursAndMinutes = gmt.split(":");
                int zoneH = Integer.valueOf(zoneHoursAndMinutes[0]);
                int zoneM = Integer.parseInt(zoneHoursAndMinutes[1]);
                if (zoneH < 0)
                    zoneM *= -1;
                return zoneH + zoneM / 60.0;
            }
        } catch (NumberFormatException ignored) {
        }
        return 0.0f;
    }
}
