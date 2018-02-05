package thibault_roturier.moodtracker.Activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.Date;

import thibault_roturier.moodtracker.Database.DBHandler;
import thibault_roturier.moodtracker.Models.Mood;
import thibault_roturier.moodtracker.R;

public class A_MoodTracker extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    Date date = new Date();
    int moodState = 3;
    float x1,x2;
    float y1, y2;
    private Mood CurrMood = new Mood();
    ImageSwitcher imsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If this is the first time the application is launched today, we create a new line in the database
        if(db.getMood(date) == null) {
            Mood mood = new Mood(date, 3);
            db.addMood(mood);
        }
        // We retrieve the mood of the day and assign mood state to the corresponding variable
        CurrMood = db.getMood(date);
        moodState = CurrMood.getMoodState();

        // Setting the ImageSwitcher factory
        imsw = findViewById(R.id.imageSwitcherMain);
        imsw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        // Adding animations to the ImageSwitcher
        Animation in = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        imsw.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        imsw.setOutAnimation(out);

        // We change the displayed mood according to the moodState variable
        moodStateChange(moodState);
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

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(CurrMood.getComment());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.save_comment, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                CurrMood.setComment(m_Text);
                // Update the comment of the mood in the database
                db.updateMood(CurrMood);
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

    /**
     * Detect common gestures performed on the screen
     * @param touchevent A touch event
     * @return Boolean
     */
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                // if UP to Down swipe event on screen
                if (y1 < y2)
                {
                    if(moodState > 0){
                        moodState = moodState - 1;
                        // Changing the display accordingly
                        moodStateChange(moodState);
                        // Updating the database
                        CurrMood.setMoodState(moodState);
                        db.updateMood(CurrMood);
                    }
                }

                // if Down to UP swipe event on screen
                if (y1 > y2)
                {
                    if(moodState < 4){
                        moodState = moodState + 1;
                        // Changing the display accordingly
                        moodStateChange(moodState);
                        // Updating the database
                        CurrMood.setMoodState(moodState);
                        db.updateMood(CurrMood);
                    }
                }
                break;
            }
        }
        return false;
    }

    /**
     * Change the background color and smiley displayed on the main activity according to the mood state
     * @param moodState A mood state id
     */
    public void moodStateChange (int moodState){
        RelativeLayout background = findViewById(R.id.backgroundMain);

        // Definition of the smiley image and the background color of the activity according to the  moodState
        switch (moodState) {
            case 0:
                imsw.setImageResource(R.mipmap.smiley_sad);
                changeBackgroundColor(background, getResources().getColor(R.color.faded_red));
                break;
            case 1:
                imsw.setImageResource(R.mipmap.smiley_disappointed);
                changeBackgroundColor(background, getResources().getColor(R.color.warm_grey));
                break;
            case 2:
                imsw.setImageResource(R.mipmap.smiley_normal);
                changeBackgroundColor(background, getResources().getColor(R.color.cornflower_blue_65));
                break;
            case 3:
                imsw.setImageResource(R.mipmap.smiley_happy);
                changeBackgroundColor(background, getResources().getColor(R.color.light_sage));
                break;
            case 4:
                imsw.setImageResource(R.mipmap.smiley_super_happy);
                changeBackgroundColor(background, getResources().getColor(R.color.banana_yellow));
                break;
            }
    }

    /**
     * Returns the backgroundColor of a View
     * @param view A view object
     * @return Color int
     */
    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            return colorDrawable.getColor();
        }
        return 0;
    }

    /**
     * Changes the backgroundColor of a view with a smooth transition and plays a sound
     * @param background A view object
     * @param color Final color int
     */
    public void changeBackgroundColor (View background, int color) {
        ObjectAnimator anim = ObjectAnimator.ofInt(background, "backgroundColor", getBackgroundColor(background), color);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatCount(0);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setDuration(1000);
        anim.start();
        // Plays a transition sound
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.swipe);
        mp.start();
    }
}


