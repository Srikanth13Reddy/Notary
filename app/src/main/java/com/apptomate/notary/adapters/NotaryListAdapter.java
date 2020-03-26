package com.apptomate.notary.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apptomate.notary.R;
import com.apptomate.notary.activities.NotariesListActivity;
import com.apptomate.notary.models.NotaryListModel;
import com.apptomate.notary.utils.ApiConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotaryListAdapter extends RecyclerView.Adapter<NotaryListAdapter.MyHolder>
{
    ArrayList<NotaryListModel> arrayList;
    Context context;
    String rId;

    public NotaryListAdapter(ArrayList<NotaryListModel> arrayList, Context context, String rId) {
        this.arrayList = arrayList;
        this.context = context;
        this.rId=rId;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v=layoutInflater.inflate(R.layout.notary_list_style,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        if (arrayList.get(position).getProfileImage()!=null)
        {
            if (arrayList.get(position).getProfileImage().equalsIgnoreCase(""))
            {
                holder.iv_n_.setImageResource(R.drawable.profile_d);
            }
            else {
                Picasso.get().load(arrayList.get(position).getProfileImage()).into(holder.iv_n_);
            }

        }
        holder.tv_n_name.setText(arrayList.get(position).getName());
        holder.iv_send.setOnClickListener(v -> {
            AlertDialog.Builder alb=new AlertDialog.Builder(context);
            alb.setTitle("Confirmation");
            alb.setMessage("Do you want assign to "+arrayList.get(position).getName());
            alb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    assignData(arrayList.get(position).getSaasUserId());
                }
            });
            alb.create().show();

        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        CircleImageView iv_n_;
        AppCompatTextView tv_n_name;
        AppCompatImageView iv_send;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            iv_n_=itemView.findViewById(R.id.img);
            tv_n_name=itemView.findViewById(R.id.tv_n_name);
            iv_send=itemView.findViewById(R.id.iv_send);
        }
    }

    private void assignData(String id)
    {
        ProgressDialog progressDialog= ApiConstants.showProgressDialog(context,"Please wait....");
        progressDialog.show();
        JSONObject js=new JSONObject();
        try {
            js.put("assignedTo",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody=js.toString();
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, ApiConstants.BaseUrl + "/request?requestId="+rId, response -> {
            progressDialog.dismiss();
            Log.e("ResponseStatus",response);
            try {
                JSONObject js1 =new JSONObject(response);
                String status= js1.optString("status");
                if (status.equalsIgnoreCase("Success"))
                {
                    Toast.makeText(context, "Assigned Successfully", Toast.LENGTH_SHORT).show();
                   NotariesListActivity activity= (NotariesListActivity)context;
                   activity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            progressDialog.dismiss();
            Log.e("Response",""+error);
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
        };
        requestQueue.add(stringRequest);
    }
}
