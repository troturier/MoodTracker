package thibault_roturier.moodtracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerticalViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        vpPager.setCurrentItem(3);

    }

    /**
     * Displays an alert dialog box containing an EditText object
     * @param view View in which the alert dialog box should appear
     */
    public void commentInput (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Commentaire");

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
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


