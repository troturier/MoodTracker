package thibault_roturier.moodtracker;

import java.util.Date;

/**
 * Created by Thibault on 26/01/2018.
 * Project for the Ministry of Happiness and Good Mood (OpenClassrooms).
 */

public class Mood {
    private Date date;
    private int moodState;
    private String comment;

    public Mood(Date dateP, int moodStateP, String commentP){
        this.date = dateP;
        this.moodState = moodStateP;
        this.comment = commentP;
    }

    public Mood(Date dateP, int moodStateP){
        this.date = dateP;
        this.moodState = moodStateP;
    }

    public Mood() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMoodState() {
        return moodState;
    }

    public void setMoodState(int moodState) {
        this.moodState = moodState;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
