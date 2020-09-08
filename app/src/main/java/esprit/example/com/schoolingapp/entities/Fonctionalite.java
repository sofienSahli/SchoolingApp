package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Fonctionalite implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    int id_fonct;
    @ColumnInfo(name = "func")
    String func;
    @Ignore
    FichePFE fichePFE;
    @ForeignKey(entity = FichePFE.class , childColumns = "fiche_id", parentColumns = "id")
    int fiche_id;
    boolean is_implemented;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fonctionalite that = (Fonctionalite) o;
        return func.equals(that.func);
    }

    @Override
    public int hashCode() {
        return Objects.hash(func);
    }

    @Override
    public String toString() {
        return "Fonctionalite{" +
                "id_fonct=" + id_fonct +
                ", func='" + func + '\'' +
                ", fichePFE=" + fichePFE +
                ", fiche_id=" + fiche_id +
                ", is_implemented=" + is_implemented +
                '}';
    }

    public Fonctionalite(int id_fonct, String func, FichePFE fichePFE, int fiche_id, boolean is_implemented) {
        this.id_fonct = id_fonct;
        this.func = func;
        this.fichePFE = fichePFE;
        this.fiche_id = fiche_id;
        this.is_implemented = is_implemented;
    }

    protected Fonctionalite(Parcel in) {
        id_fonct = in.readInt();
        func = in.readString();
        fiche_id = in.readInt();
        is_implemented = in.readByte() != 0;
    }

    public static final Creator<Fonctionalite> CREATOR = new Creator<Fonctionalite>() {
        @Override
        public Fonctionalite createFromParcel(Parcel in) {
            return new Fonctionalite(in);
        }

        @Override
        public Fonctionalite[] newArray(int size) {
            return new Fonctionalite[size];
        }
    };

    public boolean isIs_implemented() {
        return is_implemented;
    }

    public void setIs_implemented(boolean is_implemented) {
        this.is_implemented = is_implemented;
    }


    public Fonctionalite(String toString, int fiche_id) {
    this.fiche_id = fiche_id ;
    this.func = toString ;
    }




    public int getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(int fiche_id) {
        this.fiche_id = fiche_id;
    }

    public Fonctionalite() {
    }
    public Fonctionalite(String s) {
    this.func = s ;
    }

    public int getId_fonct() {
        return id_fonct;
    }

    public void setId_fonct(int id_fonct) {
        this.id_fonct = id_fonct;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_fonct);
        dest.writeString(func);
        dest.writeInt(fiche_id);
        dest.writeByte((byte) (is_implemented ? 1 : 0));
    }
}
