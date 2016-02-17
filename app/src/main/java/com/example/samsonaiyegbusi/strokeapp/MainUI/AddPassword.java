package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.samsonaiyegbusi.strokeapp.R;

public class AddPassword extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_password);

        Button goButton = (Button) findViewById(R.id.new_password);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add password to database

                Intent goBack = new Intent(AddPassword.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }
}

