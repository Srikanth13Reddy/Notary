package com.apptomate.notary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apptomate.notary.R;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.utils.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class NotariesListActivity extends AppCompatActivity implements SaveView
{
    SharedPrefs sharedPrefs;
    String id,agencyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notaries_list);
        getLoginData();
    }

    private void getLoginData()
    {
        sharedPrefs=new SharedPrefs(this);
        try {
            if (sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA)!=null)
            {
                JSONObject js=new JSONObject(sharedPrefs.getLoginData().get(SharedPrefs.LOGIN_DATA));
                id= js.optString("id");
                agencyId= js.optString("agencyId");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveSucess(String code, String response, String type) {

    }

    @Override
    public void onSaveFailure(String error) {

    }
}
