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
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Etc.Util;
import com.example.myapplication.List.MP_List_Item;
import com.example.myapplication.List.MP_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {
    MP_List_Item_Adapter listadapter;
    ArrayList<MP_List_Item> items;
    int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);

        ListView listview = view.findViewById(R.id.mi_list);

        items = new ArrayList<>();
        items.add(new MP_List_Item(R.string.change_address));
        items.add(new MP_List_Item(R.string.change_height));
        items.add(new MP_List_Item(R.string.change_weight));
        if (((MainActivity)getActivity()).getIs_user().equals("트레이너")) {
            items.add(new MP_List_Item(R.string.change_youtubechannelid));
        }

        listadapter = new MP_List_Item_Adapter(getContext(), R.layout.mp_list_item, items);
        listview.setAdapter(listadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                Intent intent = new Intent(getActivity().getApplicationContext(), InfoChangeActivity.class);
                intent.putExtra("whatchange", items.get(position).getNameId());
                startActivityForResult(intent, 101);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 101){
                String changed = data.getStringExtra("changed");
                switch (pos) {
                    case 0:
                        ((MainActivity)getActivity()).setAddress(changed);
                        break;
                    case 1:
                        ((MainActivity)getActivity()).setHeight(changed);
                        break;
                    case 2:
                        ((MainActivity)getActivity()).setWeight(changed);
                        break;
                    case 3:
                        ((MainActivity)getActivity()).setYoutube(changed);
                        break;
                }
                Util.UpdateUser(((MainActivity)getActivity()).getKakaoid() + "", ((MainActivity)getActivity()).getAddress(), ((MainActivity)getActivity()).getWeight(), ((MainActivity)getActivity()).getHeight(), ((MainActivity)getActivity()).getYoutube());
                Toast.makeText(getActivity().getApplicationContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
