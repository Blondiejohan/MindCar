package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Connected;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.MessageParser;

public class HomeActivity extends Activity implements View.OnClickListener{

    Button bLogout;
    EditText ET_USER_NAME, ET_PASS;
    SmartCar car;
    EEGObject eeg;
    TGDevice tgDevice;
    ProgressBar attentionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);
        tgDevice.start();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:

                startActivity(new Intent(this, Login2.class));
                break;
        }

    }

    /**
     * Handles messages from TGDevice
     */
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what== TGDevice.MSG_ATTENTION){
                Connected.write(CommandUtils.toByteArray(car.getCommands()));
                MessageParser.parseMessage(msg, car, eeg);
                // att.setText(eeg.getAttention() + "");
                attentionBar.setProgress(eeg.getAttention());
            }

        }
    };

}


