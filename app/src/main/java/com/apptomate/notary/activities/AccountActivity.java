package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apptomate.notary.R;

public class AccountActivity extends AppCompatActivity
{
    boolean password=false;
    AppCompatImageView iv_right,iv_down;
    LinearLayout ll_pass;
    RelativeLayout rl_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    private void findViews()
    {
        ll_pass=  findViewById(R.id.change_pass_ll);
        iv_right=  findViewById(R.id.iv_right);
        iv_down=  findViewById(R.id.iv_down);
        rl_pass=  findViewById(R.id.rl_pass);
        rl_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password)
                {
                    ll_pass.setVisibility(View.GONE);
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
            }
        });
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
}
