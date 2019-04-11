package com.example.myapplication.Etc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MP_List_Item_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<MP_List_Item> items;

    public MP_List_Item_Adapter(Context context, int layout, ArrayList<MP_List_Item> items){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=layout;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=inflater.inflate(layout,viewGroup,false);
        }

        MP_List_Item listviewitem = items.get(i);
        TextView text = view.findViewById(R.id.mp_li_t);
        text.setText(listviewitem.getName());

        return view;
    }
}
