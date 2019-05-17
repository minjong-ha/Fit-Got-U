package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Etc.Util;
import com.example.myapplication.Etc.YoutubeData;
import com.example.myapplication.Etc.YoutubeUserSearchTask;
import com.example.myapplication.Etc.YoutubeVideoSearchTask;
import com.example.myapplication.List.TM2_List_Item_adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TrainerMatch2Fragment extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match2, container, false);

        if (getArguments() != null) {

        }
        ArrayList<YoutubeData> youtubelist = new ArrayList<>();
        try {
            YoutubeUserSearchTask task = new YoutubeUserSearchTask(getString(R.string.google_api_key), "xordn6579");
            String userid = Util.paringYoutubeUserJsonData(task.execute().get());
            YoutubeVideoSearchTask task2 = new YoutubeVideoSearchTask(getString(R.string.google_api_key), userid);
            youtubelist = Util.paringYoutubeVideoJsonData(task2.execute().get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView lv = (ListView)view.findViewById(R.id.tm2_list);
        lv.setAdapter(new TM2_List_Item_adapter(getContext(), R.layout.tm2_list_item, youtubelist));

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
