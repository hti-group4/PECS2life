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

    private Context c;
    private ArrayList<Player> players;
    ArrayList<Player> checkedPlayers = new ArrayList<>();

    MyAdapter(Context c, ArrayList<Player> players) {
        this.c = c;
        this.players = players;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);

        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.nameTxt.setText(players.get(position).getName());
        holder.img.setImageResource(players.get(position).getImage());

        holder.setItemClickListener((v, pos) -> {
            CheckBox chk = (CheckBox) v;

            if (chk.isChecked()) {
                checkedPlayers.add(players.get(pos));
            } else if (!chk.isChecked()) {
                checkedPlayers.remove(players.get(pos));
            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
