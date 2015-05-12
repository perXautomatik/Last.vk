package rom.hadoken.kiro.lastvk.DAOK;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import rom.hadoken.kiro.lastvk.Logic.DBHelper;
import rom.hadoken.kiro.lastvk.Logic.User;
import rom.hadoken.kiro.lastvk.Logic._Factory;

/**
 * Created by Kiro on 11.05.2015.
 */
public class UserDAO {

    private static DBHelper dbHelper=null;
    private static SQLiteDatabase database = null;
    private static String[] columns = {DBHelper.NAME,DBHelper.KEY,DBHelper.SUBS};

    public static void initializeInstance(){
        if(dbHelper == null)
            dbHelper = _Factory.getDbHelper();

    }

    public static User getUserToken() {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.USER,columns,null,null,null,null,null);
        cursor.moveToFirst();
        try {
            database.close();
            return getUserToken(cursor);
        }catch (Exception e){
            e.printStackTrace();
        }
        database.close();
        return null;
    }

    private static User getUserToken(Cursor cursor) {
        return new User(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
    }

    public static void setUserToken(User user){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, user.getName());
        values.put(DBHelper.KEY,user.getKey());
        values.put(DBHelper.SUBS,user.getSubs());
        long insertID =  database.insertOrThrow(DBHelper.USER,null,values);
        Log.d("hadoken", String.valueOf(insertID));
        database.close();
    }

    public static void deleteUserToken() throws SQLException{
        database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.USER, DBHelper.NAME + " = " + getUserToken().getName(), null);
        database.close();
    }
}
