package com.example.samsonaiyegbusi.strokeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MakeRequest extends AppCompatActivity implements Variable_Initialiser{

    ImageView requestImage;
    EditText requestName;
    ImageButton deviceAudio_ib;
    ImageButton record_ib;
    ImageButton stop_ib;
    ImageButton play_ib;
    Button deleteRec_bt;
    Button addRequest_bt;

    private MediaRecorder myAudioRecorder;
    private static String mFileName = null;

    public static final int image_loaded = 1;
    public static final int audio_loaded = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VariableInitialiser();
        audioSetup();

    }

    @Override
    public void VariableInitialiser() {
        requestImage = (ImageView) findViewById(R.id.chosen_image_iv);
        requestImage.setOnClickListener(this);

        requestName = (EditText) findViewById(R.id.requestName_et);

        deviceAudio_ib = (ImageButton) findViewById(R.id.device_audio_ib);
        deviceAudio_ib.setOnClickListener(this);

        record_ib = (ImageButton) findViewById(R.id.record_ib);
        record_ib.setOnClickListener(this);

        stop_ib = (ImageButton) findViewById(R.id.stop_ib);
        stop_ib.setEnabled(false);
        stop_ib.setOnClickListener(this);

        play_ib = (ImageButton) findViewById(R.id.play_ib);
        play_ib.setEnabled(false);
        play_ib.setOnClickListener(this);

        deleteRec_bt = (Button) findViewById(R.id.delete_rec_bt);
        deleteRec_bt.setOnClickListener(this);

        addRequest_bt = (Button) findViewById(R.id.add_reques_bt);

    }

    private void imageOptions()
    {
       final CharSequence[] items = {"Take a picture", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MakeRequest.this);
        builder.setTitle("Add an image:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take a picture")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, image_loaded);
                } else if (items[item].equals("Choose from gallery")) {
                    Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallery, image_loaded);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
builder.show();

    }

    private void audioSetup()
    {

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;

        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.reset();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(mFileName);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.chosen_image_iv:
            imageOptions();
                break;

            case R.id.record_ib:

                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                record_ib.setEnabled(false);
                stop_ib.setEnabled(true);

                Toast.makeText(MakeRequest.this, "Recording...", Toast.LENGTH_LONG).show();

                break;

            case R.id.stop_ib:

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop_ib.setEnabled(false);
                play_ib.setEnabled(true);

                Toast.makeText(MakeRequest.this, "Audio Recorded",Toast.LENGTH_LONG).show();

                break;

            case R.id.play_ib:
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(mFileName);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing...", Toast.LENGTH_LONG).show();

                break;

            case R.id.device_audio_ib:

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Gallery"), audio_loaded);

                break;

            case R.id.delete_rec_bt:

                mFileName = null;
                record_ib.setEnabled(true);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_loaded && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            requestImage.setImageURI(selectedImage);

        }
    }
}