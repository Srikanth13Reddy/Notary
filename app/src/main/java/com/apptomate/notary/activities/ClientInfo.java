package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apptomate.notary.R;
import com.apptomate.notary.adapters.DocumentsAdapter;
import com.apptomate.notary.adapters.DocumentsTypeAdapter;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.models.DocumentsDetailsModel;
import com.apptomate.notary.models.DocumentsModel;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.MyListView;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ClientInfo extends AppCompatActivity implements SaveView , PopupMenu.OnMenuItemClickListener
{

    MyListView lv_documents,lv_documents1;
    String rId,status;
    String id,roleId;
    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    AppCompatTextView tv_client_name,tv_client_shipping_address,tv_service,tv_assign_;
    private String userRequestDetailsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        getSupportActionBar().setTitle("Client Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        findViews();
        getLoginData();
        Bundle b= getIntent().getExtras();
        if (b != null) {
            rId= b.getString("rId");
            status= b.getString("status");
            getRequestData();
        }

        if (roleId.equalsIgnoreCase("1")&&status.equalsIgnoreCase("New"))
        {
            tv_assign_.setVisibility(View.VISIBLE);
            tv_assign_.setText("Assign");
        }
        else if (roleId.equalsIgnoreCase("1")&&status.equalsIgnoreCase("Pending"))
        {
            tv_assign_.setVisibility(View.VISIBLE);
            tv_assign_.setText("Re Assign");
        }
        else {
            tv_assign_.setVisibility(View.GONE);
        }

        tv_assign_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getLoginData()
    {
        sharedPrefs=new SharedPrefs(this);
        try {
            if (sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)!=null)
            {
                JSONObject js=new JSONObject(sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA));
                id= js.optString("id");
                roleId= js.optString("roleId");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViews()
    {
        lv_documents= findViewById(R.id.lv_documents);
        lv_documents1= findViewById(R.id.lv_documents1);
        tv_assign_= findViewById(R.id.tv_assign_);
        tv_client_name= findViewById(R.id.tv_client_name);
        tv_client_shipping_address= findViewById(R.id.tv_client_shipping_address);
       // tv_service= findViewById(R.id.tv_service);
       // lv_documents.setAdapter(new DocumentsAdapter(this));

    }

    public void getRequestData()
    {
        progressDialog.show();
        new SaveImpl(this).handleSave(new JSONObject(),"requestbyid/"+rId,"GET","");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSaveSucess(String code, String response,String type)
    {
        progressDialog.dismiss();
        Log.e("requestbyid",response);
        ArrayList<DocumentsModel> arrayList=new ArrayList<>();
        ArrayList<DocumentsDetailsModel> arrayList1=new ArrayList<>();

        if (code.equalsIgnoreCase("200"))
        {
            try {
                JSONObject js=new JSONObject(response);
                JSONObject jsonObject= js.getJSONObject("request");
                String fullAddress= jsonObject.optString("fullAddress");
                String firstName= jsonObject.optString("firstName");
                String lastName= jsonObject.optString("lastName");
                 userRequestDetailsId= jsonObject.optString("userRequestDetailsId");
                tv_client_shipping_address.setText(fullAddress);
                tv_client_name.setText(firstName+" "+lastName);
                String documents= js.optString("documents");
                String documentdetails= js.optString("documentdetails");
                JSONArray jaa=new JSONArray(documentdetails);
                for (int j=0;j<jaa.length();j++)
                {
                   JSONObject json= jaa.getJSONObject(j);
                    String documentDetailsId= json.optString("documentDetailsId");
                    String serviceName= json.optString("serviceName");
                    String documentName= json.optString("documentName");
                    String stateName= json.optString("stateName");
                    String status= json.optString("status");
                    DocumentsDetailsModel detailsModel=new DocumentsDetailsModel();
                    detailsModel.setDocumentDetailsId(documentDetailsId);
                    detailsModel.setServiceName(serviceName);
                    detailsModel.setStateName(stateName);
                    detailsModel.setStatus(status);
                    detailsModel.setDocumentName(documentName);
                    arrayList1.add(detailsModel);
                }
                DocumentsTypeAdapter typeAdapter=new DocumentsTypeAdapter(arrayList1,this);
                JSONArray ja=new JSONArray(documents);
                for (int i=0;i<ja.length();i++)
                {
                    JSONObject json= ja.getJSONObject(i);
                    String fileName= json.optString("fileName");
                    String url= json.optString("url");
                    String fileType= json.optString("fileType");
                   // String status= json.optString("status");
                   // String stateId= json.optString("stateId");
                   // String serviceId= json.optString("serviceId");
                    String userDocumentId= json.optString("requestId");
                    String userRequestDetailsId= json.optString("documentId");
                    DocumentsModel documentsModel=new DocumentsModel();
                    documentsModel.setFileName(fileName);
                    documentsModel.setUrl(url);
                    documentsModel.setStatus(status);
                    documentsModel.setFileType(fileType);
                  //  documentsModel.setStateId(stateId);
                   // documentsModel.setServiceId(serviceId);
                    documentsModel.setUserDocumentId(userDocumentId);
                    documentsModel.setUserRequestDetailsId(userRequestDetailsId);
                    arrayList.add(documentsModel);
                }
                DocumentsAdapter documentsAdapter=new DocumentsAdapter(this,arrayList);
                lv_documents.setAdapter(documentsAdapter);
                lv_documents1.setAdapter(typeAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onSaveFailure(String error) {
        progressDialog.dismiss();
        Log.e("requestbyid",error);
    }

    public void edit(View view)
    {


        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(ClientInfo.this);
        inflater.inflate(R.menu.edit_style, popup.getMenu());
        popup.show();

//        String[] names={"Reject","Pending","InProgress","Complete"};
//        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.activity_list_item,names);
//       LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//       View v=layoutInflater.inflate(R.layout.edit_screen,null,false);
//      TextView tv=v.findViewById(R.id.tv);
//      // listView.setAdapter(arrayAdapter);
//
//        new SimpleTooltip.Builder(this)
//                .anchorView(view)
//              //  .text(al.get(position).getDescription())
//                .gravity(Gravity.TOP)
//                .dismissOnOutsideTouch(true)
//                .contentView(R.layout.edit_screen)
//                .dismissOnInsideTouch(false)
//                .animated(true)
//                .build()
//                .show();
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ClientInfo.this, "Hiii", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        //Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId())
        {
            case R.id.rejected:
                // do your code
                changeStatus("Rejected");
                return true;
            case R.id.pending:
                // do your code
                changeStatus("Pending");
                return true;
            case R.id.progress:
                // do your code
                changeStatus("Inprogress");
                return true;
            case R.id.complete:
                // do your code
                changeStatus("Completed");
                return true;
            default:
                return false;
        }
    }

    private void changeStatus(String status)
    {
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody=js.toString();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, ApiConstants.BaseUrl + "/request?requestId="+userRequestDetailsId, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                Log.e("ResponseStatus",response);
                try {
                    JSONObject js=new JSONObject(response);
                   String status= js.optString("status");
                   if (status.equalsIgnoreCase("Success"))
                   {
                       finish();
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
