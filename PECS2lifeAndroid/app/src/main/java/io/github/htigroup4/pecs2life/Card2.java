package io.github.htigroup4.pecs2life;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_table2")
public class Card2 {

    @PrimaryKey
    @NonNull
    private String title;

    private int imageResource;

    public Card2(@NonNull String title, int imageResource) {
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
