package com.example.myapplication.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class DA2_List_Item_Adapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<DA2_List_Item> m_oData = null;
    private int nListCnt = 0;

    public DA2_List_Item_Adapter(ArrayList<DA2_List_Item> _oData) {
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
            convertView = inflater.inflate(R.layout.da2_list_item, parent, false);
        }

        TextView oTextExercise = (TextView) convertView.findViewById(R.id.textExercise);
        TextView oTextCount = (TextView) convertView.findViewById(R.id.textCount);
        TextView oTextText = (TextView) convertView.findViewById(R.id.textText);

        oTextExercise.setText(m_oData.get(position).getStrExercise());
        oTextCount.setText(m_oData.get(position).getExerciseCount());
        oTextText.setText(m_oData.get(position).getStrText());
        return convertView;
    }
}
