package mindcar.testing.ui.dev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mindcar.testing.R;
import mindcar.testing.ui.StartActivity;

/**
 * Created by Mattias Landkvist on 4/25/16.
 */
public class DeveloperMainActivity extends Activity implements View.OnClickListener {

    Button devAddCommand, devEditDatabase, devBackToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        devAddCommand = (Button) findViewById(R.id.devEditCommand);
        devEditDatabase = (Button) findViewById(R.id.devEditUsers);
        devBackToStart = (Button) findViewById(R.id.devBackToStart);

        devAddCommand.setOnClickListener(this);
        devEditDatabase.setOnClickListener(this);
        devBackToStart.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devEditCommand:
                startActivity(new Intent(this, EditCommandsActivity.class));
                break;
            case R.id.devEditUsers:
                startActivity(new Intent(this, EditUsersActivity.class));
                break;
            case R.id.devBackToStart:
                startActivity(new Intent(this, StartActivity.class));
                break;
        }
    }
}
