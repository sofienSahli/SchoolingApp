package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Enseignant extends User implements Parcelable {
    String specialite;
    Cours cours;
    List<Groupe> classes;
    List<Media> medias;
    List<FichePFE> fiches;

    @Override
    public String toString() {
        return "Enseignant{" +
                "specialite='" + specialite + '\'' +
                ", cours=" + cours +
                ", classes=" + classes +
                ", medias=" + medias +
                ", fiches=" + fiches +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", phone=" + phone +
                '}';
    }

    protected Enseignant(Parcel in) {
        super(in);
        specialite = in.readString();
        medias = in.createTypedArrayList(Media.CREATOR);
        fiches = in.createTypedArrayList(FichePFE.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(specialite);
        dest.writeTypedList(medias);
        dest.writeTypedList(fiches);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Enseignant> CREATOR = new Creator<Enseignant>() {
        @Override
        public Enseignant createFromParcel(Parcel in) {
            return new Enseignant(in);
        }

        @Override
        public Enseignant[] newArray(int size) {
            return new Enseignant[size];
        }
    };

    public List<FichePFE> getFiches() {
        return fiches;
    }

    public Enseignant(String specialite, Cours cours, List<Groupe> classes, List<Media> medias, List<FichePFE> fiches) {
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
        this.fiches = fiches;
    }

    public Enseignant(int id, String name, String last_name, String e_mail, String identifiant, long phone, String specialite, Cours cours, List<Groupe> classes, List<Media> medias, List<FichePFE> fiches) {
        super(id, name, last_name, e_mail, identifiant, phone);
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
        this.fiches = fiches;
    }

    public Enseignant(Parcel in, String specialite, Cours cours, List<Groupe> classes, List<Media> medias, List<FichePFE> fiches) {
        super(in);
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
        this.fiches = fiches;
    }

    public void setFiches(List<FichePFE> fiches) {
        this.fiches = fiches;
    }

    public Enseignant(String specialite, Cours cours, List<Groupe> classes, List<Media> medias) {
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
    }

    public Enseignant(int id, String name, String last_name, String e_mail, String identifiant, long phone, String specialite, Cours cours, List<Groupe> classes, List<Media> medias) {
        super(id, name, last_name, e_mail, identifiant, phone);
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
    }

    public Enseignant(Parcel in, String specialite, Cours cours, List<Groupe> classes, List<Media> medias) {
        super(in);
        this.specialite = specialite;
        this.cours = cours;
        this.classes = classes;
        this.medias = medias;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public Enseignant() {

    }

    public List<Groupe> getClasses() {
        return classes;
    }

    public void setClasses(List<Groupe> classes) {
        this.classes = classes;
    }

    public Enseignant(int id, String name, String last_name, String e_mail, String identifiant, long phone, String specialite, Cours cours) {
        super(id, name, last_name, e_mail, identifiant, phone);
        this.specialite = specialite;
        this.cours = cours;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }


}
