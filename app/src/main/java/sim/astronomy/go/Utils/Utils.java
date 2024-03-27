package sim.astronomy.go.Utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Utils {
    public static LocationData initializeCityDataContainer(@NonNull Context ctx) {
        LocationData cityDataLocationArray;
        try {
            cityDataLocationArray = readingJsonProcedure(ctx);
        } catch (IOException e) {
            cityDataLocationArray = LocationData.CreateDefault();
            try {
                writingJsonProcedure(ctx, cityDataLocationArray);
            } catch (IOException e1) {

            }
        }
        return cityDataLocationArray;
    }

    public static void writingJsonProcedure(Context ctx, LocationData cityDataLocationArray) throws IOException {
        OutputStream outStream = ctx.openFileOutput("location.json", 0x00000000);
        try (OutputStreamWriter outStreamWrite = new OutputStreamWriter(outStream)) {
            outStreamWrite.write(new Gson().toJson(cityDataLocationArray));
        }
    }

    private static LocationData readingJsonProcedure(Context ctx) throws IOException {
        InputStream inStream = ctx.openFileInput("location.json");
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder stringBuild = new StringBuilder();

        try {
            String str;
            while ((str = buffReader.readLine()) != null) {
                stringBuild.append(str);
            }
        } finally {
            buffReader.close();
        }

        try {
            return new Gson().fromJson(stringBuild.toString(), LocationData.class);
        }
        catch (Exception e)
        {
            return LocationData.CreateDefault();
        }
    }

    @NonNull
    public static String numberToStringAddZeroIfNeeded(int value) {
        return String.format(Locale.US, "%02d", value);
    }

    @NonNull
    public static String numberToStringAddZeroIfNeeded(double value) {
        return numberToStringAddZeroIfNeeded((int) value);
    }
}
