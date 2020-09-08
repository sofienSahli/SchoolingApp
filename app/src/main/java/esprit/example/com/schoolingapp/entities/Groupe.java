package esprit.example.com.schoolingapp.entities;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Groupe {
    List<Student> students;
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;

    public Groupe() {
    }

    public Groupe(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Groupe(List<Student> students, int id, String name) {
        this.students = students;
        this.id = id;
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
