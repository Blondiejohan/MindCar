package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mindcar.testing.R;

public class HomeActivity extends Activity implements View.OnClickListener{

    Button bLogout;
    EditText ET_USER_NAME, ET_PASS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:

                startActivity(new Intent(this, Login2.class));
                break;
        }

    }

}


