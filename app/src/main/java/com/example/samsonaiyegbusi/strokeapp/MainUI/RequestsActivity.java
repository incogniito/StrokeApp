package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.Adapters.RequestAdapter;
import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetRequests;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RequestsActivity extends AppCompatActivity implements Variable_Initialiser {

    GridView gridView;
    ImageButton settings_ib;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VariableInitialiser();
        PopulateGridViewWithRequests();

    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.requests_gridView);
        gridView.setOnItemClickListener(this);

        settings_ib = (ImageButton) findViewById(R.id.requests_settings_ib);
        settings_ib.setOnClickListener(this);

        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    private void PopulateGridViewWithRequests(){

        DatabaseHelper dbRequests = new DatabaseHelper(this);
       // gridView.setAdapter(new RequestAdapter(dbRequests.selecRequestsBySubcategory(bundle.getInt("childCategoryID")), this));
    }

    @Override
    public void onClick(View v) {
        // Open Settings menu
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //show request/play sound associated with request
       // final TextView audioByteString = (TextView) findViewById(R.id.AudioBytes_tv);
        final CustomTextView audioByteString = (CustomTextView) findViewById(R.id.AudioBytes_tv);
        byte[] audioBytes = Base64.decode(audioByteString.getText().toString(), Base64.DEFAULT);
        playMp3(audioBytes);
    }

    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            File temp = File.createTempFile("requestSound", "mp3", getCacheDir());
            temp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(mp3SoundByteArray);
            fos.close();


            MediaPlayer mediaPlayer = new MediaPlayer();


            FileInputStream fis = new FileInputStream(temp);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
}
