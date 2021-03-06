package com.project.MoodMApp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;;
import android.widget.TextView;
import com.project.MoodMApp.MainActivity;
import com.project.MoodMApp.R;
import android.support.v4.app.ListFragment;
import com.project.MoodMApp.assets.DatabaseHandler;
import com.project.MoodMApp.assets.FragmentNote;
import com.project.MoodMApp.assets.NoteAdapter;

public class NewsFragment extends ListFragment {

    public static NoteAdapter noteAdapter;
    public static ListView noteListView;

    DatabaseHandler databaseHandler;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        databaseHandler = new DatabaseHandler(getActivity());

        noteAdapter = new NoteAdapter(getActivity(),R.layout.list_item,MainActivity.list);

        noteListView = (ListView)rootView.findViewById(android.R.id.list);

        setListAdapter(noteAdapter);

        return rootView;
    }
}
