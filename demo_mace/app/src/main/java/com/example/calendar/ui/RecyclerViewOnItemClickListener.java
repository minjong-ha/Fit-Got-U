package com.example.calendar.ui;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private int currentPosition;

    public RecyclerViewOnItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        this.mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
            /*
            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null){
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }*/
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            currentPosition = rv.getChildAdapterPosition(child);
            mListener.onItemClick(child, rv.getChildAdapterPosition(child));
            Toast.makeText(rv.getContext(), Integer.toString(currentPosition), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
        //void onItemLongClick(View v, int position);
    }
}