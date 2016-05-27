package mindcar.testing.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Closeable;

import mindcar.testing.R;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.UserData;

import static mindcar.testing.util.UserData.*;

/**
 * Created by Nikolaos-Machairiotis Sasopoulos on 12/05/2016.
 */
public class UserSettings  extends Activity implements View.OnClickListener {

    EditText changeUsername;
    EditText changeUsernameConf;
    EditText changePassword;
    EditText changePassordConf;

    Button usernameOK;
    Button passwordOK;

    public static String loggedUsername;
    public static String loggedPassword;

    //UserData newCredentials = new UserData();

    public void userpass(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        loggedUsername = sharedPref.getString("username", "");
        loggedPassword = sharedPref.getString("password","");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);

        changeUsername = (EditText) findViewById(R.id.changeUsername);
        changePassword = (EditText) findViewById(R.id.changePassword);
        changeUsernameConf = (EditText) findViewById(R.id.changeUsernameConf);
        changePassordConf = (EditText) findViewById(R.id.changePasswordConf);

        usernameOK = (Button) findViewById(R.id.usernameOK);
        passwordOK = (Button) findViewById(R.id.passwordOK);

        usernameOK.setOnClickListener(this);
        passwordOK.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String Username = changeUsername.getText().toString();
        String UsernameConf = changeUsernameConf.getText().toString();
        //newCredentials.setUsername(Username);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        userpass();

        switch (v.getId()) {
            case R.id.usernameOK:


                    if (databaseAccess.checkAvailability(Username) && Username.equals(UsernameConf)) {

                        databaseAccess.updateUsername(loggedUsername, Username);
                        Toast.makeText(getApplicationContext(), "Username successfully changed to " + "" + Username, Toast.LENGTH_SHORT).show();
                        saveNewInfo();
                        startActivity(new Intent(this, UserActivity.class));
                    }
                    else if ((databaseAccess.checkAvailability(Username) && !Username.equals(UsernameConf)) || Username.equals(loggedUsername)){
                        Toast.makeText(getApplicationContext(), "Confirm right username", Toast.LENGTH_SHORT).show();

                    }
                    else

                        Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_SHORT).show();

                break;

            case R.id.passwordOK:

                String Password = changePassword.getText().toString();
                String PasswordConf = changePassordConf.getText().toString();

                if (Password.equals(PasswordConf) && !Password.equals(loggedUsername)) {
                    databaseAccess.updatePassword(loggedUsername, Password);
                    Toast.makeText(getApplicationContext(), "Password successfully changed to " + Password, Toast.LENGTH_LONG).show();
                    saveNewInfo();
                }
                else if (Password.equals(PasswordConf) && Password.equals(loggedUsername)) {
                    Toast.makeText(getApplicationContext(), "Password must not be same as your username ", Toast.LENGTH_LONG).show();

            }
                else
                    Toast.makeText(getApplicationContext(), "Confirm the right password ", Toast.LENGTH_LONG).show();

                break;
        }
        //finish();

    }

   public void saveNewInfo(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", changeUsername.getText().toString());
        editor.putString("password", changePassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "User Info Saved", Toast.LENGTH_LONG).show();
    }

}
