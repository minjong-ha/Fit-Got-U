package com.example.myapplication.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TrainerMatchFragment extends Fragment {
    TrainerAdapter adapter;
    Geocoder geocoder;
    double latitude, longitude, t_latitude, t_longitude;
    MapPoint mapPoint;
    MapView mapView;
    ViewGroup mapViewContainer;
    MapPOIItem marker;
    Location location, t_location;
    ArrayList<TrainerItem> itemList = new ArrayList<TrainerItem>() ;
    boolean isSecond = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match, container, false);

        geocoder = new Geocoder(getActivity());
        final ArrayList<MapPOIItem> markers = new ArrayList<>();

        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);
        location = new Location("user_location");
        t_location = new Location("t_location");

        markers.add(new MapPOIItem());
        marker = markers.get(0);
        //mapViewed(marker, mapPoint, mapView, false);

        //리스트뷰 객체화
        ListView listView = (ListView) view.findViewById(R.id.listView);
        //어댑터 객체화
        adapter = new TrainerAdapter(itemList);
        ArrayList<HashMap<String, String>> trainerlist = Util.SelectTrainerList();

        if (((MainActivity) getActivity()).getIs_user()!=null) {
            List<Address> lists = null;
            String strs = ((MainActivity) getActivity()).getAddress();
            try {
                lists = geocoder.getFromLocationName(
                        strs, // 지역 이름
                        1); // 읽을 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (lists != null) {
                if (lists.size() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "주소를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                } else {
                    marker = markers.get(0);
                    mapView.removePOIItem(marker);
                    longitude = lists.get(0).getLongitude();
                    latitude = lists.get(0).getLatitude();
                    location.setLongitude(longitude);
                    location.setLatitude(latitude);
                    mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                    marker.setItemName("내 위치");

                    mapView.setMapCenterPoint(mapPoint, true); // animated : true
                    mapViewed(marker, mapPoint, mapView, true);
                }
            }
        }

        for (HashMap<String, String> t : trainerlist) {
            List<Address> list = null;
            String str = t.get("address");
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
                    t_longitude = list.get(0).getLongitude();
                    t_latitude = list.get(0).getLatitude();
                    mapPoint = MapPoint.mapPointWithGeoCoord(t_latitude, t_longitude);
                    MapPOIItem tmarker = new MapPOIItem();
                    markers.add(tmarker);

                    if(!isSecond) {
                    t_location.setLongitude(t_longitude);
                    t_location.setLatitude(t_latitude);

                    double distance = Math.round(location.distanceTo(t_location) / 10.0) / 100.0;
                    if (distance <= 0.01) distance = 0.01;

                        adapter.addItem(new TrainerItem(Long.parseLong(t.get("userid")), t.get("name"), distance, t.get("profile_image"), t.get("youtube")));
                    }

                    mapView.setMapCenterPoint(mapPoint, true); // animated : true
                    mapViewed(tmarker, mapPoint, mapView, false);
                }
            }
        }

        Comparator<TrainerItem> dis_asc = new Comparator<TrainerItem>() {
            @Override
            public int compare(TrainerItem t1, TrainerItem t2) {
                int res;

                if (Double.compare(t1.getDistance(),t2.getDistance())>0) res = 1;
                else if (Double.compare(t1.getDistance(),t2.getDistance()) == 0) res = 0;
                else res = -1;
                return res;
            }
        };

        //리스트뷰+어댑터
        listView.setAdapter(adapter);


        Collections.sort(itemList, dis_asc) ;
        adapter.notifyDataSetChanged() ;


        //리스트뷰 이벤트리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainerItem item = (TrainerItem) adapter.getItem(position);
                Bundle args = new Bundle();
                args.putString("youtube", item.getYoutube());
                TrainerMatch2Fragment tm2 = new TrainerMatch2Fragment();
                tm2.setArguments(args);
                ((MainActivity) getActivity()).ChangeFragmentMain(tm2);
            }
        });
        isSecond=true;
        return view;
    }

    class TrainerAdapter extends BaseAdapter {
        private ArrayList<TrainerItem> items;

        public TrainerAdapter(ArrayList<TrainerItem> itemList) {
            if (itemList == null) {
                items = new ArrayList<TrainerItem>() ;
            } else {
                items = itemList ;
            }
        }
        //ArrayList<TrainerItem> items = new ArrayList<TrainerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TrainerItem item) {
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
            TrainerItemView view = null;

            if (convertView == null) {
                //어떤 뷰든 context 객체로 받으므로 adapter의 getview에서 getApplicationContext() 로 받아옴
                view = new TrainerItemView(getActivity().getApplicationContext());
                //이 때 받아온 view 자체가 아이템
            } else {
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

    public void mapViewed(MapPOIItem marker, MapPoint mapPoint, MapView mapView, boolean user) {
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        if (user) marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        else {
            marker.setItemName("트레이너");
            marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        }
        mapView.addPOIItem(marker);
    }
}
