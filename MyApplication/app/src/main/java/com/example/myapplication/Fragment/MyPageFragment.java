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
        ListView listview = getView().findViewById(R.id.mp_list);

        ArrayList<MP_List_Item> items = new ArrayList<>();
        items.add(new MP_List_Item("item 1"));

        MP_List_Item_Adapter listadapter = new MP_List_Item_Adapter(this);

        return inflater.inflate(R.layout.fragment_my_page, container, false);
    }
}
