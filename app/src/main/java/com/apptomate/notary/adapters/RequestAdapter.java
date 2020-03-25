package com.apptomate.notary.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.apptomate.notary.R;
import com.apptomate.notary.activities.ClientInfo;
import com.apptomate.notary.models.RequestModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyHolder>
{
    Context context;
    ArrayList<RequestModel> arraylist;
    private ArrayList<RequestModel> imageModelArrayList;
    AppCompatTextView tv_notFound;

    public RequestAdapter(Context context, ArrayList<RequestModel> imageModelArrayList, AppCompatTextView tv_notFound) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        this.arraylist = new ArrayList<RequestModel>();
        this.arraylist.addAll(imageModelArrayList);
        this.tv_notFound=tv_notFound;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.request_style,parent,false);
        return new MyHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {



         RequestModel obj= imageModelArrayList.get(position);
         if (obj!=null)
         {
             holder.tv_documents_rqst.setText("Documents - "+obj.getDocuments());
             holder.tv_address_rqst.setText(obj.getFullAddress());
             holder.name_tv_rqst.setText(obj.getName());
             if (obj.getStatus().equalsIgnoreCase("New"))
             {
                 holder.tv_status_rqst.setBackgroundResource(R.drawable.request_drawable);
                 holder.tv_status_rqst.setText("New");
             }
             else if (obj.getStatus().equalsIgnoreCase("Pending"))
             {
                 holder.tv_status_rqst.setBackgroundResource(R.drawable.pending_drawable);
                 holder.tv_status_rqst.setText("Pending");
             }
             else if (obj.getStatus().equalsIgnoreCase("Inprogress"))
             {
                 holder.tv_status_rqst.setBackgroundResource(R.drawable.inprogress_drawable);
                 holder.tv_status_rqst.setText("Inprogress");
             }
             else if (obj.getStatus().equalsIgnoreCase("Completed"))
             {
                 holder.tv_status_rqst.setBackgroundResource(R.drawable.complete_drawable);
                 holder.tv_status_rqst.setText("Completed");
             }
             else if (obj.getStatus().equalsIgnoreCase("Rejected"))
             {
                 holder.tv_status_rqst.setBackgroundResource(R.drawable.reject_drawable);
                 holder.tv_status_rqst.setText("Rejected");
             }

             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v)
                 {
                     Intent i=new Intent(context, ClientInfo.class);
                     i.putExtra("rId",obj.getUserRequestDetailsId());
                     i.putExtra("status",obj.getStatus());
                     context.startActivity(i);
                     Activity mContext = (Activity) context;
                     mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                 }
             });
         }

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        imageModelArrayList.clear();
        if (charText.length() == 0) {
            imageModelArrayList.addAll(arraylist);
        } else {
            for (RequestModel wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    imageModelArrayList.add(wp);
                }
            }
        }
        if (imageModelArrayList.size()==0)
        {
            tv_notFound.setVisibility(View.VISIBLE);
        }
        else {
            tv_notFound.setVisibility(View.GONE);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount()
    {
        return imageModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        AppCompatTextView name_tv_rqst,tv_address_rqst,tv_bus_rqst,tv_documents_rqst,tv_time_rqst,tv_status_rqst;

        public MyHolder(@NonNull View v) {
            super(v);
            name_tv_rqst= v.findViewById(R.id.name_tv_rqst);
            tv_address_rqst= v.findViewById(R.id.tv_address_rqst);
            name_tv_rqst= v.findViewById(R.id.name_tv_rqst);
           // tv_bus_rqst= v.findViewById(R.id.tv_bus_rqst);
            tv_documents_rqst= v.findViewById(R.id.tv_documents_rqst);
            tv_time_rqst= v.findViewById(R.id.tv_time_rqst);
            tv_status_rqst= v.findViewById(R.id.tv_status_rqst);
        }
    }
}
