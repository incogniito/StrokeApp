package com.example.samsonaiyegbusi.strokeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import 	android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Subcategory;

/**
 * Created by IBIYE on 13/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stroke.db";

    // Categories table
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
    public static final String SubCategory_ParentID = "CATEGORY_ID";

    //requests table
    public static final String REQUESTS_TABLE_NAME ="Requests_Table";
    public static final String Request_ID = "ID";
    public static final String Request_Name = "NAME";
    public static final String Request_Image = "IMAGE";
    public static final String Request_Audio = "AUDIO";
    public static final String Request_SubCat_ID = "SUBCAT_ID";

    //Password table
    public static final String PASSWORD_TABLE = "Password_Table";
    public static final String Password_ID = "ID";
    public static final String Password_UserName = "UserName";
    public static final String Password = "Password";
    public static final String Password_SecretQuestion = "SecretQuestion";
    public static final String Password_SecretAnswer = "SecretAnswer";



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
        String subCategoriesTable = "create table if not exists " + SUBCATEGORIES_TABLE_NAME + " ( " + SubCategoryID_Column +  " INTEGER PRIMARY KEY AUTOINCREMENT," + SubCategory_Name + " TEXT," + SubCategory_Image + " BLOB," + SubCategory_ParentID + " INTEGER,"
                + " FOREIGN KEY ("+ SubCategory_ParentID +") REFERENCES "+ SUBCATEGORIES_TABLE_NAME+"("+ID_Column+"));";
        String requestsTable = "create table if not exists " + REQUESTS_TABLE_NAME + " ( " + Request_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Request_Name + " TEXT," + Request_Image + " BLOB," + Request_Audio + " BLOB," + Request_SubCat_ID + " INTEGER,"
                + " FOREIGN KEY ("+Request_SubCat_ID+") REFERENCES " + REQUESTS_TABLE_NAME+"("+SubCategoryID_Column+"));";
        String PasswordTable  = "create table if not exists " + PASSWORD_TABLE + " ( " + Password_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," + Password_UserName + " TEXT," + Password + " TEXT," + Password_SecretQuestion + " TEXT," + Password_SecretAnswer + " TEXT);";
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(CategoriesTable);
        db.execSQL(subCategoriesTable);
        db.execSQL(requestsTable);
        db.execSQL(PasswordTable);


        Log.e("Database information", "Tables created");

        // insert values
       // insertIntoCategoryTable("Food",foodImage);
        String usName = "ibiye";
        String usPass = "b";
        String secretQues = "Favourite food";
        String secrtAns = "Rice";
       // insertIntoPasswordTable(usName,usPass,secretQues,secrtAns);
        try {
            insertCategoriesFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            insertSubcategoriesFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            insertRequestsFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + REQUESTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + SUBCATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + PASSWORD_TABLE);
        onCreate(db);
    }

    // method to insert into table
    public long insertIntoCategoryTable(String CategoryName, byte[] categoryImage)
    {
        String sql  = "INSERT INTO " + CATEGORIES_TABLE_NAME + " (" + Category_Name + "," + Category_Image + ") VALUES("+null+",?,?)";

        insertStatement = db.compileStatement(sql);
        //this.insertStatement.bindString(0, null);
        this.insertStatement.bindString(1, CategoryName);
        this.insertStatement.bindBlob(2, categoryImage);
        //this.insertStatement.bindBlob(3, categoryAudio);
        db.close();
        return this.insertStatement.executeInsert();

    }
    public long insertIntoSubcategoryTable(String SubcategoryName, byte[] SubcategoryImage, int ParentID)
    {
        String sql  = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" + SubCategory_Name + "," + SubCategory_Image + ") VALUES("+null+",?,?)";

        insertStatement = db.compileStatement(sql);
        //this.insertStatement.bindString(0, ID);
        this.insertStatement.bindString(1, SubcategoryName);
        this.insertStatement.bindBlob(2, SubcategoryImage);
        //this.insertStatement.bindBlob(3, categoryAudio);
        this.insertStatement.bindLong(3, ParentID);
        db.close();
        return this.insertStatement.executeInsert();

    }
    public long insertIntoRequestTable(String RequestName, byte[] RequestImage, byte[] RequestSound, int ParentID)
    {
        String sql  = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" + SubCategory_Name + "," + SubCategory_Image + ") VALUES("+null+",?,?,?)";

        insertStatement = db.compileStatement(sql);
        //this.insertStatement.bindString(0, ID);
        this.insertStatement.bindString(1, RequestName);
        this.insertStatement.bindBlob(2, RequestImage);
        this.insertStatement.bindBlob(3, RequestSound);
        this.insertStatement.bindLong(4, ParentID);
        db.close();
        return this.insertStatement.executeInsert();
    }

    public void insertCategoriesFromCSV() throws IOException {
        FileReader file = new FileReader("../../../assets/Categories.CSV");
        BufferedReader inReader = new BufferedReader(file);
        String reader = "";
        db.beginTransaction();
        while ((reader = inReader.readLine()) != null)
        {
            String[] rowData = reader.split(",");
            byte[] rowImage = getImage(rowData[1]);
            this.insertIntoCategoryTable(rowData[0],rowImage);

            this.close();
        }

    }

    public void insertSubcategoriesFromCSV() throws IOException {
        FileReader file = new FileReader("../../../assets/Subcategories.csv");
        BufferedReader inReader = new BufferedReader(file);
        String reader = "";
        db.beginTransaction();
        while ((reader = inReader.readLine()) != null)
        {
            String[] rowData = reader.split(",");
            byte[] rowImage = getImage(rowData[1]);
            this.insertIntoSubcategoryTable(rowData[0], rowImage, Integer.parseInt(rowData[2]));

            this.close();
        }

    }

    public void insertRequestsFromCSV() throws IOException {
        FileReader file = new FileReader("../../../assets/Requests.csv");
        BufferedReader inReader = new BufferedReader(file);
        String reader = "";
        db.beginTransaction();
        while ((reader = inReader.readLine()) != null)
        {
            String[] rowData = reader.split(",");
            byte[] rowImage = getImage(rowData[1]);
            byte[] rowAudio = getAudio(rowData[2]);
            this.insertIntoRequestTable(rowData[0], rowImage, rowAudio, Integer.parseInt(rowData[2]));

            this.close();
        }

    }

    public void Insert(String[] data)
    {
        insertStatement = db.compileStatement("INSERT INTO" + SUBCATEGORIES_TABLE_NAME + "VALUES(?,?)");
        insertStatement.bindString(1, data[0]);
        insertStatement.bindString(2, data[1]);
        insertStatement.executeInsert();
    }

    public void insertIntoPasswordTable(String username, String userPassword, String secretQuestion, String secretAnswer)
    {
//        String sql  = "INSERT INTO " + PASSWORD_TABLE + " (" + Password_UserName + "," + Password + "," + Password_SecretQuestion + "," + Password_SecretAnswer + ") VALUES("+null+",?,?,?,?)";
//
//        insertStatement = db.compileStatement(sql);
//        this.insertStatement.bindString(1, username);
//        this.insertStatement.bindString(2, userPassword);
//        this.insertStatement.bindString(3, secretQuestion);
//        this.insertStatement.bindString(4, secretAnswer);
//        db.close();
//        return this.insertStatement.executeInsert();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Password_UserName,username);
        contentValues.put(Password,userPassword);
        contentValues.put(Password_SecretQuestion,secretQuestion);
        contentValues.put(Password_SecretAnswer,secretAnswer);
        db.insert(PASSWORD_TABLE, null, contentValues);
        Log.e("Database information", "One row inserted");
    }


    /**
     *
     * @param id ID of the request
     * @return
     */
    public Request selectFromRequestsByID(int id)
    {
        //String sql = "SELECT * FROM " + REQUESTS_TABLE_NAME + " WHERE " + Request_ID + " = '" + id + "';";
        //Cursor c = db.rawQuery(sql, null);
        Cursor c = db.query(REQUESTS_TABLE_NAME,null,Request_ID+"=?",new String[] {Integer.toString(id)},null,null,null);
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

    public List<Request> selectRequestsBySubcategory(int subcategoryID)
    {
        List<Request> requests = new ArrayList<Request>();
        //String sql = "SELECT * FROM " + REQUESTS_TABLE_NAME + " WHERE " + Request_SubCat_ID + " = '" + subcategoryID + "';";
        //Cursor c = db.rawQuery(sql, null);
        Cursor c = db.query(REQUESTS_TABLE_NAME,null,Request_SubCat_ID+"=?",new String[] {Integer.toString(subcategoryID)},null,null,null);
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

    public int deleteRequest(int requestID)
    {
        return db.delete(REQUESTS_TABLE_NAME,Request_ID + "= ?",new String[] {Integer.toString(requestID)});
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

    public List<Subcategory> selectSubcategoriesByParent(int parentID)
    {
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        //String sql = "SELECT * FROM " +SUBCATEGORIES_TABLE_NAME+ " WHERE " + SubCategory_ParentID + " = '" + parentID + "';";
        //Cursor c = db.rawQuery(sql, null);
        Cursor c = db.query(SUBCATEGORIES_TABLE_NAME, null, SubCategory_ParentID + "=?", new String[]{Integer.toString(parentID)}, null, null, null);
        if (c.moveToFirst())
        {
            int subcatID = c.getInt(0);
            String subcatName = c.getString(1);
            byte[] subcatImg = c.getBlob(2);
            int subcatParent = c.getInt(3);
            Subcategory subcategory = new Subcategory(subcatID, subcatName, subcatImg, subcatParent);
            subcategories.add(subcategory);
        }
        while(c.moveToNext())
        {
            int subcatID = c.getInt(0);
            String subcatName = c.getString(1);
            byte[] subcatImg = c.getBlob(2);
            int subcatParent = c.getInt(3);
            Subcategory subcategory = new Subcategory(subcatID, subcatName, subcatImg, subcatParent);
            subcategories.add(subcategory);
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


    public String findPassword (String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnNames = {Password_UserName,Password};
        //String query = "select * from " + PASSWORD_TABLE ;
                //+ " WHERE " + Password + " =  \"" + userPassword + "\"";

        Cursor cursor = db.query(PASSWORD_TABLE,columnNames,null,null,null,null,null);

       String UsernameInDatabase;
       String passwordInDatabase = "Password not found";

            if (cursor.moveToFirst())
            {
                do {
                UsernameInDatabase = cursor.getString(0);
            cursor.close();
                if(UsernameInDatabase == username)
                {
                    passwordInDatabase = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());

        }

        return passwordInDatabase;
    }
}

