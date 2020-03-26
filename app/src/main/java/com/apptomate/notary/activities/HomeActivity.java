package com.apptomate.notary.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apptomate.notary.R;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    SharedPrefs sharedPrefs;
    String name;
    AppCompatTextView tv_name;
    String id;
    ProgressDialog progressDialog;
    AppCompatTextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        findViews();
        navHeader();
        getLoginData();
        getCount();

//        getSupportActionBar().hide();
    }

    private void navHeader()
    {
        View headerLayout = navigationView.getHeaderView(0);
        ///  ListView lv=headerLayout.findViewById(R.id.lv_nav);
        // lv.setAdapter(new NavMenuAdapter(this));
        LinearLayout ll_profile=headerLayout.findViewById(R.id.ll_profile);
        LinearLayout ll_hlogout=headerLayout.findViewById(R.id.ll_hlogout);
        AppCompatTextView tv_n_name=headerLayout.findViewById(R.id.tv_n_name);
        ImageView imageView=headerLayout.findViewById(R.id.profile_image);
        LinearLayout ll_account=headerLayout.findViewById(R.id.ll_account);
        tv_n_name.setText(name);
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        ll_hlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logOut();
            }
        });
        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,AccountActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViews()
    {
        progressDialog=ApiConstants.showProgressDialog(this,"Please wait....");
        sharedPrefs=new SharedPrefs(this);
       String data= sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA);
        try {
            JSONObject js=new JSONObject(data);
           name= js.optString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_name=findViewById(R.id.tv_name_home);
        tv_count=findViewById(R.id.tv_count);
        tv_name.setText(name);
    }

    public void notification(View view) {

        Intent i=new Intent(HomeActivity.this,NotificationActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void request(View view)
    {
        Intent i=new Intent(HomeActivity.this,RequestActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void process(View view) {
        Intent i=new Intent(HomeActivity.this,InprogressActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void pending(View view) {

        Intent i=new Intent(HomeActivity.this,PendingActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void complete(View view) {

        Intent i=new Intent(HomeActivity.this,CompleteActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }



    public void openDrawer(View view) {
        drawer.openDrawer(Gravity.LEFT);
    }

    void getCount()
    {
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ApiConstants.BaseUrl + "requests?saasUserId=" + id, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray ja=new JSONArray(response);
                   int count= ja.length();
                    tv_count.setText(""+count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    void logOut()
    {
        AlertDialog.Builder alb=new AlertDialog.Builder(this);
        alb.setTitle("Logout");
        alb.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alb.setMessage("Do you want Logout from Application");
        alb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPrefs.logOut();
                finish();
                Intent i=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        alb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alb.create().show();

    }
}
