package esprit.example.com.schoolingapp.local_storage.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import esprit.example.com.schoolingapp.entities.Fonctionalite;

@Dao
public interface FonctionalitesDAO {
    @Query("SELECT * FROM fonctionalite")
    List<Fonctionalite> getAll();

    @Query("SELECT * FROM fonctionalite where id_fonct = :id")
    Fonctionalite find(int id);

    @Insert
    void insertAll(Fonctionalite... fonctionalite);

    @Insert
    void insertList(List<Fonctionalite> fonctionalites);

    @Query("SELECT * FROM fonctionalite WHERE fiche_id = :id_fiche")
    public List<Fonctionalite> findProbs(int id_fiche);

    @Update
    public void update(Fonctionalite... fonctionalites);

    @Delete
    void delete(Fonctionalite fonctionalite);
}
