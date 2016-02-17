package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.samsonaiyegbusi.strokeapp.R;

/**
 * Created by Penny on 17/02/2016.
 */
public class PasswordSettings extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int counter;
        setContentView(R.layout.content_pass_settings);

        CustomTextView reset = (CustomTextView)findViewById(R.id.forget_pass);
        Button goButton = (Button)findViewById(R.id.check_password);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.user_input);
                //needs to check database for password
                //if input == database password
                /*Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                 */
                //else

                AlertDialog.Builder wrongPass = new AlertDialog.Builder(PasswordSettings.this);
                wrongPass.setMessage("Wrong Password");
                wrongPass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
                wrongPass.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(PasswordSettings.this, ResetPassword.class);
                startActivity(reset);
            }
        });

    }

}
