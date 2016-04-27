package mindcar.testing.objects;

import android.app.Activity;
import android.util.Log;

import com.neurosky.thinkgear.TGEegPower;

import mindcar.testing.util.DatabaseAccess;
/**
 * Created by Johan Laptop on 2016-04-18.
 */
public class ComparePatterns extends Activity {
    int delta;
    int theta;
    int lowAlpha;
    int highAlpha;
    int lowBeta;
    int highBeta;
    int lowGamma;
    int midGamma;
    int bottomDelta;
    int bottomTheta;
    int bottomLowAlpha;
    int bottomHighAlpha;
    int bottomLowBeta;
    int bottomHighBeta;
    int bottomLowGamma;
    int bottomMidGamma;
    int topDelta;
    int topTheta;
    int topLowAlpha;
    int topHighAlpha;
    int topLowBeta;
    int topHighBeta;
    int topLowGamma;
    int topMidGamma;

    /**
     * This is the constructor to start comparing.
     *
     * @param input
     */
    public ComparePatterns(TGEegPower input) {
        this.delta = input.delta;
        this.theta = input.theta;
        this.lowAlpha = input.lowAlpha;
        this.highAlpha = input.highAlpha;
        this.lowBeta = input.lowBeta;
        this.highBeta = input.highBeta;
        this.lowGamma = input.lowGamma;
        this.midGamma = input.midGamma;
    }


    /**
     * Takes all the variables of the current raw package and compares it to the
     * specified saved pattern from the database. It checks if a wave is within the boundries
     * of the low and high variables of that wave.
     *
     * @param in
     * @return
     */
    public Boolean compare(String in, DatabaseAccess databaseAccess) {
        Log.i("started comparing", in);
        databaseAccess.open();
        String inDirection = databaseAccess.getDirection(in);
        databaseAccess.close();

        Log.i("data ", inDirection);
        StringBuilder result = new StringBuilder("");
        this.bottomDelta = Integer.parseInt(inDirection.substring(inDirection.indexOf('a') + 1, inDirection.indexOf('b')));
        this.topDelta = Integer.parseInt(inDirection.substring(inDirection.indexOf('b') + 1, inDirection.indexOf('c')));

        this.bottomTheta = Integer.parseInt(inDirection.substring(inDirection.indexOf('c') + 1, inDirection.indexOf('d')));
        this.topTheta = Integer.parseInt(inDirection.substring(inDirection.indexOf('d') + 1, inDirection.indexOf('e')));

        this.bottomLowAlpha = Integer.parseInt(inDirection.substring(inDirection.indexOf('e') + 1, inDirection.indexOf('f')));
        this.topLowAlpha = Integer.parseInt(inDirection.substring(inDirection.indexOf('f') + 1, inDirection.indexOf('g')));

        this.bottomHighAlpha = Integer.parseInt(inDirection.substring(inDirection.indexOf('g') + 1, inDirection.indexOf('h')));
        this.topHighAlpha = Integer.parseInt(inDirection.substring(inDirection.indexOf('h') + 1, inDirection.indexOf('i')));

        this.bottomLowBeta = Integer.parseInt(inDirection.substring(inDirection.indexOf('i') + 1, inDirection.indexOf('j')));
        this.topLowBeta = Integer.parseInt(inDirection.substring(inDirection.indexOf('j') + 1, inDirection.indexOf('k')));

        this.bottomHighBeta = Integer.parseInt(inDirection.substring(inDirection.indexOf('k') + 1, inDirection.indexOf('l')));
        this.topHighBeta = Integer.parseInt(inDirection.substring(inDirection.indexOf('l') + 1, inDirection.indexOf('m')));

        this.bottomLowGamma = Integer.parseInt(inDirection.substring(inDirection.indexOf('m') + 1, inDirection.indexOf('n')));
        this.topLowGamma = Integer.parseInt(inDirection.substring(inDirection.indexOf('n') + 1, inDirection.indexOf('o')));

        this.bottomMidGamma = Integer.parseInt(inDirection.substring(inDirection.indexOf('o') + 1, inDirection.indexOf('p')));
        this.topMidGamma = Integer.parseInt(inDirection.substring(inDirection.indexOf('p') + 1, inDirection.indexOf('q')));

        //delta
        if (delta > bottomDelta && delta < topDelta) {
            result.append('y');
        } else {
            result.append('n');
        }

        //theta
        if (theta > bottomTheta && theta < topTheta) {
            result.append('y');
        } else {
            result.append('n');
        }

        //lowAlpha
        if (lowAlpha > bottomLowAlpha && lowAlpha < topLowAlpha) {
            result.append('y');
        } else {
            result.append('n');
        }

        //highAlpha
        if (highAlpha > bottomHighAlpha && highAlpha < topHighAlpha) {
            result.append('y');
        } else {
            result.append('n');
        }

        //lowBeta
        if (lowBeta > bottomLowBeta && lowBeta < topLowBeta) {
            result.append('y');
        } else {
            result.append('n');
        }

        //highBeta
        if (highBeta > bottomHighBeta && highBeta < topHighBeta) {
            result.append('y');
        } else {
            result.append('n');
        }

        //lowGamma
        if (lowGamma > bottomLowGamma && lowGamma < topLowGamma) {
            result.append('y');
        } else {
            result.append('n');
        }

        //midGamma
        if (midGamma > bottomMidGamma && midGamma < topMidGamma) {
            result.append('y');
        } else {
            result.append('n');
        }

        return result.toString().contains("n");
    }
}
