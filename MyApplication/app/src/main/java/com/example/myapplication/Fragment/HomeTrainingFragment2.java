package com.example.myapplication.Fragment;

import android.content.Context;
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

import com.example.myapplication.Etc.HT2_List_Item;
import com.example.myapplication.Etc.HT2_List_Item_Adapter;
import com.example.myapplication.Etc.onFragmentListener;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeTrainingFragment2 extends Fragment implements AdapterView.OnItemClickListener {
    private onFragmentListener mOnHTListener;
    private String name;
    private ArrayList<HT2_List_Item> items = new ArrayList<>();

    //public static Fragment newInstance(DataObject data) {
    public static HomeTrainingFragment2 newInstance(int data) {
        HomeTrainingFragment2 f = new HomeTrainingFragment2();
        Bundle b = new Bundle();
        //b.putParcelable(DataObject.class.getName(), data);
        b.putInt("data", data);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //str = getArguments().getParcelable(DataObject.class.getName());
            switch(getArguments().getInt("data")) {
                case R.id.ht_f1:
                    name = getContext().getString(R.string.fitness_1);
                    break;
                case R.id.ht_f2:
                    name = getContext().getString(R.string.fitness_2);
                    break;
                default:
                    name = getContext().getString(R.string.fitness_3);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_home_training, container, false);

        TextView ht2_name = view.findViewById(R.id.ht2_name);
        ht2_name.setText(name);

        ListView listview = view.findViewById(R.id.ht2_list);
        items.clear();
        items.add(new HT2_List_Item("image1", R.string.fitness_1_1, "설명1"));
        items.add(new HT2_List_Item("image2", R.string.fitness_1_2, "설명2"));

        HT2_List_Item_Adapter listadapter = new HT2_List_Item_Adapter(getContext(), R.layout.ht2_list_item, items);
        listview.setAdapter(listadapter);
        listview.setOnItemClickListener(this);

        Spinner spinner = view.findViewById(R.id.ht2_spinner);
        ArrayList<String> spinnerlist = new ArrayList<>();
        spinnerlist.add("정렬1");
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof onFragmentListener) {
            mOnHTListener = (onFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnHTListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mOnHTListener != null) {
            mOnHTListener.onReceivedData(items.get(i).getNameId());
        }
    }
}
