package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apptomate.notary.R;
import com.apptomate.notary.adapters.CountryAdapter;
import com.apptomate.notary.adapters.StateAdapter;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.models.CountryModel;
import com.apptomate.notary.models.StateModel;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.MySingleton;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AccountActivity extends AppCompatActivity implements CountryAdapter.EventListener,StateAdapter.EventListener, SaveView
{
    boolean password=false;
    boolean phone=false;
    boolean address=false;
    AppCompatImageView iv_right,iv_down,iv_right_p,iv_down_p,iv_right_ad,iv_down_ad;
    LinearLayout ll_pass,change_phone_ll,change_address_ll;
    RelativeLayout rl_pass,rl_phone,rl_address;
    ProgressDialog progressDialog;
    AppCompatEditText et_oldpass,et_new_pass,et_confirm_pass,act_phone,et_street,et_apartment,et_city,et_country,et_state,et_postalcode;
    AppCompatButton acct_save_pass;
    SharedPrefs sharedPrefs;
    String id,token;
    CheckBox act_old_ps_check,act_new_ps_check,act_confirm_ps_check;
    private AlertDialog ad;
    TextView tv_country_code,tv_state_code;
    Button act_phone_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        findViews();
        getLoginData();
        getUserData();
    }
    private void getLoginData()
    {
        sharedPrefs=new SharedPrefs(this);
        try {
            if (sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)!=null)
            {
                JSONObject js=new JSONObject(Objects.requireNonNull(sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)));
                id= js.optString("id");
                token= js.optString("token");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void findViews()
    {
        tv_country_code=new TextView(this);
        tv_state_code=new TextView(this);
        et_street= findViewById(R.id.et_street);
        et_apartment= findViewById(R.id.et_apartment);
        et_city= findViewById(R.id.et_city);
        et_country= findViewById(R.id.et_country);
        et_state= findViewById(R.id.et_state);
        et_postalcode= findViewById(R.id.et_postalcode);
        rl_address= findViewById(R.id.rl_address);
        change_address_ll= findViewById(R.id.change_address_ll);
        iv_right_ad= findViewById(R.id.iv_right_ad);
        iv_down_ad= findViewById(R.id.iv_down_ad);
        change_phone_ll=  findViewById(R.id.change_phone_ll);
        iv_right_p=  findViewById(R.id.iv_right_p);
        iv_down_p=  findViewById(R.id.iv_down_p);
        rl_phone=  findViewById(R.id.rl_phone);
        act_phone=  findViewById(R.id.act_phone);
        act_phone_btn=  findViewById(R.id.act_phone_btn);
        ll_pass=  findViewById(R.id.change_pass_ll);
        acct_save_pass=  findViewById(R.id.acct_save_pass);
        et_oldpass=  findViewById(R.id.act_old_ps);
        et_new_pass=  findViewById(R.id.act_new_ps);
        et_confirm_pass=  findViewById(R.id.act_confirm_ps);
        ll_pass=  findViewById(R.id.change_pass_ll);
        iv_right=  findViewById(R.id.iv_right);
        iv_down=  findViewById(R.id.iv_down);
        rl_pass=  findViewById(R.id.rl_pass);
        act_old_ps_check=  findViewById(R.id.act_old_ps_check);
        act_new_ps_check=  findViewById(R.id.act_new_ps_check);
        act_confirm_ps_check=  findViewById(R.id.act_confirm_ps_check);


        et_street.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        et_city.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        et_apartment.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);


        et_oldpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                act_old_ps_check.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                act_new_ps_check.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_confirm_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                act_confirm_ps_check.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 et_state.setText("");
            }
        });


        rl_pass.setOnClickListener(v -> {
            if (password)
            { ll_pass.setVisibility(View.GONE);
                iv_down.setVisibility(View.GONE);
                iv_right.setVisibility(View.VISIBLE);
                password=false;
            }
            else {
                ll_pass.setVisibility(View.VISIBLE);
                iv_down.setVisibility(View.VISIBLE);
                iv_right.setVisibility(View.GONE);
                password=true;
            }
        });


        rl_phone.setOnClickListener(v -> {
            if (phone)
            {
                change_phone_ll.setVisibility(View.GONE);
                iv_down_p.setVisibility(View.GONE);
                iv_right_p.setVisibility(View.VISIBLE);
                phone=false;
            }
            else {
                change_phone_ll.setVisibility(View.VISIBLE);
                iv_down_p.setVisibility(View.VISIBLE);
                iv_right_p.setVisibility(View.GONE);
                phone=true;
            }
        });

        rl_address.setOnClickListener(v -> {
            if (address)
            {
                change_address_ll.setVisibility(View.GONE);
                iv_down_ad.setVisibility(View.GONE);
                iv_right_ad.setVisibility(View.VISIBLE);
                address=false;
            }
            else {
                change_address_ll.setVisibility(View.VISIBLE);
                iv_down_ad.setVisibility(View.VISIBLE);
                iv_right_ad.setVisibility(View.GONE);
                address=true;
            }
        });

        act_old_ps_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                et_oldpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_oldpass.setSelection(et_oldpass.length());
            } else {
                et_oldpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_oldpass.setSelection(et_oldpass.length());
            }
        });

        act_new_ps_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                et_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_new_pass.setSelection(et_new_pass.length());
            } else {
                et_new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_new_pass.setSelection(et_new_pass.length());
            }
        });

        act_confirm_ps_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                et_confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_confirm_pass.setSelection(et_confirm_pass.length());
            } else {
                et_confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_confirm_pass.setSelection(et_confirm_pass.length());
            }
        });


        et_country.setOnClickListener(v -> showCountryList());
        et_state.setOnClickListener(v -> {
            if (Objects.requireNonNull(et_country.getText()).toString().isEmpty())
            {
                Toast.makeText(AccountActivity.this, "Choose Country", Toast.LENGTH_SHORT).show();
            }
            else {
                showStatesList();
            }
        });


    }

    private void showStatesList()
    {
        LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v;
        if (layoutInflater != null) {
            v = layoutInflater.inflate(R.layout.state_search,null,false);
            RecyclerView rv_search=v.findViewById(R.id.rv_state_search);
            AppCompatTextView tv_notFound=v.findViewById(R.id.tv_notFound);
            SearchView country_search=v.findViewById(R.id.state_search);
            country_search.setIconifiedByDefault(false);
            AlertDialog.Builder alb=new AlertDialog.Builder(this);
            alb.setView(v);
            ad=alb.create();
            getStateData(rv_search,country_search,ad,tv_notFound);
        }

    }

    private void getStateData(RecyclerView rv_search, SearchView country_search, AlertDialog ad, AppCompatTextView tv_notFound)
    {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_search.getContext(), llm.getOrientation());
        rv_search.addItemDecoration(dividerItemDecoration);
        rv_search.setLayoutManager(llm);
        ArrayList<StateModel> arrayList=new ArrayList<>();
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ApiConstants.BaseUrl + "getstates?countryId="+tv_country_code.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                ad.show();
                progressDialog.dismiss();
                try {
                    JSONArray ja=new JSONArray(response);
                    for (int i=0;i<ja.length();i++)
                    {
                        JSONObject json= ja.getJSONObject(i);
                        String stateId= json.optString("stateId");
                        String stateName= json.optString("stateName");
                        String countryId= json.optString("countryId");
                        StateModel countryModel=new StateModel();
                        countryModel.setStateId(stateId);
                        countryModel.setCountryId(countryId);
                        countryModel.setStateName(stateName);
                        arrayList.add(countryModel);
                    }
                    StateAdapter countryAdapter=new StateAdapter(arrayList,AccountActivity.this,tv_notFound,AccountActivity.this,ad);
                    rv_search.setAdapter(countryAdapter);

                    country_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            countryAdapter.filter(newText);
                            return false;
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                ApiConstants.parseVolleyError(AccountActivity.this,error);
                //Toast.makeText(AccountActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> hm=new HashMap<>();
                hm.put("Authorization","Bearer "+token);
                return hm;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void changePassword(String id,String old,String newp)
    {
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("oldPassword",old);
            js.put("newPassword",newp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody=js.toString();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, ApiConstants.BaseUrl + "passwordchange?saasUserId="+id, response -> {
            progressDialog.dismiss();
            Log.e("Response",response);

            if (response.equalsIgnoreCase("{\"error\":\"Invalid Password\"}"))
            {
                try {
                    JSONObject js1 =new JSONObject(response);
                    if (js1.optString("error").equalsIgnoreCase("Invalid Password"))
                    {
                        act_old_ps_check.setVisibility(View.GONE);
                        et_oldpass.setError("Invalid Password");
                        et_oldpass.requestFocus();
                    }else {
                        Toast.makeText(AccountActivity.this, ""+ js1.optString("error"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    JSONObject js1 =new JSONObject(response);
                    String status= js1.optString("status");
                    if (status.equalsIgnoreCase("Success"))
                    {
                        et_oldpass.setText("");
                        et_confirm_pass.setText("");
                        et_new_pass.setText("");
                        Toast.makeText(AccountActivity.this, ""+ js1.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




        }, error -> {
            ApiConstants.parseVolleyError(AccountActivity.this,error);
            progressDialog.dismiss();
            Log.e("Response",""+error);
        })


        {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> hm=new HashMap<>();
                hm.put("Authorization","Bearer "+token);
                hm.put("Content-Type", "application/json; charset=utf-8");
                return hm;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void savePass(View view)
    {
        if (et_oldpass.getText().toString().isEmpty())
        {
            act_old_ps_check.setVisibility(View.GONE);
           // Toast.makeText(this, "Please enter old password", Toast.LENGTH_SHORT).show();
            et_oldpass.setError("Please enter old password");
            et_oldpass.requestFocus();
        }
        else if (et_new_pass.getText().toString().isEmpty())
        {
           // Toast.makeText(this, "Please enter new password", Toast.LENGTH_SHORT).show();
            act_new_ps_check.setVisibility(View.GONE);
            et_new_pass.setError("Please enter new password");
            et_new_pass.requestFocus();
        }

        else if(et_new_pass.getText().toString().length()<6){
            act_new_ps_check.setVisibility(View.GONE);
            et_new_pass.setError("Password requires  minimum 6 characters");
            et_new_pass.requestFocus();
           // Toast.makeText(getApplicationContext(), "Password requires  minimum 5 characters" , Toast.LENGTH_SHORT).show();
            //Snackbar.make(root," Enter Your Paasword!!!",Snackbar.LENGTH_SHORT).show();
        }
        else if(et_confirm_pass.getText().toString().isEmpty()){
            act_confirm_ps_check.setVisibility(View.GONE);
            et_confirm_pass.setError("Please enter confirm password");
            et_confirm_pass.requestFocus();
           // Toast.makeText(getApplicationContext(), "Password enter confirm password" , Toast.LENGTH_SHORT).show();
            // Snackbar.make(root," Enter Confirm Password  match!!!",Snackbar.LENGTH_SHORT).show();
        }
        else if( !et_confirm_pass.getText().toString().equals(et_new_pass.getText().toString())){
           // Toast.makeText(getApplicationContext(), "Confirm Password mismatch" , Toast.LENGTH_SHORT).show();
            act_confirm_ps_check.setVisibility(View.GONE);
            et_confirm_pass.setError("Password mismatch");
            et_confirm_pass.requestFocus();
            //Snackbar.make(root," Confirm Password not match!!!",Snackbar.LENGTH_SHORT).show();
        }
        else {
            changePassword(id,et_oldpass.getText().toString(),et_confirm_pass.getText().toString());
        }
    }

      void showCountryList()
    {
      LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      View v=layoutInflater.inflate(R.layout.country_search,null,false);
        RecyclerView rv_search=v.findViewById(R.id.rv_search);
        AppCompatTextView tv_notFound=v.findViewById(R.id.tv_notFound);
        SearchView country_search=v.findViewById(R.id.country_search);
        country_search.setIconifiedByDefault(false);
        AlertDialog.Builder alb=new AlertDialog.Builder(this);
        alb.setView(v);
        ad=alb.create();
        getCounteryData(rv_search,country_search,ad,tv_notFound);
      //  ad.show();
    }

    private void getCounteryData(RecyclerView rv_search, SearchView country_search, AlertDialog ad,AppCompatTextView tv_notFound)
    {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_search.getContext(), llm.getOrientation());
        rv_search.addItemDecoration(dividerItemDecoration);
        rv_search.setLayoutManager(llm);
        ArrayList<CountryModel> arrayList=new ArrayList<>();
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ApiConstants.BaseUrl + "getcountries", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                ad.show();
                progressDialog.dismiss();
                try {
                    JSONArray ja=new JSONArray(response);
                    for (int i=0;i<ja.length();i++)
                    {
                       JSONObject json= ja.getJSONObject(i);
                        String countryId= json.optString("countryId");
                        String sortName= json.optString("sortName");
                        String name= json.optString("name");
                        String code= json.optString("code");
                        CountryModel countryModel=new CountryModel();
                        countryModel.setCode(code);
                        countryModel.setCountryId(countryId);
                        countryModel.setSortName(sortName);
                        countryModel.setName(name);
                        arrayList.add(countryModel);
                    }
                    CountryAdapter countryAdapter=new CountryAdapter(arrayList,AccountActivity.this,tv_notFound,AccountActivity.this,ad);
                    rv_search.setAdapter(countryAdapter);

                    country_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            String text = newText;
                            countryAdapter.filter(text);
                            return false;
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> {
           progressDialog.dismiss();
           ApiConstants.parseVolleyError(AccountActivity.this,error);
          //  Toast.makeText(AccountActivity.this, ""+error, Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> hm=new HashMap<>();
                hm.put("Authorization","Bearer "+token);
                return hm;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void changeAddress(View view)
    {
        //showCountryList();
        if (et_street.getText().toString().isEmpty())
        {
            et_street.setError("Enter Address Line1");
            et_street.requestFocus();
        }
        else if (et_city.getText().toString().isEmpty())
        {
            if (et_city.getText().toString().isEmpty())
            {
                et_city.setError("Enter City");
                et_city.requestFocus();
            }
        }
        else if (et_country.getText().toString().isEmpty())
        {
//            et_country.setError("Choose Country");
//            et_country.requestFocus();

            Toast.makeText(AccountActivity.this, "Choose Country", Toast.LENGTH_SHORT).show();

        }
        else if (et_state.getText().toString().isEmpty())
        {
//            et_state.setError("Choose State");
//            et_state.requestFocus();
            Toast.makeText(AccountActivity.this, "Choose State", Toast.LENGTH_SHORT).show();
        }
        else if (et_postalcode.getText().toString().isEmpty())
        {
            et_postalcode.setError("Enter Postal Code");
            et_postalcode.requestFocus();
        }
        else {
            changeAddressData();
        }
    }

    private void changeAddressData()
    {
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("addressLine1",et_street.getText().toString().trim());
            js.put("addressLine2",et_apartment.getText().toString().trim());
            js.put("city",et_city.getText().toString().trim());
            js.put("stateId",tv_state_code.getText().toString().trim());
            js.put("countryId",tv_country_code.getText().toString().trim());
            js.put("postalCode",et_postalcode.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SaveImpl(this).handleSave(js,"saasuser?saasUserId="+id,"PUT","",token);
    }

    @Override
    public void onEvent(String cId,String name)
    {
        tv_country_code.setText(cId);
        et_country.setText(name);
    }

    public void phoneSave(View view)
    {
        if (act_phone.getText().toString().isEmpty())
        {
            act_phone.setError("Enter Phone Number");
            act_phone.requestFocus();
        }
        else {
            savePhoneNumber(act_phone.getText().toString());
        }
    }

    private void savePhoneNumber(String phone)
    {
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("phoneNumber",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody=js.toString();
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, ApiConstants.BaseUrl + "saasuser?saasUserId="+id, response -> {
            progressDialog.dismiss();
            Log.e("ResponsePhone",response);

            if (response.equalsIgnoreCase("{\"error\":\"Invalid Password\"}"))
            {
                try {
                    JSONObject js1 =new JSONObject(response);
                    Toast.makeText(AccountActivity.this, ""+ js1.optString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    JSONObject js1 =new JSONObject(response);
                    String status= js1.optString("status");
                    if (status.equalsIgnoreCase("Success"))
                    {
                       // act_phone.setText("");
                        Toast.makeText(AccountActivity.this, "Phone number updated", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




        }, error -> {
            progressDialog.dismiss();
            ApiConstants.parseVolleyError(AccountActivity.this,error);
            Log.e("ResponsePhone",""+error);
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> hm=new HashMap<>();
                hm.put("Authorization","Bearer "+token);
                return hm;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onEventState(String cId, String name) {
        tv_state_code.setText(cId);
        et_state.setText(name);
    }

    @Override
    public void onSaveSucess(String code, String response, String type)
    {
        progressDialog.dismiss();
        Log.e("ResData",response);
        if (code.equalsIgnoreCase("200"))
        {
            try {
                JSONObject js=new JSONObject(response);
                if (js.optString("status").equalsIgnoreCase("Success"))
                {
                    Toast.makeText(this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
//                    et_street.setText("");
//                    et_apartment.setText("");
//                    et_city.setText("");
//                    et_state.setText("");
//                    et_country.setText("");
//                    et_postalcode.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveFailure(String error) {
        progressDialog.dismiss();
        Log.e("ResData",error);
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }

    public void getUserData()
    {
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ApiConstants.BaseUrl+"saasuser?saasUserId=" + id, response -> {
            progressDialog.dismiss();
         assignDatatoFields(response);
        }, error -> {
             progressDialog.dismiss();
             ApiConstants.parseVolleyError(AccountActivity.this,error);
            //Toast.makeText(AccountActivity.this, ""+error, Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> hm=new HashMap<>();
                hm.put("Authorization","Bearer "+token);
                return hm;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void assignDatatoFields(String response)
    {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject js=jsonObject.getJSONObject("user");
            String phoneNumber=js.optString("phoneNumber");
            String street=js.optString("addressLine1");
            String apartment=js.optString("addressLine2");
            String city=js.optString("city");
            String stateId=js.optString("stateId");
            String countryId=js.optString("countryId");
            String postalCode=js.optString("postalCode");
            String stateName=js.optString("stateName");
            String countryName=js.optString("countryName");
            act_phone.setText(phoneNumber);
            et_street.setText(street);
            et_postalcode.setText(postalCode);
            et_city.setText(city);
            et_apartment.setText(apartment);
            tv_country_code.setText(countryId);
            tv_state_code.setText(stateId);
            et_country.setText(countryName);
            et_state.setText(stateName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
