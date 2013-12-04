package com.example.probando;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.SoundPool;

public class MainActivity extends Activity implements SensorEventListener {
    private long last_update = 0;
    private float prevX = 0, prevY = 0, prevZ = 0;
    private float curX = 0, curY = 0, curZ = 0;
    public SoundPool sp;
    public int micancion;        
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sp = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        micancion= sp.load(this,R.raw.whip2,1);
        ((TextView) findViewById(R.id.textView1)).setText("");
        ((TextView) findViewById(R.id.textView2)).setText("");
        ((TextView) findViewById(R.id.textView3)).setText("");
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);        
        if (sensors.size() > 0) {
                sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }
    
    @Override
    protected void onStop() {
            SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);            
        sm.unregisterListener(this);
        super.onStop();
    }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
                long current_time = event.timestamp;
            
            curX = event.values[0];
            curY = event.values[1];
            curZ = event.values[2];
            
            if (prevX == 0 && prevY == 0 && prevZ == 0) {
                last_update = current_time;
                prevX = curX;
                prevY = curY;
                prevZ = curZ;
            }

            long time_difference = current_time - last_update;
            if (time_difference > 0) {

                
                prevX = curX;
                prevY = curY;
                prevZ = curZ;
                last_update = current_time;
                
                
                	if (prevY > 13 && prevZ > 13){
                            sp.play(micancion, 1, 1, 0, 0, 1);
                            }
                        
                
                	if (prevY < -13 && prevZ < -13){
                            sp.play(micancion, 1, 1, 0, 0, 1);
                            }
                        
            		
                
                }
            
            
           
        CheckBox cheky = (CheckBox) findViewById(R.id.cheky);
        if (cheky.isChecked()==true){
        	((TextView) findViewById(R.id.textView1)).setText("Acelerometro X: " + curX);
            ((TextView) findViewById(R.id.textView2)).setText("Acelerometro Y: " + curY);
            ((TextView) findViewById(R.id.textView3)).setText("Acelerometro Z: " + curZ);
        }
            
        }
                
        }}    
    
