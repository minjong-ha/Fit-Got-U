package com.example.myapplication.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Etc.Util;
import com.example.myapplication.List.HT3_List_Item;
import com.example.myapplication.List.HT3_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeTrainingFragment3 extends Fragment implements AdapterView.OnItemClickListener {
    private int nameid;
    private ArrayList<HT3_List_Item> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameid = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_home_training, container, false);

        TextView ht3_name = view.findViewById(R.id.ht3_name);
        ht3_name.setText(getContext().getString(nameid));
        ListView listview = view.findViewById(R.id.ht3_list);
        items.clear();
        items.add(new HT3_List_Item(0, R.string.fitness_1_1_1, R.string.fitness_1_1_1_desc));
        items.add(new HT3_List_Item(0, R.string.fitness_1_1_2, R.string.fitness_1_1_2_desc));

        HT3_List_Item_Adapter listadapter = new HT3_List_Item_Adapter(getContext(), R.layout.ht3_list_item, items);
        listview.setAdapter(listadapter);
        listview.setOnItemClickListener(this);

        Button start = (Button)view.findViewById(R.id.ht3_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleDialog();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        showHTDialog(i);
    }

    private void showSimpleDialog() {
        DialogFragment newFragment = new SimpleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "운동 루틴 시작");
        args.putString("text", getString(R.string.dialog_ht_start_text));
        newFragment.setArguments(args);
        newFragment.setTargetFragment(this, Util.DIALOG_REQUEST_CODE);
        newFragment.show(getFragmentManager(), "simpledialog");
    }

    private void showHTDialog(int pos) {
        DialogFragment newFragment = new HTDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", getString(items.get(pos).getNameId()));
        args.putInt("imageid", items.get(pos).getImageId());
        args.putString("desc", getString(items.get(pos).getDescId()));
        newFragment.setArguments(args);
        newFragment.setTargetFragment(this, Util.DIALOG_REQUEST_CODE);
        newFragment.show(getFragmentManager(), "htdialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Util.DIALOG_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }
}
