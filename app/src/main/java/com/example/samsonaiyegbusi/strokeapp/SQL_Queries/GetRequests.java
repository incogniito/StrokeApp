package com.example.samsonaiyegbusi.strokeapp.SQL_Queries;

import com.example.samsonaiyegbusi.strokeapp.GettersAndSetters.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/02/2016.
 */
public class GetRequests {

    String childCategoryName;

    public GetRequests(String childCategoryName)
    {
        this.childCategoryName = childCategoryName;
    }

    public List<Request> RequestList(){
        List<Request> requests = new ArrayList();

        // Query SQL lite data base to return all request records associated with the child category name
        //for each record
        //check the contents of the specific column and use the setters in the requests class to associate it respected types
        // and then add that request to the list

        return requests;
    }

}
