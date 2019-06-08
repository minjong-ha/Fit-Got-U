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

import com.example.Activity.ReplyForTraineeActivity;
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

        ArrayList<HashMap<String, String>> trainee_info = new ArrayList<HashMap<String, String>>();

        //todo 값 가져와서 trainee_info에 할당
        // trainee_info = Util.값 가져오는 코드 넣어야함
        // Test코드 나중에 삭제
        trainee_info = testTraineeList();
        setTraineeList(trainee_info);

        return view;
    }
    //Budle 정보 전달 인자

    private ArrayList<HashMap<String, String>> testTraineeList(){
        ArrayList<HashMap<String, String>> trainee_info = new ArrayList<HashMap<String, String>>();

        for(int i = 1 ; i <5 ; i++) {
            HashMap<String, String> tmp_hash = new HashMap<>();
            tmp_hash.put("traineeID", "테스트아이디: "+i);
            tmp_hash.put("address", "주소"+i);
            tmp_hash.put("name", "회원"+i);
            tmp_hash.put("sex", "남"+i);
            tmp_hash.put("tall", "1"+i*10);
            tmp_hash.put("weight", ""+i*10);
            tmp_hash.put("isAccept", ((i%2==0)?"0":"1"));
            trainee_info.add(tmp_hash);
        }

        return trainee_info;
    }

    private void setTraineeList(ArrayList<HashMap<String, String>> trainee_info) {
        ArrayList<Trainee_List_Item> trainee_data = new ArrayList<>();

        for (int i = 0; i < trainee_info.size(); i++) {

            final Trainee_List_Item TLI = new Trainee_List_Item();

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
                    intent.putExtra("traineeID",TLI.getTraineeID());
                    intent.putExtra("name",TLI.getName());
                    startActivityForResult(intent, 101);
                }
            });

            TLI.setOnClickListenerAccept(new Button.OnClickListener() {
                // 버튼 선택시 식단/루틴 팝업을 켬
                @Override
                public void onClick(View v) {
                    TLI.setIsAccept("1");
                    Toast.makeText(getActivity().getApplicationContext(), "출력할 문자열", Toast.LENGTH_LONG).show();
                    refresh();
                }
            });

            TLI.setOnClickListenerDeny(new Button.OnClickListener() {
                // 버튼 선택시 식단/루틴 팝업을 켬
                @Override
                public void onClick(View v) {
                    //todo 데이터베이스 지우는 함수

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