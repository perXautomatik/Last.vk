package rom.hadoken.kiro.lastvk.Logic;

/**
 * Created by Kiro on 11.05.2015.
 */
public class User {

    private String name;
    private String key;
    private int subs;

    public User(String name, String key, int subs) {
        this.name = name;
        this.key = key;
        this.subs = subs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSubs() {
        return subs;
    }

    public void setSubs(int subs) {
        this.subs = subs;
    }
}
