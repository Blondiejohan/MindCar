package mindcar.testing.util;

import android.view.View;
import android.widget.AdapterView;

import static android.widget.AdapterView.*;

/**
 * Created by Mattias Landkvist on 4/27/16.
 */
public interface EditActivityInterFace extends OnClickListener ,OnItemClickListener {
    @Override
    void onClick(View v);

    @Override
    void onItemClick(AdapterView<?> parent, View view, int position, long id);

    void update();

}
