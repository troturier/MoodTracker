package thibault_roturier.moodtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Class to create a new pager fragment to display a background color and a smiley
 */
public class FragmentSmileyScreen extends Fragment {
    private int image;
    private int background;

    /**
     * Creates a new instance of a smiley screen fragment which is composed of a background colour and a picture
     * @param background Background color
     * @param resImage Image from resource files to be displayed in the foreground
     * @return FragmentSmileyScreen
     */
    public static FragmentSmileyScreen newInstance(int background, int resImage) {
        FragmentSmileyScreen fragment = new FragmentSmileyScreen();
        Bundle args = new Bundle();
        args.putInt("image", resImage);
        args.putInt("background", background);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes a new smiley screen fragment
     * @param savedInstanceState Bundle object containing the activity's previously saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getInt("image", 0);
        background = getArguments().getInt("background");
    }

    /**
     * Builds the view corresponding to the specified smiley screen fragment
     * @param inflater LayoutInflater instance
     * @param container ViewGroup instance
     * @param savedInstanceState Bundle object containing the activity's previously saved state
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smiley_screen, container, false);
        view.setBackgroundColor(getResources().getColor(background));
        ImageView imageView = view.findViewById(R.id.imgMain);
        imageView.setImageResource(image);
        return view;
    }
}
