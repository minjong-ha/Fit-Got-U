package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.List.HT2_List_Item;
import com.example.myapplication.List.HT2_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeTraining2Fragment extends Fragment implements AdapterView.OnItemClickListener {
    private int nameid;
    private ArrayList<HT2_List_Item> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_home_training, container, false);

        if (getArguments() != null) {
            switch(getArguments().getInt("id")) {
                case R.id.ht_f1:
                    nameid = R.string.fitness_1;
                    break;
                case R.id.ht_f2:
                    nameid = R.string.fitness_2;
                    break;
                default:
                    nameid = R.string.fitness_3;
                    break;
            }
        }

        TextView ht2_name = view.findViewById(R.id.ht2_name);
        ht2_name.setText(getContext().getString(nameid));

        ListView listview = view.findViewById(R.id.ht2_list);
        items.clear();
        items.add(new HT2_List_Item("image1", R.string.fitness_1_1, "하급자를 위한 루틴입니다.\n"+"운동을 처음 하는 분께 추천드립니다!"));
        items.add(new HT2_List_Item("image2", R.string.fitness_1_2, "중급자를 위한 루틴입니다.\n"+"기초 동작에 숙달 후 도전하세요!"));
        items.add(new HT2_List_Item("image3", R.string.fitness_1_3, "상급자를 위한 루틴입니다.\n"+"고난도의 동작이 많습니다!"));

        HT2_List_Item_Adapter listadapter = new HT2_List_Item_Adapter(getContext(), R.layout.ht2_list_item, items);
        listview.setAdapter(listadapter);
        listview.setOnItemClickListener(this);

        Spinner spinner = view.findViewById(R.id.ht2_spinner);
        ArrayList<String> spinnerlist = new ArrayList<>();
        spinnerlist.add("난이도순");
        spinnerlist.add("정렬2");
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, spinnerlist);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ((MainActivity)getActivity()).ChangeFragmentMain(items.get(i).getNameId());
    }
}
