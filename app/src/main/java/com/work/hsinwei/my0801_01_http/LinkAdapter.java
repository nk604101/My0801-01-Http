package com.work.hsinwei.my0801_01_http;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hsinwei on 2016/8/1.
 */
public class LinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<NewsLink> link;
    LinearLayout llay;
    LinkAdapter(Context c, ArrayList<NewsLink> list){
        context=c;
        link=list;
    }
    @Override
    public int getCount() {
        return link.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final int p=position;
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.listlayout,null);
        TextView tv= (TextView)v.findViewById(R.id.textView);
        tv.setText(link.get(position).title);
        llay=(LinearLayout)v.findViewById(R.id.llay1);
        llay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  = new Intent(context,P2Activity.class);
                it.putExtra("link",link.get(p).link);
                context.startActivity(it);
            }
        });
        return v;
    }
}
