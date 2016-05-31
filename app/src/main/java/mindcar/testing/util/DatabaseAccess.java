package mindcar.testing.util;

/**
 * Created by Sanja Colak on 03/04/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Sanja & Mattias & Johan
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private static SQLiteDatabase database;
    private static DatabaseAccess instance;

     //Private constructor to aboid object creation from outside classes.
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }
     //Return a singleton instance of DatabaseAccess.
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //Sanja
     //Open the database connection.
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    //Sanja
        //Close the database connection.
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //Sanja
        //Read all quotes from the database.
    public boolean checkUser(String userName, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM USERS WHERE username = '" + userName + "' AND password = '" + password + "'", null);
        return cursor.getCount() == 1;
    }
    //Sanja
    public boolean checkAvailability(String userName) {
        Cursor cursor = database.rawQuery("SELECT * FROM USERS WHERE username = '" + userName + "'", null);
        return cursor.getCount() == 0;
    }

    public Cursor getCursor(String table){
        Cursor cursor = database.rawQuery("select * from " + table + ";",null);
        return cursor;
    }

    public void insert(String table, ContentValues values){
            database.insert(table,null,values);
    }

    public void addRegistration(String username, String password, byte[] photo) throws SQLException{
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("photo", photo);
        values.put("neuralnetwork", new byte[]{});
        values.put("trainingset", new byte[]{});

        database.insert("USERS", null, values);
    }

    public byte[] getPhoto(String username){
        System.out.println("The user name for the login passed to getPhoto is " + username);
        Cursor cursor = database.rawQuery("select * from users where username = '" + username + "'", null);
        cursor.moveToLast();
        byte[] picByte;
        if (cursor != null){
            picByte = cursor.getBlob(cursor.getColumnIndex("photo"));
        return picByte;}
        else return null;
    }

    public void delete(String table, int id) {
        database.delete(table, "_id = ?", new String[]{id + ""});
    }

    public void update(String table, ContentValues values, int id) {
        database.update(table, values, "_id = " + id, null);
    }

    public void update(String table, ContentValues values, String username) {
        database.update(table, values, username, null);
    }

    public void updateUsername (String oldUsername, String newUsername) {
        String sql = "UPDATE Users SET username = '" +newUsername+ "' where username = '"+oldUsername+"';";
        database.execSQL(sql);
    }

    public void updatePassword (String username, String newPassword){
        String sql = "Update Users SET password = '" +newPassword+ "' where username = '"+username+"';";
        database.execSQL(sql);
    }

    public boolean isDeveloper(String username, String password) {
        Cursor cursor = database.rawQuery("select * from users where username = '" + username + "' and password = '" + password + "' and developer = 1", null);
        return cursor.getCount() == 1;
    }

    public byte[] getNetwork(Context context, String username) {
        Cursor cursor = database.rawQuery("select * from users where username = '" + username + "'", null);
        cursor.moveToFirst();

        byte[] bytes = cursor.getBlob(cursor.getColumnIndex("neuralnetwork"));
        return bytes;
    }

    public byte[] getTrainingSet(Context context, String username) {
        Cursor cursor = database.rawQuery("select * from users where username = '" + username + "'", null);
        cursor.moveToFirst();

        byte[] bytes = cursor.getBlob(cursor.getColumnIndex("trainingset"));
        return bytes;
    }

    //public boolean update1(String username) {
        //Cursor cursor = database.rawQuery("UPDATE USERS SET username = '" + username + "' where username ='" + UserSettings.loggedUsername + "'", null);
        //return cursor.getCount() == 0;
    //}

    //public boolean update2(String password) {
        //Cursor cursor = database.rawQuery("UPDATE USERS SET password = '" + password + "' where username ='" + UserSettings.loggedUsername + "'", null);
        //return cursor.getCount() == 0;
    //}
}
