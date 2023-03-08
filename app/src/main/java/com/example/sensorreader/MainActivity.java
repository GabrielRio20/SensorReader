package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

//implement biar tau update an dari sensor
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private Sensor sensorProximity;
    private Sensor sensorLight;
    
    private TextView textSensorLight;
    private TextView textSensorProximity;

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

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        String sensor_error = "No sensor";
        if (sensorLight == null){
            textSensorLight.setText(sensor_error);
        }
        if(sensorProximity == null){
            textSensorProximity.setText(sensor_error);
        }
    }

    @Override
    //register sensor terlebih dahulu
    protected void onStart() {
        super.onStart();
        if (sensorProximity != null){
            sensorManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorLight != null){
            sensorManager.registerListener(this, sensorLight,
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
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}