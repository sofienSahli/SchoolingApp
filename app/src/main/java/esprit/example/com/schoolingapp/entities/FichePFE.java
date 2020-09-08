package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FichePFE implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "id_etudiant")
    long id_etudiant;
    @Ignore
    int id_enseignant;
    @Ignore
    @SerializedName("functions")
    List<Fonctionalite> functions;
    @Ignore
    @SerializedName("problematiques")
    List<Problematique> problematiques;
    @Ignore
    @SerializedName("adress_entreprise")
    Adres adress_entreprise;
    @Ignore
    Student student;
    @Ignore
    @SerializedName("encadreur")
    Enseignant enseignant;
    @ColumnInfo(name = "titre")
    String titre;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "nom_entreprise")
    String nom_entreprise;
    @ColumnInfo(name = "technologies")
    @SerializedName("technologies")
    String tech;
    @Ignore
    @SerializedName("created_at")
    String created_at;
    @Ignore
    @SerializedName("updated_at")
    String updated_at;
    @Ignore
    @SerializedName("isAcceptedByAgent")
    boolean isAcceptedByAgent ;
    @Ignore
    @SerializedName("remarques")
    List<Remarques> remarques;
    @Ignore
    @SerializedName("medias")
    List<Media> reports;
    @Ignore
    Date date_depot;

    public FichePFE(int id, long id_etudiant, int id_enseignant, List<Fonctionalite> functions, List<Problematique> problematiques, Adres adress_entreprise, Student student, Enseignant enseignant, String titre, String description, String nom_entreprise, String tech, String created_at, String updated_at, boolean isAcceptedByAgent, List<Remarques> remarques, List<Media> reports, Date date_depot, boolean isEditableByStudent, boolean isAcceptedByEnseignant) {
        this.id = id;
        this.id_etudiant = id_etudiant;
        this.id_enseignant = id_enseignant;
        this.functions = functions;
        this.problematiques = problematiques;
        this.adress_entreprise = adress_entreprise;
        this.student = student;
        this.enseignant = enseignant;
        this.titre = titre;
        this.description = description;
        this.nom_entreprise = nom_entreprise;
        this.tech = tech;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isAcceptedByAgent = isAcceptedByAgent;
        this.remarques = remarques;
        this.reports = reports;
        this.date_depot = date_depot;
        this.isEditableByStudent = isEditableByStudent;
        this.isAcceptedByEnseignant = isAcceptedByEnseignant;
    }

    public Date getDate_depot() {
        return date_depot;
    }

    public void setDate_depot(Date date_depot) {
        this.date_depot = date_depot;
    }

    public static Creator<FichePFE> getCREATOR() {
        return CREATOR;
    }

    public FichePFE(int id, long id_etudiant, int id_enseignant, List<Fonctionalite> functions, List<Problematique> problematiques, Adres adress_entreprise, Student student, Enseignant enseignant, String titre, String description, String nom_entreprise, String tech, String created_at, String updated_at, boolean isAcceptedByAgent, List<Remarques> remarques, List<Media> reports, boolean isEditableByStudent, boolean isAcceptedByEnseignant) {
        this.id = id;
        this.id_etudiant = id_etudiant;
        this.id_enseignant = id_enseignant;
        this.functions = functions;
        this.problematiques = problematiques;
        this.adress_entreprise = adress_entreprise;
        this.student = student;
        this.enseignant = enseignant;
        this.titre = titre;
        this.description = description;
        this.nom_entreprise = nom_entreprise;
        this.tech = tech;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isAcceptedByAgent = isAcceptedByAgent;
        this.remarques = remarques;
        this.reports = reports;
        this.isEditableByStudent = isEditableByStudent;
        this.isAcceptedByEnseignant = isAcceptedByEnseignant;
    }


    protected FichePFE(Parcel in) {
        id = in.readInt();
        id_etudiant = in.readLong();
        id_enseignant = in.readInt();
        functions = in.createTypedArrayList(Fonctionalite.CREATOR);
        problematiques = in.createTypedArrayList(Problematique.CREATOR);
        adress_entreprise = in.readParcelable(Adres.class.getClassLoader());
        student = in.readParcelable(Student.class.getClassLoader());
        enseignant = in.readParcelable(Enseignant.class.getClassLoader());
        titre = in.readString();
        description = in.readString();
        nom_entreprise = in.readString();
        tech = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        isAcceptedByAgent = in.readByte() != 0;
        reports = in.createTypedArrayList(Media.CREATOR);
        isEditableByStudent = in.readByte() != 0;
        isAcceptedByEnseignant = in.readByte() != 0;
    }

    public static final Creator<FichePFE> CREATOR = new Creator<FichePFE>() {
        @Override
        public FichePFE createFromParcel(Parcel in) {
            return new FichePFE(in);
        }

        @Override
        public FichePFE[] newArray(int size) {
            return new FichePFE[size];
        }
    };

    public List<Media> getReports() {
        return reports;
    }

    public void setReports(List<Media> reports) {
        this.reports = reports;
    }


    public FichePFE(int id, long id_etudiant, int id_enseignant, List<Fonctionalite> functions, List<Problematique> problematiques, Adres adress_entreprise, Student student, Enseignant enseignant, String titre, String description, String nom_entreprise, String tech, String created_at, String updated_at, boolean isAcceptedByAgent, List<Remarques> remarques, boolean isEditableByStudent, boolean isAcceptedByEnseignant) {
        this.id = id;
        this.id_etudiant = id_etudiant;
        this.id_enseignant = id_enseignant;
        this.functions = functions;
        this.problematiques = problematiques;
        this.adress_entreprise = adress_entreprise;
        this.student = student;
        this.enseignant = enseignant;
        this.titre = titre;
        this.description = description;
        this.nom_entreprise = nom_entreprise;
        this.tech = tech;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isAcceptedByAgent = isAcceptedByAgent;
        this.remarques = remarques;
        this.isEditableByStudent = isEditableByStudent;
        this.isAcceptedByEnseignant = isAcceptedByEnseignant;
    }

    public List<Remarques> getRemarques() {
        return remarques;
    }

    public void setRemarques(List<Remarques> remarques) {
        this.remarques = remarques;
    }

    public FichePFE(int id, long id_etudiant, int id_enseignant, List<Fonctionalite> functions, List<Problematique> problematiques, Adres adress_entreprise, Student student, Enseignant enseignant, String titre, String description, String nom_entreprise, String tech, String created_at, String updated_at, boolean isAcceptedByAgent, boolean isEditableByStudent, boolean isAcceptedByEnseignant) {
        this.id = id;
        this.id_etudiant = id_etudiant;
        this.id_enseignant = id_enseignant;
        this.functions = functions;
        this.problematiques = problematiques;
        this.adress_entreprise = adress_entreprise;
        this.student = student;
        this.enseignant = enseignant;
        this.titre = titre;
        this.description = description;
        this.nom_entreprise = nom_entreprise;
        this.tech = tech;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isAcceptedByAgent = isAcceptedByAgent;
        this.isEditableByStudent = isEditableByStudent;
        this.isAcceptedByEnseignant = isAcceptedByEnseignant;
    }




    public boolean isAcceptedByAgent() {
        return isAcceptedByAgent;
    }

    public void setAcceptedByAgent(boolean acceptedByAgent) {
        isAcceptedByAgent = acceptedByAgent;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    boolean isEditableByStudent;

    boolean isAcceptedByEnseignant;

    @Override
    public String toString() {
        return "FichePFE{" +
                "id=" + id +
                ", id_etudiant=" + id_etudiant +
                ", id_enseignant=" + id_enseignant +
                ", functions=" + functions +
                ", problematiques=" + problematiques +
                ", adress_entreprise=" + adress_entreprise +
                ", student=" + student +
                ", enseignant=" + enseignant +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", nom_entreprise='" + nom_entreprise + '\'' +
                ", tech='" + tech + '\'' +
                ", isEditableByStudent=" + isEditableByStudent +
                ", isAcceptedByEnseignant=" + isAcceptedByEnseignant +
                '}';
    }

    public FichePFE() {
    }

    public FichePFE(int id, long id_etudiant, int id_enseignant, List<Fonctionalite> functions, List<Problematique> problematiques, Adres adress_entreprise, Student student, Enseignant enseignant, String titre, String description, String nom_entreprise, String tech, boolean isEditableByStudent, boolean isAcceptedByEnseignant) {
        this.id = id;
        this.id_etudiant = id_etudiant;
        this.id_enseignant = id_enseignant;
        this.functions = functions;
        this.problematiques = problematiques;
        this.adress_entreprise = adress_entreprise;
        this.student = student;
        this.enseignant = enseignant;
        this.titre = titre;
        this.description = description;
        this.nom_entreprise = nom_entreprise;
        this.tech = tech;
        this.isEditableByStudent = isEditableByStudent;
        this.isAcceptedByEnseignant = isAcceptedByEnseignant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(long id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public int getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(int id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public List<Fonctionalite> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Fonctionalite> functions) {
        this.functions = functions;
    }

    public List<Problematique> getProblematiques() {
        return problematiques;
    }

    public void setProblematiques(List<Problematique> problematiques) {
        this.problematiques = problematiques;
    }

    public Adres getAdress_entreprise() {
        return adress_entreprise;
    }

    public void setAdress_entreprise(Adres adress_entreprise) {
        this.adress_entreprise = adress_entreprise;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom_entreprise() {
        return nom_entreprise;
    }

    public void setNom_entreprise(String nom_entreprise) {
        this.nom_entreprise = nom_entreprise;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public boolean isEditableByStudent() {
        return isEditableByStudent;
    }

    public void setEditableByStudent(boolean editableByStudent) {
        isEditableByStudent = editableByStudent;
    }

    public boolean isAcceptedByEnseignant() {
        return isAcceptedByEnseignant;
    }

    public void setAcceptedByEnseignant(boolean acceptedByEnseignant) {
        isAcceptedByEnseignant = acceptedByEnseignant;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(id_etudiant);
        dest.writeInt(id_enseignant);
        dest.writeTypedList(functions);
        dest.writeTypedList(problematiques);
        dest.writeParcelable(adress_entreprise, flags);
        dest.writeParcelable(student, flags);
        dest.writeParcelable(enseignant, flags);
        dest.writeString(titre);
        dest.writeString(description);
        dest.writeString(nom_entreprise);
        dest.writeString(tech);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeByte((byte) (isAcceptedByAgent ? 1 : 0));
        dest.writeTypedList(reports);
        dest.writeByte((byte) (isEditableByStudent ? 1 : 0));
        dest.writeByte((byte) (isAcceptedByEnseignant ? 1 : 0));
    }
}