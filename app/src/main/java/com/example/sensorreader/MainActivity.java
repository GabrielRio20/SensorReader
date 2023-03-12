package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

//implement biar tau update an dari sensor
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private Sensor sensorLight;
    private Sensor sensorProximity;
    private Sensor sensorTemprature;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;

    private TextView textSensorLight;
    private TextView textSensorProximity;
    private TextView textSensorTemprature;
    private TextView textSensorAccelerometer;
    private TextView textSensorGyroscope;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //list utk nampung sensor
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //utk menyimpan data sensor
        StringBuilder sensorText = new StringBuilder();

        //menampilkan list sensor
        for(Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).
                    append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        textSensorLight = findViewById(R.id.label_light);
        textSensorProximity = findViewById(R.id.label_proximity);
        textSensorTemprature = findViewById(R.id.label_ambient_temp);
        textSensorAccelerometer = findViewById(R.id.label_accelerometer);
        textSensorGyroscope = findViewById(R.id.label_gyroscope);

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorTemprature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        String sensor_error = "No sensor";
        if(sensorLight == null){
            textSensorLight.setText(sensor_error);
        }
        if(sensorProximity == null){
            textSensorProximity.setText(sensor_error);
        }
        if(sensorTemprature == null){
            textSensorTemprature.setText(sensor_error);
        }
        if(sensorAccelerometer == null){
            textSensorAccelerometer.setText(sensor_error);
        }
        if(sensorGyroscope == null){
            textSensorGyroscope.setText(sensor_error);
        }
    }

    @Override
    //register sensor terlebih dahulu
    protected void onStart() {
        super.onStart();
        if (sensorLight != null){
            sensorManager.registerListener(this, sensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorProximity != null){
            sensorManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorTemprature != null){
            sensorManager.registerListener(this, sensorTemprature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorAccelerometer != null){
            sensorManager.registerListener(this, sensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorGyroscope != null){
            sensorManager.registerListener(this, sensorGyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    //di unregister
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //ambil data dari sensor eventnya, ambil perubahan dimana, light atau prox
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                textSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue));
                changeBgColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                textSensorTemprature.setText(String.format("Ambient Temperature sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_ACCELEROMETER:
                textSensorAccelerometer.setText(String.format("Accelerometer sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_GYROSCOPE:
                textSensorGyroscope.setText(String.format("Relative gyroscope sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void changeBgColor(float value){
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        if (value > 20 && value <= 10000){
            layout.setBackgroundColor(Color.CYAN);
        }
        else if (value > 10000 && value <= 30000){
            layout.setBackgroundColor(Color.YELLOW);
        }
        else if (value > 30000){
            layout.setBackgroundColor(Color.RED);
        }
    }
}