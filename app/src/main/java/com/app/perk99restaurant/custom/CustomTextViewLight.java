package com.app.perk99restaurant.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextViewLight extends TextView
{
    public CustomTextViewLight(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewLight(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomTextViewLight(Context context)
    {
        super(context);
        init();
    }

    public void init()
    {
        if (!isInEditMode())
        {
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "Raleway-Light.ttf");
            setTypeface(normalTypeface);
        }
    }
}