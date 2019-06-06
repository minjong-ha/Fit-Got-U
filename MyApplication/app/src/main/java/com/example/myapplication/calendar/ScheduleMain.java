package com.example.myapplication.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.calendar.etc.DateDialog;
import com.example.myapplication.calendar.ui.CalendarAdapter;
import com.example.myapplication.calendar.ui.CalendarListViewModel;
import com.example.myapplication.calendar.ui.RecyclerViewOnItemClickListener;
import com.example.myapplication.databinding.CalendarListBinding;

import java.util.ArrayList;

public class ScheduleMain extends AppCompatActivity {
    private CalendarListBinding binding;
    private CalendarListViewModel model;

    Context sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_calendar);
        binding = DataBindingUtil.setContentView(this, R.layout.schedule_calendar);
        model = ViewModelProviders.of(this).get(CalendarListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        observe();
        if (model != null) {
            model.initCalendarList();
        }
    }

    private void observe() {
        model.mCalendarList.observe(this, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> objects) {
                RecyclerView view = binding.pagerCalendar;
                CalendarAdapter adapter = (CalendarAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setCalendarList(objects);
                } else {
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
                    adapter = new CalendarAdapter(objects);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);
                    if (model.mCenterPosition >= 0) {
                        view.scrollToPosition(model.mCenterPosition);
                    }
                }

                view.addOnItemTouchListener(new RecyclerViewOnItemClickListener(sm, view,
                        new RecyclerViewOnItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent = new Intent(getApplicationContext(), DateDialog.class);
                                startActivityForResult(intent, 103);
                            }
                        /*
                            @Override
                            public void onItemLongClick(View v, int position) {
                                Toast.makeText(getApplicationContext(), "긴 클릭", Toast.LENGTH_LONG).show();
                            }*/
                        }
                ));
            }
        });
    }
}