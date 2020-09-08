package esprit.example.com.schoolingapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UnseenFiches {
    @PrimaryKey(autoGenerate = true)
    int id;
    int fiche_id;
    boolean isSeen;

    public UnseenFiches() {
    }

    public UnseenFiches(int id, int fiche_id, boolean isSeen) {
        this.id = id;
        this.fiche_id = fiche_id;
        this.isSeen = isSeen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(int fiche_id) {
        this.fiche_id = fiche_id;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
