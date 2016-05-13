package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import mindcar.testing.R;
import mindcar.testing.ui.StartActivity;

public class Intro extends Activity {

    private final int SPLASH_DISPLAY_DURATION = 2000;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_intro);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(Intro.this,BluetoothActivity.class);
                Intro.this.startActivity(mainIntent);
                Intro.this.finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }}