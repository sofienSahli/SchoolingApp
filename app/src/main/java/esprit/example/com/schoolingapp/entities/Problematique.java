package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Problematique implements Parcelable {
    @ColumnInfo(name = "prob")
    String prob;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_prob")
    int id_prob;
    @ForeignKey(entity = FichePFE.class, childColumns = "fiche_id", parentColumns = "id")
    int fiche_id;
    boolean is_solved;

    public Problematique(String prob, int id_prob, int fiche_id, boolean is_solved, FichePFE fichePFE) {
        this.prob = prob;
        this.id_prob = id_prob;
        this.fiche_id = fiche_id;
        this.is_solved = is_solved;
        this.fichePFE = fichePFE;
    }


    protected Problematique(Parcel in) {
        prob = in.readString();
        id_prob = in.readInt();
        fiche_id = in.readInt();
        is_solved = in.readByte() != 0;
    }

    public static final Creator<Problematique> CREATOR = new Creator<Problematique>() {
        @Override
        public Problematique createFromParcel(Parcel in) {
            return new Problematique(in);
        }

        @Override
        public Problematique[] newArray(int size) {
            return new Problematique[size];
        }
    };

    public boolean isIs_solved() {
        return is_solved;
    }

    public void setIs_solved(boolean is_solved) {
        this.is_solved = is_solved;
    }


    public Problematique(String s) {
        this.prob = s;
    }

    public Problematique(String toString, int fiche_id) {
        this.prob = toString;
        this.fiche_id = fiche_id;
    }


    public int getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(int fiche_id) {
        this.fiche_id = fiche_id;
    }

    @Ignore
    FichePFE fichePFE;

    public Problematique() {
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    public int getId_prob() {
        return id_prob;
    }

    public void setId_prob(int id_prob) {
        this.id_prob = id_prob;
    }

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    @Override
    public String toString() {
        return "Problematique{" +
                "prob='" + prob + '\'' +
                ", id_prob=" + id_prob +
                ", fiche_id=" + fiche_id +
                ", is_solved=" + is_solved +
                ", fichePFE=" + fichePFE +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prob);
        dest.writeInt(id_prob);
        dest.writeInt(fiche_id);
        dest.writeByte((byte) (is_solved ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problematique that = (Problematique) o;
        return prob.toLowerCase().equals(that.prob.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(prob);
    }
}
