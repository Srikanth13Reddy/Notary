package com.apptomate.notary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.apptomate.notary.R;
import com.apptomate.notary.interfaces.SaveView;
import com.apptomate.notary.utils.ApiConstants;
import com.apptomate.notary.utils.SaveImpl;
import com.apptomate.notary.utils.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements SaveView
{

    ProgressDialog progressDialog;
    AppCompatEditText et_mail,et_pass;
    SharedPrefs sharedPrefs;
    CheckBox log_checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        progressDialog= ApiConstants.showProgressDialog(this,"Please wait....");
        findViews();
    }

    private void findViews()
    {
        sharedPrefs=new SharedPrefs(this);
        et_mail=findViewById(R.id.login_email_tv);
        et_pass=findViewById(R.id.logon_pass_tv);
        log_checkBox=findViewById(R.id.log_checkBox);
        log_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pass.setSelection(et_pass.length());
                } else {
                    et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pass.setSelection(et_pass.length());
                }
            }
        });

    }

    public void login(View view)
    {

        if (et_mail.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else {
            if (et_pass.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
            else {
                loginToNotary(et_mail.getText().toString().trim(),et_pass.getText().toString().trim());

            }
        }

    }

    private void loginToNotary(String email, String pass)
    {
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("email",email);
            js.put("password",pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SaveImpl(this).handleSave(js,"auth/userlogin","POST","");
    }

    @Override
    public void onSaveSucess(String code, String response,String type)
    {
        progressDialog.dismiss();

        if (code.equalsIgnoreCase("200"))
        {
           sharedPrefs.saveLoginData(response);
           sharedPrefs.LoginSuccess();
           finish();
           Intent i=new Intent(LoginActivity.this,HomeActivity.class);
           startActivity(i);
           overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (code.equalsIgnoreCase("500"))
        {
            try {
                Toast.makeText(this, ""+new JSONObject(response).optString("error"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onSaveFailure(String error) {
        progressDialog.dismiss();
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }
}
