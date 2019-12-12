package io.github.htigroup4.pecs2life;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView teamone, teamtwo;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        teamone = itemView.findViewById(R.id.teamone);
        teamtwo = itemView.findViewById(R.id.teamtwo);
    }
}
