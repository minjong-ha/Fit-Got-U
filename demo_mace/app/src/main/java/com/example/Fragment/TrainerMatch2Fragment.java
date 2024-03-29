package com.example.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Activity.MainActivity;
import com.example.Etc.Util;
import com.example.Etc.YoutubeData;
import com.example.Etc.YoutubeUserSearchTask;
import com.example.Etc.YoutubeVideoSearchTask;
import com.example.List.TM2_List_Item_adapter;
import com.example.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainerMatch2Fragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<YoutubeData> youtubelist = new ArrayList<>();
    private String youtube = null;
    private String name = "";
    private String address = "";
    private String height = "";
    private String weight = "";
    private String trainerid = "";
    private String profile_image = "";
    private TM2_List_Item_adapter adapter;
    private String subid = "";
    private ImageButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match2, container, false);
        if (getArguments() != null) {
            trainerid = getArguments().getString("trainerid");
            youtube = getArguments().getString("youtube");
            name = getArguments().getString("name");
            address = getArguments().getString("address");
            height = getArguments().getString("height");
            weight = getArguments().getString("weight");
            profile_image = getArguments().getString("profile_image");
        }

        TextView view1 = view.findViewById(R.id.tm2_profile_name);
        view1.setText(name);
        TextView view2 = view.findViewById(R.id.tm2_profile_address);
        view2.setText(address);
        TextView view3 = view.findViewById(R.id.tm2_profile_height);
        view3.setText(height);
        TextView view4 = view.findViewById(R.id.tm2_profile_weight);
        view4.setText(weight);
        button = view.findViewById(R.id.tm2_profile_subscription);

        ArrayList<HashMap<String, String>> mysub = Util.SelectSubscriptionbyUser(((MainActivity)getActivity()).getKakaoid() + "");
        for (HashMap<String, String> sub : mysub) {
            if (sub.get("trainerid").equals(trainerid)) {
                button.setImageDrawable(getResources().getDrawable(R.drawable.meal_routine)); //button.setImageDrawable();
                subid = sub.get("id");
                break;
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subid.equals("")) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.meal_routine)); //button.setImageDrawable();
                    subid = Util.InsertSubscription(((MainActivity)getActivity()).getKakaoid() + "", trainerid);
                    Toast.makeText(getActivity().getApplicationContext(), "신청하였습니다.", Toast.LENGTH_SHORT).show();
                    Util.sendNotification(trainerid, ((MainActivity)getActivity()).getNickname() + "님의 신청", "식단과 루틴을 제공해주세요", "1");
                } else {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.meal_routine2));//button.setImageDrawable();
                    Util.DeleteSubscription(subid);
                    subid = "";
                    Toast.makeText(getActivity().getApplicationContext(), "신청을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    Util.sendNotification(trainerid, ((MainActivity)getActivity()).getNickname() + "님의 신청 취소", "신청이 취소되었습니다.", "1");
                }
            }
        });

        ImageView image = view.findViewById(R.id.tm2_profile_image);
        if (!profile_image.equals("")) {
            Util.getImagefromURL(profile_image, image);
        }

        try {
            if (youtube != null) {
                youtube = ParsingUrl(youtube);
                if (youtube != null) {
                    YoutubeVideoSearchTask task2 = new YoutubeVideoSearchTask(getString(R.string.google_api_key), youtube);
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
