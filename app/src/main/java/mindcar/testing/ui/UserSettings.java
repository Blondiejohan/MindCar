package mindcar.testing.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mindcar.testing.R;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.UserData;

/**
 * Created by Darkthronen on 12/05/2016.
 */
public class UserSettings  extends Activity implements View.OnClickListener {

    EditText changeUsername;
    EditText changeUsernameConf;
    EditText changePassword;
    EditText changePassordConf;

    Button usernameOK;
    Button passwordOK;

    //UserData newCredentials = new UserData();


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

        switch (v.getId()) {
            case R.id.usernameOK:

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();

                if (databaseAccess.checkAvailability(Username) && Username.equals(UsernameConf)){

                    databaseAccess.updateUsername("nikos", Username);
                    Toast.makeText(getApplicationContext(), "new Changed to " + "" + Username, Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();
                }

                else

                    Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_SHORT).show();

               break;

            case R.id.passwordOK:

                String Password = changePassword.getText().toString();
                String PasswordConf = changePassordConf.getText().toString();
                //newCredentials.setPassword(Password);

                if (Password.equals(PasswordConf))
                    //databaseAccess.updatePassword(oldPassword,Password);

                Toast.makeText(getApplicationContext(), "No code", Toast.LENGTH_SHORT).show();

                break;
                }
        }
    }

