package com.example.myapplication.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.List.TrainerItem;
import com.example.myapplication.List.TrainerItemView;
import com.example.myapplication.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainerMatchFragment extends Fragment {
    TrainerAdapter adapter;
    Button button;
    EditText editText;
    Geocoder geocoder;
    double latitude=37.283014,longitude=127.046355;
    MapPoint mapPoint;
    MapView mapView;
    ViewGroup mapViewContainer;
    MapPOIItem marker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match, container, false);

        button = (Button)view.findViewById(R.id.button);
        editText = (EditText)view.findViewById(R.id.editText);
        geocoder = new Geocoder(getActivity());
        marker = new MapPOIItem();

        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        marker.setItemName("아주대학교");
        mapViewed(marker, mapPoint, mapView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;


                String str = editText.getText().toString();
                try {
                    list = geocoder.getFromLocationName(
                            str, // 지역 이름
                            10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "주소를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                    } else {
                        longitude=list.get(0).getLongitude();
                        latitude=list.get(0).getLatitude();
                        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);

                        mapView.setMapCenterPoint(mapPoint, true); // animated : true
                        mapViewed(marker, mapPoint, mapView);
                    }
                }
            }
        });

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
	
    public static Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    }

    public static void mapViewed(MapPOIItem marker, MapPoint mapPoint, MapView mapView){
        marker.setItemName("검색 위치");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }
}
