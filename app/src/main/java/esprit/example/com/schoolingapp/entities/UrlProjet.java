package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class UrlProjet implements Parcelable {
    int fiche_id ;
    int id ;
    String libelle;
    String updated_at;
    String created_at;
    String url_text;
    String titre ;
    String description ;

    public UrlProjet() {
    }

    public UrlProjet(int fiche_id, int id, String libelle, String updated_at, String created_at, String url_text, String titre, String description) {
        this.fiche_id = fiche_id;
        this.id = id;
        this.libelle = libelle;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.url_text = url_text;
        this.titre = titre;
        this.description = description;
    }

    protected UrlProjet(Parcel in) {
        fiche_id = in.readInt();
        id = in.readInt();
        libelle = in.readString();
        updated_at = in.readString();
        created_at = in.readString();
        url_text = in.readString();
        titre = in.readString();
        description = in.readString();
    }

    public static final Creator<UrlProjet> CREATOR = new Creator<UrlProjet>() {
        @Override
        public UrlProjet createFromParcel(Parcel in) {
            return new UrlProjet(in);
        }

        @Override
        public UrlProjet[] newArray(int size) {
            return new UrlProjet[size];
        }
    };

    public int getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(int fiche_id) {
        this.fiche_id = fiche_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUrl_text() {
        return url_text;
    }

    public void setUrl_text(String url_text) {
        this.url_text = url_text;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fiche_id);
        dest.writeInt(id);
        dest.writeString(libelle);
        dest.writeString(updated_at);
        dest.writeString(created_at);
        dest.writeString(url_text);
        dest.writeString(titre);
        dest.writeString(description);
    }
}
