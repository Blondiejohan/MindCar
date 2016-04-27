package mindcar.testing.objects;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mindcar.testing.R;
import mindcar.testing.util.DatabaseAccess;


/**
 * Created by Mattias Landkvist on 4/25/16.
 */
public class SimpleListCursorAdapter extends CursorAdapter implements  View.OnClickListener {
    private LayoutInflater cursorInflater;
    private Context context;
    private Cursor cursor;
    private String table;

    public SimpleListCursorAdapter(Context context, Cursor cursor, String table, int flags) {
        super(context, cursor, flags);
        this.context = context;
        this.cursor = cursor;
        this.table = table;
        cursorInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.active_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(1))));

        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(this);

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.delete):
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.delete(table,1);
                break;
            case(R.id.edit):
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_dialog);

                TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                text1.setText(cursor.getString(view.getId()));



                break;
        }
    }

}
