package io.github.htigroup4.pecs2life;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void insert(Card card);

    @Query("DELETE FROM card_table")
    void deleteAll();

    @Query("SELECT * from card_table ORDER BY title ASC")
    LiveData<List<Card>> getAllCards();

    @Delete
    void deleteCard(Card card);
}
