package thibault_roturier.moodtracker.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import thibault_roturier.moodtracker.Database.DBHandler;
import thibault_roturier.moodtracker.Models.Mood;
import thibault_roturier.moodtracker.R;

public class A_MoodTracker extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If this is the first time the application is launched today, we create a new line in the database
        if(db.getMood(date) == null) {
            Mood mood = new Mood(date, 3);
            db.addMood(mood);
        }
        final Mood CurrMood = db.getMood(date);

    }

    /**
     * Start activity_history
     * @param view Context
     */
    public void showHistory(View view){
        Intent intent = new Intent(A_MoodTracker.this, A_History.class);
        startActivity(intent);
    }

    /**
     * Displays an alert dialog box containing an EditText object
     * @param view View in which the alert dialog box should appear
     */
    public void commentInput (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.comment);
        final Mood mood = db.getMood(date);

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(mood.getComment());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.save_comment, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                mood.setComment(m_Text);
                // Update the comment of the mood in the database
                db.updateMood(mood);
                // Play a sound when the user validate his comment
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.comment);
                mp.start();
                // Display a toast message
                Toast.makeText(getApplicationContext(), R.string.comment_saved, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}


