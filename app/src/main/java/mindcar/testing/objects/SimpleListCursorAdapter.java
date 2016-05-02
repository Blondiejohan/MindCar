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
public class SimpleListCursorAdapter extends CursorAdapter implements View.OnClickListener {
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
        return cursorInflater.inflate(R.layout.active_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(1))));
    }

    @Override
    public void onClick(View view) {



    }

}
