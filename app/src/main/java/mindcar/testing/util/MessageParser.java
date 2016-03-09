package mindcar.testing.util;

import android.os.Message;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;

/**
 * Created by sid on 3/7/16.
 */
public class MessageParser {
    public static void parseMessage(Message msg, SmartCar car, EEGObject eeg){
        switch (msg.what) {
            case TGDevice.MSG_STATE_CHANGE:
                break;
            case TGDevice.MSG_POOR_SIGNAL:
                break;
            case TGDevice.MSG_RAW_DATA:
                break;
            case TGDevice.MSG_HEART_RATE:
                break;
            case TGDevice.MSG_ATTENTION:
                //att = msg.arg1;
                eeg.setAttention(msg.arg1);
                car.setCommand(eeg);
                break;
            case TGDevice.MSG_MEDITATION:
                eeg.setMeditation(msg.arg1);
                break;
            case TGDevice.MSG_BLINK:
                eeg.setBlink(msg.arg1);
                break;
            case TGDevice.MSG_RAW_COUNT:
                //tv.append("Raw Count: " + msg.arg1 + "\n");
                parseEEG(TGDevice.MSG_EEG_POWER, eeg);
                break;
            case TGDevice.MSG_LOW_BATTERY:
                break;
            case TGDevice.MSG_EEG_POWER:
                break;
            default:
                break;
        }
    }

    public static void parseEEG(int rawEEG, EEGObject eeg){
        if(rawEEG >= 0 && rawEEG <= 3){
            eeg.setDelta(rawEEG);
        }
    }
}