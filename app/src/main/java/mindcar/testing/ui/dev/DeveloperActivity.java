package mindcar.testing.ui.dev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mindcar.testing.R;
import mindcar.testing.ui.StartActivity;
import mindcar.testing.ui.dev.edit.EditCommandsActivity;
import mindcar.testing.ui.dev.edit.EditUsersActivity;

/**
 * Basic class starting new activities corresponding to the button pressed.
 * Created by Mattias Landkvist on 4/25/16.
 */
public class DeveloperActivity extends Activity implements View.OnClickListener {

    Button devAddCommand, devEditDatabase, devBackToStart;

    /**
     * This method assigns listeners to buttons on create.
     * @param savedInstanceState
     */
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

    /**
     * Start a new activity corresponding to the button pressed.
     * @param v
     */
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
                this.finish();
                break;
        }
    }
}
