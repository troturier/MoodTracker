package thibault_roturier.moodtracker;

import java.util.Date;

/**
 * Model class for creating and manipulating a mood object
 */
class Mood {
    private Date date;
    private int moodState;
    private String comment;

    /**
     * Constructor of the mood class taking into account all the attributes
     * @param dateP Date of the mood (yyyy/MM/dd)
     * @param moodStateP State of the mood
     * @param commentP Comment related to the mood
     */
    Mood(Date dateP, int moodStateP, String commentP){
        this.date = dateP;
        this.moodState = moodStateP;
        this.comment = commentP;
    }

    /**
     * Constructor of the mood class not taking into account comment
     * @param dateP Date of the mood (yyyy/MM/dd)
     * @param moodStateP State of the mood
     */
    Mood(Date dateP, int moodStateP){
        this.date = dateP;
        this.moodState = moodStateP;
    }

    /**
     * Constructor of the mood class to create an empty object
     */
    Mood() {

    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    int getMoodState() {
        return moodState;
    }

    void setMoodState(int moodState) {
        this.moodState = moodState;
    }

    String getComment() {
        return comment;
    }

    void setComment(String comment) {
        this.comment = comment;
    }
}
