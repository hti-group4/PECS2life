package io.github.htigroup4.pecs2life;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @NonNull
    @ColumnInfo(name = "color")
    private Integer mColor;

    public Word() {
    }

    @Ignore
    public Word(@NonNull String word, @NonNull Integer color) {
        this.mWord = word;
        this.mColor = color;
    }

    public String getWord() {
        return this.mWord;
    }

    public Integer getColor() {
        return this.mColor;
    }

    public void setWord(@NonNull String mWord) {
        this.mWord = mWord;
    }

    public void setColor(@NonNull Integer mColor) {
        this.mColor = mColor;
    }
}
