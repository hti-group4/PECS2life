package io.github.htigroup4.pecs2life;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {

    @PrimaryKey
    @NonNull
    private String title;

    private int imageResource;

    public Card(@NonNull String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
