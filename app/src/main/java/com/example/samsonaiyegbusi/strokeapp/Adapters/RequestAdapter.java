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

import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/02/2016.
 */
public class RequestAdapter extends BaseAdapter {


    List<Request> requests = new ArrayList();
    Context currentPage;


    public RequestAdapter(List<Request> requests, Context currentPage)
    {
        this.requests = requests;
        this.currentPage = currentPage;

    }

    @Override
    public int getCount() {
        return requests.size();
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

        ImageView requestImage = (ImageView) v.findViewById(R.id.requestImage_iv);
        TextView requestText = (TextView) v.findViewById(R.id.requestText_tv);

        String categoryText = requests.get(position).getName();
        byte[] bytes = requests.get(position).getImageBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        requestImage.setImageBitmap(bmp);
        requestText.setText(categoryText);

        return v;
    }
}
