package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.DatabaseOpenHelper;

public class DeveloperActivity extends Activity implements View.OnClickListener {

    Button devAddCommand;
    Button devEditDatabase;
    Button devBackToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        devAddCommand = (Button) findViewById(R.id.devEditCommand);
        devEditDatabase = (Button) findViewById(R.id.devEditDatabase);
        devBackToStart = (Button) findViewById(R.id.devBackToStart);

        devAddCommand.setOnClickListener(this);
        devEditDatabase.setOnClickListener(this);
        devBackToStart.setOnClickListener(this);
    }

    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";

    public void onClick(View v) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.close();
        switch (v.getId()) {
            case R.id.devEditCommand:
                setContentView(R.layout.dev_edit_command);

                databaseAccess.open();

                ListView commandList = (ListView) findViewById(R.id.commandList);
                ArrayList<Map<String, String>> commands = new ArrayList<>();
                Map<String, String> tmp = new HashMap<>(databaseAccess.getNumberOfRows("commands"));

                for (int i = 1; i >= databaseAccess.getNumberOfRows("commands"); i++) {
                    Cursor cursor = databaseAccess.getRow("commands", i);
                    tmp.put(TEXT1, cursor.getString(cursor.getColumnIndex("description")));
                    tmp.put(TEXT2, cursor.getString(cursor.getColumnIndex("command")));
                    commands.add(Collections.unmodifiableMap(tmp));
                }
                databaseAccess.close();

                commandList.setAdapter(new SimpleAdapter(this, commands, android.R.layout.simple_expandable_list_item_2, new String[]{TEXT1, TEXT2}, new int[]{android.R.id.text1, android.R.id.text2}));

                break;
            case R.id.devEditDatabase:
                setContentView(R.layout.dev_edit_command);

                databaseAccess.open();
                ExpandableListView databaseList = (ExpandableListView) findViewById(R.id.databaseList);
                ArrayList<Map<String, String>> entries = new ArrayList<>();
                Map<String, String> data = new HashMap<>(databaseAccess.getNumberOfRows("users"));

                for (int i = 1; i >= databaseAccess.getNumberOfRows("users"); i++) {
                    Cursor cursor = databaseAccess.getRow("users", i);
                    data.put(TEXT1, cursor.getString(cursor.getColumnIndex("user")));
                    data.put(TEXT2, cursor.getString(cursor.getColumnIndex("password")));
                    entries.add(Collections.unmodifiableMap(data));
                }
                databaseAccess.close();

                databaseList.setAdapter(new SimpleAdapter(this, entries, android.R.layout.simple_expandable_list_item_2, new String[]{TEXT1, TEXT2}, new int[]{android.R.id.text1, android.R.id.text2}));


                break;
            case R.id.devBackToStart:
                startActivity(new Intent(this, StartActivity.class));
                break;
        }
    }
}
