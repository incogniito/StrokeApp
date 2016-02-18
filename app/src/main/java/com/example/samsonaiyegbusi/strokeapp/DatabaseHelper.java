package com.example.samsonaiyegbusi.strokeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import 	android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;

/**
 * Created by IBIYE on 13/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stroke.db";

    // Categobries table
    public static final String CATEGORIES_TABLE_NAME ="Categories_Table";
    public static final String ID_COLUMN = "ID";
    public static final String CATEGORY_NAME = "NAME";
    public static final String CATEGORY_IMAGE = "IMAGE";
    //public static final String Category_Audio = "AUDIO";

    //subcategories table
    public static final String SUBCATEGORIES_TABLE_NAME ="Subcategories_Table";
    public static final String SUB_CATEGORY_ID_COLUMN = "ID";
    public static final String SUB_CATEGORY_NAME = "NAME";
    public static final String SUB_CATEGORY_IMAGE = "IMAGE";
    //public static final String SubCategory_Audio = "AUDIO";
    public static final String PARENT_ID = "CATEGORY_ID";

    //requests table
    public static final String REQUESTS_TABLE_NAME ="Requests_Table";
    public static final String REQUEST_ID = "ID";
    public static final String REQUEST_NAME = "NAME";
    public static final String REQUEST_IMAGE = "IMAGE";
    public static final String REQUEST_AUDIO = "AUDIO";
    public static final String REQUEST_SUB_CAT_ID = "SUBCAT_ID";
    SQLiteDatabase db;
    String CategoriesTable;
    SQLiteStatement insertStatement;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CategoriesTable = "create table if not exists " + CATEGORIES_TABLE_NAME + " ( " + ID_COLUMN +  " INTEGER PRIMARY KEY AUTOINCREMENT," + CATEGORY_NAME + " TEXT," + CATEGORY_IMAGE + " BLOB);";
        String subCategoriesTable = "create table if not exists " + SUBCATEGORIES_TABLE_NAME + " ( " + SUB_CATEGORY_ID_COLUMN +  " INTEGER PRIMARY KEY AUTOINCREMENT," + SUB_CATEGORY_NAME + " TEXT," + SUB_CATEGORY_IMAGE + " BLOB," + PARENT_ID + " INTEGER,"
                + " FOREIGN KEY ("+ PARENT_ID +") REFERENCES "+ SUBCATEGORIES_TABLE_NAME+"("+ID_COLUMN+"));";
        String requestsTable = "create table if not exists " + REQUESTS_TABLE_NAME + " ( " + REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + REQUEST_NAME + " TEXT," + REQUEST_IMAGE + " BLOB," + REQUEST_AUDIO + " BLOB," + REQUEST_SUB_CAT_ID + " INTEGER,"
                + " FOREIGN KEY ("+ REQUEST_SUB_CAT_ID +") REFERENCES " + REQUESTS_TABLE_NAME+"("+ SUB_CATEGORY_ID_COLUMN +"));";
        db.execSQL(CategoriesTable);
        db.execSQL(subCategoriesTable);
        db.execSQL(requestsTable);


        //String foodImageURL = "http://images.clipartpanda.com/food-clip-art-9czqXBBcE.jpeg";
       // byte[] foodImage = getImage(foodImageURL);
        byte[] foodAudio = getAudio("C:\\Users\\IBIYE\\Music\\audio\\PTT-20150426-WA0012.aac");

        // insert values
       // insertIntoCategoryTable("Food",foodImage);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
        onCreate(db);
    }

    // method to insert into table
    public long insertIntoCategoryTable(String CategoryName, byte[] categoryImage)
    {
        String sql  = "INSERT INTO " + CATEGORIES_TABLE_NAME + " (" + CATEGORY_NAME + "," + CATEGORY_IMAGE + ") VALUES("+null+",?,?)";

        db.compileStatement(sql);
        //this.insertStatement.bindString(0, null);
        this.insertStatement.bindString(1, CategoryName);
        this.insertStatement.bindBlob(2, categoryImage);
        //this.insertStatement.bindBlob(3, categoryAudio);
        db.close();
        return this.insertStatement.executeInsert();

    }
    public long insertIntoSubcategoryTable(String SubcategoryName, byte[] SubcategoryImage)
    {
        String sql  = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" + SUB_CATEGORY_NAME + "," + SUB_CATEGORY_IMAGE + ") VALUES("+null+",?,?)";

        db.compileStatement(sql);
        //this.insertStatement.bindString(0, ID);
        this.insertStatement.bindString(1, SubcategoryName);
        this.insertStatement.bindBlob(2, SubcategoryImage);
        //this.insertStatement.bindBlob(3, categoryAudio);
        db.close();
        return this.insertStatement.executeInsert();

    }
    public long insertIntoRequestTable(String RequestName, byte[] RequestImage, byte[] RequestSound)
    {
        String sql  = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" + SUB_CATEGORY_NAME + "," + SUB_CATEGORY_IMAGE + ") VALUES("+null+",?,?,?)";

        db.compileStatement(sql);
        //this.insertStatement.bindString(0, ID);
        this.insertStatement.bindString(1, RequestName);
        this.insertStatement.bindBlob(2, RequestImage);
        this.insertStatement.bindBlob(3, RequestSound);
        db.close();
        return this.insertStatement.executeInsert();
    }
    /**
     *
     * @param id ID of the request
     * @return
     */
    public Request selectFromRequestsByID(String id)
    {
        String sql = "SELECT * FROM " + REQUESTS_TABLE_NAME + " WHERE " + REQUEST_ID + " = '" + id + "';";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            int reqID = c.getInt(0);
            String reqName = c.getString(1);
            byte[] reqImg = c.getBlob(2);
            byte[] reqAud = c.getBlob(3);
            int reqSubCat = c.getInt(4);
            Request request = new Request(reqID, reqName, reqImg, reqAud, reqSubCat);
            return request;
        }
        Request request = new Request();
        return request;
    }

    public List<Request> selectAllRequests()
    {
        List<Request> requests = new ArrayList<Request>();
        String sql = "SELECT * FROM " + REQUESTS_TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            int reqID = c.getInt(0);
            String reqName = c.getString(1);
            byte[] reqImg = c.getBlob(2);
            byte[] reqAud = c.getBlob(3);
            int reqSubCat = c.getInt(4);
            Request request = new Request(reqID, reqName, reqImg, reqAud, reqSubCat);
            requests.add(request);
        }
        while(c.moveToNext())
        {
            int reqID = c.getInt(0);
            String reqName = c.getString(1);
            byte[] reqImg = c.getBlob(2);
            byte[] reqAud = c.getBlob(3);
            int reqSubCat = c.getInt(4);
            Request request = new Request(reqID, reqName, reqImg, reqAud, reqSubCat);
            requests.add(request);
        }
        return requests;
    }

    public List<Request> selecRequestsBySubcategory(int subcategoryID)
    {
        List<Request> requests = new ArrayList<Request>();
        String sql = "SELECT * FROM " + REQUESTS_TABLE_NAME + " WHERE " + REQUEST_SUB_CAT_ID + " = '" + subcategoryID + "';";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            int reqID = c.getInt(0);
            String reqName = c.getString(1);
            byte[] reqImg = c.getBlob(2);
            byte[] reqAud = c.getBlob(3);
            int reqSubCat = c.getInt(4);
            Request request = new Request(reqID, reqName, reqImg, reqAud, reqSubCat);
            requests.add(request);
        }
        while(c.moveToNext())
        {
            int reqID = c.getInt(0);
            String reqName = c.getString(1);
            byte[] reqImg = c.getBlob(2);
            byte[] reqAud = c.getBlob(3);
            int reqSubCat = c.getInt(4);
            Request request = new Request(reqID, reqName, reqImg, reqAud, reqSubCat);
            requests.add(request);
        }
        return requests;
    }

    public List<Categories> selectAllCategories()
    {
        List<Categories> categories = new ArrayList<Categories>();
        String sql = "SELECT * FROM " +CATEGORIES_TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            int catID = c.getInt(0);
            String catName = c.getString(1);
            byte[] catImg = c.getBlob(2);
            Categories category = new Categories(catID, catName, catImg);
            categories.add(category);
        }
        while(c.moveToNext())
        {
            int catID = c.getInt(0);
            String catName = c.getString(1);
            byte[] catImg = c.getBlob(2);
            Categories category = new Categories(catID, catName, catImg);
            categories.add(category);
        }

        return categories;
    }

    public List<Categories> selectSubCategoriesByParent(int parentID)
    {
        List<Categories> subcategories = new ArrayList<Categories>();
        String sql = "SELECT * FROM " +SUBCATEGORIES_TABLE_NAME + " WHERE " + PARENT_ID + " = '" + parentID + "';";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            int catID = c.getInt(0);
            String catName = c.getString(1);
            byte[] catImg = c.getBlob(2);
            Categories category = new Categories(catID, catName, catImg);
            subcategories.add(category);
        }
        while(c.moveToNext())
        {
            int catID = c.getInt(0);
            String catName = c.getString(1);
            byte[] catImg = c.getBlob(2);
            Categories category = new Categories(catID, catName, catImg);
            subcategories.add(category);
        }

        return subcategories;
    }

    private byte[] getImage(String url){
        try {
            URL imageUrl = new URL(url);

            URLConnection urlConn = imageUrl.openConnection();

            InputStream inputStream = urlConn.getInputStream();
            BufferedInputStream bufferedIS = new BufferedInputStream(inputStream);

            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            byte[] data = new byte[500];
            int current = 0;
            while ((current = bufferedIS.read(data,0,data.length)) != -1) {
                byteArrayOS.write(data,0,current);
            }

            return byteArrayOS.toByteArray();
        } catch (Exception e) {
            Log.d("Image conversion ", "Error: " + e.toString());
        }
        return null;
    }
    private byte[] getAudio(String url)
    {
        FileInputStream instream = null;
        try {
            instream = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bif = new BufferedInputStream(instream);
        byte[] byteAudio1 = new byte[0];
        try {
            byteAudio1 = new byte[bif.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bif.read(byteAudio1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteAudio1;
    }
}
