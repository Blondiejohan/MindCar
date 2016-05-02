package mindcar.testing.ui.dev.edit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import static android.widget.AdapterView.*;

/**
 * Created by Mattias Landkvist on 5/3/16.
 */
public interface EditActivity extends OnClickListener, OnItemClickListener {

    /**
     * Should handle overall button clicks.
     * @param v
     */
    @Override
    void onClick(View v);

    /**
     * Should handle overall listview/adapterview item clicks.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    void onItemClick(AdapterView<?> parent, View view, int position, long id);

    /**
     * Refresh the database with the latest updates to the database.
     */
    void update();

}
