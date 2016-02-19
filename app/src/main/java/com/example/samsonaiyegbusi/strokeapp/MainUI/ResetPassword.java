package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.app.AlertDialog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;

/**
 * Created by Penny on 17/02/2016.
 */
public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_reset_pass);

        CustomTextView question = (CustomTextView) findViewById(R.id.ques_fill);
        Button submitQues = (Button) findViewById(R.id.sumbit_ques);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        final String quesInDB = db.findSecretQuestion("ibiye");
        //Need to get set question from database
        question.setText(quesInDB);

        submitQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets their answer
                EditText ans = (EditText) findViewById(R.id.ques_ans);
                String answer = ans.getText().toString();

                EditText newPassword = (EditText) findViewById(R.id.editText3);

                if(answer.equalsIgnoreCase(quesInDB))
                {
                    //place password in database
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


        });
    }
}