package com.apptomate.notary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import com.apptomate.notary.R;
import com.apptomate.notary.activities.DocumentViewActivity;
import com.apptomate.notary.models.DocumentsModel;

import java.util.ArrayList;

public class DocumentsAdapter extends BaseAdapter
{

    String names[]={"Birth certificate","Death Certificate","Diploma"};
    int images[]={R.drawable.request,R.drawable.request,R.drawable.request};

    Context context;
    ArrayList<DocumentsModel> arrayList;

    public DocumentsAdapter(Context context, ArrayList<DocumentsModel> arrayList) {
        this.context = context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v=layoutInflater.inflate(R.layout.document_style,parent,false);
        ImageView doc_img= v.findViewById(R.id.doc_img);
        AppCompatTextView doc_tv_title= v.findViewById(R.id.doc_tv_title);
        doc_img.setImageResource(images[position]);
        doc_tv_title.setText(arrayList.get(position).getFileName());
        doc_tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DocumentViewActivity.class);
                i.putExtra("url",arrayList.get(position).getUrl());
                context.startActivity(i);
            }
        });
        return v;
    }
}
