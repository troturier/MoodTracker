package thibault_roturier.moodtracker.Models;

import java.util.Date;

/**
 * Model class for creating and manipulating a mood object
 */
public class Mood {
    private Date date;
    private int moodState;
    private String comment;

    /**
     * Constructor of the mood class taking into account all the attributes
     * @param dateP Date of the mood (yyyy/MM/dd)
     * @param moodStateP State of the mood
     * @param commentP Comment related to the mood
     */
    public Mood(Date dateP, int moodStateP, String commentP){
        this.date = dateP;
        this.moodState = moodStateP;
        this.comment = commentP;
    }

    /**
     * Constructor of the mood class not taking into account comment
     * @param dateP Date of the mood (yyyy/MM/dd)
     * @param moodStateP State of the mood
     */
    public Mood(Date dateP, int moodStateP){
        this.date = dateP;
        this.moodState = moodStateP;
    }

    /**
     * Constructor of the mood class to create an empty object
     */
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
