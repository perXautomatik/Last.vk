package rom.hadoken.kiro.lastvk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.SQLException;

import rom.hadoken.kiro.lastvk.DAOK.UserDAO;
import rom.hadoken.kiro.lastvk.Logic.DBHelper;
import rom.hadoken.kiro.lastvk.Logic.User;
import rom.hadoken.kiro.lastvk.Logic._Factory;
import rom.hadoken.kiro.util.SupportSuff;


public class MainActivity extends ActionBarActivity implements SupportSuff {

    private static User user = null;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(this);
        _Factory.setDbHelper(dbHelper);
        UserDAO.initializeInstance();
        user = UserDAO.getUserToken();
        if(user == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.show(getSupportFragmentManager(), "well try");
        }
        textView = (TextView)findViewById(R.id.tvName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setLayoutAfterLogin() {
        user = UserDAO.getUserToken();
        if(user != null)
            textView.setText(user.getName());
    }
}
