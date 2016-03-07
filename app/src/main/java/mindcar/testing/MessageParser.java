package mindcar.testing;

import android.os.Message;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

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
                break;
            case TGDevice.MSG_LOW_BATTERY:
                break;
            case TGDevice.MSG_RAW_MULTI:
                break;
            default:
                break;
        }

    }
}