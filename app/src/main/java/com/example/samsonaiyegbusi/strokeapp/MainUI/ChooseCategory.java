package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samsonaiyegbusi.strokeapp.DatabaseHelper;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Subcategory;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.Variable_Initialiser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChooseCategory extends AppCompatActivity implements Variable_Initialiser, AdapterView.OnItemSelectedListener {

    EditText categoryName;
    Spinner categoryList;
    Spinner subcategroyList;
    CustomImageView chooseImg;
    Button next;
    Button selectSubcategory;

    List<Categories> categories ;
    List<String> categoryNameValue ;

    List<Subcategory> subCategories ;
    List<String> subCategoryNameValue ;

    DatabaseHelper dbHelp;

    Bundle bundle;
    int parentID = 0;
    int childID = 0;



    int image_loaded = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VariableInitialiser();

    }


    @Override
    public void VariableInitialiser() {


        bundle = new Bundle();

        dbHelp = DatabaseHelper.getInstance(this);

        categoryNameValue = new ArrayList();
        subCategoryNameValue = new ArrayList();
        categories = dbHelp.selectAllCategories();

        categoryName = (EditText) findViewById(R.id.makeCategory_et);
        categoryName.setVisibility(View.INVISIBLE);
        categoryList = (Spinner) findViewById(R.id.chooseCategory_sp);
        subcategroyList = (Spinner) findViewById(R.id.chooseSubcategory_sb);
        subcategroyList.setOnItemSelectedListener(this);
        subcategroyList.setVisibility(View.INVISIBLE);
        chooseImg = (CustomImageView) findViewById(R.id.makeCategoryImg_iv);
        chooseImg.setVisibility(View.INVISIBLE);
        chooseImg.setOnClickListener(this);

        selectSubcategory = (Button) findViewById(R.id.button2);
        selectSubcategory.setOnClickListener(this);

        next = (Button) findViewById(R.id.Next_ib);
        next.setOnClickListener(this);

//Filling the categories spinner
        for (Categories category : categories) {
            categoryNameValue.add(category.getName());
        }
        ArrayAdapter<String> catSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNameValue);
        catSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(catSpinner);



    }


    public void makeNewCategory(){
        chooseImg.setVisibility(View.VISIBLE);
        categoryName.setVisibility(View.VISIBLE);

    }

    public void getInformation()
    {

        // Locate the image in res > drawable-hdpi
        Bitmap bmpimage = ((BitmapDrawable) chooseImg.getDrawable()).getBitmap();
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Compress image to lower quality scale 1 - 100
        bmpimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

        Bitmap newbmp = Bitmap.createScaledBitmap(b, 300, 270, false);
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

        newbmp.compress(Bitmap.CompressFormat.PNG, 100, stream2);

        byte[] resizedImage = stream2.toByteArray();
       String subcategoryName =  categoryName.getText().toString();
        bundle.putInt("parentID", parentID);
        bundle.putByteArray("categoryImg", resizedImage);
        bundle.putString("subcategoryName", subcategoryName);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.Next_ib:
                getInformation();

                Intent makerequest = new Intent(ChooseCategory.this, MakeRequest.class);
                makerequest.putExtras(bundle);
                startActivity(makerequest);

                break;
            case R.id.makeCategoryImg_iv:
                imageOptions();
                break;

            case R.id.button2:
                selectSubcategory();
                break;


        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    private void imageOptions() {
        final CharSequence[] items = {"Take a picture", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseCategory.this);
        builder.setTitle("Add an image:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take a picture")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, image_loaded);
                } else if (items[item].equals("Choose from gallery")) {
                    Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallery, image_loaded);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    private void selectSubcategory(){
        categoryName.setVisibility(View.INVISIBLE);
        chooseImg.setVisibility(View.INVISIBLE);

        subCategoryNameValue.clear();


        String chosenCategoryName = categoryList.getSelectedItem().toString();
        for (Categories category : categories) {
            if (category.getName().equalsIgnoreCase(chosenCategoryName)) {
                parentID = category.getId();
            }
        }

        if (parentID != 0) {
            subcategroyList.setVisibility(View.VISIBLE);
            subCategories = dbHelp.selectSubcategoriesByParent(parentID);
        }


        for (Subcategory subcategory : subCategories) {
            subCategoryNameValue.add(subcategory.getName());
        }
        subCategoryNameValue.add("Add a New Category");

        ArrayAdapter<String> subcatSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCategoryNameValue);
        subcatSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategroyList.setAdapter(subcatSpinner);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_loaded && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            chooseImg.setImageURI(selectedImage);
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryName.setVisibility(View.INVISIBLE);
        chooseImg.setVisibility(View.INVISIBLE);
        String subcategoryItem = subcategroyList.getItemAtPosition(position).toString();

        if (subcategoryItem.equalsIgnoreCase("Add a New Category")){
            makeNewCategory();
        }  else {


            for (Subcategory subcategory : subCategories) {
                if (subcategory.getName().equalsIgnoreCase(subcategoryItem)) {
                    childID = subcategory.getId();
                }
            }

            bundle.putInt("subcategoryID", childID);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
