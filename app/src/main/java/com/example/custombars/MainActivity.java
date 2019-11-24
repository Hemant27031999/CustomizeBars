package com.example.custombars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customizebars.ColorPicker;

public class MainActivity extends AppCompatActivity implements SensorListener {

    private LinearLayout ll;
    private SensorManager sensorMgr;
    private final int SHAKE_THRESHOLD = 500;
    long lastUpdate = 0;
    float last_x = 0;
    float last_y = 0;
    float last_z = 0;
    private final String PREFERENCE_FILE_KEY = "Colorpreferences";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPref;
    private String descriptionStatusBar = "StatusBar";
    private String descriptionActionBar = "ActionBar";
    private String descriptionBackground = "Background";
    private int statusbar;
    private int actionbar;
    private int background;
    private int defaultStatusbar = 0;
    private int defaultActionbar = 0;
    private int defaultbackground = 0;
    private String myActivityName;
    public int helper = 0;

    private TextView textv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sharedPref = getSharedPreferences(PREFERENCE_FILE_KEY, this.MODE_PRIVATE);
        editor = sharedPref.edit();
        statusbar = sharedPref.getInt(descriptionStatusBar, defaultStatusbar);
        actionbar = sharedPref.getInt(descriptionActionBar, defaultActionbar);
        background = sharedPref.getInt(descriptionBackground, defaultbackground);
        ll = findViewById(R.id.testb);
        textv = findViewById(R.id.textme);
        String s = this.getClass().getName();
        myActivityName = s;
        textv.setText(s);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionbar));
        ll.setBackgroundColor(background);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusbar);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener((SensorListener) this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER && helper<1) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ColorPicker.class);
                    intent.putExtra("NextActivity", myActivityName);
                    startActivity(intent);
                    finish();
                    helper++;
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(int i, int i1) {

    }
}
