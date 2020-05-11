package de.stepchallenge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

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
        }
    }

    private void updateSteps(SensorEvent event){
        realSteps += event.values[0] - this.steps;
        steps = event.values[0];
    }
}
