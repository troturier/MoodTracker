package thibault_roturier.moodtracker.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import thibault_roturier.moodtracker.Database.DBHandler;
import thibault_roturier.moodtracker.Models.Mood;
import thibault_roturier.moodtracker.R;

import static java.lang.Math.toIntExact;

/**
 * Class to manage the history activity
 */
public class A_History extends AppCompatActivity {

    // We retrieve today's date
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        DBHandler db = new DBHandler(this);

        // We retrieve the last recorded moods
        List<Mood> moods = db.getLastMoods();
        // We reverse this list
        Collections.reverse(moods);

        // Variable that will serve us to call the different placeholders from the history activity
        int iLL = 1;
        boolean test = false;
        while(!test){
            // We go through the list of moods
            for(int i = 0; i<moods.size(); i++){
                // We check that this is not the mood of today
                if(moods.get(i).daysDifference(date) != 0) {
                    // We retrieve the next placeholder
                    String history_ph = "history_placeholder_" + String.valueOf(iLL);
                    int resID = getResources().getIdentifier(history_ph, "id", getPackageName());
                    LinearLayout history_p = findViewById(resID);
                    // We add the current mood to this placeholder
                    Mood moodA = moods.get(i);
                    addLayout(moodA, history_p);
                    // We move to the next placeholder
                    iLL = iLL + 1;
                }
            }
            test=true;
        }
    }

    /**
     * Add a new item in the history activity from the layout history_item
     * @param mood A Mood object
     * @param history_place_holder A placeholder of the history activity (history_placeholder_#)
     */
    private void addLayout(final Mood mood, LinearLayout history_place_holder){
        View history_item = LayoutInflater.from(this).inflate(R.layout.history_item, history_place_holder, false);

        RelativeLayout history_item_RL = history_item.findViewById(R.id.history_item_RL);
        // Label of the number of days
        TextView history_item_Tv = history_item.findViewById(R.id.history_item_Tv);
        // Space not covered by the bar in the placeholder
        Space history_item_Sp = history_item.findViewById(R.id.history_item_Sp);
        // ImageButton used to display mood comment in the form of toast
        ImageButton history_item_Ib = history_item.findViewById(R.id.history_item_Ib);

        // If there is no comment the ImageButton will not be displayed
        if(mood.getComment() == null || TextUtils.isEmpty(mood.getComment())){
            history_item_Ib.setVisibility(View.GONE);
        }else{
            // Add a listener to the button to display the comment via a toast message
            history_item_Ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), mood.getComment(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Different display of the history item depending on the state of the mood
        switch (mood.getMoodState()){
            case 0:
                // We define the background color of the history item
                history_item_RL.setBackgroundColor(getResources().getColor(R.color.faded_red));
                // And we set the size of the bar
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.8f);
                history_item_RL.setLayoutParams(params1);
                history_item_Sp.setLayoutParams(params2);
                break;
            case 1:
                history_item_RL.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.4f);
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.6f);
                history_item_RL.setLayoutParams(params3);
                history_item_Sp.setLayoutParams(params4);
                break;
            case 2:
                history_item_RL.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.6f);
                LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.4f);
                history_item_RL.setLayoutParams(params5);
                history_item_Sp.setLayoutParams(params6);
                break;
            case 3:
                history_item_RL.setBackgroundColor(getResources().getColor(R.color.light_sage));
                LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.8f);
                LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);
                history_item_RL.setLayoutParams(params7);
                history_item_Sp.setLayoutParams(params8);
                break;
            case 4:
                history_item_RL.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                LinearLayout.LayoutParams params9 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                LinearLayout.LayoutParams params10 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f);
                history_item_RL.setLayoutParams(params9);
                history_item_Sp.setLayoutParams(params10);
                break;
        }

        // We calculate the number of days between the date of the current mood and today
        long daysDiff = mood.daysDifference(date);

        // We specify a different label depending on the number of days that have elapsed
        switch (toIntExact(daysDiff)){
            case 1:
                history_item_Tv.setText(R.string.yesterday);
                break;
            case 2:
                history_item_Tv.setText(R.string.before_yesterday);
                break;
            case 7:
                history_item_Tv.setText(R.string.a_week_ago);
                break;
            default:
                String str = getString(R.string.days_ago_1) + toIntExact(daysDiff) + getString(R.string.spc_days);
                history_item_Tv.setText(str);
                break;
        }

        // And we add this new element to the history activity corresponding placeholder
        history_place_holder.addView(history_item);
    }
}
