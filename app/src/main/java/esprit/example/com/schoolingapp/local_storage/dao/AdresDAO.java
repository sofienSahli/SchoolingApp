package esprit.example.com.schoolingapp.local_storage.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import esprit.example.com.schoolingapp.entities.Adres;

@Dao
public interface AdresDAO {
    @Query("SELECT * FROM adres")
    List<Adres> getAll();

    @Insert
    void insertAll(Adres... adres);

    @Delete
    void delete(Adres adres);

    @Update
    public void updateFICHE(Adres... adres);

    @Query("SELECT * FROM adres WHERE id_fiche = :id_fiche")
    public Adres findAdres(int id_fiche);
}
