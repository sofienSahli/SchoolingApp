package esprit.example.com.schoolingapp.local_storage.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;

@Dao
public interface Problematiques {

    @Query("SELECT * FROM problematique")
    List<Problematique> getAll();

    @Query("SELECT * FROM problematique where id_prob = :id")
    Problematique find(int id);

    @Insert
    void insertAll(Problematique... problematiques);
    @Insert
    void insertList (List<Problematique> problematiques);
    @Query("SELECT * FROM problematique WHERE fiche_id = :id_fiche")
    public List<Problematique> findProbs(int id_fiche);

    @Update
    public void update(Problematique... problematiques);
    @Delete
    void delete(Problematique problematique);
}
