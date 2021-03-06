package mindcar.testing.util;

import android.os.Message;

import mindcar.testing.objects.Eeg;

/**
 * This class help with assigning messages to containers
 * Contributors: Mattias, Sarah and Johan.
 */
public class MessageParser {

    /**
     * Assign values from raw data to the correct eeg frequency
     * @param msg
     * @param eeg
     */
    public static void parseRawData(Message msg, Eeg eeg) {
        int value = msg.arg1;
        if (value > 0 && value <= 3) {
            eeg.setDelta(value);
        } else if (value >= 4 && value <= 7) {
            eeg.setTheta(value);
        } else if (value >= 8 && value <= 9) {
            eeg.setLowAlpha(value);
        } else if (value >= 10 && value <= 12) {
            eeg.setHighAlpha(value);
        } else if (value >= 13 && value <= 17) {
            eeg.setLowBeta(value);
        } else if (value >= 18 && value <= 30) {
            eeg.setHighBeta(value);
        } else if (value >= 31 && value <= 40) {
            eeg.setLowGamma(value);
        } else if (value >= 41 && value <= 50) {
            eeg.setHighGamma(value);
        } else {
            ;
        }

    }

    /**
     * Creates a String from an array of doubles
     * @param doubles
     * @return
     */
    public static String toString(double[] doubles){
        StringBuilder str = new StringBuilder();
        for(double d: doubles){
            str.append(d + " ");
        }
        return str.toString();
    }

}