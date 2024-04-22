package sim.horologium.app.ui.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;

import sim.horologium.app.R;
import sim.horologium.app.Utils.CustomImageView;
import sim.horologium.app.databinding.CompassBinding;
import sim.horologium.app.ui.calendar.CalendarViewModel;

public class CompassFragment extends Fragment implements SensorEventListener {
    private static final DecimalFormat df2 = new DecimalFormat("0.00");
    private static final DecimalFormat df0 = new DecimalFormat("0");

    private CustomImageView compassImage;
    private TextView azimuthDegrees, barometerAtmospheres;
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    private Sensor barometer;
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel CalendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        CompassBinding binding = CompassBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        compassImage = view.findViewById(R.id.compass);
        azimuthDegrees = view.findViewById(R.id.compassAzimuthDegrees);
        barometerAtmospheres = view.findViewById(R.id.compassBarometerPressure);
//        compassImage.getDrawable().mutate();
        compassImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, magnetometer);
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, barometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == magnetometer || event.sensor == accelerometer) {
            processCompass(event);
        }
        else if (event.sensor == barometer)
        {
            processBarometer(event);
        }
    }

    private void processBarometer(SensorEvent event) {
        float pressure = event.values[0] / SensorManager.PRESSURE_STANDARD_ATMOSPHERE;
        barometerAtmospheres.setText(df2.format(pressure) +" " + getString(R.string.compassBarometerPostfix));
    }

    private void processCompass(SensorEvent event) {
        if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            lastMagnetometerSet = true;
        } else if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            lastAccelerometerSet = true;
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(rotationMatrix, orientation);

            float azimuthInRadians = orientation[0];
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            float rotationCorrection = (rotation % 2) * (rotation * 90.0f);
            float azimuthInDegrees = (float) (Math.toDegrees(azimuthInRadians) + 360 + rotationCorrection) % 360;
            compassImage.setRotation(-azimuthInDegrees);

            float imageRotation = compassImage.getCurrentRotation();
            imageRotation = imageRotation > 0 ? imageRotation : 360.0f + imageRotation;
            azimuthDegrees.setText(df0.format(imageRotation)+getString(R.string.degree_post));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

