package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;

/**
 * Created by Penny on 17/02/2016.
 */
public class PasswordSettings extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pass_settings);

        databaseHelper = DatabaseHelper.getInstance(this);
        CustomTextView reset = (CustomTextView)findViewById(R.id.forget_pass);
        Button goButton = (Button)findViewById(R.id.check_password);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userNameInput = (EditText) findViewById(R.id.userNameText);
                String getUserName = userNameInput.getText().toString();
                EditText passwordInput = (EditText) findViewById(R.id.user_input);
                String getPassword = passwordInput.getText().toString();

                System.out.println("<-----------user name : " + getUserName + "----------->");
                String password = databaseHelper.findPassword(getUserName);

                if(getPassword.equals(password))
                {
                     Intent settings = new Intent(PasswordSettings.this, Settings.class);
                        startActivity(settings);
                }
                else
                {
                    AlertDialog.Builder wrongPass = new AlertDialog.Builder(PasswordSettings.this);
                    wrongPass.setMessage("Wrong Password");
                    wrongPass.setMessage("here " + password);
                    wrongPass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    wrongPass.show();
                }


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
