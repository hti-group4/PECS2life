package io.github.htigroup4.pecs2life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Player> players;
    ArrayList<Player> checkedPlayers = new ArrayList<>();

    public MyAdapter(Context c, ArrayList<Player> players) {
        this.c = c;
        this.players = players;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        MyHolder holder = new MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.nameTxt.setText(players.get(position).getName());
        holder.img.setImageResource(players.get(position).getImage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk = (CheckBox) v;

                if (chk.isChecked()) {
                    checkedPlayers.add(players.get(pos));
                } else if (!chk.isChecked()) {
                    checkedPlayers.remove(players.get(pos));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
