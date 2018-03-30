package com.example.fluper.clinsher.appActivity.controller.myjob;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.utils.MyJobUserInfo;

import java.util.ArrayList;

public class MyJobActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private MyJobUserInfo info;
    private ArrayList<MyJobUserInfo> alInfo = new ArrayList<>();
    private RecyclerView rv;
    private MyJobRecyclerAdapter myJobRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job);
        rv = findViewById(R.id.my_job_recyclerView);

        alInfo.add(new MyJobUserInfo("prabhat", "1 day ago", "Assistant Councel", "Luxembourg"));
        alInfo.add(new MyJobUserInfo("prabhat", "1 day ago", "Assistant Councel", "Luxembourg"));
        alInfo.add(new MyJobUserInfo("prabhat", "1 day ago", "Assistant Councel", "Luxembourg"));


        myJobRecyclerAdapter = new MyJobRecyclerAdapter(this, alInfo);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(myJobRecyclerAdapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);



        /*rv.setLayoutManager(new LinearLayoutManager(this));

        //setupRecyclerView();
        rv.setAdapter(myJobRecyclerAdapter);
        RecyclerItemTouchHelper swipeController = new RecyclerItemTouchHelper(myJobRecyclerAdapter,this);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rv);*/


/*
        RecyclerItemTouchHelper  res = new RecyclerItemTouchHelper(new SwipControllerAction() {
            @Override
            public void onRightClicked(int position) {
                alInfo.remove(position);
                myJobRecyclerAdapter.notifyItemRemoved(position);
                myJobRecyclerAdapter.notifyItemRangeChanged(position, myJobRecyclerAdapter.getItemCount());
            }
        });

    }*/

   /* public void setupRecyclerView() {
        // ...
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                RecyclerItemTouchHelper.onDraw(c);
            }
        });
    }


*/
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        myJobRecyclerAdapter.removeItem(viewHolder.getAdapterPosition());
    }
}