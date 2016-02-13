package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.samsonaiyegbusi.strokeapp.Adapters.RequestAdapter;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetRequests;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

public class RequestsActivity extends AppCompatActivity implements Variable_Initialiser {

    GridView gridView;
    ImageButton settings_ib;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VariableInitialiser();
        PopulateGridViewWithRequests();

    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.requests_gridView);
        gridView.setOnItemClickListener(this);

        settings_ib = (ImageButton) findViewById(R.id.requests_settings_ib);
        settings_ib.setOnClickListener(this);

        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    private void PopulateGridViewWithRequests(){

        GetRequests requests = new GetRequests(bundle.getString("childCategoryName"));

        gridView.setAdapter(new RequestAdapter(requests.RequestList(), this));
    }

    @Override
    public void onClick(View v) {
        // Open Settings menu
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //show request/play sound associated with request
    }
}
