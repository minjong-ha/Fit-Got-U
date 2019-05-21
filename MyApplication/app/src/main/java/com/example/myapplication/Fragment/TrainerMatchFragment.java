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

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Etc.Util;
import com.example.myapplication.List.TrainerItem;
import com.example.myapplication.List.TrainerItemView;
import com.example.myapplication.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainerMatchFragment extends Fragment {
    TrainerAdapter adapter;
    //Button button;
    //EditText editText;
    Button trainer_button;
    EditText trainer_editText;
    Geocoder geocoder;
    double latitude=37.283014,longitude=127.046355;
    double trainer_latitude, trainer_longitude;
    MapPoint mapPoint;
    MapView mapView;
    ViewGroup mapViewContainer;
    MapPOIItem marker;
    Location location, trainer_location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match, container, false);

        //button = (Button)view.findViewById(R.id.button);
        trainer_button = (Button)view.findViewById(R.id.button2);
        //editText = (EditText)view.findViewById(R.id.editText);
        trainer_editText = (EditText)view.findViewById(R.id.editText2);
        geocoder = new Geocoder(getActivity());
        final ArrayList<MapPOIItem> markers = new ArrayList<>();

        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);
        location= new Location("user_location");
        trainer_location=new Location("trainer_location");

        markers.add(new MapPOIItem());
        marker=markers.get(0);
        marker.setItemName("아주대학교");
        mapViewed(marker, mapPoint, mapView, false);

        //리스트뷰 객체화
        ListView listView = (ListView) view.findViewById(R.id.listView);
        //어댑터 객체화
        adapter = new TrainerAdapter();


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
        if(((MainActivity)getActivity()).getIs_user().equals("유저")) {
            List<Address> list = null;
            String str = ((MainActivity) getActivity()).getAddress();
            try {
                list = geocoder.getFromLocationName(
                        str, // 지역 이름
                        10); // 읽을 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (list != null) {
                if (list.size() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "주소를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                } else {
                    marker = markers.get(0);
                    mapView.removePOIItem(marker);
                    longitude = list.get(0).getLongitude();
                    latitude = list.get(0).getLatitude();
                    location.setLongitude(longitude);
                    location.setLatitude(latitude);
                    mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);

                    mapView.setMapCenterPoint(mapPoint, true); // animated : true
                    mapViewed(marker, mapPoint, mapView, false);
                }
            }

//            }
//        });
        }

//        trainer_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                if(((MainActivity)getActivity()).getIs_user().equals("트레이너")) {
                    List<Address> list = null;
                    String str = ((MainActivity) getActivity()).getAddress();
                    //String str = trainer_editText.getText().toString();
                    try {
                        list = geocoder.getFromLocationName(
                                str, // 지역 이름
                                10); // 읽을 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                    }

                    if (list != null) {
                        if (list.size() == 0) {
                            Toast.makeText(getActivity().getApplicationContext(), "주소를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                        } else {
                            trainer_longitude = list.get(0).getLongitude();
                            trainer_latitude = list.get(0).getLatitude();
                            mapPoint = MapPoint.mapPointWithGeoCoord(trainer_latitude, trainer_longitude);
                            MapPOIItem tmarker = new MapPOIItem();
                            markers.add(tmarker);

                            trainer_location.setLongitude(trainer_longitude);
                            trainer_location.setLatitude(trainer_latitude);

                            double distance = Math.round(location.distanceTo(trainer_location) / 10.0) / 100.0;
                            if (distance <= 0.01) distance = 0.01;

                            ArrayList<HashMap<String, String>> trainerlist = Util.SelectTrainerList();
                            for (HashMap<String, String> t : trainerlist) {
                                adapter.addItem(new TrainerItem(Long.parseLong(t.get("userid")), t.get("name"), distance, t.get("profile_image"), t.get("userid")));
                            }

                            mapView.setMapCenterPoint(mapPoint, true); // animated : true
                            mapViewed(tmarker, mapPoint, mapView, true);
                        }
                    }
//            }
//        });
                }
        //리스트뷰+어댑터
        listView.setAdapter(adapter);

        //리스트뷰 이벤트리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainerItem item = (TrainerItem) adapter.getItem(position);
                Bundle args = new Bundle();
                args.putString("youtube", item.getYoutube());
                TrainerMatch2Fragment tm2 = new TrainerMatch2Fragment();
                tm2.setArguments(args);
                ((MainActivity)getActivity()).ChangeFragmentMain(tm2);
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
            view.setImage(item.getProfile_image());

            return view;
        }
    }

    public static void mapViewed(MapPOIItem marker, MapPoint mapPoint, MapView mapView, boolean trainer){
        marker.setItemName("검색 위치");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        if(!trainer) marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        else marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }
}
