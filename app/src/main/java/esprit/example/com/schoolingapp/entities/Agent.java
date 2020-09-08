package esprit.example.com.schoolingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class Agent implements Parcelable {
    long id;
    String identifiant;
    String name, email, password, last_name;
    boolean isActive;
    long phone;
    Date birth_date ;

    protected Agent(Parcel in) {
        id = in.readLong();
        identifiant = in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        last_name = in.readString();
        isActive = in.readByte() != 0;
        phone = in.readLong();
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        @Override
        public Agent createFromParcel(Parcel in) {
            return new Agent(in);
        }

        @Override
        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Agent() {
    }

    public long getId() {
        return id;
    }

    public String getIdentfiant() {
        return identifiant;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLast_name() {
        return last_name;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getTelephone() {
        return phone;
    }

    public Agent(long id, String identfiant, String name, String email, String password, String last_name, boolean isActive, long telephone, Date birth_date) {
        this.id = id;
        this.identifiant = identfiant;
        this.name = name;
        this.email = email;
        this.password = password;
        this.last_name = last_name;
        this.isActive = isActive;
        this.phone = telephone;
        this.birth_date = birth_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(identifiant);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(last_name);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeLong(phone);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", identfiant='" + identifiant + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", last_name='" + last_name + '\'' +
                ", isActive=" + isActive +
                ", telephone=" + phone +
                ", birth_date=" + birth_date +
                '}';
    }
}
