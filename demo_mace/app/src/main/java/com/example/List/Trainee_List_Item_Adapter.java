package com.example.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.R;

import java.util.ArrayList;

public class Trainee_List_Item_Adapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<Trainee_List_Item> m_oData = null;
    private int nListCnt = 0;

    public Trainee_List_Item_Adapter(ArrayList<Trainee_List_Item> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.trainee_item, parent, false);
        }

        TextView oTextName = (TextView) convertView.findViewById(R.id.textName);
        TextView oTextTall = (TextView) convertView.findViewById(R.id.textTall);
        TextView oTextWeight = (TextView) convertView.findViewById(R.id.textWeight);
        TextView oTextSex = (TextView) convertView.findViewById(R.id.textSex);
        TextView oTextAddress = (TextView) convertView.findViewById(R.id.textAddress);
        Button oBtn = (Button) convertView.findViewById(R.id.btnDetail);
        Button oBtn2 = (Button) convertView.findViewById(R.id.btninfo);
        Button oBtnAccept =(Button) convertView.findViewById(R.id.btnAccept);
        Button oBtnDeny =(Button) convertView.findViewById(R.id.btnDeny);


        oTextName.setText(m_oData.get(position).getName());
        oTextTall.setText(m_oData.get(position).getTall());
        oTextWeight.setText(m_oData.get(position).getWeight());
        oTextSex.setText(m_oData.get(position).getSex());
        oTextAddress.setText((m_oData.get(position).getAddress()));
        if(m_oData.get(position).getIsAccept().equals("1")) {
            oBtn.setVisibility(View.VISIBLE);
            oBtn2.setVisibility(View.VISIBLE);
            oBtn.setOnClickListener(m_oData.get(position).getOnClickListenerDetail());
            oBtn2.setOnClickListener(m_oData.get(position).getOnClickListenerInfo());
            oBtn.setBackgroundColor(Color.TRANSPARENT);
            oBtn2.setBackgroundColor(Color.TRANSPARENT);
            oBtnAccept.setVisibility(View.INVISIBLE);
            oBtnDeny.setVisibility(View.INVISIBLE);
        }
        else {
            oBtn.setVisibility(View.INVISIBLE);
            oBtn2.setVisibility(View.INVISIBLE);
            oBtnAccept.setVisibility(View.VISIBLE);
            oBtnDeny.setVisibility(View.VISIBLE);
            oBtnAccept.setOnClickListener(m_oData.get(position).getOnClickListenerAccept());
            oBtnDeny.setOnClickListener(m_oData.get(position).getOnClickListenerDeny());
            oBtnAccept.setBackgroundColor(Color.TRANSPARENT);
            oBtnDeny.setBackgroundColor(Color.TRANSPARENT);
        }

        convertView.setTag(""+position);
        return convertView;
    }
}
