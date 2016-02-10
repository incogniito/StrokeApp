package com.example.samsonaiyegbusi.strokeapp;

/**
 * Class for Request objects which contain images, sounds, etc
 * Created by Rob on 10/02/2016.
 */
public class Request
{
    private int id;
    private String name;
    private int soundID;
    private int imageID;

    public Request(int newID, String newName, int newSoundID, int newImageID)
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

    public int getImageID()
    {
        return imageID;
    }

}
