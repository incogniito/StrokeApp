package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextView extends TextView {

    static float defaultFontSize = 16;
    static float fontSize = defaultFontSize;
    static boolean toBold = false;


    public CustomTextView(Context context)
    {
        super(context);
        // this.setTextSizes (textSize);
        this.setTextSize(fontSize);
        if(toBold) {
            this.setTypeface(null, Typeface.BOLD);
        }

    }

    public CustomTextView(Context context, AttributeSet attribute)
    {
        super(context, attribute);
        // this.setTextSizes(textSize);
        this.setTextSize(fontSize);
        if(toBold) {
            this.setTypeface(null, Typeface.BOLD);
        }

    }

    public CustomTextView(Context context, AttributeSet attribute, int value) {
        super(context, attribute, value);
        //  this.setTextSizes(textSize);
        this.setTextSize(fontSize);
        if(toBold) {
            this.setTypeface(null, Typeface.BOLD);
        }


    }
    public static void setTextSizes(int ts)
    {
        fontSize = ts;
    }

    public static void makeBold()
    {
        toBold = true;

    }

    public static void removeBold()
    {
        toBold = false;

    }


}




