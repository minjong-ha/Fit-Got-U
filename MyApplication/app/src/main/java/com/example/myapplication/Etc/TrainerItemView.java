package com.example.myapplication.Etc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        textView2 = (TextView)findViewById(R.id.textView2);
        imageView = (ImageView)findViewById(R.id.imageView);
    }
    public void setName(String name){
        textView.setText(name);
    }

    public void setDistance(String distance){
        textView2.setText(distance);
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

}
