package com.apptomate.notary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.apptomate.notary.R;
import com.apptomate.notary.adapters.NotifictionAdapter;

public class NotificationActivity extends AppCompatActivity
{

    RecyclerView rv_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_notification=findViewById(R.id.rv_notification);
        rv_notification.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData()
    {
        rv_notification.setAdapter(new NotifictionAdapter(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater= getMenuInflater();
       menuInflater.inflate(R.menu.notification_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
