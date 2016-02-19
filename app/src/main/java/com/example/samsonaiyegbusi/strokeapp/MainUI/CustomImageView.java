package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

/**
 * Created by Penny on 18/02/2016.
 */
public class CustomImageView extends ImageView {

    static int newHeight = 300;
    static int newWidth = 300;

    public CustomImageView(Context context)
    {
        super(context);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/

        Bitmap bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
// Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

        this.setImageBitmap(Bitmap.createScaledBitmap(b, newWidth, newHeight, false));



//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
//        this.setLayoutParams(layoutParams);
//        System.out.println("HELLO 1");
//        this.requestLayout();
//        int h = this.getLayoutParams().height;
//        System.out.println(h);


    }

    public CustomImageView(Context context, AttributeSet attribute) {

        super(context, attribute);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/

        Bitmap bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
// Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

        this.setImageBitmap(Bitmap.createScaledBitmap(b, newWidth, newHeight, false));

//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
//        this.setLayoutParams(layoutParams);
//        System.out.println("HELLO 2");
//        this.requestLayout();
//        int h = this.getLayoutParams().height;
//        System.out.println(h);

    }

    public CustomImageView(Context context, AttributeSet attribute, int value)
    {
        super(context, attribute, value);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/
        Bitmap bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
// Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

        this.setImageBitmap(Bitmap.createScaledBitmap(b, newWidth, newHeight, false));



//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
//        this.setLayoutParams(layoutParams);
//        System.out.println("HELLO 3");
//        this.requestLayout();
//        int h = this.getLayoutParams().height;
//        System.out.println(h);
    }

    public static void setSizes(int height, int width)
    {

        newHeight = height;
        newWidth =  width;

    }

    public static void resize()
    {

         newHeight = 600;
         newWidth = 600;

    }

}
