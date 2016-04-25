package mindcar.testing.util;

import android.os.Message;

import mindcar.testing.objects.Command;
import mindcar.testing.objects.EEGObject;
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
    public static void parseMessage(Message msg, EEGObject eeg) {

       // TGRawMulti rawMulti = (TGRawMulti) msg.obj;
       // eeg.setDelta(rawMulti.ch1);
       // eeg.setTheta(rawMulti.ch2);
        //eeg.setAlpha((rawMulti.ch3 + rawMulti.ch4) / 2);
       // eeg.setBeta((rawMulti.ch5 + rawMulti.ch6) / 2);
       // eeg.setGamma((rawMulti.ch7 + rawMulti.ch8) / 2);


    }

    /**
     * Assigning commands by comparing patterns
     *
     * @param pattern
     * @param car
     */
    public static void assignCommand(Pattern<EEGObject> pattern, SmartCar car) {

        //TODO - Connect with saved patterns
        Pattern<EEGObject> rightPattern = null;
        Pattern<EEGObject> leftPattern = null;
        Pattern<EEGObject> forwardPattern = null;
        Pattern<EEGObject> backwardPattern = null;
        Pattern<EEGObject> stopPattern = null;

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

}