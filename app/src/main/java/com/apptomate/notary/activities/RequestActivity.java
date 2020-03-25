package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.apptomate.notary.R;
import com.apptomate.notary.adapters.NotifictionAdapter;
import com.apptomate.notary.adapters.RequestAdapter;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.models.RequestModel;
import com.apptomate.notary.models.SortbyStatus;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements SaveView
{

    RecyclerView rv_notification;
    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    String id;
    ArrayList<RequestModel> al=new ArrayList<>();
    private SearchView searchView;
    AppCompatTextView tv_notFound;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        getLoginData();
        getSupportActionBar().setTitle("Notary Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefs.saveStatus("");
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        rv_notification = findViewById(R.id.rv_request);
        tv_notFound = findViewById(R.id.tv_notFound);
        rv_notification.setLayoutManager(new LinearLayoutManager(this));

        //getData();
        getRequestData("");
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

    private void getRequestData(String type)
    {
       progressDialog.show();
       new SaveImpl(this).handleSave(new JSONObject(),"requests?saasUserId="+id,"GET",type);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId()==R.id.notification_filter)
        {
            showBottomSheetDialog();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notification_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.notification_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        //  searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);



        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPrefs.saveStatus("");
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void onSaveSucess(String code, String response,String type) {
        progressDialog.dismiss();
        Log.e("RequestRes",response);
        if (code.equalsIgnoreCase("200"))
        {
            assignData(response,type);
        }
    }

    private void assignData(String response,String type)
    {
        al.clear();
        try {
           // JSONObject js=new JSONObject(response);
           JSONArray ja= new JSONArray(response);
           for (int i=0;i<ja.length();i++)
           {
              JSONObject json= ja.getJSONObject(i);
               String userRequestDetailsId= json.optString("userRequestDetailsId");
               String saasUserId= json.optString("saasUserId");
               String requestedDate= json.optString( "requestedDate");
               String customerId= json.optString("customerId");
               String serviceId= json.optString("serviceId");
               String status= json.optString("status");
               String comments= json.optString("comments");
               String compeletedDate= json.optString("compeletedDate");
               String assignedTo= json.optString("assignedTo");
               String firstName= json.optString("firstName");
               String lastName= json.optString("lastName");
               String email= json.optString("email");
               String phoneNumber= json.optString("phoneNumber");
               String street= json.optString("street");
               String apartment= json.optString("apartment");
               String city= json.optString("city");
               String state= json.optString("state");
               String postalcode= json.optString("postalcode");
               String country= json.optString("country");
               String countryCode= json.optString("countryCode");
               String serviceName= json.optString("serviceName");
               String price= json.optString("price");
               String description= json.optString("description");
               String dateFrom= json.optString("dateFrom");
               String dateTo= json.optString("dateTo");
               String feeDescription= json.optString("feeDescription");
               String fullAddress= json.optString("fullAddress");
               String periodType= json.optString("periodType");
               String documents= json.optString("documentsCount");
               String name= json.optString("name");
               RequestModel requestModel=new RequestModel();
               requestModel.setApartment(apartment);
               requestModel.setName(name);
               requestModel.setAssignedTo(assignedTo);
               requestModel.setCity(city);
               requestModel.setUserRequestDetailsId(userRequestDetailsId);
               requestModel.setDateFrom(dateFrom);
               requestModel.setDocuments(documents);
               requestModel.setPeriodType(periodType);
               requestModel.setFeeDescription(feeDescription);
               requestModel.setDateTo(dateTo);
               requestModel.setDescription(description);
               requestModel.setPrice(price);
               requestModel.setServiceName(serviceName);
               requestModel.setCountryCode(countryCode);
               requestModel.setCountry(country);
               requestModel.setPostalcode(postalcode);
               requestModel.setState(state);
               requestModel.setSaasUserId(saasUserId);
               requestModel.setCustomerId(customerId);
               requestModel.setRequestedDate(requestedDate);
               requestModel.setStreet(street);
               requestModel.setServiceId(serviceId);
               requestModel.setComments(comments);
               requestModel.setStatus(status);
               requestModel.setCompeletedDate(compeletedDate);
               requestModel.setFirstName(firstName);
               requestModel.setLastName(lastName);
               requestModel.setEmail(email);
               requestModel.setFullAddress(fullAddress);
               requestModel.setPhoneNumber(phoneNumber);
               al.add(requestModel);

           }
           if (type.equalsIgnoreCase(""))
           {
               requestAdapter=  new RequestAdapter(this,al,tv_notFound);
               rv_notification.setAdapter(requestAdapter);
           }
           else if (type.equalsIgnoreCase("name"))
           {
               ArrayList<RequestModel> sortedByName = new SortbyStatus(al).getSortedByName();
               requestAdapter=  new RequestAdapter(this,sortedByName,tv_notFound);
               rv_notification.setAdapter(requestAdapter);
           }
           else
           {
               ArrayList<RequestModel> sortedByName = new SortbyStatus(al).getSortedByStatus();
               requestAdapter=  new RequestAdapter(this,sortedByName,tv_notFound);
               rv_notification.setAdapter(requestAdapter);
           }


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String s)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s)
                {
                    String text = s;
                    requestAdapter.filter(text);
                    return false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveFailure(String error) {
         progressDialog.dismiss();
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }


    public void showBottomSheetDialog()
    {
        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.sort_style, null,false);
        RelativeLayout rv_name=view.findViewById(R.id.name_sort);
        RelativeLayout rv_status=view.findViewById(R.id.status_sort);
        RadioButton rb_name=view.findViewById(R.id.rb_name);
        RadioButton rb_status=view.findViewById(R.id.tb_status);
       String status=  sharedPrefs.getStatus().get(SharedPrefs.STATUS_DATA);
        if (status.equalsIgnoreCase(""))
        {
            rb_name.setChecked(false);
            rb_status.setChecked(false);
        }
        else if (status.equalsIgnoreCase("name"))
        {
            rb_name.setChecked(true);
            rb_status.setChecked(false);
        }
        else if (status.equalsIgnoreCase("status"))
        {
            rb_name.setChecked(false);
            rb_status.setChecked(true);
        }
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        rv_name.setOnClickListener(v -> {
             dialog.dismiss();
             sharedPrefs.saveStatus("name");
            getRequestData("name");
        });
        rv_status.setOnClickListener(v -> {
            dialog.dismiss();
            sharedPrefs.saveStatus("status");
            getRequestData("status");
        });
        rb_name.setOnClickListener(v -> {
            dialog.dismiss();
            sharedPrefs.saveStatus("name");
            getRequestData("name");
        });
        rb_status.setOnClickListener(v -> {
            dialog.dismiss();
            sharedPrefs.saveStatus("status");
            getRequestData("status");
        });
        dialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getRequestData(sharedPrefs.getStatus().get(SharedPrefs.STATUS_DATA));
    }
}