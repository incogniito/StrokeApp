package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetCategories;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetRequests;

public class RemoveRequest extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_remove_request);

    }
    public void fillSpinner()
    {
        //GetRequests allRequests = new GetRequests()
        Spinner requests = (Spinner)findViewById(R.id.requests_spinner);


    }
}

