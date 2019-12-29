package io.github.htigroup4.pecs2life;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository2 {

    private CardDao2 mCardDao;
    private LiveData<List<Card2>> mAllCards;

    CardRepository2(Application application) {
        CardRoomDatabase2 db = CardRoomDatabase2.getDatabase(application);
        mCardDao = db.cardDao2();
        mAllCards = mCardDao.getAllCards();
    }

    public LiveData<List<Card2>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card2 card) {
        new insertAsyncTask(mCardDao).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<Card2, Void, Void> {
        private CardDao2 mAsyncTaskDao;

        insertAsyncTask(CardDao2 dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card2... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
