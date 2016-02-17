package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.Adapters.CategoryAdapter;
import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetCategories;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

public class MainActivity extends AppCompatActivity implements Variable_Initialiser {

    GridView gridView;
    ImageButton config_ib;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VariableInitialiser();
        PopulateGridViewWithCategories();

        // create database at the start of the App
        //SQLiteDatabase databaseHelper = new DatabaseHelper(this).getWritableDatabase();
    }

    private void PopulateGridViewWithCategories(){
        GetCategories categories = new GetCategories();

        gridView.setAdapter(new CategoryAdapter(categories.categoryList(), this));
    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.category_gridView);
        gridView.setOnItemClickListener(this);

        config_ib = (ImageButton) findViewById(R.id.settings_ib);
        config_ib.setOnClickListener(this);


        bundle = new Bundle();
    }

    @Override
    public void onClick(View v) {
        // open the settings menu
        switch (v.getId())
        {
            case R.id.settings_ib:
                configOptions();
                break;

        }
    }

    private void configOptions()
    {
        final CharSequence[] items = {"Make a Request", "Remove a Request","Settings","Add Password", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Configurations:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Make a Request")) {
                    Intent MakeRequest = new Intent(MainActivity.this, MakeRequest.class);
                    startActivity(MakeRequest);

                } else if (items[item].equals("Remove a Request")) {
               //     Intent RemoveRequest = new Intent(MainActivity.this, RemoveAcivity.class);
                 //   startActivity(RemoveRequest);

                }else if (items[item].equals("Settings")) {
                         Intent ConfigureSettings = new Intent(MainActivity.this, Settings.class);
                       startActivity(ConfigureSettings);
                }else if (items[item].equals("Add Password")) {
                    //     Intent SetPassword = new Intent(MainActivity.this, Password.class);
                    //   startActivity(SetPassword);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // final ImageView requestImage = (ImageView) view.findViewById(R.id.requestImage_iv);
        final TextView categoryName = (TextView) view.findViewById(R.id.requestText_tv);

        String CategoryName = categoryName.getText().toString();

        bundle.putString("categoryName", CategoryName);

        Intent nextPage = new Intent(this, CategoryChildActivity.class );
        nextPage.putExtras(bundle);
        startActivity(nextPage);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
