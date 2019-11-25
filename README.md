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
Declare the following variables inside the class :
```java
private ScrollView {YOUR BASE LAYOUT};
private SensorManager sensorMgr;
```
</br>
The view initialized in the first line is the base view of this activity. It can be any base/background view like Linear Layout, Relative Layout, Scroll View etc. The background color which you will choose will be added to this view. The second variable is a SensorManager's object which access the device's sensors.

</br>
</br>

Make the class to implement SensorListener

</br>

```java
public class MainActivity extends AppCompatActivity implements SensorListener {
...
}
```

</br>
</br>

Implement the required methods of SensorListener inside the activity 
</br>
```java
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
                
                    // The Intent which take you to the ColorPicker activity
                    Intent intent = new Intent(MainActivity.this, ColorPicker.class);
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
```

</br>
</br>

Initialize the declared variables and call the method to add your previously selected theme to your activity. Add the following code to your onCreate method.
</br>
```java
{YOUR BASE LAYOUT} = findViewById(R.id.{BASE_LAYOUT_ID});
sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
// this method reads the old theme from shared preferences and apply them
HandlingColorPicker.addPreviousValues(ll, getWindow(), Objects.requireNonNull(getSupportActionBar()), MainActivity.this);
```
</br>
Now open the app, go to the activity, shake your phone and BOOM

</br>
</br>

## Don't want to have SensorListener ?

</br>

If you have your SensorListener implemented for some other purpose or if you want to open the theme selecting activity by some other mean, you are free to do so. Follow the given procedure :

</br>

Declare your base view inside the required class :

```java
private ScrollView {YOUR BASE LAYOUT};
```

</br>

Initialize it inside your onCreate method and call the method to add your previously selected theme to your activity.

</br>

```java
{YOUR BASE LAYOUT} = findViewById(R.id.{BASE_LAYOUT_ID});
// this method reads the old theme from shared preferences and apply them
HandlingColorPicker.addPreviousValues(ll, getWindow(), Objects.requireNonNull(getSupportActionBar()), MainActivity.this);
```

</br>

Call the Intent to ColorPicker Activity in whatsoever way you want. For eg, if you want to open it upon a button click, do it as follow :

</br>

```java
{BUTTON}.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The Intent which take you to the ColorPicker activity
                Intent intent = new Intent(MainActivity.this, ColorPicker.class);
                intent.putExtra("NextActivity", this.getClass().getName());
                startActivity(intent);
                finish();
            }
        });
```
</br>
And there you go :)
</br>
</br>

## Bugs or Issues
If you find any bug or issue, feel free to report it. Your suggestions and PRs are also welcomed. 
