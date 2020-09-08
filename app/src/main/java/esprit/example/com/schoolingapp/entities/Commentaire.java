package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Commentaire implements Parcelable {
    int id ;
    long id_enseignant;
    String messages;
    int media_id;
    String created_at;
    String updated_at;
    Enseignant enseignant;

    protected Commentaire(Parcel in) {
        id = in.readInt();
        id_enseignant = in.readLong();
        messages = in.readString();
        media_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        enseignant = in.readParcelable(Enseignant.class.getClassLoader());
    }

    public static final Creator<Commentaire> CREATOR = new Creator<Commentaire>() {
        @Override
        public Commentaire createFromParcel(Parcel in) {
            return new Commentaire(in);
        }

        @Override
        public Commentaire[] newArray(int size) {
            return new Commentaire[size];
        }
    };

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public static Creator<Commentaire> getCREATOR() {
        return CREATOR;
    }

    public Commentaire() {
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", id_enseignant=" + id_enseignant +
                ", messages='" + messages + '\'' +
                ", media_id=" + media_id +
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

    public long getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(long id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
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

    public Commentaire(int id, long id_enseignant, String messages, int media_id, String created_at, String updated_at) {
        this.id = id;
        this.id_enseignant = id_enseignant;
        this.messages = messages;
        this.media_id = media_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(id_enseignant);
        dest.writeString(messages);
        dest.writeInt(media_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeParcelable(enseignant, flags);
    }
}
