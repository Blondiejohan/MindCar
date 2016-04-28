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
        database.delete(table, "_id = ?" , new String[]{id + ""});
    }

    public void update(String table, ContentValues values, int id) {
        database.update(table,values,"id = " + id,null);
    }
}
