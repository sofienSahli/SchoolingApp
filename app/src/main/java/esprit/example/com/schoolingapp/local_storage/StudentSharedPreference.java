package esprit.example.com.schoolingapp.local_storage;

import android.content.SharedPreferences;

import esprit.example.com.schoolingapp.entities.Student;

public class StudentSharedPreference {
    public static final String USER_ID = "USER_ID";
    public static final String EMAIL = "EMAIL";
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    public static final String USER_BIRTHDATE = "USER_BIRTHDATE";
    public static final String USER_PASSWRD = "USER_PASSWRD";
    public static final String USER_IDENTIFIANT = "USER_IDENTIFIANT";
    public static final String USER_FATEHR_NAME = "USER_FATHER_NAME";
    public static final String USER_BAC = "USER_BAC";
    public static final String USER_BAC_DATE = "USER_BAC_DATE";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_PARENT_PHONE = "USER_PARENT_PHONE";
    public static final String USER_PROFILE_PICTURE = "USER_PICTURE";
    public static final String USER_FILE = "student";
    public static final String IS_FICHE_EDITABLE = "is_fiche_editable";


    SharedPreferences sharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public StudentSharedPreference(SharedPreferences sharedPreferences) {
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

    public  void logOut() {

        sharedPreferences.edit().clear().apply();
    }

    public void logIn(Student user) {
        this.insertString(StudentSharedPreference.USER_FIRST_NAME, user.getName());
        insertString(StudentSharedPreference.USER_LAST_NAME, user.getLast_name());
        insertString(StudentSharedPreference.USER_PASSWRD, user.getPassword());
        insertString(StudentSharedPreference.EMAIL, user.getEmail());
        insertString(StudentSharedPreference.USER_BIRTHDATE, user.getBirth_date().toString());
        inserLong(StudentSharedPreference.USER_ID, user.getId());
        inserLong(StudentSharedPreference.USER_PHONE, user.getPhone());
        insertString(USER_BAC, user.getBac());
        insertString(USER_BAC_DATE, user.getBac_date().toString());
        insertString(USER_FATEHR_NAME, user.getParent_name());
        inserLong(USER_PHONE, user.getPhone());
        inserLong(USER_PARENT_PHONE, user.getPhone());
        insertString(USER_IDENTIFIANT, user.getIdentifiant());
    }

    public boolean isUserLogged() {
        if(getString(USER_IDENTIFIANT).equals(""))
            return false;
        else
            return  true ; 
    }
}
