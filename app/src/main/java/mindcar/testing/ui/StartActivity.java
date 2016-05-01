package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

public class StartActivity extends Activity implements View.OnClickListener {

    Button bLogin, bSINGUP;
    TextView TV_USER_NAME, TV_PASSWORD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TV_USER_NAME = (TextView) findViewById(R.id.TV_USER_NAME);
        TV_PASSWORD = (TextView) findViewById(R.id.TV_PASSWORD);
        bLogin = (Button) findViewById(R.id.bLogin);
        bSINGUP = (Button) findViewById(R.id.bSIGNUP);


        bLogin.setOnClickListener(this);
        bSINGUP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if (databaseAccess.checkUser(TV_USER_NAME.getText().toString(), TV_PASSWORD.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();
                    startActivity(new Intent(this, BluetoothActivity.class));
                    //startActivity(new Intent(this, Connection.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                break;

            case R.id.bSIGNUP:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }

    }
}
