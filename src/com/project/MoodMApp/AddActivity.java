package com.project.MoodMApp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private static final int TAKE_PICTURE = 1;

    private Camera camera;

    Uri fileUri;

    private Bitmap bitmap;

    TextView textDescription;
    Button buttonMood;
    RelativeLayout layout;
    Drawable[] layers;
    LayerDrawable background;
    EditText textComment;

    ImageView imagePreview;

    String imageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        layout = (RelativeLayout) this.findViewById(R.id.foo);

        final SeekBar seekBarMood = (SeekBar) findViewById(R.id.seekBarMood);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textComment = (EditText) findViewById(R.id.edit_text_comment);
        buttonMood = (Button) findViewById(R.id.buttonShareMood);

        imagePreview = (ImageView) findViewById(R.id.imagePreview);

        buttonMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonShareMood:
                        Intent intent = new Intent();
                        intent.putExtra("mood", textDescription.getText());
                        intent.putExtra("comment", textComment.getText().toString());

                        intent.putExtra("image",imageToSend);

                        setResult(RESULT_OK, intent);
                        AddActivity.this.finish();
                        break;
                }
            }
        });

        textDescription.setText(" ");

        seekBarMood.setOnSeekBarChangeListener(this);

        Resources resources = getResources();
        layers = new Drawable[2];
        layers[0] = resources.getDrawable(R.drawable.bad);
        layers[1] = resources.getDrawable(R.drawable.good);
        background = new LayerDrawable(layers);
    }

    @Override
    public void onProgressChanged(SeekBar seekBarMood, int progress, boolean fromUser) {
        if (seekBarMood.getProgress() >= 0 && seekBarMood.getProgress() <= 51) {
            textDescription.setText("Bad");
        }

        if (seekBarMood.getProgress() >= 52 && seekBarMood.getProgress() <= 102) {
            textDescription.setText("Not too bad");
        }

        if (seekBarMood.getProgress() >= 103 && seekBarMood.getProgress() <= 153) {
            textDescription.setText("So-so");
        }

        if (seekBarMood.getProgress() >= 154 && seekBarMood.getProgress() <= 204) {
            textDescription.setText("Good");
        }

        if (seekBarMood.getProgress() >= 205 && seekBarMood.getProgress() <= 255) {
            textDescription.setText("VERY F*ING AWESOME!");
        }

        layers[1].setAlpha(seekBarMood.getProgress());
        layout.setBackgroundDrawable(background);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBarMood) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBarMood) {
        switch (seekBarMood.getProgress()) {
            case 0:
                textDescription.setText("Bad");
                break;
            case 1:
                textDescription.setText("So-so");
                break;
            case 2:
                textDescription.setText("Good");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddmmyyyy, hhmmss");
                String date = simpleDateFormat.format(new Date());


                File file = new File(Environment.getExternalStorageDirectory(), "MoodApp");
                file.mkdirs();

                File imageFile = new File(file, "mymood" + date + ".jpg");

                fileUri = Uri.fromFile(imageFile);

                imageToSend = fileUri.getPath();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, TAKE_PICTURE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        if(camera!=null){
            camera.release();
            camera = null;
        }

        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

            imagePreview.setImageBitmap(bitmap);

            //imageToSend = fileUri.getPath();

            //convertedImage = convertBitmapToByteArray(bitmap);
        }
        if(resultCode == RESULT_CANCELED) {
            imageToSend = null;
        }
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        if(bitmap == null) {
            return null;
        } else {
            byte[] bytes = null;
            try{
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                bytes = stream.toByteArray();
            } catch (Exception e){
                e.printStackTrace();
            }

            return bytes;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("fileUri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable("fileUri");
    }
}