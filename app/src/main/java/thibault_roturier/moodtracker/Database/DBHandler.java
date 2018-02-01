package thibault_roturier.moodtracker.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import thibault_roturier.moodtracker.Models.Mood;

/**
 * Class required for creation and access to the database
 * The database containing only one table the DAO class of it is contained in this class
 */
public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MOODDB";
    private static final String TABLE_MOOD = "MOOD";
    private static final String KEY_DATE = "date";
    private static final String KEY_MOODSTATE = "moodState";
    private static final String KEY_COMMENT = "comment";
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOOD_TABLE = "CREATE TABLE " + TABLE_MOOD + "("
                + KEY_DATE + " TEXT PRIMARY KEY," + KEY_MOODSTATE + " INTEGER,"
                + KEY_COMMENT + " TEXT" + ")";
        db.execSQL(CREATE_MOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOD);
        // Creating tables again
        onCreate(db);
    }

    /**
     * Adding a new mood to the database
     * @param mood Mood object
     */
    public void addMood(Mood mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, format.format(mood.getDate())); // Mood date
        values.put(KEY_MOODSTATE, mood.getMoodState()); // Mood state id
        values.put(KEY_COMMENT, mood.getComment()); // Mood comment
        // Inserting Row
        db.insert(TABLE_MOOD, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting one mood from the database
     * @param date Date of the requested mood
     * @return Mood object
     */
    public Mood getMood(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_MOOD, new String[] { KEY_DATE,
                        KEY_MOODSTATE, KEY_COMMENT }, KEY_DATE + "=?",
                new String[] { format.format(date) }, null, null, null, null);
        Mood mood = null;
        if (cursor.moveToFirst()){
        try {
            mood = new Mood(format.parse(cursor.getString(0)),
                    cursor.getInt(1), cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }}
        return mood;
    }

    /**
     * Retrieves the last 8 moods of the database
     * @return A list of 8 Mood objects
     */
    public List<Mood> getLastMoods() {
        List<Mood> moodList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MOOD + " ORDER BY date DESC " + "LIMIT 8";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mood mood = new Mood();
                try {
                    mood.setDate(format.parse(cursor.getString(0)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mood.setMoodState(cursor.getInt(1));
                mood.setComment(cursor.getString(2));
                // Adding mood to list
                moodList.add(mood);
            } while (cursor.moveToNext());
        }
        return moodList;
    }

    /**
     * Updating a mood in the database
     * @param mood Mood object
     */
    public void updateMood(Mood mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, format.format(mood.getDate()));
        values.put(KEY_MOODSTATE, mood.getMoodState());
        values.put(KEY_COMMENT, mood.getComment());
        // updating row
        db.update(TABLE_MOOD, values, KEY_DATE + " = ?",
                new String[]{format.format(mood.getDate())});
    }

}
