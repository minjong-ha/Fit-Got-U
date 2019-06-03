package com.example.myapplication.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Etc.Util;
import com.example.myapplication.R;

public class TrainerItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    ImageView imageView;


    public TrainerItemView(Context context) {
        super(context);
        init(context);
    }

    public TrainerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.trainer_item, this, true);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView22);
        imageView = (ImageView)findViewById(R.id.imageView);
    }
    public void setName(String name){
        textView.setText(name);
    }

    public void setDistance(double distance){
        textView2.setText(Double.toString(distance).concat(" km"));
    }

    public void setImage(String youtube){
        imageView.setImageBitmap(Util.getImagefromURL(youtube));
    }

}