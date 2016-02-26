package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.Adapters.RequestAdapter;
import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;
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
    ImageButton alarm_ib;

    boolean passwordSet;

    int AlarmCounter;
    MediaPlayer alarm;

    DatabaseHelper dbRequests;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VariableInitialiser();
        PopulateGridViewWithRequests();



    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.requests_gridView);
        gridView.setOnItemClickListener(this);

        settings_ib = (ImageButton) findViewById(R.id.requests_settings_ib);
        settings_ib.setOnClickListener(this);

        alarm_ib = (ImageButton) findViewById(R.id.request_alarm_ib);
        alarm_ib.setOnClickListener(this);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        alarm = MediaPlayer.create(this, R.raw.alarm);
        AlarmCounter = 1;
        //passwordSet  = false; // replace
        passwordSet = bundle.getBoolean("passwordSet");

    }

    private void PopulateGridViewWithRequests(){

        dbRequests =  DatabaseHelper.getInstance(this);
        gridView.setAdapter(new RequestAdapter(dbRequests.selectRequestsBySubcategory(bundle.getInt("childCategoryID")), this));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.requests_settings_ib:
                configOptions();
                break;
            case R.id.request_alarm_ib:
                if (AlarmCounter == 1){
                    alarm = MediaPlayer.create(this, R.raw.alarm);
                    alarm.start();
                    AlarmCounter++;
                } else {

                    alarm.stop();
                    AlarmCounter = 1;
                }

                break;
        }    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final TextView audioByteString = (TextView) view.findViewById(R.id.AudioBytes_tv);
        byte[] audioBytes = Base64.decode(audioByteString.getText().toString(), Base64.DEFAULT);
        playMp3(audioBytes);
    }





    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();

            File temp = File.createTempFile("requestSound", "mp3", getCacheDir());
            temp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(mp3SoundByteArray);
            fos.close();




            FileInputStream fis = new FileInputStream(temp);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent(RequestsActivity.this, CategoryChildActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configOptions()
    {
        final CharSequence[] items = {"Make a Request", "Remove a Request","Settings","Add Password", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RequestsActivity.this);
        builder.setTitle("Configurations:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Make a Request")) {
                    Intent MakeRequest = new Intent(RequestsActivity.this, ChooseCategory.class);
                    startActivity(MakeRequest);

                } else if (items[item].equals("Remove a Request")) {
                    Intent RemoveRequest = new Intent(RequestsActivity.this, RemoveRequest.class);
                    startActivity(RemoveRequest);

                } else if (items[item].equals("Settings")) {

                    //needs to check if there's a password to start with in database
                    //bool for now for testing
                    if (passwordSet) {
                        Intent settings = new Intent(RequestsActivity.this, PasswordSettings.class);
                        startActivity(settings);
                    } else {
                        Intent Intentsettings = new Intent(RequestsActivity.this, Settings.class);
                        startActivity(Intentsettings);
                    }
                } else if (items[item].equals("Add Password")) {

                    //needs to check if there's a password to start with in database

                    if (passwordSet) {
                        AlertDialog.Builder setPass = new AlertDialog.Builder(RequestsActivity.this);
                        setPass.setMessage("There is already a password set.");
                        setPass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        setPass.show();

                    } else {
                        Intent newPass = new Intent(RequestsActivity.this, AddPassword.class);
                        startActivity(newPass);
                        //passwordSet = true;
                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


}
