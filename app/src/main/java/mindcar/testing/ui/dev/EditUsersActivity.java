package mindcar.testing.ui.dev;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mindcar.testing.R;
import mindcar.testing.objects.SimpleListCursorAdapter;
import mindcar.testing.util.DatabaseAccess;

/**
 * Created by Mattias Landkvist on 4/27/16.
 */
public class EditUsersActivity extends Activity implements View.OnClickListener {
    DatabaseAccess databaseAccess;
    Button addNewUser;
    Cursor cursor;
    SimpleListCursorAdapter simpleListCursorAdapter;
    EditText username, password;
    Button addUser;
    private TextView text1, text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_edit_users);
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final ListView databaseList = (ListView) findViewById(R.id.databaseList);
        addNewUser = (Button) findViewById(R.id.addNewUser);

        cursor = databaseAccess.getCursor("Users");
        simpleListCursorAdapter = new SimpleListCursorAdapter(this, cursor, "Users", 0);

        databaseList.setAdapter(simpleListCursorAdapter);
        databaseList.setClickable(true);
        databaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.edit_dialog);

                cursor.moveToPosition(position);

                text1 = (TextView) dialog.findViewById(R.id.text1);
                text1.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));

                text2 = (TextView) dialog.findViewById(R.id.text2);
                text2.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));

                Button update = (Button) dialog.findViewById(R.id.update);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues cv = new ContentValues();
                        cv.put("description", text1.getText().toString());
                        cv.put("command", text2.getText().toString());
                        databaseAccess.update("Commands", cv);
                    }
                });

                dialog.show();
            }
        });

        addNewUser.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.addNewUser):
                setContentView(R.layout.dev_add_users);
                databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                username = (EditText) findViewById(R.id.devAddName);
                password = (EditText) findViewById(R.id.devAddPassword);
                addUser = (Button) findViewById(R.id.devAddUser);
                addUser.setOnClickListener(this);

                break;

            case (R.id.devAddUser):
                ContentValues cv = new ContentValues();
                cv.put("username", username.getText().toString());
                cv.put("password", password.getText().toString());
                databaseAccess.insert("Users", cv);
                Toast.makeText(getApplicationContext(), "New user added to database", Toast.LENGTH_SHORT).show();
                this.finish();
                startActivity(new Intent(this, EditUsersActivity.class));

                break;
        }
    }
}
