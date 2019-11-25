package com.example.custombars;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.customizebars.ColorPicker;
import com.example.customizebars.HandlingColorPicker;

import java.util.Objects;

import static com.example.customizebars.HandlingColorPicker.helper;
import static com.example.customizebars.HandlingColorPicker.lastUpdate;
import static com.example.customizebars.HandlingColorPicker.last_x;
import static com.example.customizebars.HandlingColorPicker.last_y;
import static com.example.customizebars.HandlingColorPicker.last_z;

public class NewTEstingActivity extends AppCompatActivity implements SensorListener {

    private RelativeLayout ll;
    private Button b1;

    private SensorManager sensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_testing);

        b1= findViewById(R.id.click2);

        ll = findViewById(R.id.back2);

        HandlingColorPicker.addPreviousValues(ll, getWindow(), Objects.requireNonNull(getSupportActionBar()), NewTEstingActivity.this);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener((SensorListener) this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTEstingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER && helper<1) {
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > 500) {
                    Intent intent = new Intent(NewTEstingActivity.this, ColorPicker.class);
                    intent.putExtra("NextActivity", this.getClass().getName());
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
