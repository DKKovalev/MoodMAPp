package com.project.MoodMApp.assets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.project.MoodMApp.MainActivity;
import com.project.MoodMApp.R;
import com.project.MoodMApp.assets.animations.ZoomInActivity;
import com.project.MoodMApp.assets.animations.ZoomOutActivity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by PsichO on 05.04.2014.
 */
public class NoteAdapter extends ArrayAdapter<Note>  {

    private ArrayList<Note> notes;

    Animation animationZoomIn;

    LayoutInflater inflater;

    private Context context;

    private static View view;

    public NoteAdapter(Context context, int textViewResourceId, ArrayList<Note> notes){
        super(context, textViewResourceId, notes);
        this.context = context;
        this.notes = notes;
        inflater = LayoutInflater.from(context);

    }

    public View getView(int pos, View convertView, ViewGroup parent){
        ViewHolder holder;



        this.view = convertView;

        animationZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.mood = (TextView)convertView.findViewById(R.id.mood);
            holder.comment = (TextView)convertView.findViewById(R.id.comment);
            holder.photo = (ImageView)convertView.findViewById(R.id.photo);
            holder.date = (TextView)convertView.findViewById(R.id.date);
            holder.lat = (TextView)convertView.findViewById(R.id.lat);
            holder.lng = (TextView)convertView.findViewById(R.id.lng);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Note note = notes.get(pos);
        if(note != null) {
            holder.mood.setText(note.getMood());
            holder.comment.setText(note.getComment());

            if(note.image != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bmp = BitmapFactory.decodeFile(note.getImage(), options);

                /*if(bmp != null){
                    photo.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, true));
                } */

                holder.photo.setImageBitmap(bmp);
            }

            holder.lat.setText(note.getLat().toString());
            holder.lng.setText(note.getLng().toString());
            holder.date.setText(note.getDate().toString());
        }

        return convertView;
    }


    static class ViewHolder
    {
        TextView mood;
        TextView comment;
        TextView date;
        ImageView photo;
        TextView lat;
        TextView lng;
    }

}
