package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddPassword extends AppCompatActivity {
    Context C;
    SQLiteDatabase dbHelper;
    DatabaseHelper dbHelp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelp = DatabaseHelper.getInstance(this);
        List questions = new ArrayList();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("SecretQuestions.csv")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                for(int i = 0; i < row.length; i++)
                {
                    questions.add(row[i]);

                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        setContentView(R.layout.set_password);
        final Spinner quesSpinner = (Spinner)findViewById(R.id.ques_spinner);
        Button goButton = (Button) findViewById(R.id.new_password);

        ArrayAdapter<String> adaptSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, questions);

        adaptSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quesSpinner.setAdapter(adaptSpinner);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Find what they put
                EditText userName = (EditText)findViewById(R.id.userNametyped);
                String userNameInput = userName.getText().toString();

                //Given password
                EditText password = (EditText)findViewById(R.id.passwordId);
                String passwordInput = password.getText().toString();

                //Given ques. ans
                EditText quesAns = (EditText)findViewById(R.id.userAns);
                //GET ANSWER AND CHOSEN QUESTION
                String answer = quesAns.getText().toString();
                //given question
                String selected = String.valueOf(quesSpinner.getSelectedItem());

                dbHelp.insertIntoPasswordTable(userNameInput, passwordInput, selected, answer);


                AlertDialog.Builder passAdded = new AlertDialog.Builder(AddPassword.this);
                passAdded.setMessage("Password Added");
                passAdded.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent goBack = new Intent(AddPassword.this, MainActivity.class);
                        startActivity(goBack);

                    }
                });
                passAdded.show();


            }
        });
    }

}

