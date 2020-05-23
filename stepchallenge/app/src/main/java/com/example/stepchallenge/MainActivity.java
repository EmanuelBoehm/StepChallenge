package com.example.stepchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private StepListener stepListener;
    private SensorManager sensorManager;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepListener = new StepListener();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        text = findViewById(R.id.homeText);

        if (sensor != null) {
            sensorManager.registerListener(stepListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        }

    }
    public class StepListener implements SensorEventListener {
        private float steps;
        private float realSteps = 0;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
                updateSteps(event);
                text.setText("Your steps: " + event.values[0]);
            }

        }

        private void updateSteps(SensorEvent event){
            realSteps += event.values[0] - this.steps;
            steps = event.values[0];
        }
        public int get_steps(){
            return (int)steps;
        }



    }

}
