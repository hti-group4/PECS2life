package io.github.htigroup4.pecs2life;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel2 extends AndroidViewModel {
    private CardRepository2 mRepository;
    private LiveData<List<Card>> mAllCards;

    public CardViewModel2(@NonNull Application application) {
        super(application);
        mRepository = new CardRepository2(application);
        mAllCards = mRepository.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        mRepository.insert(card);
    }

    void deleteCard(Card card) {
        mRepository.deleteCard(card);
    }
}
