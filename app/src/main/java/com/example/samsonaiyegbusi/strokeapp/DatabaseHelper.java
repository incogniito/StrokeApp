package com.example.samsonaiyegbusi.strokeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import 	android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IBIYE on 13/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stroke.db";

    // Categobries table
    public static final String CATEGORIES_TABLE_NAME ="Categories_Table";
    public static final String ID_Column = "ID";
    public static final String Category_Name = "NAME";
    public static final String Category_Image = "IMAGE";
    //public static final String Category_Audio = "AUDIO";

    //subcategories table
    public static final String SUBCATEGORIES_TABLE_NAME ="Subcategories_Table";
    public static final String SubCategoryID_Column = "ID";
    public static final String SubCategory_Name = "NAME";
    public static final String SubCategory_Image = "IMAGE";
    //public static final String SubCategory_Audio = "AUDIO";
    public static final String category_ID = "CATEGORY_ID";

    //requests table
    public static final String REQUESTS_TABLE_NAME ="Requests_Table";
    public static final String Request_ID = "ID";
    public static final String Request_Name = "NAME";
    public static final String Request_Image = "IMAGE";
    public static final String Request_Audio = "AUDIO";
    public static final String Request_SubCat_ID = "SUBCAT_ID";
    SQLiteDatabase db;
    String CategoriesTable;
    SQLiteStatement insertStatement;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CategoriesTable = "create table if not exists " + CATEGORIES_TABLE_NAME + " ( " + ID_Column +  " INTEGER PRIMARY KEY AUTOINCREMENT," + Category_Name + " TEXT," + Category_Image + " BLOB);";
        String subCategoriesTable = "create table if not exists " + SUBCATEGORIES_TABLE_NAME + " ( " + SubCategoryID_Column +  " INTEGER PRIMARY KEY AUTOINCREMENT," + SubCategory_Name + " TEXT," + SubCategory_Image + " BLOB," + category_ID + " INTEGER,"
                + " FOREIGN KEY ("+category_ID+") REFERENCES "+ SUBCATEGORIES_TABLE_NAME+"("+ID_Column+"));";
        String requestsTable = "create table if not exists " + REQUESTS_TABLE_NAME + " ( " + Request_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Request_Name + " TEXT," + Request_Image + " BLOB," + Request_Audio + " BLOB," + Request_SubCat_ID + " INTEGER,"
                + " FOREIGN KEY ("+Request_SubCat_ID+") REFERENCES " + REQUESTS_TABLE_NAME+"("+SubCategoryID_Column+"));";
        db.execSQL(CategoriesTable);
        db.execSQL(subCategoriesTable);
        db.execSQL(subCategoriesTable);


        String foodImageURL = "../../../Images/Food.png";
        byte[] foodImage = getImage(foodImageURL);
        byte[] foodAudio = getAudio("C:\\Users\\IBIYE\\Music\\audio\\PTT-20150426-WA0012.aac");

        // insert values
        insertIntoTable("1", "Food",foodImage, foodAudio);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
        onCreate(db);
    }

    // method to insert into table
    public long insertIntoTable(String ID,String CategoryName,
                            byte[] categoryImage, byte[] categoryAudio) {
        String sql  = "INSERT INTO TABLE_NAME (ID_Column,Category_Name,Category_Image,Category_Audio) VALUES(?,?,?,?)";

        db.compileStatement(sql);
        this.insertStatement.bindString(1, ID);
        this.insertStatement.bindString(2, CategoryName);
        this.insertStatement.bindBlob(3, categoryImage);
        this.insertStatement.bindBlob(4, categoryAudio);
        db.close();
        return this.insertStatement.executeInsert();

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
