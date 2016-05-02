package mindcar.testing.objects;

import android.app.Activity;
import android.util.Log;

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
    int left;
    int right;
    int forward;
    int stop;

    String comp2="";

    /**
     * This is the constructor to start comparing.
     *
     * @param input
     */
    public ComparePatterns(EEGObject input) {
        this.delta = input.delta;
        this.theta = input.theta;
        this.lowAlpha = input.lowAlpha;
        this.highAlpha = input.highAlpha;
        this.lowBeta = input.lowBeta;
        this.highBeta = input.highBeta;
        this.lowGamma = input.lowGamma;
        this.midGamma = input.highGamma;

        comp2 = "Delta:" + this.delta + " Theta:" + this.theta + " LowAlpha:" + this.lowAlpha + " HighAlpha" +
                this.highAlpha + " LowBeta:" + this.lowBeta + " HighBeta:" + this.highBeta + " lowGamma:" +
                this.lowGamma + " MidGamma:" + this.midGamma + " End";
    }


    public int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    /**
     * Takes all the variables of the current raw package and compares it to the
     * specified saved pattern from the database. It checks if a wave is within the boundries
     * of the low and high variables of that wave.
     */
    public String compare(DatabaseAccess databaseAccess) {
        databaseAccess.open();
        String sLeft = databaseAccess.getDirection("left");
        String sRight = databaseAccess.getDirection("right");
        String sForward = databaseAccess.getDirection("forward");
        String sStop = databaseAccess.getDirection("stop");
        databaseAccess.close();
        left = levenshteinDistance(sLeft, comp2);
        right = levenshteinDistance(sRight, comp2);
        forward = levenshteinDistance(sForward, comp2);
        stop = levenshteinDistance(sStop, comp2);
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
