package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Student implements Parcelable {
    @Expose
    @SerializedName("id")
    @PrimaryKey
    long id;
    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("last_name")
    String last_name;
    @Expose
    @SerializedName("password")
    String password;
    @Expose
    @SerializedName("identifiant")
    String identifiant;
    @Expose
    @SerializedName("phone")
    long phone;
    @Expose
    @SerializedName("birth_date")
    Date birth_date;
    @Expose
    @SerializedName("email")
    String email;
    @Expose
    @SerializedName("isActive")
    boolean isActive;
    @Expose
    @SerializedName("parent_name")
    String parent_name;
    @Expose
    @SerializedName("parent_mobile_phone")
    long parent_phone;
    @Expose
    @SerializedName("bac")
    String bac;
    @Expose
    @SerializedName("bac_date")
    Date bac_date;
    @Expose
    @SerializedName("groupe_id")
    long groupe_id;
    @Ignore
   List <Media> medias ;
    @Ignore
    Groupe groupe;


    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public static Creator<Student> getCREATOR() {
        return CREATOR;
    }

    public Student(int i, String farhad, String hachad, String s, String s1, int i1, Groupe groupe) {
    }


    public Student(long id, String name, String last_name, String password, String identifiant, long phone, Date birth_date, String email, boolean isActive, String parent_name, long parent_phone, String bac, Date bac_date, long groupe_id, List<Media> medias, Groupe groupe) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.identifiant = identifiant;
        this.phone = phone;
        this.birth_date = birth_date;
        this.email = email;
        this.isActive = isActive;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.bac = bac;
        this.bac_date = bac_date;
        this.groupe_id = groupe_id;
        this.medias = medias;
        this.groupe = groupe;
    }

    protected Student(Parcel in) {
        id = in.readLong();
        name = in.readString();
        last_name = in.readString();
        password = in.readString();
        identifiant = in.readString();
        phone = in.readLong();
        email = in.readString();
        isActive = in.readByte() != 0;
        parent_name = in.readString();
        parent_phone = in.readLong();
        bac = in.readString();
        groupe_id = in.readLong();
        medias = in.createTypedArrayList(Media.CREATOR);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public long getGroupe_id() {
        return groupe_id;
    }

    public void setGroupe_id(long groupe_id) {
        this.groupe_id = groupe_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return identifiant.equals(student.identifiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }

    public Student(long id, String name, String last_name, String password, String identifiant, long phone, Date birth_date, String email, boolean isActive, String parent_name, long parent_phone, String bac, Date bac_date, Groupe groupe) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.identifiant = identifiant;
        this.phone = phone;
        this.birth_date = birth_date;
        this.email = email;
        this.isActive = isActive;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.bac = bac;
        this.bac_date = bac_date;
        this.groupe = groupe;
    }

    public Student() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public long getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(long parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getBac() {
        return bac;
    }

    public void setBac(String bac) {
        this.bac = bac;
    }

    public Date getBac_date() {
        return bac_date;
    }

    public void setBac_date(Date bac_date) {
        this.bac_date = bac_date;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(last_name);
        dest.writeString(password);
        dest.writeString(identifiant);
        dest.writeLong(phone);
        dest.writeString(email);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(parent_name);
        dest.writeLong(parent_phone);
        dest.writeString(bac);
        dest.writeLong(groupe_id);
        dest.writeTypedList(medias);
    }
}
