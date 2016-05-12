package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mindcar.testing.ui.dev.DeveloperActivity;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

public class StartActivity extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText ET_USER_NAME, ET_PASS;
    Button bSIGNUP;
    TextView devView;

    //nikos
    Button userSettings;

    public static String un, pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bLogin = (Button) findViewById(R.id.bLogin);
        bSIGNUP = (Button) findViewById(R.id.bSIGNUP);
        devView = (TextView) findViewById(R.id.devView);

        //nikos
        userSettings = (Button) findViewById(R.id.userSettings);
        userSettings.setOnClickListener(this);

        bLogin.setOnClickListener(this);
        bSIGNUP.setOnClickListener(this);
        devView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.yes);
                    mp.start();
                    startActivity(new Intent(this, BluetoothActivity.class));
                    //startActivity(new Intent(this, oldConnection.class));
                }
                else {

                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                    MediaPlayer mp2 = MediaPlayer.create(this, R.raw.no);
                    mp2.start();
                }
                break;

            case R.id.bSIGNUP:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.devView:
                startActivity(new Intent(this, DeveloperActivity.class));
                break;

            case R.id.userSettings:
                startActivity(new Intent(this, UserSettings.class));
               break;
        }

    }
}
