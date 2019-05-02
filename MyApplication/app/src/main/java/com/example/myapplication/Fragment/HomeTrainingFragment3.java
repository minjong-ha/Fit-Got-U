package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class HomeTrainingFragment3 extends Fragment {
    private String name;

    public static HomeTrainingFragment3 newInstance(String data) {
        HomeTrainingFragment3 f = new HomeTrainingFragment3();
        Bundle b = new Bundle();
        b.putString("data", data);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            switch(getArguments().getString("data")) {
                /*case R.id.ht_f1:
                    name = getContext().getString(R.string.fitness_1);
                    break;
                case R.id.ht_f2:
                    name = getContext().getString(R.string.fitness_2);
                    break;*/
                default:
                    name = getContext().getString(R.string.fitness_3);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_home_training, container, false);
        return view;
    }

}
