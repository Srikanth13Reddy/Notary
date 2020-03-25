package com.apptomate.notary.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apptomate.notary.R;
import com.apptomate.notary.activities.ClientInfo;
import com.apptomate.notary.models.DocumentsDetailsModel;
import com.apptomate.notary.utils.ApiConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DocumentsTypeAdapter extends BaseAdapter
{
    ArrayList<DocumentsDetailsModel> arrayList;
    Context context;

    public DocumentsTypeAdapter(ArrayList<DocumentsDetailsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      @SuppressLint("ViewHolder") View v= layoutInflater.inflate(R.layout.document_type_style,parent,false);
        Switch aSwitch=v.findViewById(R.id.sw);
        if (arrayList.get(position).getStatus().equalsIgnoreCase("Unverified"))
        {
            aSwitch.setChecked(false);
        }
        else {
            aSwitch.setChecked(true);
        }
        AppCompatTextView fname=v.findViewById(R.id.fname);
        AppCompatTextView sname=v.findViewById(R.id.sname);
        AppCompatTextView service=v.findViewById(R.id.service);
        fname.setText(""+(position+1)+"."+ arrayList.get(position).getDocumentName());
        sname.setText(arrayList.get(position).getStateName());
        service.setText(arrayList.get(position).getServiceName());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {


                 if (arrayList.get(position).getStatus().equalsIgnoreCase("Unverified"))
                 {
                     changeStatus(arrayList.get(position).getDocumentDetailsId(),"Verified","");
                 }
                 else {
                      showTextDialog(arrayList.get(position).getDocumentDetailsId(),"Unverified");
                 }

            }
        });
        return v;
    }

    private void showTextDialog(String documentDetailsId, String status)
    {
        AlertDialog.Builder alb=new AlertDialog.Builder(context);
       LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v=layoutInflater.inflate(R.layout.pending_dialog,null,false);
       AppCompatEditText et=v.findViewById(R.id.et_text);
       Button btn_acpt=v.findViewById(R.id.btn_acpt);
       alb.setView(v);
      AlertDialog ad= alb.create();
      ad.show();
      btn_acpt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (et.getText().toString().equalsIgnoreCase(""))
              {
                  Toast.makeText(context, "Please enter comments", Toast.LENGTH_SHORT).show();
              }
              else {
                  ad.cancel();
                  changeStatus(documentDetailsId,status,et.getText().toString());
              }
          }
      });
    }

    private void changeStatus(String id,String status,String comments)
    {
        ProgressDialog progressDialog=ApiConstants.showProgressDialog(context,"Please wait.....");
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("status",status);
            js.put("comments",comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody=js.toString();
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, ApiConstants.BaseUrl + "/document?documentId="+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                Log.e("Response",response);

                try {
                    JSONObject js=new JSONObject(response);
                   String status= js.optString("status");
                   if (status.equalsIgnoreCase("Success"))
                   {
                       if (context instanceof ClientInfo) {
                           ((ClientInfo)context).getRequestData();
                       }
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Log.e("Response",""+error);
            }
        })

        {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(stringRequest);
    }
}
