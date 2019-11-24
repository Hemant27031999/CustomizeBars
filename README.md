# CustomizeBars
CustomizeBars is a simple android library which helps you to choose your app theme. You can change color of status bar, action bar and background by using it.
</br>
</br>
## How it works
Shake the phone to open the theme editing activity
![Shaking the phone](https://github.com/Hemant27031999/CustomizeBars/blob/master/display/Main1.jpg)
Choose a sweet theme by adjusting color of Status bar, Action Bar and Background as per your liking
</br>
</br>

Theme 1             |  Theme2          |  Theme 3      |   Theme 4        
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------
![](https://github.com/Hemant27031999/CustomizeBars/blob/master/display/side1.jpeg)  |  ![](https://github.com/Hemant27031999/CustomizeBars/blob/master/display/side2.jpeg) |  ![](https://github.com/Hemant27031999/CustomizeBars/blob/master/display/side3.jpeg) |  ![](https://github.com/Hemant27031999/CustomizeBars/blob/master/display/side4.jpeg)

</br>
Finally that theme will be applied to all those activities for which you choose to implement it
</br>
</br>
</br>
<p align="center">
<img src="https://github.com/Hemant27031999/CustomizeBars/blob/master/display/main2.jpeg" width="250" height="440" />
</p>
</br>
</br>

## Adding it to your App


#### Adding Dependency
Add the following Dependency to your app gradle file

```java
dependencies {
    ...
    implementation 'com.github.Hemant27031999:CustomizeBars:0.1.3'
}
```
</br>

#### Adding code in required Activities
Add the following code in **all those activities in which you want to have your chosen theme implemented.**
<br/>
Initialize the following variables inside the class :
```java
private ScrollView {YOUR BASE LAYOUT};
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
private int defaultStatusbar = Color.parseColor("#6e5e34");
private int defaultActionbar = Color.parseColor("#30270f");
private int defaultbackground = Color.parseColor("#b8b7b6");
private String myActivityName;
public int helper = 0;
```
The view initialized in the first line is the base view of this activity.</br></br>
Make the class to implement SensorListener
```java
public class MainActivity extends AppCompatActivity implements SensorListener {
...
}
```
</br></br>
Implements the required methods of SensorListener inside activity 
```java
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
```
</br></br>
and finally add the following code to your onCreate method
```java
{YOUR BASE LAYOUT} = findViewById(R.id.{BASE_LAYOUT_ID});
sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
    sharedPref = getSharedPreferences(PREFERENCE_FILE_KEY, this.MODE_PRIVATE);
    editor = sharedPref.edit();
    statusbar = sharedPref.getInt(descriptionStatusBar, defaultStatusbar);
    actionbar = sharedPref.getInt(descriptionActionBar, defaultActionbar);
    background = sharedPref.getInt(descriptionBackground, defaultbackground);
    String s = this.getClass().getName();
    myActivityName = s;

    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionbar));
    {YOUR BASE LAYOUT}.setBackgroundColor(background);
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(statusbar);

    sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
    sensorMgr.registerListener((SensorListener) this,
            SensorManager.SENSOR_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_GAME);
```

</br>

## Bugs or Issues
If you find any bug or issue, feel free to report it. Your suggestions and PRs are also welcomed. 
