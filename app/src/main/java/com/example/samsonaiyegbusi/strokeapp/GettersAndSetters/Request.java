package com.example.samsonaiyegbusi.strokeapp.GettersAndSetters;

/**
 * Class for Request objects which contain images, sounds, etc
 * Created by Rob on 10/02/2016.
 */
public class Request
{
    private int id;
    private String name;
    private int soundID;
    private byte[] imageID;

    public Request(int newID, String newName, int newSoundID, byte[] newImageID)
    {
        id = newID; //Would this be auto-generated?
        name = newName;
        soundID = newSoundID;
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

    public int getSoundID()
    {
        return soundID;
    }

    public byte[] getImageBytes()
    {
        return imageID;
    }


}
