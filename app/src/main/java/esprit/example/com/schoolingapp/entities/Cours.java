package esprit.example.com.schoolingapp.entities;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Cours {
    @PrimaryKey(autoGenerate = true)
    int id;
    //@ForeignKey()
    @ColumnInfo(name = "enseignant_id")
    Enseignant enseignant;
    //@ForeignKey()
    List<Groupe> groupes;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "horraire")
    String horraire;
    @ColumnInfo(name = "classrom")
    String classroom;
    @ColumnInfo(name = "nbr_ue")
    int nbr_ue;

    public Cours() {
    }

    public Cours(int id, Enseignant enseignant, List<Groupe> groupes, String date, String horraire, String classroom, int nbr_ue) {
        this.id = id;
        this.enseignant = enseignant;
        this.groupes = groupes;
        this.date = date;
        this.horraire = horraire;
        this.classroom = classroom;
        this.nbr_ue = nbr_ue;
    }

    public int getNbr_ue() {
        return nbr_ue;
    }

    public void setNbr_ue(int nbr_ue) {
        this.nbr_ue = nbr_ue;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHorraire() {
        return horraire;
    }

    public void setHorraire(String horraire) {
        this.horraire = horraire;
    }
}
