package esprit.example.com.schoolingapp.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Remarques {
    int id;
    FichePFE fichePFE;
    long fiche_id;
    long id_person;
    String created_at;
    String updated_at;
    @SerializedName("messages")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remarques remarques = (Remarques) o;
        return message.equals(remarques.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public Remarques() {
    }

    public Remarques(int id, FichePFE fichePFE, long fiche_id, long id_person, String created_at, String updated_at, String message) {
        this.id = id;
        this.fichePFE = fichePFE;
        this.fiche_id = fiche_id;
        this.id_person = id_person;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Remarques{" +
                "id=" + id +
                ", fichePFE=" + fichePFE +
                ", fiche_id=" + fiche_id +
                ", id_person=" + id_person +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    public long getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(long fiche_id) {
        this.fiche_id = fiche_id;
    }

    public long getId_person() {
        return id_person;
    }

    public void setId_person(long id_person) {
        this.id_person = id_person;
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
}
