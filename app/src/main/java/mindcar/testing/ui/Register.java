package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

public class Register extends Activity implements View.OnClickListener {

    Button bRegister;
    EditText ET_USER_NAME, ET_PASS;
    String  user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }
    public void userReg (View view)
    {
        user_name = ET_USER_NAME.getText().toString();
        user_pass = ET_PASS.getText().toString();

    }
    @Override
    public void onClick(View v) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            switch (v.getId()){
                case R.id.bRegister:
                    if (databaseAccess.checkAvailability(ET_USER_NAME.getText().toString())) {
                        //Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Add " + ET_USER_NAME.getText().toString() + " " + ET_PASS.getText().toString(), Toast.LENGTH_SHORT).show();
                        databaseAccess.addRegistration(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString());
                        //databaseAccess.close();
                        startActivity(new Intent(this, Login2.class));
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_SHORT).show();
                    break;
            }

    }

}
