package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.app.AlertDialog;
import android.provider.ContactsContract;
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
public class ResetPassword extends AppCompatActivity implements View.OnClickListener{

    EditText ans;
    EditText newPassword;

     String quesInDB;
    String ansInDB;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_reset_pass);

        CustomTextView question = (CustomTextView) findViewById(R.id.ques_fill);
        Button submitQues = (Button) findViewById(R.id.sumbit_ques);
        submitQues.setOnClickListener(this);

         db = DatabaseHelper.getInstance(this);
        EditText userName = (EditText)findViewById(R.id.user_fill);
        String usrName = userName.getText().toString();
        System.out.println("usrName : " + usrName + "-------------------" );

        quesInDB = db.findSecretQuestion();
        ansInDB = db.findSecretQuestionAnswer();
        //Need to get set question from database
        question.setText(quesInDB);

         ans = (EditText) findViewById(R.id.ques_ans);
         newPassword = (EditText) findViewById(R.id.editText3);


    }

    @Override
    public void onClick(View v) {
        String answer = ans.getText().toString();
        if(answer.equalsIgnoreCase(ansInDB))
        {
            //place password in database
            String NewPassword = newPassword.getText().toString();
            db.updatePassword(NewPassword);


            Toast.makeText(ResetPassword.this, "Your Password has been reset", Toast.LENGTH_SHORT);
            Intent intent = new Intent(ResetPassword.this, MainActivity.class);
            startActivity(intent);

        }
        else {

            AlertDialog.Builder wrongAns = new android.app.AlertDialog.Builder(ResetPassword.this);
            wrongAns.setMessage("Wrong answer given.");
            wrongAns.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            wrongAns.show();


        }
    }
}