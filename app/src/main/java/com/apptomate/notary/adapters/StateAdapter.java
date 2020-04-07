package com.apptomate.notary.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.apptomate.notary.R;
import com.apptomate.notary.models.StateModel;
import com.apptomate.notary.models.StateModel;

import java.util.ArrayList;
import java.util.Locale;



public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyHolder>
{
    ArrayList<StateModel> arrayList;
    AppCompatTextView tv_notfound;
    Context context;
    public EventListener eventListener;
    private ArrayList<StateModel> imageModelArrayList;
    AlertDialog alertDialog;

    public interface EventListener
    {
        void onEventState(String cId,String name);

    }

    public StateAdapter(ArrayList<StateModel> imageModelArrayList, Context context, AppCompatTextView tv_notFound, EventListener eventListener, AlertDialog ad) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        this.arrayList = new ArrayList<StateModel>();
        this.arrayList.addAll(imageModelArrayList);
        this.tv_notfound=tv_notFound;
        this.eventListener=eventListener;
        alertDialog=ad;
        tv_notFound.setText("No state found");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= layoutInflater.inflate(R.layout.country_style,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (imageModelArrayList.get(position).getStateName()!=null)
        {
            holder.tv_country.setText(imageModelArrayList.get(position).getStateName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                eventListener.onEventState(imageModelArrayList.get(position).getStateId(),imageModelArrayList.get(position).getStateName());
                //Toast.makeText(context, ""+imageModelArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        imageModelArrayList.clear();
        if (charText.length() == 0) {
            imageModelArrayList.addAll(arrayList);
        } else {
            for (StateModel wp : arrayList) {
                if (wp.getStateName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    imageModelArrayList.add(wp);
                }
            }
        }
        if (imageModelArrayList.size()==0)
        {
            tv_notfound.setVisibility(View.VISIBLE);
        }
        else {
            tv_notfound.setVisibility(View.GONE);
        }
        notifyDataSetChanged();

    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        AppCompatTextView tv_country;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_country= itemView.findViewById(R.id.tv_country);
        }
    }
}

