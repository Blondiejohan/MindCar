package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mindcar.testing.R;
import mindcar.testing.objects.Command;

public class DeveloperActivity extends Activity implements View.OnClickListener{

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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.devEditCommand:

                setContentView(R.layout.dev_edit_command);
                ListView commandList = (ListView) findViewById(R.id.commandList);
                ArrayList<Map<String,String>> commands = new ArrayList<>();
                Map<String,String> tmp = new HashMap<>(Command.values().length);
                for(Command c: Command.values()){
                    tmp.put(TEXT1, c.toString());
                    commands.add(tmp);
                }

                commandList.setAdapter(new SimpleAdapter(this, commands, android.R.layout.simple_expandable_list_item_2, new String[]{TEXT1}, new int[]{android.R.id.text1}));

                break;
            case R.id.devEditDatabase:
                setContentView(R.layout.dev_edit_command);


                ExpandableListView databaseList = (ExpandableListView) findViewById(R.id.databaseList);
                break;
            case R.id.devBackToStart:
                startActivity(new Intent(this, StartActivity.class));
                break;
        }
    }
}
