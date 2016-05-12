package mindcar.testing.util;

/**
 * Created by colak on 03/04/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        double[] arr = new double[0];
        if (cursor.moveToFirst()) {
            str = cursor.getString(cursor.getColumnIndex(direction) + 3);
        }
        arr[0]= Double.parseDouble(str.substring(str.indexOf("a"), str.indexOf("b")));
        arr[1]= Double.parseDouble( str.substring(str.indexOf("b"), str.indexOf("c")));
        arr[2]= Double.parseDouble( str.substring(str.indexOf("c"), str.indexOf("d")));
        arr[3]= Double.parseDouble( str.substring(str.indexOf("d"), str.indexOf("e")));
        arr[4]= Double.parseDouble( str.substring(str.indexOf("e"), str.indexOf("f")));
        arr[5]= Double.parseDouble( str.substring(str.indexOf("f"), str.indexOf("g")));
        arr[6]= Double.parseDouble( str.substring(str.indexOf("g"), str.indexOf("h")));
        arr[7]= Double.parseDouble( str.substring(str.indexOf("h"), str.indexOf("i")));
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

    public void update(String username) {
        ContentValues usernameChange = new ContentValues();
        usernameChange.put("username", username);
        database.update("Users", usernameChange, " username = "+ username, null );
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
}
