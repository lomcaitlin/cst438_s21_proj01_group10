package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<PlayerPost> playersList;

    public Adaptery(Context mContext, List playersList) {
        this.mContext = mContext;
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.activity_home, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.first_name.setText(playersList.get(position).getFirst_name());
        holder.last_name.setText(playersList.get(position).getLast_name());
        //holder.id.setText(playersList.get(position).getId());
        holder.id.setText(String.valueOf(playersList.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView first_name;
        TextView last_name;
        TextView id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            first_name = itemView.findViewById(R.id.textView);
            last_name = itemView.findViewById(R.id.textView2);
            id = itemView.findViewById(R.id.textView3);
        }
    }

}
