package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import mindcar.testing.R;

//Sanja
//This class loads the splash screen and switches over to BluetoothActivity after 2 seconds.
public class Intro extends Activity {
    private final int SPLASH_DISPLAY_DURATION = 2000;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Intro.this,StartActivity.class);
                Intro.this.startActivity(mainIntent);
                Intro.this.finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }}