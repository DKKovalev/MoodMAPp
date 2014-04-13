package com.project.MoodMApp.assets;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.project.MoodMApp.R;

public class FragmentNote extends Fragment {

    TextView title;
    TextView comment;
    TextView date;

    ImageView photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addView = inflater.inflate(R.layout.fragment_note, null);
        title = (TextView)addView.findViewById(R.id.text_view_title);

        Bundle bundle = getArguments();
        if(bundle != null){
            String titleText = bundle.getString("title", "blah");
            title.setText(titleText);
        }

        return addView;
    }
}