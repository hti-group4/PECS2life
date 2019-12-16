package io.github.htigroup4.pecs2life;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView nameTxt;
    CheckBox chk;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
    }
}
