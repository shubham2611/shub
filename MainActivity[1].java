package seth.latestapp;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView ;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import mr.go.sgfilter.SGFilter;
import mr.go.sgfilter.ZeroEliminator;



public class MainActivity extends Activity implements SensorEventListener {

    private TextView X, Y,Z;
    private Button btnStart, btnStop ;
    private SensorManager sensorM;
    private Sensor accelerometer;
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private double acc = 0;
    private int count = 0;
    private int localcount = 0;
    private int state = 0;

    private LineGraphSeries<DataPoint> series;

    private final int nl = 5;
    private final int nr = 5;
    private final int degree = 3;
    private SGFilter sgFilter;

    private double[] data =new double[200];

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
        series = new LineGraphSeries<>();
        series.appendData(new DataPoint(0, 0), true, 200);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(200);

        sgFilter = new SGFilter(nl, nr);
        sgFilter.appendPreprocessor(new ZeroEliminator());
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0] ;
        y = event.values[1] ;
        z = event.values[2] ;
        acc = Math.sqrt(x*x+y*y+z*z);
        X.setText("X: " + x);
        Y.setText("Y: " + y);
        Z.setText("Z: " + z);
        int i=0;

        if (state == 1){
            count++;

            if (count > 200) {
                for (i = 0; i < 199; i++) {
                    data[i] = data[i + 1];
                }
                localcount=200;
                data[199]=acc;
            }
            else{
                localcount=count%200;
                if(count==200) localcount=200;
                data[count-1]=acc;
            }


            if(count>nl+nr) {
                double[] smooth = sgFilter.smooth(data, SGFilter.computeSGCoefficients(nl, nr, degree));
                series.appendData(new DataPoint(count, smooth[localcount - 1 - nl]), true, 200);
            }

            //graph.addSeries(series);
        }
    }
    public void startfn(View v){
        state = 1;

    }
    public void stopfn(View v){

        state = 0;
    }



}