package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.Adapters.CategoryAdapter;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.SQL_Queries.GetChildCategories;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

public class CategoryChildActivity extends AppCompatActivity implements Variable_Initialiser {

    GridView gridView;
    ImageButton settings_ib;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VariableInitialiser();
        PopulateGridViewWithChildCategories();
    }

    @Override
    public void VariableInitialiser() {
        gridView = (GridView) findViewById(R.id.categoryChild_gridView);
        gridView.setOnItemClickListener(this);

        settings_ib = (ImageButton) findViewById(R.id.categorychild_settings_ib);
        settings_ib.setOnClickListener(this);

        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    private void PopulateGridViewWithChildCategories(){

        GetChildCategories childCategories = new GetChildCategories(bundle.getString("categoryName"));

        gridView.setAdapter(new CategoryAdapter(childCategories.categoryList(), this));
    }

    @Override
    public void onClick(View v) {

        //opens settings menu

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final TextView childCategoryName = (TextView) view.findViewById(R.id.requestText_tv);

        String ChildCategoryName = childCategoryName.getText().toString();

        bundle.putString("childCategoryName", ChildCategoryName);

        Intent nextPage = new Intent(this, CategoryChildActivity.class );
        nextPage.putExtras(bundle);
        startActivity(nextPage);

    }
}