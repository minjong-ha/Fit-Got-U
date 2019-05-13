package com.example.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.List.MP_List_Item;
import com.example.myapplication.List.MP_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MyPageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ListView listview = view.findViewById(R.id.mp_list);

        ArrayList<MP_List_Item> items = new ArrayList<>();
        items.add(new MP_List_Item(R.string.my_info));
        items.add(new MP_List_Item(R.string.my_trainer));
        items.add(new MP_List_Item(R.string.app_info));
        items.add(new MP_List_Item(R.string.logout));

        final MP_List_Item_Adapter listadapter = new MP_List_Item_Adapter(getContext(), R.layout.mp_list_item, items);
        listview.setAdapter(listadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).ChangeFragmentMain(((MP_List_Item) listadapter.getItem(position)).getNameId());
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
        }
    }
}
