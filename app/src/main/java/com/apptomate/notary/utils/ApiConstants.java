package com.apptomate.notary.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ApiConstants
{
    public static final String BaseUrl="https://notaryapi.herokuapp.com/";

   public static ProgressDialog showProgressDialog(Context context,String message)
    {
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage(message);
        pd.setCancelable(false);
        return pd;
    }
}
