package seth.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;


public class MainActivity extends Activity implements SensorEventListener {

    private TextView X, Y, Z;
    private SensorManager sensorM;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorM = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorM.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        X = (TextView) findViewById(R.id.textx);
        Y = (TextView) findViewById(R.id.texty);
        Z = (TextView) findViewById(R.id.textz);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        X.setText("X:" + event.values[0]);
        Y.setText("Y:" + event.values[1]);
        Z.setText("Z:" + event.values[2]);


    }
}