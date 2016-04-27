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
public class EditCommandsActivity extends Activity implements View.OnClickListener {
    private DatabaseAccess databaseAccess;
    private Cursor cursor;
    private SimpleListCursorAdapter simpleListCursorAdapter;
    private Button addNewCommand, addCommand;
    private EditText command, description;
    private TextView text1,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_edit_command);
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        ListView commandList = (ListView) findViewById(R.id.commandList);
        addNewCommand = (Button) findViewById(R.id.addNewCommand);
        addNewCommand.setOnClickListener(this);

        cursor = databaseAccess.getCursor("Commands");


        //   Log.i("Columns: ", cursor.getColumnName(0) + " " + cursor.getColumnName(1) + " " + cursor.getColumnName(2));
        simpleListCursorAdapter = new SimpleListCursorAdapter(this, cursor, "Commands", 0);

        commandList.setAdapter(simpleListCursorAdapter);
        commandList.setClickable(true);
        commandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.edit_dialog);

                cursor.moveToPosition(position);

                text1 = (TextView) dialog.findViewById(R.id.text1);
                text1.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")));

                text2 = (TextView) dialog.findViewById(R.id.text2);
                text2.setText(cursor.getString(cursor.getColumnIndexOrThrow("command")));

                Button update = (Button) dialog.findViewById(R.id.update);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues cv = new ContentValues();
                        cv.put("description",text1.getText().toString());
                        cv.put("command",text2.getText().toString());
                        databaseAccess.update("Commands", cv);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.addNewCommand):
                setContentView(R.layout.dev_add_users);
                databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                command = (EditText) findViewById(R.id.devTextCommand);
                description = (EditText) findViewById(R.id.devTextDescription);
                addCommand = (Button) findViewById(R.id.devAddCommand);

                addCommand.setOnClickListener(this);

                break;

            case(R.id.devAddCommand):
                ContentValues cv = new ContentValues();
                cv.put("command", command.getText().toString());
                cv.put("description", description.getText().toString());
                databaseAccess.insert("Commands", cv);
                Toast.makeText(getApplicationContext(), "New command added to database", Toast.LENGTH_SHORT).show();
                this.finish();
                startActivity(new Intent(this, EditCommandsActivity.class));

                break;
        }
    }

}
