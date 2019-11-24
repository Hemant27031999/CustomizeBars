package com.example.customizebars;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class ColorPicker extends AppCompatActivity {

    private com.larswerkman.holocolorpicker.ColorPicker picker;
    private ValueBar valueBar;
    private View first;
    private OpacityBar opacityBar;
    private SaturationBar saturationBar;
    private View second;
    private View third;
    private LinearLayout lm;
    private final String PREFERENCE_FILE_KEY = "Colorpreferences";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPref;
    private String descriptionStatusBar = "StatusBar";
    private String descriptionActionBar = "ActionBar";
    private String descriptionBackground = "Background";
    private int defaultStatusbar = 0;
    private int defaultActionbar = 0;
    private int defaultbackground = 0;
    private int statusbar;
    private int actionbar;
    private String myActivityName;
    private int background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker);

        picker = findViewById(R.id.picker);
        valueBar = (ValueBar) findViewById(R.id.valuebar);
        opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        picker.addValueBar(valueBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        lm = findViewById(R.id.mainback);
        myActivityName = getIntent().getStringExtra("NextActivity");

        sharedPref = ColorPicker.this.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        statusbar = sharedPref.getInt(descriptionStatusBar, defaultStatusbar);
        actionbar = sharedPref.getInt(descriptionActionBar, defaultActionbar);
        background = sharedPref.getInt(descriptionBackground, defaultbackground);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionbar));
        lm.setBackgroundColor(background);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusbar);

        first = findViewById(R.id.statusbar);
        second = findViewById(R.id.actionbar);
        third = findViewById(R.id.background);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setOldCenterColor(picker.getColor());
                first.setBackgroundColor(picker.getColor());
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(picker.getColor());
                Toast.makeText(ColorPicker.this, " "+ Color.red(picker.getColor())+" "+Color.green(picker.getColor())+" "+Color.blue(picker.getColor())+" ", Toast.LENGTH_SHORT).show();
                editor.putInt(descriptionStatusBar, picker.getColor());
                editor.apply();
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setOldCenterColor(picker.getColor());
                second.setBackgroundColor(picker.getColor());
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(picker.getColor()));
                editor.putInt(descriptionActionBar, picker.getColor());
                editor.apply();
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setOldCenterColor(picker.getColor());
                third.setBackgroundColor(picker.getColor());
                lm.setBackgroundColor(picker.getColor());
                editor.putInt(descriptionBackground, picker.getColor());
                editor.apply();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        try {
            Class<?> c = Class.forName(myActivityName);
            Intent setIntent = new Intent(ColorPicker.this, c);
            startActivity(setIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }
}
