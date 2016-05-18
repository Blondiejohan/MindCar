package mindcar.testing.util;

import android.os.Message;

import mindcar.testing.objects.Command;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;

/**
 * this class parses msgs and updates the smart car object
 * Created by Mattias & Sarah on 3/7/16.
 */
public class MessageParser {

    /**
     * Saves incomming raw values to a eeg object.
     *
     * @param msg
     * @param eeg
     */
    public static void parseMessage(Message msg, Eeg eeg) {

    }

    /**
     * Assigning commands by comparing patterns
     *
     * @param pattern
     * @param car
     */
    public static void assignCommand(Pattern pattern, SmartCar car) {

        //TODO - Connect with saved patterns
        Pattern rightPattern = null;
        Pattern leftPattern = null;
        Pattern forwardPattern = null;
        Pattern backwardPattern = null;
        Pattern stopPattern = null;

        if (pattern.equals(rightPattern)) {
            car.setCommand(Command.r);
        } else if (pattern.equals(leftPattern)) {
            car.setCommand(Command.l);
        } else if (pattern.equals(forwardPattern)) {
            car.setCommand(Command.f);
        } else if (pattern.equals(backwardPattern)) {
            car.setCommand(Command.s);
        } else if (pattern.equals(stopPattern)) {
            car.setCommand(Command.STOP);
        }
    }


    /**
     * Assign values from raw data to the correct eeg frequency
     * @param msg
     * @param eeg
     */
    public static void parseRawData(Message msg, Eeg eeg){
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

}