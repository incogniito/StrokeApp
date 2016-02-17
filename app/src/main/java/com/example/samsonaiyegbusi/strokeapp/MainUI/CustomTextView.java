package com.example.samsonaiyegbusi.strokeapp.MainUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Penny on 17/02/2016.
 */
public class CustomTextView extends TextView {

    static float defaultFontSize = 16;
    static float fontSize = defaultFontSize;


    public CustomTextView(Context context)
    {
        super(context);
        // this.setTextSizes(textSize);
        this.setTextSize(fontSize);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // this.setTextSizes(textSize);
        this.setTextSize(fontSize);

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //  this.setTextSizes(textSize);
        this.setTextSize(fontSize);


    }
    public static void setTextSizes(int ts)
    {
        fontSize = ts;
    }


}




