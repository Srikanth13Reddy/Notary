package com.apptomate.notary.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptomate.notary.R;
import com.apptomate.notary.activities.ClientInfo;
import com.apptomate.notary.activities.HomeActivity;
import com.apptomate.notary.activities.LoginActivity;

public class NotifictionAdapter extends RecyclerView.Adapter<NotifictionAdapter.MyHolder>
{
    Context context;

    public NotifictionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v=layoutInflater.inflate(R.layout.notification_style,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  Intent i=new Intent(context, ClientInfo.class);
                  context.startActivity(i);
                  Activity mContext = (Activity) context;
                  mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
              }
          });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
