package thibault_roturier.moodtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Thibault on 18/01/2018.
 * Project for the Ministry of Happiness and Good Mood (OpenClassrooms).
 */

public class FragmentSmileyScreen extends Fragment {
    private int image;
    private int background;

    public static FragmentSmileyScreen newInstance(int background, int resImage) {
        FragmentSmileyScreen fragment = new FragmentSmileyScreen();
        Bundle args = new Bundle();
        args.putInt("image", resImage);
        args.putInt("background", background);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getInt("image", 0);
        background = getArguments().getInt("background");
    }

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
