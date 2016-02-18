package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetCategories;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetRequests;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class RemoveRequest extends AppCompatActivity {

    DatabaseHelper dbHelp = new DatabaseHelper(this);

    List<Request> requests = new ArrayList();
    List<String> requestName = new ArrayList();
    Spinner requestNames = (Spinner)findViewById(R.id.requests_spinner);
    Button deleteReq = (Button) findViewById(R.id.delete_request);
    Button deleteCat = (Button) findViewById(R.id.delete_category);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_remove_request);

        requests = dbHelp.selectAllRequests();


        for( Request request : requests)
        {
            requestName.add(request.getName());
        }

        ArrayAdapter<String> adaptSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, requestName);
        adaptSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestNames.setAdapter(adaptSpinner);

        deleteReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Request request : requests)
                {
                    String name = requestNames.getSelectedItem().toString();
                    if (request.getName().equalsIgnoreCase(name))
                    {
                        int id = request.getId();
                        dbHelp.deleteRequest(id);
                    }
                    else
                    {
                        AlertDialog.Builder wrongReq = new AlertDialog.Builder(RemoveRequest.this);
                        wrongReq.setMessage("No request was found by that name.");
                        wrongReq.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        wrongReq.show();
                    }
                }

            }
        });


    }

}

