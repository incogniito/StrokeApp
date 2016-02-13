package com.example.samsonaiyegbusi.strokeapp.SQL_Queries;

import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Categories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/02/2016.
 */
public class GetCategories {


    public GetCategories()
    {
    }

    public List<Categories> categoryList(){
        List<Categories> Categories = new ArrayList();

        // Query SQL lite data base to return all category records
        //for each record
        //check the contents of the specific column and use the setters in the categories class to associate it respected types
        // and then add that category to the list

        return Categories;
    }

}
