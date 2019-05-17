package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Etc.Util;
import com.example.myapplication.Etc.YoutubeData;
import com.example.myapplication.Etc.YoutubeUserSearchTask;
import com.example.myapplication.Etc.YoutubeVideoSearchTask;
import com.example.myapplication.List.TM2_List_Item_adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TrainerMatch2Fragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<YoutubeData> youtubelist = new ArrayList<>();
    private String youtubechannelurl = null;
    private TM2_List_Item_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match2, container, false);
        if (getArguments() != null) {
            youtubechannelurl = getArguments().getString("youtubechannelurl");
        }
        try {
            if (youtubechannelurl != null) {
                youtubechannelurl = ParsingUrl(youtubechannelurl);
                if (youtubechannelurl != null) {
                    YoutubeVideoSearchTask task2 = new YoutubeVideoSearchTask(getString(R.string.google_api_key), youtubechannelurl);
                    youtubelist = Util.paringYoutubeVideoJsonData(task2.execute().get());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView lv = (ListView)view.findViewById(R.id.tm2_list);
        adapter = new TM2_List_Item_adapter(getContext(), R.layout.tm2_list_item, youtubelist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        YoutubeData item = (YoutubeData) adapter.getItem(i);
        Bundle args = new Bundle();
        args.putString("youtubevideourl", item.getVideoId());
        TrainerMatch3Fragment tm3 = new TrainerMatch3Fragment();
        tm3.setArguments(args);
        ((MainActivity)getActivity()).ChangeFragmentMain(tm3);
    }

    public String ParsingUrl(String url) {
        String parsing = null;
        String[] split = url.split("/");
        if (split.length > 4) {
            if (split[3].equals("user")) {
                try {
                    YoutubeUserSearchTask task = new YoutubeUserSearchTask(getString(R.string.google_api_key), split[4]);
                    parsing = Util.paringYoutubeUserJsonData(task.execute().get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (split[3].equals("channel")) {
                parsing = split[4];
            }
        }
        return parsing;
    }
}
