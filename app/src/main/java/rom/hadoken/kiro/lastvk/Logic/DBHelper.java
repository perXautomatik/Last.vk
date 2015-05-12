package rom.hadoken.kiro.lastvk.Logic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kiro on 11.05.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String USER = "user";

    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String SUBS = "subscriber";

    private static final String DB_CREATE = "create table " + USER + "(" + NAME + " text not null,"
            + KEY + " text not null,"
            + SUBS + " integer not null);";


    public DBHelper(Context context) {
        super(context, "lastfm", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
