package com.example.samsonaiyegbusi.strokeapp;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by samsonaiyegbusi on 12/02/2016.
 */
public interface Variable_Initialiser extends View.OnClickListener, AdapterView.OnItemClickListener {


   void VariableInitialiser();

    @Override
    void onClick(View v);

    @Override
    void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
