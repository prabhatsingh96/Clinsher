package com.example.fluper.clinsher.appActivity.controller.myjob;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fluper.clinsher.R;
import com.example.fluper.clinsher.appActivity.controller.utils.MyJobUserInfo;

import java.util.ArrayList;

/**
 * Created by fluper on 9/3/18.
 */

public class MyJobRecyclerAdapter extends RecyclerView.Adapter<MyJobRecyclerAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<MyJobUserInfo> info = new ArrayList<>();

    public MyJobRecyclerAdapter(Context context ,ArrayList<MyJobUserInfo> info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_job_recycler_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(info.get(position).getName().toString().trim());
        holder.location.setText(info.get(position).getLocation().toString().trim());
        holder.address.setText(info.get(position).getAddress().toString().trim());
        holder.time.setText(info.get(position).getJob().toString().trim());

    }

    @Override
    public int getItemCount() {
        return info.size();
    }


    public void removeItem(int pos){
        info.remove(pos);
        this.notifyItemRemoved(pos);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView address;
        TextView location;
        TextView time;
        RelativeLayout backGround;
        CardView foreGround;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_my_job_name_recycler_view);
            address = itemView.findViewById(R .id.my_job_recyler_view_address);
            location = itemView.findViewById(R.id.my_job_recyler_view_location);
            time = itemView.findViewById(R.id.tv_my_job_time_text);
            backGround = itemView.findViewById(R.id.view_background);
            foreGround = itemView.findViewById(R.id.card_view_foreground);

        }
    }

}
