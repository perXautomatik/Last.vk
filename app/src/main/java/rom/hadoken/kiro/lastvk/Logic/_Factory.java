package rom.hadoken.kiro.lastvk.Logic;

/**
 * Created by Kiro on 11.05.2015.
 */
public class _Factory {

    public static final String KEY = "5fed7f375ff942b10750d094a861bbea";
    public static final String SECRET = "7f79acbb810499caf3ad7f8d5f513819";
    public static String SIG;

    private static DBHelper dbHelper = null;

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(DBHelper _dbHelper) {
        if(dbHelper == null)
            _Factory.dbHelper = _dbHelper;
    }
}
