package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Penny on 18/02/2016.
 */
public class CustomImageView extends ImageView {

    static int newHeight = 1000;
    static int newWidth = 1000;

    public CustomImageView(Context context)
    {
        super(context);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
        this.setLayoutParams(layoutParams);
        System.out.println("HELLO 1");
        this.requestLayout();
        int h = this.getLayoutParams().height;
        System.out.println(h);


    }

    public CustomImageView(Context context, AttributeSet attribute) {

        super(context, attribute);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
        this.setLayoutParams(layoutParams);
        System.out.println("HELLO 2");
        this.requestLayout();
        int h = this.getLayoutParams().height;
        System.out.println(h);

    }

    public CustomImageView(Context context, AttributeSet attribute, int value)
    {
        super(context, attribute, value);
        /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        this.setLayoutParams(layoutParams);*/
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(newHeight, newWidth);
        this.setLayoutParams(layoutParams);
        System.out.println("HELLO 3");
        this.requestLayout();
        int h = this.getLayoutParams().height;
        System.out.println(h);
    }

    public static void setSizes(int height, int width)
    {

        newHeight = height;
        newWidth = width;

    }

    public static void resize()
    {

         newHeight = 600;
         newWidth = 600;

    }

}
