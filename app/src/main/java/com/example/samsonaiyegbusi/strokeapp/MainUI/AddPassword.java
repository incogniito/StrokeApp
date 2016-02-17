package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.samsonaiyegbusi.strokeapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddPassword extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_password);
        final Spinner quesSpinner = (Spinner)findViewById(R.id.ques_spinner);
        Button goButton = (Button) findViewById(R.id.new_password);

        String qOne = "Name of your first pet?";
        String qTwo = "Name of the street you live on";
        String qThree = "What is your favourite animal?";

        List<String> questions = new ArrayList<String>();
        questions.add(qOne);
        questions.add(qTwo);
        questions.add(qThree);

        ArrayAdapter<String> adaptSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, questions);
        adaptSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        quesSpinner.setAdapter(adaptSpinner);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText quesAns = (EditText)findViewById(R.id.userAns);
                //GET ANSWER AND CHOSEN QUESTION
                String answer = quesAns.getText().toString();
                String selected = String.valueOf(quesSpinner.getSelectedItem());


                //CHECK SQL BASE FOR ANSWER
                //IF THEY MATCH REPLACE THE PASSWORD WITH THE NEW ONE

                Intent goBack = new Intent(AddPassword.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }

}

