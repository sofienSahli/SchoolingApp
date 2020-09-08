package esprit.example.com.schoolingapp.entities;

import java.util.List;

public class ActivityDay {
    int id;
    String groupe;
    Days days;
    List<String> cours;

    public ActivityDay() {
    }

    public ActivityDay(int id, String groupe, Days days, List<String> cours) {
        this.id = id;
        this.groupe = groupe;
        this.days = days;
        this.cours = cours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public List<String> getCours() {
        return cours;
    }

    public void setCours(List<String> cours) {
        this.cours = cours;
    }
}
