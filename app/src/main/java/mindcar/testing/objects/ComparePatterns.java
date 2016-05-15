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

    String comp2="";

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

        neuralNetwork.setInput(compare1);
        neuralNetwork.calculate();
        double[] res = neuralNetwork.getOutput();
        String result = "";
        double line = 0;
        if(res[0] < 1.5){
            line = linearRegressionLeft.R2();
            Log.i("Linear ", line + "");
            if(line > 0.007) {
                result = "Left";
            } else {
                result = "";
            }
        } else if (res[0] > 1.5 && res[0] < 2.5){
            line = linearRegressionRight.R2();
            Log.i("Linear ", line + "");
            if(line > 0.007) {
                result = "Right";
            } else {
                result = "";
            }
        } else if (res[0] > 2.5 && res[0] < 3.5){
            line = linearRegressionForward.R2();
            Log.i("Linear ", line + "");
            if(line > 0.007) {
                result = "Forward";
            } else {
                result = "";
            }
        } else {
            line = linearRegressionStop.R2();
            Log.i("Linear ", line + "");
            if(line > 0.007) {
                result = "Stop";
            } else {
                result = "";
            }
        }

        Log.i("Line ", line + "");
        return result;
    }
}
