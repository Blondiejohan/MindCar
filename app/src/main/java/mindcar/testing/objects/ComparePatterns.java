package mindcar.testing.objects;

import android.app.Activity;
import android.util.Log;

import mindcar.testing.util.DatabaseAccess;

/**
 * Created by Johan Laptop on 2016-04-18.
 */
public class ComparePatterns extends Activity {
    public double[] compare1;

    String comp2="";

    /**
     * This is the constructor to start comparing.
     *
     * @param input
     */
    public ComparePatterns(Eeg input) {
       this.compare1 = input.arr;

    }




    /**
     * Takes all the variables of the current raw package and compares it to the
     * specified saved pattern from the database. It checks if a wave is within the boundries
     * of the low and high variables of that wave.
     */
    public String compare(DatabaseAccess databaseAccess) {
        databaseAccess.open();
        double[] sLeft = databaseAccess.getDirection("left");
        double[] sRight = databaseAccess.getDirection("right");
        double[] sForward = databaseAccess.getDirection("forward");
        double[] sStop = databaseAccess.getDirection("stop");
        databaseAccess.close();
        LinearRegression linearRegressionLeft = new LinearRegression(compare1,sLeft);
        LinearRegression linearRegressionRight = new LinearRegression(compare1,sRight);
        LinearRegression linearRegressionForward = new LinearRegression(compare1,sForward);
        LinearRegression linearRegressionStop = new LinearRegression(compare1,sStop);
        double left = linearRegressionLeft.R2();
        double right = linearRegressionRight.R2();
        double forward = linearRegressionForward.R2();
        double stop = linearRegressionStop.R2();
        String result = "";
        if (left < right && left < forward && left < stop) {
            result = "Left";
            Log.i("test", ( left)+" Left");
        }
        if (right < left && right < forward && right < stop) {
            result = "Right";
            Log.i("test", ( right)+" Right");
        }
        if (forward < left && forward < right && forward < stop) {
            result = "Forward";
            Log.i("test", ( forward)+" Forward");
        }
        if (stop < left && stop < right && stop < forward) {
            result = "Stop";
            Log.i("test", ( stop)+" Stop");
        }

        return result;
    }
}
