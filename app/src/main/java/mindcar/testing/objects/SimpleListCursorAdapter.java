package mindcar.testing.objects;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by Mattias Landkvist on 4/25/16.
 */
public class SimpleListCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    public SimpleListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(1))));

    }
}
