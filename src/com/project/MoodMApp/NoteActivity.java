package com.project.MoodMApp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PsichO on 07.04.2014.
 */
public class NoteActivity extends Activity {

    TextView titleView;
    TextView commentView;
    TextView dateView;
    ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = (TextView)findViewById(R.id.text_view_title);
        commentView = (TextView)findViewById(R.id.text_view_comment);
        dateView = (TextView)findViewById(R.id.text_view_date);
        photoView = (ImageView)findViewById(R.id.image_view_photo);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String title = extras.getString("title");
            titleView.setText(title);

            String comment = extras.getString("comment");
            commentView.setText(comment);

            String photo = extras.getString("photo");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            Bitmap bmp = BitmapFactory.decodeFile(photo, options);
            photoView.setImageBitmap(bmp);

            String date = extras.getString("date");
            dateView.setText(date);
        }
    }
}
