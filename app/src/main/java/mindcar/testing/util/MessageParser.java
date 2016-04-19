package mindcar.testing.util;

import android.os.Message;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGRawMulti;

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
     * @param msg
     * @param eeg
     */
    public static void parseMessage(Message msg, Eeg eeg){
        if(msg.what == TGDevice.MSG_RAW_MULTI){
            TGRawMulti rawMulti = (TGRawMulti) msg.obj;
            eeg.setDelta(rawMulti.ch1);
            eeg.setTheta(rawMulti.ch2);
            eeg.setAlpha((rawMulti.ch3 + rawMulti.ch4) /2);
            eeg.setBeta((rawMulti.ch5 + rawMulti.ch6) /2);
            eeg.setGamma((rawMulti.ch7 + rawMulti.ch8) /2);

        }
    }

    /**
     * assigning commands by comparing patterns
      * @param pattern
     * @param car
     */
    public static void assignCommand(Pattern<Eeg> pattern, SmartCar car){
        Pattern<Eeg> rightPattern = null;
        Pattern<Eeg> leftPattern = null;
        Pattern<Eeg> forwardPattern = null;
        Pattern<Eeg> backwardPattern= null;
        Pattern<Eeg> stopPattern= null;

        if(pattern.equals(rightPattern)){
            car.setCommand(Command.RIGHT);
        } else if (pattern.equals(leftPattern)){
            car.setCommand(Command.LEFT);
        }else if (pattern.equals(forwardPattern)){
            car.setCommand(Command.FORWARD);
        }else if(pattern.equals(backwardPattern)){
            car.setCommand(Command.BACKWARD);
        }else if(pattern.equals(stopPattern)){
            car.setCommand(Command.STOP);
        }
    }

}