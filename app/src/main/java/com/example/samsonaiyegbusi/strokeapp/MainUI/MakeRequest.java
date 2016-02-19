package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MakeRequest extends AppCompatActivity implements Variable_Initialiser {

    CustomImageView requestImage;
    EditText requestName;
    ImageButton deviceAudio_ib;
    ImageButton record_ib;
    ImageButton stop_ib;
    ImageButton play_ib;
    Button deleteRec_bt;
    Button addRequest_bt;

    CustomTextView record;
    CustomTextView play;
    CustomTextView stop;

Bundle bundle;
    private MediaRecorder myAudioRecorder;
    private static String mFileName = null;

    public static final int image_loaded = 1;
    public static final int audio_loaded = 2;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VariableInitialiser();
        audioSetup();


    }

    @Override
    public void VariableInitialiser() {
        requestImage = (CustomImageView) findViewById(R.id.chosen_image_iv);
        requestImage.setOnClickListener(this);

        requestName = (EditText) findViewById(R.id.requestName_et);

        deviceAudio_ib = (ImageButton) findViewById(R.id.device_audio_ib);
        deviceAudio_ib.setOnClickListener(this);

        record_ib = (ImageButton) findViewById(R.id.record_ib);
        record = (CustomTextView) findViewById(R.id.Record_tv);
        record_ib.setOnClickListener(this);

        stop_ib = (ImageButton) findViewById(R.id.stop_ib);
        stop = (CustomTextView) findViewById(R.id.Stop_tv);
        stop_ib.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        stop_ib.setOnClickListener(this);

        play_ib = (ImageButton) findViewById(R.id.play_ib);
        play = (CustomTextView) findViewById(R.id.play_tv);
        play_ib.setVisibility(View.INVISIBLE);
        play.setVisibility(View.INVISIBLE);
        play_ib.setOnClickListener(this);

        deleteRec_bt = (Button) findViewById(R.id.delete_rec_bt);
        deleteRec_bt.setOnClickListener(this);


        addRequest_bt = (Button) findViewById(R.id.add_reques_bt);
        addRequest_bt.setOnClickListener(this);

Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    private void imageOptions() {
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

    private void audioSetup() {

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+requestName.getText()+".3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.reset();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(mFileName);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.chosen_image_iv:
                imageOptions();
                break;

            case R.id.record_ib:

                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                record_ib.setVisibility(View.INVISIBLE);
                record.setVisibility(View.INVISIBLE);
                stop_ib.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);

                Toast.makeText(MakeRequest.this, "Recording...", Toast.LENGTH_LONG).show();

                break;

            case R.id.stop_ib:

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stop_ib.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                play_ib.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);

                Toast.makeText(MakeRequest.this, "Audio Recorded", Toast.LENGTH_LONG).show();

                break;

            case R.id.play_ib:

                if (requestName.length() == 0) {
                    Toast.makeText(MakeRequest.this, "Please fill in Event name", Toast.LENGTH_SHORT).show();
                    break;
                }

                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(mFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(MakeRequest.this, "Playing...", Toast.LENGTH_LONG).show();

                break;

            case R.id.device_audio_ib:

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Gallery"), audio_loaded);


                break;

            case R.id.delete_rec_bt:


                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+requestName.getText()+".3gp";

                record_ib.setVisibility(View.VISIBLE);
                record.setVisibility(View.VISIBLE);
                play_ib.setVisibility(View.INVISIBLE);
                play.setVisibility(View.INVISIBLE);

                Toast.makeText(MakeRequest.this, "Audio Deleted", Toast.LENGTH_LONG).show();

                break;

            case R.id.add_reques_bt:
                //check every field as an entry
                if (requestImage.toString().length() == 0){
                    Toast.makeText(MakeRequest.this, "Please Choose An Image ", Toast.LENGTH_SHORT).show();
                    break;
                }
                //call insert statement

                // Locate the image in res > drawable-hdpi
                Bitmap bmpimage = ((BitmapDrawable) requestImage.getDrawable()).getBitmap();
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                // Compress image to lower quality scale 1 - 100
                bmpimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

                Bitmap newbmp = Bitmap.createScaledBitmap(b, 300, 270, false);
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

                newbmp.compress(Bitmap.CompressFormat.PNG, 100, stream2);

                byte[] resizedImage = stream2.toByteArray();





                File file = new File(mFileName);
                InputStream inStream ;


                byte[] audiofile = null;
                try {
                     inStream = new FileInputStream(file);
                    audiofile = convertStreamToByteArray(inStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }


byte[] subgategoryIMg = bundle.getByteArray("categoryImg");
int parentID = bundle.getInt("parentID");
int childID = bundle.getInt("subcategoryID");
String subCategoryName = bundle.getString("subcategoryName");



                DatabaseHelper dbInsert = DatabaseHelper.getInstance(this);

                if (subgategoryIMg != null)
                {
                    dbInsert.insertIntoSubcategoryTable(subCategoryName, subgategoryIMg, parentID, dbInsert.getWritableDatabase());
                }

                dbInsert.insertIntoRequestTable(requestName.getText().toString(), resizedImage, audiofile,childID, dbInsert.getWritableDatabase());

                Toast.makeText(MakeRequest.this, "You have successfully added Request: "+requestName.getText().toString(), Toast.LENGTH_SHORT).show();


                Intent takeusertoMain = new Intent(MakeRequest.this, MainActivity.class);
                startActivity(takeusertoMain);

                break;


        }

    }

    public static byte[] convertStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i ;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }

        return baos.toByteArray(); // be sure to close InputStream in calling function
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_loaded && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            requestImage.setImageURI(selectedImage);
        }

        else if (requestCode == audio_loaded && resultCode == RESULT_OK && data != null) {
            Uri selectedAudio = data.getData();
            mFileName = selectedAudio.toString();

            record_ib.setVisibility(View.INVISIBLE);
            record.setVisibility(View.INVISIBLE);
            play_ib.setVisibility(View.VISIBLE);
            play.setVisibility(View.VISIBLE);
            Toast.makeText(MakeRequest.this, "Audio has been selected", Toast.LENGTH_LONG).show();

        }

    }
}

