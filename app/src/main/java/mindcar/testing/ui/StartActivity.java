package mindcar.testing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

public class StartActivity extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText ET_USER_NAME, ET_PASS;
    TextView registerLink;

    public static String un, pw;


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
        switch (v.getId()){
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();

                    un = ET_USER_NAME.getText().toString();
                    pw = ET_PASS.getText().toString();
                    System.out.println(un);

                    SharedPreferences sharedpreferences = getSharedPreferences("username", Context.MODE_PRIVATE);

                    Editor editor = sharedpreferences.edit();
                    editor.putString("username", ET_USER_NAME.getText().toString());
                    editor.apply();



                    startActivity(new Intent(this, UserActivity.class));
                    //startActivity(new Intent(this, Connection.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();

                break;

            case R.id.ET_REG_HERE:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }


    }
}
