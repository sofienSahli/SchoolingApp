package esprit.example.com.schoolingapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class Credit {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name="title")
    String title ;

    String classe;
    String nombre_uat ;

    public Credit(int id, String title, String classe, String nombre_uat) {
        this.id = id;
        this.title = title;
        this.classe = classe;
        this.nombre_uat = nombre_uat;
    }

    public Credit(String title, String classe, String nombre_uat) {
        this.title = title;
        this.classe = classe;
        this.nombre_uat = nombre_uat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNombre_uat() {
        return nombre_uat;
    }

    public void setNombre_uat(String nombre_uat) {
        this.nombre_uat = nombre_uat;
    }
}
