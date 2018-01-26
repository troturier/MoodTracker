package thibault_roturier.moodtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thibault on 26/01/2018.
 * Project for the Ministry of Happiness and Good Mood (OpenClassrooms).
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
}
