package esprit.example.com.schoolingapp.local_storage;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.entities.UnseenFiches;
import esprit.example.com.schoolingapp.local_storage.dao.AdresDAO;
import esprit.example.com.schoolingapp.local_storage.dao.FichePFEDAO;
import esprit.example.com.schoolingapp.local_storage.dao.FonctionalitesDAO;
import esprit.example.com.schoolingapp.local_storage.dao.Problematiques;
import esprit.example.com.schoolingapp.local_storage.dao.UnseenFichesDAO;


@Database(entities = {FichePFE.class, Fonctionalite.class, Problematique.class, Adres.class, UnseenFiches.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract FichePFEDAO fichePFE();

    public abstract Problematiques Problematiques();

    public abstract FonctionalitesDAO FonctionalitesDAO();

    public abstract AdresDAO adresDAO();

    public abstract UnseenFichesDAO unseenFichesDAO();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mydb")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }


}
