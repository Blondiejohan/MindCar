package mindcar.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class Login2 extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText ET_USER_NAME, ET_PASS;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.ET_REG_HERE);


        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
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
                    startActivity(new Intent(this, HomeActivity.class));
                    //startActivity(new Intent(this, Connection.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                break;

            case R.id.ET_REG_HERE:
                startActivity(new Intent(this, Register.class));
                break;
        }

    }
}
