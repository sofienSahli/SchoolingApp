package esprit.example.com.schoolingapp.local_storage.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import esprit.example.com.schoolingapp.entities.FichePFE;

@Dao
public interface FichePFEDAO {
    @Query("SELECT * FROM fichepfe")
    List<FichePFE> getAll();
    @Insert
    long[] insertAll(FichePFE... fichePFES);

    @Delete
    void delete(FichePFE fichePFE);
    @Update
    public void updateFICHE(FichePFE... FichePFE);
}
