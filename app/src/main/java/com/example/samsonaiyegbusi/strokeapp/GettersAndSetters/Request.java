package com.example.samsonaiyegbusi.strokeapp.GettersAndSetters;

/**
 * Class for Request objects which contain images, sounds, etc
 * Created by Rob on 10/02/2016.
 */
public class Request
{
    private int id;
    private String name;
    private byte[] sound;
    private byte[] image;

    public Request()
    {

    }

    public Request(int newID, String newName, byte[] newSound, byte[] newImage)
    {
        id = newID; //Would this be auto-generated?
        name = newName;
        sound = newSound;
        image = newImage;
    }
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public byte[] getSound()
    {
        return sound;
    }

    public byte[] getImageBytes()
    {
        return image;
    }


}
