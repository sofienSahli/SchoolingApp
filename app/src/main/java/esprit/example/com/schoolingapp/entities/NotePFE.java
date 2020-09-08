package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class NotePFE implements Parcelable {
    int id;
    double maitrise_technologie;
    double travail_equipe;
    double encadrement;
    double asseduite;
    double ergonomi;
    double total;
    long fiche_id;
    String created_at;
    String updated_at;

    public NotePFE() {

    }

    public NotePFE(int id, double maitrise_technologie, double travail_equipe, double encadrement, double asseduite, double ergonomi, double total, long fiche_id, String created_at, String updated_at) {
        this.id = id;
        this.maitrise_technologie = maitrise_technologie;
        this.travail_equipe = travail_equipe;
        this.encadrement = encadrement;
        this.asseduite = asseduite;
        this.ergonomi = ergonomi;
        this.total = total;
        this.fiche_id = fiche_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected NotePFE(Parcel in) {
        id = in.readInt();
        maitrise_technologie = in.readDouble();
        travail_equipe = in.readDouble();
        encadrement = in.readDouble();
        asseduite = in.readDouble();
        ergonomi = in.readDouble();
        total = in.readDouble();
        fiche_id = in.readLong();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<NotePFE> CREATOR = new Creator<NotePFE>() {
        @Override
        public NotePFE createFromParcel(Parcel in) {
            return new NotePFE(in);
        }

        @Override
        public NotePFE[] newArray(int size) {
            return new NotePFE[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMaitrise_technologie() {
        return maitrise_technologie;
    }

    public void setMaitrise_technologie(double maitrise_technologie) {
        this.maitrise_technologie = maitrise_technologie;
    }

    public double getTravail_equipe() {
        return travail_equipe;
    }

    public void setTravail_equipe(double travail_equipe) {
        this.travail_equipe = travail_equipe;
    }

    public double getEncadrement() {
        return encadrement;
    }

    public void setEncadrement(double encadrement) {
        this.encadrement = encadrement;
    }

    public double getAsseduite() {
        return asseduite;
    }

    public void setAsseduite(double asseduite) {
        this.asseduite = asseduite;
    }

    public double getErgonomi() {
        return ergonomi;
    }

    public void setErgonomi(double ergonomi) {
        this.ergonomi = ergonomi;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getFiche_id() {
        return fiche_id;
    }

    public void setFiche_id(long fiche_id) {
        this.fiche_id = fiche_id;
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
        dest.writeDouble(maitrise_technologie);
        dest.writeDouble(travail_equipe);
        dest.writeDouble(encadrement);
        dest.writeDouble(asseduite);
        dest.writeDouble(ergonomi);
        dest.writeDouble(total);
        dest.writeLong(fiche_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
