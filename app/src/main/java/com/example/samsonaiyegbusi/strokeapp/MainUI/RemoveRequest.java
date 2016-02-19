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
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Subcategory;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetCategories;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetRequests;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RemoveRequest extends AppCompatActivity {

    DatabaseHelper dbHelp = new DatabaseHelper(this);

    List<Request> requests = new ArrayList();
    List<String> requestName = new ArrayList();
    List<Categories> categories = new ArrayList();
    List<String> categoryNameValue = new ArrayList();
    List<Subcategory> subCategories = new ArrayList();
    List<String> subCategoryNameValue = new ArrayList();
    Spinner requestNames = (Spinner)findViewById(R.id.requests_spinner);
    Button deleteReq = (Button) findViewById(R.id.delete_request);
    Button deleteCat = (Button) findViewById(R.id.delete_category);
    Spinner categoryNames = (Spinner) findViewById(R.id.cat_spinner);
    Button deleteSubCat = (Button) findViewById(R.id.delete_sub_category);
    Spinner subCategoryNames = (Spinner) findViewById(R.id.sub_cat_spinner);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_remove_request);

        requests = dbHelp.selectAllRequests();
        categories = dbHelp.selectAllCategories();
        //subCategories = dbHelp.selectAllSubCategories();
        //NEEDS TO BE ADDED
        //Filling the requests spinner
        for( Request request : requests)
        {
            requestName.add(request.getName());
        }

        ArrayAdapter<String> adaptSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, requestName);
        adaptSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestNames.setAdapter(adaptSpinner);

        //Filling the subcategories spinner
        for( Subcategory subcategory : subCategories)
        {
            subCategoryNameValue.add(subcategory.getName());
        }

        ArrayAdapter<String> subcatSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, subCategoryNameValue);
        subcatSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategoryNames.setAdapter(subcatSpinner);

        //Filling the categories spinner
        for( Categories category : categories)
        {
            categoryNameValue.add(category.getName());
        }

        ArrayAdapter<String> catSpinner = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, categoryNameValue);
        catSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryNames.setAdapter(catSpinner);

        //delete request
        deleteReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean requestFound = false;
                for (Request request : requests)
                {
                    String name = requestNames.getSelectedItem().toString();
                    if (request.getName().equalsIgnoreCase(name))
                    {
                        int id = request.getId();
                        dbHelp.deleteRequest(id);
                        requestFound = true;
                    }
                }
                if(!requestFound)
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
        });

        deleteCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean catFound = false;
                for (Categories category : categories)
                {
                    String givenCat = categoryNames.getSelectedItem().toString();
                    if (category.getName().equalsIgnoreCase(givenCat))
                    {
                        int id = category.getId();
                        dbHelp.deleteCategory(id);
                        catFound = true;
                    }
                }
                if(!catFound)
                {
                    AlertDialog.Builder wrongCat = new AlertDialog.Builder(RemoveRequest.this);
                    wrongCat.setMessage("No category was found by that name.");
                    wrongCat.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    wrongCat.show();
                }
            }
        });

        deleteSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subcatFound = false;
                for (Subcategory subcategory : subCategories)
                {
                    String givenCat = subCategoryNames.getSelectedItem().toString();
                    if (subcategory.getName().equalsIgnoreCase(givenCat))
                    {
                        int id = subcategory.getId();
                        dbHelp.deleteSubategory(id);
                        subcatFound = true;
                    }
                }
                if(!subcatFound)
                {
                    AlertDialog.Builder wrongCat = new AlertDialog.Builder(RemoveRequest.this);
                    wrongCat.setMessage("No subcategory was found by that name.");
                    wrongCat.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    wrongCat.show();
                }
            }
        });

    }

}

