package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.Etc.MP_List_Item;
import com.example.myapplication.Etc.MP_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ListView listview = view.findViewById(R.id.mp_list);

        ArrayList<MP_List_Item> items = new ArrayList<>();
        items.add(new MP_List_Item("item 1"));
        items.add(new MP_List_Item("item 2"));

        MP_List_Item_Adapter listadapter = new MP_List_Item_Adapter(getContext(), R.layout.mp_list_item, items);
        listview.setAdapter(listadapter);

        return view;
    }
}
