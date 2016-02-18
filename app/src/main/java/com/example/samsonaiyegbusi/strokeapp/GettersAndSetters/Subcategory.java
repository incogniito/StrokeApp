package com.example.samsonaiyegbusi.strokeapp.GettersAndSetters;

/**
 * Created by Rob on 18/02/2016.
 */
public class Subcategory extends Categories
{
    protected int parentID;
    public Subcategory(int newID, String newName, byte[] newImageID, int newParentID)
    {
        id = newID; //Would this be auto-generated?
        name = newName;
        imageID = newImageID;
        parentID = newParentID;
    }
    public int getParentID()
    {
        return parentID;
    }
}
