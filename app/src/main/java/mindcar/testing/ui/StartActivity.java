package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
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
    TextView registerLink;
    TextView develperViewLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.ET_REG_HERE);


        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if (databaseAccess.isDeveloper(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString())) {
                    startActivity(new Intent(this, DeveloperActivity.class));
                    this.finish();
                } else if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, BluetoothActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ET_REG_HERE:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }

    }

    public void onResume(){
        super.onResume();
    }
}
