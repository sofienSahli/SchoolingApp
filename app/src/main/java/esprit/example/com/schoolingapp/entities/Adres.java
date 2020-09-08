package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Adres implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int identifiant;
    @ColumnInfo(name = "rue")
    String rue;

    @ColumnInfo(name = "ville")
    String ville;
    @ColumnInfo(name = "postal_code")
    @SerializedName("postal_code")
    int code_postal;
    @ForeignKey(entity = FichePFE.class, parentColumns = "id", childColumns = "id_fiche")
    int id_fiche;
    @SerializedName("longi")
    @ColumnInfo(name = "longi")

    double longi;
    @SerializedName("lati")
    @ColumnInfo(name = "lati")
    double lati ;

    public Adres(int identifiant, String rue, String ville, int code_postal, int id_fiche, double longi, double lati) {
        this.identifiant = identifiant;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.id_fiche = id_fiche;
        this.longi = longi;
        this.lati = lati;
    }


    protected Adres(Parcel in) {
        identifiant = in.readInt();
        rue = in.readString();
        ville = in.readString();
        code_postal = in.readInt();
        id_fiche = in.readInt();
        longi = in.readDouble();
        lati = in.readDouble();
    }

    public static final Creator<Adres> CREATOR = new Creator<Adres>() {
        @Override
        public Adres createFromParcel(Parcel in) {
            return new Adres(in);
        }

        @Override
        public Adres[] newArray(int size) {
            return new Adres[size];
        }
    };

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }


    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getId_fiche() {
        return id_fiche;
    }

    public void setId_fiche(int id_fiche) {
        this.id_fiche = id_fiche;
    }

    public Adres() {
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(identifiant);
        dest.writeString(rue);
        dest.writeString(ville);
        dest.writeInt(code_postal);
        dest.writeInt(id_fiche);
        dest.writeDouble(longi);
        dest.writeDouble(lati);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return identifiant == adres.identifiant;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "identifiant=" + identifiant +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                ", code_postal=" + code_postal +
                ", id_fiche=" + id_fiche +
                ", longi=" + longi +
                ", lati=" + lati +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }
}
