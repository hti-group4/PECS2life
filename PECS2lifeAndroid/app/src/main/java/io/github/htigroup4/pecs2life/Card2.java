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

    private String id;

    public Card2(@NonNull String title, int imageResource, String id) {
        this.title = title;
        this.imageResource = imageResource;
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getId() {
        return id;
    }
}
