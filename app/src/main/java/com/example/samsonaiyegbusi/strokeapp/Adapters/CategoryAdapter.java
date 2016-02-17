package com.example.samsonaiyegbusi.strokeapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;
import com.example.samsonaiyegbusi.strokeapp.MainUI.CustomTextView;
import com.example.samsonaiyegbusi.strokeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/02/2016.
 */
public class CategoryAdapter extends BaseAdapter {

    List<Categories> categories = new ArrayList();
    Context currentPage;


    public CategoryAdapter(List<Categories> categories, Context currentPage)
    {
        this.categories = categories;
        this.currentPage = currentPage;

    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null){
            LayoutInflater inflator = LayoutInflater.from(currentPage);
            v = inflator.inflate(R.layout.requests, null);
        }

        ImageView categoryImage = (ImageView) v.findViewById(R.id.requestImage_iv);
        CustomTextView categoryText = (CustomTextView) v.findViewById(R.id.requestText_tv);

        String CategoryText = categories.get(position).getName();
        byte[] bytes = categories.get(position).getImageBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        categoryImage.setImageBitmap(bmp);
        categoryText.setText(CategoryText);

        return v;
    }
}
