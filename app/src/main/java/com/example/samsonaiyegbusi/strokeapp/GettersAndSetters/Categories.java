package com.example.samsonaiyegbusi.strokeapp.GettersAndSetters;

/**
 * Class for Request objects which contain images, sounds, etc
 * Created by Rob on 10/02/2016.
 */
public class Categories
{
    private int id;
    private String name;
    private byte[] imageID;
    private int subcatID;

    public Categories(int newID, String newName, byte[] newImageID)
    {
        id = newID; //Would this be auto-generated?
        name = newName;
        imageID = newImageID;
    }
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public byte[] getImageBytes()
    {
        return imageID;
    }

}
