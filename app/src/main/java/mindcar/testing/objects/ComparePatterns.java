package mindcar.testing.objects;

import android.app.Activity;
import android.util.Log;

import org.neuroph.core.NeuralNetwork;

import mindcar.testing.util.DatabaseAccess;

/**
 * This class compares the inmcoming steam of data with the existing neural network.
 * Contributors: Johan and Mattias.
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
        neuralNetwork.setInput(compare1);
        neuralNetwork.calculate();
        double[] res = neuralNetwork.getOutput();
        String result = "w";
        Log.i("res", res[0] + " " + res[1] + " " + res[2] + " " + res[3]);

        if (res[0] == 1 && res[1] == 0 && res[2] == 0 && res[3] == 0) {
                result = "l";
        } else if (res[0] == 0 && res[1] == 1 && res[2] == 0 && res[3] == 0) {
                result = "r";
        } else if (res[0] == 0 && res[1] == 0 && res[2] == 1 && res[3] == 0) {
                result = "f";
        } else if (res[0] == 0 && res[1] == 0 && res[2] == 0 && res[3] == 1) {
                result = "s";
        }
        return result;

    }
}
