package mindcar.testing.util;

import mindcar.testing.ui.dev.edit.EditActivity;
import mindcar.testing.ui.dev.edit.EditCommandsActivity;
import mindcar.testing.ui.dev.edit.EditUsersActivity;

/**
 * Adapter for different edit views
 * Created by Mattias Landkvist on 5/3/16.
 */
public class EditAdapter {
    EditActivity editActivity;

    /**
     * Creates an adapter with the view corresponding to the parameter.
     * @param table
     */
    public EditAdapter(String table){
        if(table.equalsIgnoreCase("commands")){
            editActivity = new EditCommandsActivity();
        } else if(table.equalsIgnoreCase("users")){
            editActivity = new EditUsersActivity();
        }
    }
}
