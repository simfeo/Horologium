package sim.horologium.app.EditCords;

import static sim.horologium.app.Utils.Utils.initializeCityDataContainer;
import static sim.horologium.app.Utils.Utils.numberToStringAddZeroIfNeeded;
import static sim.horologium.app.Utils.Utils.writingJsonProcedure;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import sim.horologium.app.Utils.LocationData;
import sim.horologium.app.R;

public class EditLocationActivity extends AppCompatActivity {
    Resources res;
    EditText cityName, longDeg, longMin, longSec, latDeg, latMin, latSec;
    //    TextView gmtText;
    RadioButton eastRadio, westRadio, northRadio, southRadio, dstState_off, dstState_eu, dstState_us;

    ImageButton saveButton, backButton, getGpsButton;

    Spinner gmtSpinner;
    int activityResult = Activity.RESULT_CANCELED;

    private LocationManager locationManager;
    private final ActivityResultLauncher<String> requestFinePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    getExactGpsLocation();
                } else {
                    getWeakGpsLocation();
                }
            });

    private final ActivityResultLauncher<String> requestCoursePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        getExactGpsLocation();
                    } else {
                        requestFinePermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                } else {
                    Toast.makeText(this, res.getString(R.string.editLocation_failedGetPermission_ToastText), Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_location);
        res = getResources();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        gmtText = findViewById(R.id.editLocation_GmtText);
        eastRadio = findViewById(R.id.editLocation_eastCheck);
        westRadio = findViewById(R.id.editLocation_westCheck);
        northRadio = findViewById(R.id.editLocation_northCheck);
        southRadio = findViewById(R.id.editLocation_southCheck);
        dstState_off = findViewById(R.id.editLocation_DstOff);
        dstState_eu = findViewById(R.id.editLocation_DstEu);
        dstState_us = findViewById(R.id.editLocation_DstUsCanada);

        cityName = findViewById(R.id.editLocation_CityName);
        longDeg = findViewById(R.id.editLocation_longitudeDegrees);
        longMin = findViewById(R.id.editLocation_longitudeMinutes);
        longSec = findViewById(R.id.editLocation_longitudeSeconds);

        latDeg = findViewById(R.id.editLocation_latitudeDegrees);
        latMin = findViewById(R.id.editLocation_latitudeMinutes);
        latSec = findViewById(R.id.editLocation_latitudeSeconds);

        longDeg.setFilters(new InputFilter[]{new InputFilterMinMax(0, 180)});
        longMin.setFilters(new InputFilter[]{new InputFilterMinMax(0, 59)});
        longSec.setFilters(new InputFilter[]{new InputFilterMinMax(0, 59)});
        latDeg.setFilters(new InputFilter[]{new InputFilterMinMax(0, 90)});
        latMin.setFilters(new InputFilter[]{new InputFilterMinMax(0, 59)});
        latSec.setFilters(new InputFilter[]{new InputFilterMinMax(0, 59)});

        gmtSpinner = findViewById(R.id.editLocation_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.availableGmt,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gmtSpinner.setAdapter(adapter);

        LocationData locationData = initializeCityDataContainer(getBaseContext());
        setupLocationToLayout(locationData);


        saveButton = findViewById(R.id.editLocation_buttonSave);
        getGpsButton = findViewById(R.id.editLocation_buttonGps);
        backButton = findViewById(R.id.editLocation_buttonBack);


        backButton.setOnClickListener(v -> {
            setResult(activityResult);
            finish();
        });

        saveButton.setOnClickListener(v -> saveLocationData());

        getGpsButton.setOnClickListener(v -> dealWithGpsData());
    }

    @Override
    protected void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            revokeSelfPermissionOnKill(Manifest.permission.ACCESS_FINE_LOCATION);
            revokeSelfPermissionOnKill(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        super.onDestroy();
    }

    private void dealWithGpsData() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            return;
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                getExactGpsLocation();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                DialogInterface.OnClickListener okButtonListener = (arg0, arg1) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                };

                DialogInterface.OnClickListener cancelButtonListener = (arg0, arg1) -> {
                };

                new AlertDialog.Builder(this)
                        .setTitle("GPS") //title
                        .setMessage(res.getString(R.string.permiss_gps)) //message
                        .setPositiveButton(res.getString(R.string.yes_string), okButtonListener) //positive button
                        .setNegativeButton(res.getString(R.string.no_string), cancelButtonListener) //negative button
                        .show(); //show dialog
            } else {
                requestFinePermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        } else {
            requestCoursePermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

    }

    private void getExactGpsLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                locationManager.getCurrentLocation(
                        LocationManager.FUSED_PROVIDER,
                        null,
                        getMainExecutor(),
                        this::setGpsLocationToLayout);
            } catch (SecurityException ignored) // if we are here, so access is granted
            {
            }
        } else {
            getWeakGpsLocation();
        }
    }

    private void getWeakGpsLocation() {
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setGpsLocationToLayout(location);
        } catch (SecurityException ignored) // if we are here, so access is granted
        {
        }
    }


    private void setGpsLocationToLayout(Location location) {
        if (location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            setGpsDataToView(latitude, longitude);
        } else {
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                try {
                    Location l = locationManager.getLastKnownLocation(provider);
                    if (l != null) {
                        if (bestLocation == null) {
                            bestLocation = l;
                        } else if (bestLocation.getAccuracy() > l.getAccuracy()) {
                            bestLocation = l;
                        }
                    }
                } catch (SecurityException ignored) // some providers can no have any access
                {
                }
            }
            if (bestLocation != null) {
                double longitude = bestLocation.getLongitude();
                double latitude = bestLocation.getLatitude();
                setGpsDataToView(latitude, longitude);
            }
        }
    }


    private void setGpsDataToView(double latitude, double longitude) {
        double tmpLatitude = Math.abs(latitude);
        latDeg.setText(String.valueOf((int) tmpLatitude));
        tmpLatitude = (tmpLatitude % 1) * 60;
        latMin.setText(String.valueOf((int) tmpLatitude));
        tmpLatitude = (tmpLatitude % 1) * 60;
        latSec.setText(String.valueOf((int) tmpLatitude));
        double tmpLongitude = Math.abs(longitude);
        longDeg.setText(String.valueOf((int) tmpLongitude));
        tmpLongitude = (tmpLongitude % 1) * 60;
        longMin.setText(String.valueOf((int) tmpLongitude));
        tmpLongitude = (tmpLongitude % 1) * 60;
        longSec.setText(String.valueOf((int) tmpLongitude));

        if (latitude >= 0)
            northRadio.setChecked(true);
        else
            southRadio.setChecked(true);
        if (longitude >= 0)
            eastRadio.setChecked(true);
        else
            westRadio.setChecked(true);

        Toast.makeText(this, res.getString(R.string.editLocation_gpsDataObtained_ToastText), Toast.LENGTH_SHORT).show();
    }

    private void saveLocationData() {
        LocationData locationData = LocationData.CreateEmpty();
        locationData.cityName = cityName.getText().toString();
        if (locationData.cityName.trim().length() == 0) {
            locationData.cityName = res.getString(R.string.defaultCityName);
        }
        int spinnerPosition = gmtSpinner.getSelectedItemPosition();
        locationData.gmt = gmtSpinner.getAdapter().getItem(spinnerPosition).toString();
        if (dstState_eu.isChecked()) {
            locationData.daylightSavingEnabled = LocationData.DayLightState.Eu;
        } else if (dstState_us.isChecked()) {
            locationData.daylightSavingEnabled = LocationData.DayLightState.UsCanada;
        } else {
            locationData.daylightSavingEnabled = LocationData.DayLightState.off;
        }
        locationData.latitude.isPlus = northRadio.isChecked();
        locationData.longitude.isPlus = eastRadio.isChecked();

        int latitudeMax = 90;
        int maxMinutesAndSeconds = 59;
        locationData.latitude.degrees = getTextInputAsStringAndClamp(latDeg, latitudeMax, 0, 0);
        if (locationData.latitude.degrees != latitudeMax) {
            locationData.latitude.minutes = getTextInputAsStringAndClamp(latMin, maxMinutesAndSeconds, 0, 0);
            locationData.latitude.seconds = getTextInputAsStringAndClamp(latSec, maxMinutesAndSeconds, 0, 0);
        } else {
            locationData.latitude.minutes = 0;
            locationData.latitude.seconds = 0;
        }

        int longitudeMax = 180;
        locationData.longitude.degrees = getTextInputAsStringAndClamp(longDeg, longitudeMax, 0, 0);
        if (locationData.longitude.degrees != longitudeMax) {
            locationData.longitude.minutes = getTextInputAsStringAndClamp(longMin, maxMinutesAndSeconds, 0, 0);
            locationData.longitude.seconds = getTextInputAsStringAndClamp(longSec, maxMinutesAndSeconds, 0, 0);
        } else {
            locationData.longitude.minutes = 0;
            locationData.longitude.seconds = 0;
        }
        try {
            writingJsonProcedure(this, locationData);
            activityResult = Activity.RESULT_OK;
            Toast.makeText(this, res.getString(R.string.editLocation_saved_ToastText), Toast.LENGTH_SHORT).show();
        } catch (IOException ignored) {
            Toast.makeText(this,res.getString(R.string.editLocation_failed_save_ToastText), Toast.LENGTH_SHORT).show();
        }
    }

    public int getTextInputAsStringAndClamp(TextView v, int max, int min, int def) {
        try {
            int num = Integer.parseInt(v.getText().toString());
            num = Math.min(max, Math.max(num, min));
            return num;
        } catch (Exception e) {
            return def;
        }
    }

    private void setupLocationToLayout(LocationData locationData) {
        cityName.setText(locationData.cityName);
        for (int i = 0; i < gmtSpinner.getCount(); ++i) {
            if (locationData.gmt.equals(gmtSpinner.getAdapter().getItem(i).toString())) {
                gmtSpinner.setSelection(i);
                break;
            }
        }

        //gmt.setText (arrayContainer[1]);
        latDeg.setText(Integer.toString(locationData.latitude.degrees));
        latMin.setText(numberToStringAddZeroIfNeeded(locationData.latitude.minutes));
        latSec.setText(numberToStringAddZeroIfNeeded(locationData.latitude.seconds));
        longDeg.setText(Integer.toString(locationData.longitude.degrees));
        longMin.setText(numberToStringAddZeroIfNeeded(locationData.longitude.minutes));
        longSec.setText(numberToStringAddZeroIfNeeded(locationData.longitude.seconds));
        switch (locationData.daylightSavingEnabled) {
            case Eu:
                dstState_eu.setChecked(true);
                break;
            case UsCanada:
                dstState_us.setChecked(true);
                break;
            default:
                dstState_off.setChecked(true);
                break;
        }

        if (locationData.isNorth())
            northRadio.setChecked(true);
        else
            southRadio.setChecked(true);
        if (locationData.isEast())
            eastRadio.setChecked(true);
        else
            westRadio.setChecked(true);
    }
}