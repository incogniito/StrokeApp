package com.example.samsonaiyegbusi.strokeapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.strokeapp.MainUI.CustomTextView;
import com.example.samsonaiyegbusi.strokeapp.R;
import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;

import org.w3c.dom.Text;

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
        CustomTextView requestText = (CustomTextView) v.findViewById(R.id.requestText_tv);
        TextView audioByteHolder = (TextView) v.findViewById(R.id.AudioBytes_tv);
        audioByteHolder.setVisibility(v.INVISIBLE);

        String categoryText = requests.get(position).getName();
        byte[] imageBytes = requests.get(position).getImageBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        String Audiobytes = Base64.encodeToString(requests.get(position).getSound(), Base64.DEFAULT);

        audioByteHolder.setText(Audiobytes);
        requestImage.setImageBitmap(bmp);
        requestText.setText(categoryText);

        return v;
    }
}
