package com.example.customizebars;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HandlingColorPicker extends AppCompatActivity {

    public static long lastUpdate = 0;
    public static float last_x = 0;
    public static float last_y = 0;
    public static float last_z = 0;
    private static final String PREFERENCE_FILE_KEY = "Colorpreferences";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPref;
    private static String descriptionStatusBar = "StatusBar";
    private static String descriptionActionBar = "ActionBar";
    private static String descriptionBackground = "Background";
    private static int statusbar;
    private static int actionbar;
    private static int background;
    private static int defaultStatusbar = Color.parseColor("#6e5e34");
    private static int defaultActionbar = Color.parseColor("#30270f");
    private static int defaultbackground = Color.parseColor("#b8b7b6");
    private static String myActivityName;
    public static int helper = 0;

    public static void addPreviousValues(View view , Window window, ActionBar actionBar, Context context){
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, context.MODE_PRIVATE);
        editor = sharedPref.edit();
        statusbar = sharedPref.getInt(descriptionStatusBar, defaultStatusbar);
        actionbar = sharedPref.getInt(descriptionActionBar, defaultActionbar);
        background = sharedPref.getInt(descriptionBackground, defaultbackground);

        actionBar.setBackgroundDrawable(new ColorDrawable(actionbar));

        view.setBackgroundColor(background);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusbar);
    }
}
