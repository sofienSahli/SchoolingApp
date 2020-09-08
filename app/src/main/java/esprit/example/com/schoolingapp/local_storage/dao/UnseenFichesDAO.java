package esprit.example.com.schoolingapp.local_storage.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import esprit.example.com.schoolingapp.entities.UnseenFiches;

@Dao
public interface UnseenFichesDAO {
    @Query("SELECT * FROM unseenfiches")
    List<UnseenFiches> getAll();
    @Query("SELECT * FROM unseenfiches where fiche_id = :fiche_id ")
    UnseenFiches find( int fiche_id);

    @Insert
    long[] insertAll(UnseenFiches... unseenFiches);

    @Delete
    void delete(UnseenFiches unseenFiches);

    @Update
    public void updateFICHE(UnseenFiches... unseenFiches);


}
