package mindcar.testing.ui.dev.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import mindcar.testing.R;
import mindcar.testing.objects.SimpleListCursorAdapter;
import mindcar.testing.util.DatabaseAccess;

/**
 * This class creates the activity for editing the commands table within the database.
 * Created by Mattias Landkvist on 4/27/16.
 */
public class EditUsersActivity extends Activity implements EditActivity{
    private DatabaseAccess databaseAccess;
    private Button addNewUser, addUser, updateAddImage;
    private Cursor cursor;
    private SimpleListCursorAdapter simpleListCursorAdapter;
    private EditText username, password, text1, text2;
    private TextView textView1, textView2;
    private Switch aSwitch, updateDevStatus;
    private ListView databaseList;
    private int devBoolean = 0;

    /**
     * Creates the activity for editing the Users table.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_edit_users);
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseList = (ListView) findViewById(R.id.databaseList);
        addNewUser = (Button) findViewById(R.id.addNewUser);

        cursor = databaseAccess.getCursor("Users");
        simpleListCursorAdapter = new SimpleListCursorAdapter(this, cursor, "Users", 0);

        databaseList.setAdapter(simpleListCursorAdapter);
        databaseList.setClickable(true);
        databaseList.setOnItemClickListener(this);

        addNewUser.setOnClickListener(this);

    }

    /**
     * Handles button clicks
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.addNewUser):
                setContentView(R.layout.dev_add_users);
                databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                username = (EditText) findViewById(R.id.devAddName);
                password = (EditText) findViewById(R.id.devAddPassword);
                aSwitch = (Switch) findViewById(R.id.swichDeveloper);
                addUser = (Button) findViewById(R.id.devAddUser);

                addUser.setOnClickListener(this);

                break;

            case (R.id.devAddUser):
                int devBoolean = 0;
                if(aSwitch.isChecked()){
                    devBoolean = 1;
                }

                ContentValues cv = new ContentValues();
                cv.put("username", username.getText().toString());
                cv.put("password", password.getText().toString());
                cv.put("developer", devBoolean);
                databaseAccess.insert("Users", cv);
                Toast.makeText(getApplicationContext(), "New user added to database", Toast.LENGTH_SHORT).show();
                this.finish();
                startActivity(new Intent(this, EditUsersActivity.class));

                break;
        }
    }

    /**
     * Handles clicks on the list items within the ListView.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.edit_dialog);
        dialog.setTitle("Edit user");
        cursor.moveToPosition(position);

        textView1 = (TextView) dialog.findViewById(R.id.textView1);
        textView1.setText("Username");

        textView2 = (TextView) dialog.findViewById(R.id.textView2);
        textView2.setText("Password");

        text1 = (EditText) dialog.findViewById(R.id.text1);
        text1.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));

        text2 = (EditText) dialog.findViewById(R.id.text2);
        text2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        text2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        text2.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));


        final Button update = (Button) dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("username", text1.getText().toString());
                cv.put("password", text2.getText().toString());
                cv.put("developer", devBoolean);
                databaseAccess.update("Users", cv, ((int) id));
                update();
                dialog.cancel();
            }
        });

        final Button delete = (Button) dialog.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAccess.delete("Users", ((int) id));
                update();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * Updates the ListView with the latest information from the database.
     */
    public void update() {
        cursor = databaseAccess.getCursor("Users");
        simpleListCursorAdapter = new SimpleListCursorAdapter(this, cursor, "Users", 0);
        databaseList.setAdapter(simpleListCursorAdapter);
    }


}
