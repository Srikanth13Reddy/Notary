package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.apptomate.notary.R;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity implements SaveView
{
    SharedPrefs sharedPrefs;
    String id;
    ProgressDialog progressDialog;
    AppCompatTextView tv_total,tv_pending,tv_process,tv_complete,tv_name,tv_type,tv_email,tv_mobile,tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        findViews();
        getLoginData();
        getData();
    }

    private void findViews()
    {
        tv_total=findViewById(R.id.tv_total);
        tv_complete=findViewById(R.id.tv_complete);
        tv_pending=findViewById(R.id.tv_pending);
        tv_process=findViewById(R.id.tv_process);
        tv_name=findViewById(R.id.tv_name);
        tv_type=findViewById(R.id.tv_type);
        tv_address=findViewById(R.id.tv_address);
        tv_email=findViewById(R.id.tv_email);
        tv_mobile=findViewById(R.id.tv_mobile);
    }

    private void getData()
    {
        progressDialog.show();
        new SaveImpl(this).handleSave(new JSONObject(),"saasuser?saasUserId="+id,"GET","");
    }

    private void getLoginData()
    {
        sharedPrefs=new SharedPrefs(this);
        try {
            if (sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)!=null)
            {
                JSONObject js=new JSONObject(sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA));
                id= js.optString("id");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSaveSucess(String code, String response, String type)
    {
        progressDialog.dismiss();
        Log.e("ProfileStatus",response);
        if (code.equalsIgnoreCase("200"))
        {
            try {
                JSONObject jsonObject=new JSONObject(response);
                String totalRequest=  jsonObject.optString("totalRequest");
                String pendingRequests=  jsonObject.optString("pendingRequests");
                String inprogressRequests=  jsonObject.optString("inprogressRequests");
                String completedRequests=  jsonObject.optString("completedRequests");
                JSONObject js=jsonObject.getJSONObject("user");
                String profileImage=js.optString("profileImage");
                String email=js.optString("email");
                String phoneNumber=js.optString("phoneNumber");
                String name=js.optString("name");
                String roleName=js.optString("roleName");
                String fullAddress=js.optString("fullAddress");
                tv_total.setText(""+totalRequest);
                tv_pending.setText(""+pendingRequests);
                tv_process.setText(""+inprogressRequests);
                tv_complete.setText(""+completedRequests);
                tv_name.setText(name);
                tv_email.setText(email);
                tv_mobile.setText(phoneNumber);
                tv_type.setText(roleName);
                tv_address.setText(fullAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onSaveFailure(String error)
    {
        progressDialog.dismiss();
        Log.e("ProfileStatus",error);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
