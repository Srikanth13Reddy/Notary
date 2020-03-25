package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.apptomate.notary.R;
import com.apptomate.notary.adapters.CompleteAdapter;
import com.apptomate.notary.adapters.InprogressAdapter;
import com.apptomate.notary.adapters.PendingAdapter;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.models.RequestModel;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingActivity extends AppCompatActivity implements SaveView
{
    
    RecyclerView rv_pending;
    ProgressDialog progressDialog;
    SharedPrefs sharedPrefs;
    String id;
    ArrayList<RequestModel> al=new ArrayList<>();
    private SearchView searchView;
    AppCompatTextView tv_notFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        getSupportActionBar().setTitle("Pending Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        rv_pending = findViewById(R.id.rv_pending);
        tv_notFound = findViewById(R.id.tv_notFound);
        rv_pending.setLayoutManager(new LinearLayoutManager(this));
//        getData();
        getLoginData();
        //getData();
        getRequestData();
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

    private void getRequestData()
    {
        progressDialog.show();
        new SaveImpl(this).handleSave(new JSONObject(),"requests?saasUserId="+id,"GET","");
    }

//    private void getData() {
//        rv_pending.setAdapter(new PendingAdapter(this));
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    @Override
    public void onSaveFailure(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSaveSucess(String code, String response,String type) {
        progressDialog.dismiss();
        Log.e("RequestRes",response);
        if (code.equalsIgnoreCase("200"))
        {
            assignData(response);
        }
    }

    private void assignData(String response)
    {
        al.clear();
        try {
            // JSONObject js=new JSONObject(response);
            JSONArray ja= new JSONArray(response);
            for (int i=0;i<ja.length();i++)
            {
                JSONObject json= ja.getJSONObject(i);
                String status_= json.optString("status");
                if (status_.equalsIgnoreCase("Pending"))
                {
                    String userRequestDetailsId= json.optString("userRequestDetailsId");
                    String saasUserId= json.optString("saasUserId");
                    String requestedDate= json.optString("requestedDate");
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
                    String periodType= json.optString("periodType");
                    String documents= json.optString("documentsCount");
                    String name= json.optString("name");
                    String fullAddress= json.optString("fullAddress");
                    RequestModel requestModel=new RequestModel();
                    requestModel.setName(name);
                    requestModel.setApartment(apartment);
                    requestModel.setFullAddress(fullAddress);
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
                    requestModel.setPhoneNumber(phoneNumber);
                    al.add(requestModel);
                }


            }
            PendingAdapter requestAdapter=  new PendingAdapter(this,al,tv_notFound);
            rv_pending.setAdapter(requestAdapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String s)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    String text = s;
                    requestAdapter.filter(text);
                    return false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}