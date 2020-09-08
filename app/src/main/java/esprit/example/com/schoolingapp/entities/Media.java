package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Media implements Parcelable {

    int id;
    String type;
    String file;
    String file_extension;
    String created_at;
    String updated_at;
    int filable_id;
    String filable_type;
    String titre ;
    List<Commentaire> commentaires;

    public Media(int id, String type, String file, String file_extension, String created_at, String updated_at, int filable_id, String filable_type, String titre, List<Commentaire> commentaires) {
        this.id = id;
        this.type = type;
        this.file = file;
        this.file_extension = file_extension;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.filable_id = filable_id;
        this.filable_type = filable_type;
        this.titre = titre;
        this.commentaires = commentaires;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", file='" + file + '\'' +
                ", file_extension='" + file_extension + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", filable_id=" + filable_id +
                ", filable_type='" + filable_type + '\'' +
                ", titre='" + titre + '\'' +
                ", commentaires=" + commentaires +
                '}';
    }

    protected Media(Parcel in) {
        id = in.readInt();
        type = in.readString();
        file = in.readString();
        file_extension = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        filable_id = in.readInt();
        filable_type = in.readString();
        titre = in.readString();
        commentaires = in.createTypedArrayList(Commentaire.CREATOR);
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }


    public Media(int id, String type, String file, String file_extension, String created_at, String updated_at, int filable_id, String filable_type, String titre) {
        this.id = id;
        this.type = type;
        this.file = file;
        this.file_extension = file_extension;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.filable_id = filable_id;
        this.filable_type = filable_type;
        this.titre = titre;
    }




    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setFilable_id(int filable_id) {
        this.filable_id = filable_id;
    }

    public void setFilable_type(String filable_type) {
        this.filable_type = filable_type;
    }

    public Media() {

    }

    public Media(int id, String type, String file, String file_extension, String created_at, String updated_at, int filable_id, String filable_type) {
        this.id = id;
        this.type = type;
        this.file = file;
        this.file_extension = file_extension;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.filable_id = filable_id;
        this.filable_type = filable_type;
    }

    public int getFilable_id() {
        return filable_id;
    }

    public String getFilable_type() {
        return filable_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(file);
        dest.writeString(file_extension);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeInt(filable_id);
        dest.writeString(filable_type);
        dest.writeString(titre);
        dest.writeTypedList(commentaires);
    }
}
