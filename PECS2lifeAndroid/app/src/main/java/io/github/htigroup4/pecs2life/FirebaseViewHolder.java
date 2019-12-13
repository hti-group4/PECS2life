package io.github.htigroup4.pecs2life;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    //Member Variables for the views
    public TextView name;
    public ImageView musicImage;


    FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        //Initialize the views
        name = itemView.findViewById(R.id.name);
        musicImage = itemView.findViewById(R.id.musicImage);
    }
}
