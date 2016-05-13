package mindcar.testing.objects;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mindcar.testing.R;


/**
 * An Adapter to ease populating ListViews with Cursors
 * Created by Mattias Landkvist on 4/25/16.
 */
public class SimpleListCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    /**
     * Basic constructor setting a LayoutInflater with the context.
     * @param context
     * @param cursor
     * @param table
     * @param flags
     */
    public SimpleListCursorAdapter(Context context, Cursor cursor, String table, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Build a new view with a XML layout through the LayoutInflater
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.active_list_item, parent, false);
    }

    /**
     * Set the elements of the XML layout with corresponding information from the Cursor.
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text = (TextView) view.findViewById(R.id.emptyText);
        text.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(1))));
    }

}
