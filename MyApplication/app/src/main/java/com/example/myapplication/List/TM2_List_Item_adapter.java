package com.example.myapplication.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Etc.YoutubeData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TM2_List_Item_adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<YoutubeData> items;

    public TM2_List_Item_adapter(Context context, int layout, ArrayList<YoutubeData> items){
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

        YoutubeData listviewitem = items.get(i);
        TextView title = view.findViewById(R.id.tm2_title);
        title.setText(listviewitem.getTitle());
        TextView desc = view.findViewById(R.id.tm2_desc);
        desc.setText(listviewitem.getDesc());
        TextView updated = view.findViewById(R.id.tm2_updated);
        updated.setText(listviewitem.getPublishedAt());

        return view;
    }
}
