package mindcar.testing.util;

/**
 * Created by colak on 03/04/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public boolean checkUser(String userName, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM USERS WHERE username = '" + userName + "' AND password = '" + password + "'", null);
        return cursor.getCount() == 1;
    }

    public boolean checkAvailability(String userName) {
        Cursor cursor = database.rawQuery("SELECT * FROM USERS WHERE username = '" + userName + "'", null);
        return cursor.getCount() == 0;
    }

    public int getNumberOfRows(String table){
        Cursor cursor = database.rawQuery("select id from " + table + ";",null);
        return cursor.getCount();
    }

    public Cursor getRow(String table, int i){
        Cursor cursor = database.rawQuery("select * from " + table + " where id = " + i + ";",null);
        return cursor;
    }

    public double[] getDirection(String direction) {
        Cursor cursor = database.rawQuery("SELECT * FROM PATTERNS WHERE direction = '" + direction + "'", null);
        String str= "";
        double[] arr = new double[800];
        if (cursor.moveToFirst()) {
            str = cursor.getString(cursor.getColumnIndex(direction) + 3);
        }
        int i = 0;
        while (str.length()!= 0 && str.charAt(0) == 's') {
            String s = str.substring(str.indexOf('s')+1,str.indexOf('e'));
            arr[i] = Double.parseDouble(s);

            str = str.substring(str.indexOf('e')+1);
            i++;
        }
        StringBuilder log = new StringBuilder();
        for (int j = 0; j < arr.length;j++){
            log.append(arr[j]+" ");
        }
        Log.i("String2",log.toString()+"");
        return arr;
    }

    public Cursor getCursor(String table){
        Cursor cursor = database.rawQuery("select * from " + table + ";",null);
        return cursor;
    }

    public void insert(String table, ContentValues values){
            database.insert(table,null,values);
    }

    public void addRegistration(String username, String password){
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        database.insert("USERS", null, values);
    }



    public void delete(String table, int id) {
        database.delete(table, "_id = ?", new String[]{id + ""});
    }

    public void update(String table, ContentValues values, int id) {
        database.update(table, values, "_id = " + id, null);
    }

    public boolean isDeveloper(String username, String password) {
        Cursor cursor = database.rawQuery("select * from users where username = '" + username + "' and password = '" + password + "' and developer = 1", null);
        return cursor.getCount() == 1;
    }

    public void addDirection(String direction, String pattern) {
        ContentValues direc = new ContentValues();
        direc.put("direction", direction);
        direc.put("pattern", pattern);
        database.insert("PATTERNS", null, direc);
    }

    public void addBaseline(String pattern) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("baseline", pattern);
        database.update("Users",contentValues, "username = 'Sanja'", null);
    }
}
