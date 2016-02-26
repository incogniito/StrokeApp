package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.Adapters.CategoryAdapter;
import com.example.samsonaiyegbusi.strokeapp.Adapters.SubCategoryAdapter;
import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetChildCategories;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

public class CategoryChildActivity extends AppCompatActivity implements Variable_Initialiser {

    GridView gridView;
    ImageButton settings_ib;
    ImageButton alarm_ib;
    boolean passwordSet;


    Bundle bundle;

    int AlarmCounter;
    MediaPlayer alarm;

    DatabaseHelper dbSubcategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VariableInitialiser();
        PopulateGridViewWithChildCategories();
    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.categoryChild_gridView);
        gridView.setOnItemClickListener(this);

        settings_ib = (ImageButton) findViewById(R.id.categorychild_settings_ib);
        settings_ib.setOnClickListener(this);

        alarm_ib = (ImageButton) findViewById(R.id.categorychild_alrm_ib);
        alarm_ib.setOnClickListener(this);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        alarm = MediaPlayer.create(this, R.raw.alarm);
        AlarmCounter = 1;
        //passwordSet  = false; // replace
        passwordSet = bundle.getBoolean("passwordSet");
    }

    private void PopulateGridViewWithChildCategories() {

         dbSubcategory = DatabaseHelper.getInstance(this);

        gridView.setAdapter(new SubCategoryAdapter(dbSubcategory.selectSubcategoriesByParent(bundle.getInt("categoryID")), this));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.categorychild_settings_ib:
                configOptions();
                break;
            case R.id.categorychild_alrm_ib:
                if (AlarmCounter == 1){
                    alarm = MediaPlayer.create(this, R.raw.alarm);
                    alarm.start();
                    AlarmCounter++;
                } else {

                    alarm.stop();
                    AlarmCounter = 1;
                }

                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // final TextView childCategoryID = (TextView) view.findViewById(R.id.ID_tv);
        final CustomTextView childCategoryID = (CustomTextView) view.findViewById(R.id.ID_tv);
        int ChildCategoryID = Integer.parseInt(childCategoryID.getText().toString());

        bundle.putInt("childCategoryID", ChildCategoryID);

        Intent nextPage = new Intent(this, RequestsActivity.class);
        nextPage.putExtras(bundle);
        startActivityForResult(nextPage, 1);

    }

    private void configOptions()
    {
        final CharSequence[] items = {"Make a Request", "Remove a Request","Settings","Add Password", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryChildActivity.this);
        builder.setTitle("Configurations:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Make a Request")) {
                    Intent MakeRequest = new Intent(CategoryChildActivity.this, ChooseCategory.class);
                    startActivity(MakeRequest);

                } else if (items[item].equals("Remove a Request")) {
                    Intent RemoveRequest = new Intent(CategoryChildActivity.this, RemoveRequest.class);
                    startActivity(RemoveRequest);

                } else if (items[item].equals("Settings")) {

                    //needs to check if there's a password to start with in database
                    //bool for now for testing
                    if (passwordSet) {
                        Intent settings = new Intent(CategoryChildActivity.this, PasswordSettings.class);
                        startActivity(settings);
                    } else {
                        Intent Intentsettings = new Intent(CategoryChildActivity.this, Settings.class);
                        startActivity(Intentsettings);
                    }
                } else if (items[item].equals("Add Password")) {

                    //needs to check if there's a password to start with in database

                    if (passwordSet) {
                        AlertDialog.Builder setPass = new AlertDialog.Builder(CategoryChildActivity.this);
                        setPass.setMessage("There is already a password set.");
                        setPass.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        setPass.show();

                    } else {
                        Intent newPass = new Intent(CategoryChildActivity.this, AddPassword.class);
                        startActivity(newPass);
                        //passwordSet = true;
                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }



}
