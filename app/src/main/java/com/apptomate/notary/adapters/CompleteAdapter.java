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
import java.util.ArrayList;
import java.util.Locale;

import static com.apptomate.notary.utils.ApiConstants.time;
import static com.apptomate.notary.utils.ApiConstants.toTitleCase;


public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MyHolder>
{
    Context context;
    ArrayList<RequestModel> arraylist;
    private ArrayList<RequestModel> imageModelArrayList;
    AppCompatTextView tv_notFound;

//    public CompleteAdapter(Context context) {
//        this.context = context;
//    }


    public CompleteAdapter(Context context, ArrayList<RequestModel> imageModelArrayList, AppCompatTextView tv_notFound) {
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
        View v=layoutInflater.inflate(R.layout.complete_style,parent,false);
        return new MyHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        if (imageModelArrayList.get(position)!=null)
        {

            RequestModel obj=imageModelArrayList.get(position);
            holder.tv_time_rqst.setText(time(obj.getRequestedDate()));
            holder.tv_documents_com.setText("Documents - "+obj.getDocumentsCount());
            holder.tv_address_com.setText(obj.getFullAddress());
            holder.name_tv_com.setText(toTitleCase(obj.getName()));
           // holder.tv_notary_name.setText(obj.getAssignedToName());
            holder.tv_notary_name.setText(toTitleCase(obj.getAssignedToName()));
            holder.itemView.setOnClickListener(v -> {
                Intent i=new Intent(context, ClientInfo.class);
                i.putExtra("rId",obj.getUserRequestDetailsId());
                i.putExtra("status",obj.getStatus());
                context.startActivity(i);
                Activity mContext = (Activity) context;
                mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        AppCompatTextView name_tv_com,tv_address_com,tv_documents_com,tv_time_rqst,tv_notary_name;

        public MyHolder(@NonNull View v) {
            super(v);
            name_tv_com= v.findViewById(R.id.name_tv_com);
            tv_address_com= v.findViewById(R.id.tv_address_com);
            tv_documents_com= v.findViewById(R.id.tv_documents_com);
            tv_time_rqst= v.findViewById(R.id.tv_time_rqst);
            tv_notary_name= v.findViewById(R.id.tv_notary_name);

        }
    }
}



