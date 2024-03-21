package sim.astronomy.go.EditCords;

import static sim.astronomy.go.Utils.Utils.initializeCityDataContainer;
import static sim.astronomy.go.Utils.Utils.numberToStringAddZeroIfNeeded;
import static sim.astronomy.go.Utils.Utils.writingJsonProcedure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;

import sim.astronomy.go.R;
import sim.astronomy.go.Utils.LocationData;

public class EditLocationActivity extends AppCompatActivity {
    final Map<String, String> cardinalDirections = Map.of("n", "north", "s", "south", "w", "west", "e", "east");
    Resources res;
    EditText cityName, longDeg, longMin, longSec, latDeg, latMin, latSec;
//    TextView gmtText;
    RadioButton eastRadio, westRadio, northRadio, southRadio;
    Switch daySavingTimeSwitch;

    ImageButton gmtIcreaseButton, gmtDecreaseButton;
    Button saveButton, backButton, getGpsButton;

    Spinner gmtSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_location);
        res = getResources();


//        gmtText = findViewById(R.id.editLocation_GmtText);
        eastRadio = findViewById(R.id.editLocation_eastCheck);
        westRadio = findViewById(R.id.editLocation_westCheck);
        northRadio = findViewById(R.id.editLocation_northCheck);
        southRadio = findViewById(R.id.editLocation_southCheck);
        daySavingTimeSwitch = findViewById(R.id.editLocation_daySavingTimeSwitch);

        cityName = findViewById(R.id.editLocation_CityName);
        longDeg = findViewById(R.id.editLocation_longitudeDegrees);
        longMin = findViewById(R.id.editLocation_longitudeMinutes);
        longSec = findViewById(R.id.editLocation_longitudeSeconds);

        latDeg = findViewById(R.id.editLocation_latitudeDegrees);
        latMin = findViewById(R.id.editLocation_latitudeMinutes);
        latSec = findViewById(R.id.editLocation_latitudeSeconds);

        longDeg.setFilters(new InputFilter[]{new InputFilterMinMax("0", "180")});
        longMin.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        longSec.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        latDeg.setFilters(new InputFilter[]{new InputFilterMinMax("0", "90")});
        latMin.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        latSec.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});

        gmtSpinner = findViewById(R.id.editLocation_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.availableGmt,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        gmtSpinner.setAdapter(adapter);

        LocationData locationData = initializeCityDataContainer(getBaseContext());
        setupLocationToLayout(locationData);

//        gmtIcreaseButton = findViewById(R.id.editLocation_buttonGmtPlus);
//        gmtDecreaseButton = findViewById(R.id.editLocation_buttonGmtMinus);

        saveButton = findViewById(R.id.editLocation_buttonSave);
        getGpsButton = findViewById(R.id.editLocation_buttonGps);
        backButton = findViewById(R.id.editLocation_buttonBack);

//        gmtIcreaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gmtIncreaseButtonClick(v);
//            }
//        });
//
//        gmtDecreaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gmtDecreaseButtonClick(v);
//            }
//        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocationData();
            }
        });

    }

    private void saveLocationData() {
        LocationData locationData = LocationData.CreateEmpty();
        locationData.cityName = cityName.getText().toString();
//        locationData.gmt = gmtText.getText().toString();
        int spinnerPosition = gmtSpinner.getSelectedItemPosition();
        locationData.gmt = gmtSpinner.getAdapter().getItem(spinnerPosition).toString();
        locationData.daylightSavingEnabled = daySavingTimeSwitch.isChecked();
        locationData.latitude.isPlus = northRadio.isChecked();
        locationData.longitude.isPlus = eastRadio.isChecked();

        int latitudeMax = 90;
        int maxMinutesAndSeconds = 59;
        locationData.latitude.degrees = getTextInputAsStringAndClamp(latDeg, latitudeMax, 0, 0);
        if (locationData.latitude.degrees != latitudeMax) {
            locationData.latitude.minutes = getTextInputAsStringAndClamp(latMin, maxMinutesAndSeconds, 0, 0);
            locationData.latitude.seconds = getTextInputAsStringAndClamp(latSec, maxMinutesAndSeconds, 0, 0);
        }
        else
        {
            locationData.latitude.minutes = 0;
            locationData.latitude.seconds = 0;
        }

        int longitudeMax = 180;
        locationData.longitude.degrees = getTextInputAsStringAndClamp(longDeg, longitudeMax, 0,0);
        if (locationData.longitude.degrees != longitudeMax)
        {
            locationData.longitude.minutes = getTextInputAsStringAndClamp(longMin, maxMinutesAndSeconds, 0,0);
            locationData.longitude.seconds = getTextInputAsStringAndClamp(longSec, maxMinutesAndSeconds, 0,0);
        }
        else
        {
            locationData.longitude.minutes = 0;
            locationData.longitude.seconds = 0;
        }
        try {
            writingJsonProcedure(this, locationData);
        } catch (IOException e) {

        }
    }

    public int getTextInputAsStringAndClamp (TextView v, int max, int min, int def)
    {
        try {
            int num = Integer.parseInt(v.getText().toString());
            num = Math.min(max, Math.max(num, min));
            return num;
        }
        catch (Exception e)
        {
            return def;
        }
    }

    private void setupLocationToLayout(LocationData locationData) {
        cityName.setText(locationData.cityName);
//        gmtText.setText(locationData.gmt);
        for (int i = 0; i < gmtSpinner.getCount(); ++i)
        {
            if (locationData.gmt.equals(gmtSpinner.getAdapter().getItem(i).toString()))
            {
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
        daySavingTimeSwitch.setChecked(locationData.daylightSavingEnabled);

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