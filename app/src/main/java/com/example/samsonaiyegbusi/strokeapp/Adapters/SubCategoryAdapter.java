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
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Subcategory;
import com.example.samsonaiyegbusi.strokeapp.MainUI.CustomImageView;
import com.example.samsonaiyegbusi.strokeapp.MainUI.CustomTextView;
import com.example.samsonaiyegbusi.strokeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/02/2016.
 */
public class SubCategoryAdapter extends BaseAdapter {

    List<Subcategory> categories = new ArrayList();
    Context currentPage;


    public SubCategoryAdapter(List<Subcategory> categories, Context currentPage)
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

        CustomImageView categoryImage = (CustomImageView) v.findViewById(R.id.requestImage_iv);
        //ImageView categoryImage = (ImageView) v.findViewById(R.id.requestImage_iv);
        CustomTextView categoryText = (CustomTextView) v.findViewById(R.id.requestText_tv);
        TextView ID_tv = (TextView) v.findViewById(R.id.ID_tv);
        ID_tv.setVisibility(v.INVISIBLE);

        TextView audioByteHolder = (TextView) v.findViewById(R.id.AudioBytes_tv);
        audioByteHolder.setVisibility(v.INVISIBLE);

        String CategoryText = categories.get(position).getName();
        String CategoryID = Integer.toString(categories.get(position).getId());
        byte[] bytes = categories.get(position).getImageBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ID_tv.setText(CategoryID);
        categoryImage.setImageBitmap(bmp);
        categoryText.setText(CategoryText);

        return v;
    }
}
