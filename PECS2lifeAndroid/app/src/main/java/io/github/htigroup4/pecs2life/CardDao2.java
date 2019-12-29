package io.github.htigroup4.pecs2life;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao2 {

    @Insert
    void insert(Card2 card);

    @Query("DELETE FROM card_table2")
    void deleteAll();

    @Query("SELECT * from card_table2 ORDER BY title ASC")
    LiveData<List<Card2>> getAllCards();
}
