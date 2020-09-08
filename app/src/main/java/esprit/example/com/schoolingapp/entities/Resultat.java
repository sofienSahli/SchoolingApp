package esprit.example.com.schoolingapp.entities;

public class Resultat {

    int id;
    String subject_name;
    float moyenne ;
    float note_ds;
    float note_examens;
    float note_cc ;

    public Resultat(String subject_name, float moyenne, float note_ds, float note_examens, float note_cc) {
        this.subject_name = subject_name;
        this.moyenne = moyenne;
        this.note_ds = note_ds;
        this.note_examens = note_examens;
        this.note_cc = note_cc;
    }

    public Resultat(int id, String subject_name, float moyenne, float note_ds, float note_examens, float note_cc) {
        this.id = id;
        this.subject_name = subject_name;
        this.moyenne = moyenne;
        this.note_ds = note_ds;
        this.note_examens = note_examens;
        this.note_cc = note_cc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public float getNote_ds() {
        return note_ds;
    }

    public void setNote_ds(float note_ds) {
        this.note_ds = note_ds;
    }

    public float getNote_examens() {
        return note_examens;
    }

    public void setNote_examens(float note_examens) {
        this.note_examens = note_examens;
    }

    public float getNote_cc() {
        return note_cc;
    }

    public void setNote_cc(float note_cc) {
        this.note_cc = note_cc;
    }
}
