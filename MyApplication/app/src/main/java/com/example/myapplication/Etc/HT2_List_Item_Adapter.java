package com.example.myapplication.Etc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class HT2_List_Item_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<HT2_List_Item> items;

    public HT2_List_Item_Adapter(Context context, int layout, ArrayList<HT2_List_Item> items){
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

        HT2_List_Item listviewitem = items.get(i);
        ImageView image = view.findViewById(R.id.ht_list_image);//이미지 따로 처리해줘야됨
        TextView name = view.findViewById(R.id.ht_list_name);
        name.setText(listviewitem.getName());
        TextView desc = view.findViewById(R.id.ht_list_desc);
        desc.setText(listviewitem.getDesc());

        return view;
    }

}
