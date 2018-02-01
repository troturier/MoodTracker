package thibault_roturier.moodtracker.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import thibault_roturier.moodtracker.Database.DBHandler;
import thibault_roturier.moodtracker.Models.FragmentSmileyScreen;
import thibault_roturier.moodtracker.Models.Mood;
import thibault_roturier.moodtracker.R;

public class A_MoodTracker extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    @SuppressLint("SimpleDateFormat")
    DBHandler db = new DBHandler(this);
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final VerticalViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(3);


        // If this is the first time the application is launched today, we create a new line in the database
        if(db.getMood(date) == null) {
            Mood mood = new Mood(date, vpPager.getCurrentItem());
            db.addMood(mood);
        }
        final Mood CurrMood = db.getMood(date);

        // We define the state of mood that is displayed on the one that has already been registered in database
        vpPager.setCurrentItem(CurrMood.getMoodState());

        // We update the state of mood when the user performs a swipe on the screen
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CurrMood.setMoodState(position);
                db.updateMood(CurrMood);
            }

            @Override
            public void onPageSelected(int position) {
                CurrMood.setMoodState(position);
                db.updateMood(CurrMood);
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.swipe);
                mp.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.comment);
                mp.start();
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

   public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 5;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * Returns total number of pages.
         * @return int
         */
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        /**
         * Returns the fragment to display for a particular page.
         * @param position Position of the Fragment in the PagerAdapter
         * @return Fragment
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentSmileyScreen.newInstance(R.color.faded_red, R.mipmap.smiley_sad);
                case 1:
                    return FragmentSmileyScreen.newInstance(R.color.warm_grey, R.mipmap.smiley_disappointed);
                case 2:
                    return FragmentSmileyScreen.newInstance(R.color.cornflower_blue_65, R.mipmap.smiley_normal);
                case 3:
                    return FragmentSmileyScreen.newInstance(R.color.light_sage, R.mipmap.smiley_happy);
                case 4:
                    return FragmentSmileyScreen.newInstance(R.color.banana_yellow, R.mipmap.smiley_super_happy);
                default:
                    return null;
            }
        }
    }


}


