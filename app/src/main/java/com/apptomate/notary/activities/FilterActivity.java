package com.apptomate.notary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apptomate.notary.R;
import com.apptomate.notary.adapters.StateAdapter;
import com.apptomate.notary.adapters.StateFilterAdapter;
import com.apptomate.notary.adapters.StatusAdapter;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.models.StateModel;
import com.apptomate.notary.models.StatusModel;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements SaveView
{

    RecyclerView rv_status;
    String status[]={"New","Pending","Inprogress","Completed","Rejected"};
    String status_result;
    SearchView state_search;
   // LinearLayout ll_state;
    AppCompatTextView tv_status;
    SharedPrefs sharedPrefs;
    String id,token;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        rv_status=findViewById(R.id.rv_status);
        state_search=findViewById(R.id.state_search);
       // ll_state=findViewById(R.id.ll_state);
       // rv_state_search=findViewById(R.id.rv_state_search);
        tv_status=findViewById(R.id.tv_status);
       // tv_state=findViewById(R.id.tv_state);
        getSupportActionBar().setTitle("Filters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        LinearLayoutManager llm1 = new LinearLayoutManager(this);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_status.getContext(), llm.getOrientation());
//        rv_status.addItemDecoration(dividerItemDecoration);
        rv_status.setLayoutManager(llm);
       // rv_state_search.setLayoutManager(llm1);
        getLoginData();
        Bundle b=getIntent().getExtras();
        if (b != null) {
            status_result=b.getString("result");
        }
        //getStatusData();
        actions();
        getStates();
    }

    private void actions()
    {
//        tv_state.setOnClickListener(v -> {
//
//            tv_state.setBackgroundColor(getResources().getColor(R.color.white));
//            tv_status.setBackgroundColor(getResources().getColor(R.color.background));
//            ll_state.setVisibility(View.VISIBLE);
//            rv_status.setVisibility(View.GONE);
//        });
//        tv_status.setOnClickListener(v -> {
//            tv_state.setBackgroundColor(getResources().getColor(R.color.background));
//            tv_status.setBackgroundColor(getResources().getColor(R.color.white));
//            ll_state.setVisibility(View.GONE);
//            rv_status.setVisibility(View.VISIBLE);
//        });
    }

    private void getLoginData()
    {
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        sharedPrefs=new SharedPrefs(this);
        try {
            if (sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)!=null)
            {
                JSONObject js=new JSONObject(sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA));
                id= js.optString("id");
                token= js.optString("token");
               // roleId= js.optString("roleId");
                Log.e("data",sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getStates()
    {
       progressDialog.show();
       new SaveImpl(this).handleSave(new JSONObject(),"usstates","GET","",token);
    }

    private void getStatusData()
    {
        ArrayList<StatusModel> modelArrayList=new ArrayList<>();
        for (int i=0;i<status.length;i++)
        {
            StatusModel statusModel=new StatusModel();
            if (status_result!=null)
            {
                try {
                    JSONArray jaa=new JSONArray(status_result);
                    for (int j=0;j<jaa.length();j++)
                    {
                      String name=  jaa.getJSONObject(j).optString("name");
                        if (status[i].equalsIgnoreCase(name))
                        {
                           statusModel.setSelected(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            statusModel.setStatus(status[i]);
            modelArrayList.add(statusModel);
        }
        StatusAdapter stateAdapter=new StatusAdapter(modelArrayList,FilterActivity.this);
        rv_status.setAdapter(stateAdapter);
    }

    public void apply(View view)
    {
        Intent returnIntent = new Intent(this,HomeActivity.class);
        returnIntent.putExtra("result",getSelectedData());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

   public String getSelectedData()
    {
        ArrayList<String> al=new ArrayList<>();
        JSONArray jsonArray=new JSONArray();
        for (int i = 0; i < StatusAdapter.arrayList.size(); i++)
        {
            if(StatusAdapter.arrayList.get(i).getSelected())
            {
                // tv.setText(tv.getText() + " " + CustomAdapter.imageModelArrayList.get(i).getAnimal());
                // al.add(CustomAdapter.imageModelArrayList.get(i));

                JSONObject js=new JSONObject();
                try {
                    js.put("name",StatusAdapter.arrayList.get(i).getStatus());
                    jsonArray.put(js);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                //  Log.e("check",""+name);
                //  Toast.makeText(activity, name + " clicked!", Toast.LENGTH_SHORT).show();


            }
        }


        return jsonArray.toString();

    }

    public void clear(View view)
    {
        status_result="";
        getStatusData();
    }

    @Override
    public void onSaveSucess(String code, String response, String type)
    {
        Log.e("States",response);
       progressDialog.dismiss();
       //assigndata(response);
    }

//    private void assigndata(String response)
//    {
//        ArrayList<StateModel> arrayList=new ArrayList<>();
//        try {
//            JSONArray ja=new JSONArray(response);
//            for (int i=0;i<ja.length();i++)
//            {
//               JSONObject json= ja.getJSONObject(i);
//                String  usStateId= json.optString("usStateId");
//                String stateName= json.optString("stateName");
//                StateModel stateModel=new StateModel();
//                stateModel.setStateName(stateName);
//                stateModel.setStateId(usStateId);
//                arrayList.add(stateModel);
//            }
//
//            StateFilterAdapter adapter=new StateFilterAdapter(arrayList,this);
//            rv_state_search.setAdapter(adapter);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onSaveFailure(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }
}
