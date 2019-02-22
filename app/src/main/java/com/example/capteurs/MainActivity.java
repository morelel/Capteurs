package com.example.capteurs;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ListView sensorList;
    SensorManager sensorManager;
    List<Sensor> sensor;
    private boolean color = false;
    private TextView textView;
    Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Exercice 1
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = (ListView) findViewById(R.id.listView);
        sensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorList.setAdapter(new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1, sensor));

        //Exercice 2
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Capteurs du téléphone");
        Sensor temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (temp != null) {

        } else {
            builder.setMessage("Fonctionnalité introuvable");
        }

        AlertDialog alert = builder.create();
        alert.show();

        //Exercice 3
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        textView = (TextView) this.findViewById(R.id.textView);


    }


    @Override
    protected void onPause(){
        sensorManager.unregisterListener(this, accelerometerSensor);
        super.onPause();
    }

    @Override
    protected void onResume(){
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
           return;
        }

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double angle = (Math.atan2(y, Math.sqrt(x*x+z*z))/(Math.PI/100));
        StringBuilder sb = new StringBuilder().append("x:").append(x).append("\n");
        sb.append("y:").append(y).append("\n");
        sb.append("z:").append(z).append("\n");
        sb.append("degree:").append(angle);
        textView.setText(sb.toString());

        if(z < 0){
            textView.setBackgroundColor(Color.parseColor("#5fba7d"));
        }
        else {
            if(z > 5){
                textView.setBackgroundColor(Color.parseColor("#f90404"));
            }
            else {
                textView.setBackgroundColor(Color.parseColor("#4c81db"));
            }
        }
    }



        /*
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);

        lastUpdate = System.currentTimeMillis();
        Sensor accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float[] values = event.values;

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

            //Get current time
            long actualTime = System.currentTimeMillis();
            if (accelationSquareRoot >= 2) {
                if (actualTime - lastUpdate < 200) {
                    return;
                }
                lastUpdate = actualTime;
                Toast.makeText(this, "Device was shaked", Toast.LENGTH_SHORT).show();
                if (color){
                    view.setBackgroundColor(Color.GREEN);
                } else {
                    view.setBackgroundColor(Color.RED);
                }
                color = !color;
            }
        }
    }

    */

}

