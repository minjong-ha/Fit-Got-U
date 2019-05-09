package com.example.myapplication.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Activity.InfoChangeActivity;
import com.example.myapplication.Etc.MP_List_Item;
import com.example.myapplication.Etc.MP_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {
    MP_List_Item_Adapter listadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);

        ListView listview = view.findViewById(R.id.mi_list);

        ArrayList<MP_List_Item> items = new ArrayList<>();
        items.add(new MP_List_Item("아이디 변경"));

        listadapter = new MP_List_Item_Adapter(getContext(), R.layout.mp_list_item, items);
        listview.setAdapter(listadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Intent intent = new Intent(getActivity().getApplicationContext(), InfoChangeActivity.class);
                        startActivityForResult(intent, 101);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 101){
                String name = data.getStringExtra("name");
                if(name.equals("NULLNAME")) Toast.makeText(getActivity().getApplicationContext(), "이름 변경을 취소하였습니다.",Toast.LENGTH_LONG).show();
                else Toast.makeText(getActivity().getApplicationContext(), "변경된 이름 : " + name, Toast.LENGTH_LONG).show();
            }
        }
    }
}
