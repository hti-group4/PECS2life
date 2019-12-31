package io.github.htigroup4.pecs2life;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository2 {

    private CardDao2 mCardDao;
    private LiveData<List<Card>> mAllCards;

    CardRepository2(Application application) {
        CardRoomDatabase2 db = CardRoomDatabase2.getDatabase(application);
        mCardDao = db.cardDao2();
        mAllCards = mCardDao.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        new insertAsyncTask(mCardDao).execute(card);
    }

    void deleteCard(Card card) {
        new deleteCardAsyncTask(mCardDao).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao2 mAsyncTaskDao;

        insertAsyncTask(CardDao2 dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao2 mAsyncTaskDao;

        deleteCardAsyncTask(CardDao2 dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.deleteCard(params[0]);
            return null;
        }
    }
}
