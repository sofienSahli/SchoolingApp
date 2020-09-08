package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "last_name")
    String last_name;

    @ColumnInfo(name = "e_mail")
    @SerializedName("email")
    String e_mail;
    @PrimaryKey
    @ColumnInfo(name = "identifiant")
    String identifiant;

    @ColumnInfo(name = "phone")
    long phone;

    public User() {
    }

    public User(int id, String name, String last_name, String e_mail, String identifiant, long phone) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.e_mail = e_mail;
        this.identifiant = identifiant;
        this.phone = phone;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        last_name = in.readString();
        e_mail = in.readString();
        identifiant = in.readString();
        phone = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(last_name);
        dest.writeString(e_mail);
        dest.writeString(identifiant);
        dest.writeLong(phone);
    }
}
