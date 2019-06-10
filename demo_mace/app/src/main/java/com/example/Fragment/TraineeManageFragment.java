package com.example.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.Activity.LittleDAPopupActivity;
import com.example.Activity.MainActivity;
import com.example.Activity.ReplyForTraineeActivity;
import com.example.Etc.Util;
import com.example.List.Trainee_List_Item;
import com.example.List.Trainee_List_Item_Adapter;
import com.example.R;

import java.util.ArrayList;
import java.util.HashMap;


public class TraineeManageFragment extends Fragment {

    // view 및 context 변수
    private View view;
    private Context c;
    ListAdapter oAdapter;
    //listViwe
    private ListView m_oListView = null;


    public TraineeManageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_management, container, false);
        c = view.getContext();

        ArrayList<HashMap<String, String>> trainee_info = new ArrayList<>();

        ArrayList<HashMap<String, String>> sub = Util.SelectSubscriptionbyTrainer(((MainActivity)getActivity()).getKakaoid() + "");
        for (HashMap<String, String> temp : sub) {
            HashMap<String, String> userdata = Util.SelectUser(temp.get("userid"));
            if (userdata != null) {
                HashMap<String, String> tmp_hash = new HashMap<>();
                tmp_hash.put("subid", temp.get("id"));
                tmp_hash.put("traineeID", temp.get("userid"));
                tmp_hash.put("isAccept", temp.get("status"));

                tmp_hash.put("address", userdata.get("address"));
                tmp_hash.put("name", userdata.get("username"));
                tmp_hash.put("sex", "남");
                tmp_hash.put("tall", userdata.get("height"));
                tmp_hash.put("weight", userdata.get("weight"));
                trainee_info.add(tmp_hash);
            }
        }
        setTraineeList(trainee_info);

        return view;
    }

    private void setTraineeList(ArrayList<HashMap<String, String>> trainee_info) {
        ArrayList<Trainee_List_Item> trainee_data = new ArrayList<>();

        for (int i = 0; i < trainee_info.size(); i++) {

            final Trainee_List_Item TLI = new Trainee_List_Item();

            TLI.setSubid(trainee_info.get(i).get("subid"));
            TLI.setTraineeID(trainee_info.get(i).get("traineeID"));
            TLI.setAddress(trainee_info.get(i).get("address"));
            TLI.setName(trainee_info.get(i).get("name"));
            TLI.setSex(trainee_info.get(i).get("sex"));
            TLI.setTall(trainee_info.get(i).get("tall")+"cm");
            TLI.setWeight(trainee_info.get(i).get("weight")+"kg");
            TLI.setIsAccept(trainee_info.get(i).get("isAccept"));
            TLI.setOnClickListenerDetail(new Button.OnClickListener() {
                // 버튼 선택시 식단/루틴 팝업을 켬
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(), ReplyForTraineeActivity.class);
                    intent.putExtra("trainerID", ((MainActivity)getActivity()).getKakaoid() + "");
                    intent.putExtra("trainername",((MainActivity)getActivity()).getNickname());
                    intent.putExtra("traineeID",TLI.getTraineeID());
                    intent.putExtra("name",TLI.getName());
                    startActivityForResult(intent, 101);
                }
            });

            TLI.setOnClickListenerInfo(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(), LittleDAPopupActivity.class);
                    intent.putExtra("traineeID",TLI.getTraineeID());
                    intent.putExtra("name",TLI.getName());
                    startActivityForResult(intent, 101);
                }
            });

            TLI.setOnClickListenerAccept(new Button.OnClickListener() {
                // 버튼 선택시 식단/루틴 팝업을 켬
                @Override
                public void onClick(View v) {
                    Util.UpdateSubscription(TLI.getSubid(), "1");
                    TLI.setIsAccept("1");
                    ((Trainee_List_Item_Adapter)oAdapter).notifyDataSetChanged();
                    Util.sendNotification(TLI.getTraineeID(), ((MainActivity)getActivity()).getNickname() + "님의 수락", "식단 요청이 수락되었습니다.", "1");
                    Toast.makeText(getActivity().getApplicationContext(), "승낙하였습니다.", Toast.LENGTH_LONG).show();
                    refresh();
                }
            });

            TLI.setOnClickListenerDeny(new Button.OnClickListener() {
                // 버튼 선택시 식단/루틴 팝업을 켬
                @Override
                public void onClick(View v) {
                    Util.DeleteSubscription(TLI.getSubid());
                    Util.sendNotification(TLI.getTraineeID(), ((MainActivity)getActivity()).getNickname() + "님의 거절", "식단 요청이 거절되었습니다.", "1");
                    Toast.makeText(getActivity().getApplicationContext(), "거절하였습니다.", Toast.LENGTH_LONG).show();
                    refresh();
                }
            });

            trainee_data.add(TLI);
        }
        connectAdapter(trainee_data);
    }

    private void connectAdapter(ArrayList<Trainee_List_Item> oData) {
        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView) view.findViewById(R.id.trainee_manage_listView);
        oAdapter = new Trainee_List_Item_Adapter(oData);
        m_oListView.setAdapter(oAdapter);
    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}