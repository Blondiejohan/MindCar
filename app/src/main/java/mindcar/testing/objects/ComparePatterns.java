package mindcar.testing.objects;

import android.app.Activity;
import android.util.Log;

import org.neuroph.core.NeuralNetwork;

import mindcar.testing.util.DatabaseAccess;

/**
 * Created by Johan Laptop on 2016-04-18.
 */
public class ComparePatterns extends Activity {
    public double[] compare1;
    private NeuralNetwork neuralNetwork;

    String comp2 = "";

    /**
     * This is the constructor to start comparing.
     *
     * @param input
     */
    public ComparePatterns(double[] input, NeuralNetwork neuralNetwork) {
        this.compare1 = input;
        this.neuralNetwork = neuralNetwork;

    }


    /**
     * Takes all the variables of the current raw package and compares it to the
     * specified saved pattern from the database. It checks if a wave is within the boundries
     * of the low and high variables of that wave.
     */
    public String compare(DatabaseAccess databaseAccess) {
//        databaseAccess.open();
//        double[] sLeft = databaseAccess.getDirection("left");
//        double[] sRight = databaseAccess.getDirection("right");
//        double[] sForward = databaseAccess.getDirection("forward");
//        double[] sStop = databaseAccess.getDirection("stop");
//        databaseAccess.close();

        double[] sLeft = {1, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8};
        double[] sRight = {2, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9};
        double[] sForward = {3, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10};
        double[] sStop = {4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11};


        LinearRegression linearRegressionLeft = new LinearRegression(compare1, sLeft);
        LinearRegression linearRegressionRight = new LinearRegression(compare1, sRight);
        LinearRegression linearRegressionForward = new LinearRegression(compare1, sForward);
        LinearRegression linearRegressionStop = new LinearRegression(compare1, sStop);

        neuralNetwork.setInput(compare1);
        neuralNetwork.calculate();
        double[] res = neuralNetwork.getOutput();
        String result = "";
        Log.i("res", res[0] + " " + res[1] + " " + res[2] + " " + res[3]);
        double line = 0;
        double limit = 0.008;

        if (res[0] > 0.9 && res[0] <= 1.0) {
            line = linearRegressionLeft.R2();
            Log.i("Linear ", line + "");
            if (line > limit) {
                result = "Left";
            } else {
                result = "";
            }
        } else if (res[1] > 0.9 && res[1] <= 1) {
            line = linearRegressionRight.R2();
            Log.i("Linear ", line + "");
            if (line > limit) {
                result = "Right";
            } else {
                result = "";
            }
        } else if (res[2] > 0.9 && res[2] <= 1) {
            line = linearRegressionForward.R2();
            Log.i("Linear ", line + "");
            if (line > limit) {
                result = "Forward";
            } else {
                result = "";
            }
        } else if (res[3] > 0.9 && res[3] <= 1) {
            line = linearRegressionStop.R2();
            Log.i("Linear ", line + "");
            if (line > limit) {
                result = "Stop";
            } else {
                result = "";
            }
        }
        Log.i("Line ", line + "");
        return result;

    }
}
