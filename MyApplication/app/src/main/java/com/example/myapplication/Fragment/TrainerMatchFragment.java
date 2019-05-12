package com.example.myapplication.Fragment;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.List.TrainerItem;
import com.example.myapplication.List.TrainerItemView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TrainerMatchFragment extends Fragment {
    TrainerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match, container, false);

        //리스트뷰 객체화
        ListView listView = (ListView) view.findViewById(R.id.listView);

        //어댑터 객체화
        adapter = new TrainerAdapter();

        //addItem
        adapter.addItem(new TrainerItem("김등빨", "2.3km",R.drawable.ic_launcher_background));
        adapter.addItem(new TrainerItem("이어깨", "3.5km",R.drawable.ic_launcher_foreground));
        adapter.addItem(new TrainerItem("박하체", "4.8km",R.drawable.ic_launcher_background));
        adapter.addItem(new TrainerItem("박근육", "6.3km",R.drawable.ic_launcher_foreground));
        adapter.addItem(new TrainerItem("하정우", "8.1km",R.drawable.ic_launcher_background));
        adapter.addItem(new TrainerItem("장가슴", "9.0km",R.drawable.ic_launcher_foreground));

        //리스트뷰+어댑터
        listView.setAdapter(adapter);

        //리스트뷰 이벤트리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainerItem item = (TrainerItem) adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(), "선택"+position +" : "+item.getName(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    class TrainerAdapter extends BaseAdapter {
        ArrayList<TrainerItem> items = new ArrayList<TrainerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TrainerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TrainerItemView view=null;

            if(convertView == null){
                //어떤 뷰든 context 객체로 받으므로 adapter의 getview에서 getApplicationContext() 로 받아옴
                view = new TrainerItemView(getActivity().getApplicationContext());
                //이 때 받아온 view 자체가 아이템
            }else {
                //부 재사용 (메모리 효용)
                view = (TrainerItemView) convertView;
            }

            //뷰 포지션값 get
            TrainerItem item = items.get(position);
            view.setName(item.getName());
            view.setDistance(item.getDistance());
            view.setImage(item.getResId());

            return view;
        }
    }
}
