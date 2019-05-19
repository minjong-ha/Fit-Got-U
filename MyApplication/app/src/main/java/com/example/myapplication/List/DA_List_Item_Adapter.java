package com.example.myapplication.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

// DA_List_item_Adapter -> 데이터분석화면에서 일주일 list adapter
public class DA_List_Item_Adapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<DA_List_Item> m_oData = null;
    private int nListCnt = 0;

    public DA_List_Item_Adapter(ArrayList<DA_List_Item> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.da_list_item, parent, false);
        }

        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        TextView oTextText = (TextView) convertView.findViewById(R.id.textText);
        Button oBtn = (Button) convertView.findViewById(R.id.btnSelector);
        oTextDate.setText(m_oData.get(position).getStrDate());
        oTextText.setText(m_oData.get(position).getStrText());
        oBtn.setOnClickListener(m_oData.get(position).getOnClickListener());

        convertView.setTag(""+position);
        return convertView;
    }
}
