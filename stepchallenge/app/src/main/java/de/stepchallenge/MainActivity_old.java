package de.stepchallenge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.StringTokenizer;

public class MainActivity_old extends AppCompatActivity {
    private SensorManager sensorManager;
    private TextView stepText;
    private TextView sensorText;
    private StepListener stepListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepListener = new StepListener();
        sensorText = findViewById(R.id.sensorListing);
        stepText = findViewById(R.id.steptextblock);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        initGUI();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            sensorManager.registerListener(stepListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        }
    }
    private void initGUI(){
        String str = "your steps: " + stepListener.getSteps();
        stepText.setText(str);
        printingSensors();

    }
    public void printingSensors(){
        List <Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorString = new StringBuilder();
        sensorString.append("Your bratforce Sensors on your phone: \n");

        for(Object str: sensorList) {
            StringTokenizer tok = new StringTokenizer(str.toString(),",");
            String toAppend = tok.nextToken();
            sensorString.append(toAppend.substring(13)).append("\n");
        }
        sensorText.setText(sensorString.toString());

    }


     public class StepListener implements SensorEventListener {
        private float steps;
        private float realSteps = 0;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
                updateSteps(event);
                stepText.setText(String.valueOf(realSteps));
            }
        }
        private void updateSteps(SensorEvent event){
            realSteps += event.values[0] - this.steps;
            steps = event.values[0];
        }
        public int getSteps(){
            return (int) realSteps;
        }

    }
}
