package mindcar.testing.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Connection;
import mindcar.testing.ui.dev.DeveloperActivity;
import mindcar.testing.util.DatabaseAccess;

public class StartActivity extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText ET_USER_NAME, ET_PASSWORD;
    Button bSIGNUP;
    TextView devView;

    //variables from BluetoothActivity
    //public static TGDevice tgDevice = BluetoothActivity.tgDevice;
    //public static BluetoothAdapter theAdapter = BluetoothActivity.theAdapter;
    //public static Connection connect = BluetoothActivity.connect;

    //nikos
    Button userSettings;

    public static String un, pw;
    public static boolean registration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASSWORD = (EditText) findViewById(R.id.ET_PASSWORD);
        bLogin = (Button) findViewById(R.id.bLogin);
        bSIGNUP = (Button) findViewById(R.id.bSIGNUP);
        devView = (TextView) findViewById(R.id.devView);


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
                if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASSWORD.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();

                    //This value will be passed to User activity to fetch the name and photo
                    //of whoever logs in to the app
                    saveInfo();
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.yes);
                    mp.start();
                    //startActivity(new Intent(this, UserActivity.class));
                    registration=false;
                    startActivity(new Intent(this, UserActivity.class));
                    //startActivity(new Intent(this, oldConnection.class));
                    this.finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                    MediaPlayer mp2 = MediaPlayer.create(this, R.raw.no);
                    mp2.start();
                }
                break;

            case R.id.bSIGNUP:
              //  registration=true;
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.devView:
                startActivity(new Intent(this, DeveloperActivity.class));
                this.finish();
                break;

        }

    }

    public void saveInfo(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", ET_USER_NAME.getText().toString());
        editor.putString("password", ET_PASSWORD.getText().toString());
        editor.apply();

        Toast.makeText(this, "User Info Saved", Toast.LENGTH_LONG).show();
    }
}
