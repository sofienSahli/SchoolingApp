package esprit.example.com.schoolingapp.local_storage;

import android.content.SharedPreferences;

import esprit.example.com.schoolingapp.entities.Agent;
import esprit.example.com.schoolingapp.entities.Student;

public class AgentSharedPreference {
    public static final String USER_ID = "USER_ID";
    public static final String EMAIL = "EMAIL";
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    public static final String USER_BIRTHDATE = "USER_BIRTHDATE";
    public static final String USER_PASSWRD = "USER_PASSWRD";
    public static final String USER_IDENTIFIANT = "USER_IDENTIFIANT";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_PROFILE_PICTURE = "USER_PICTURE";
    public static final String USER_FILE = "agent";


    SharedPreferences sharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public AgentSharedPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean insertString(String label, String newText) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(label, newText);
        editor.apply();
        return editor.commit();
    }

    public String getString(String label) {
        return sharedPreferences.getString(label, "");
    }

    public boolean insertInt(String label, int newInt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(label, newInt);
        editor.apply();
        return editor.commit();
    }

    public boolean inserLong(String label, long newInt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(label, newInt);

        editor.apply();
        return editor.commit();
    }

    public boolean insertBollean(String label, boolean bool) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(label, bool);

        editor.apply();
        return editor.commit();

    }

    public boolean getBoolean(String label) {
        return sharedPreferences.getBoolean(label, false);
    }

    public int getInt(String label) {
        return sharedPreferences.getInt(label, -1);
    }

    public long getLong(String label) {
        return sharedPreferences.getLong(label, -1L);
    }

    public static void logOut(SharedPreferences sharedPreferences) {

        sharedPreferences.edit().clear().apply();
    }

    public void logIn(Agent user) {
        this.insertString(USER_FIRST_NAME, user.getName());
        insertString(USER_LAST_NAME, user.getLast_name());
        insertString(USER_PASSWRD, user.getPassword());
        insertString(EMAIL, user.getEmail());
        insertString(USER_BIRTHDATE, user.getBirth_date().toString());
        inserLong(USER_ID, user.getId());
        inserLong(USER_PHONE, user.getTelephone());
        insertString(USER_IDENTIFIANT, user.getIdentfiant());
    }

    public boolean isUserLogged() {
        if(getString(USER_IDENTIFIANT).equals(""))
            return false;
        else
            return  true ;
    }
}
