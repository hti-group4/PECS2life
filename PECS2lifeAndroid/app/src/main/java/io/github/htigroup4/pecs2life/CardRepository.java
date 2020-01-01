/*
 * Copyright (C) of the original file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019-2020 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: use word "Card" instead of "Word".
 * Do not delete all cards from the database in this class.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.htigroup4.pecs2life;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class CardRepository {

    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    CardRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
        mAllCards = mCardDao.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        new insertAsyncTask(mCardDao).execute(card);
    }

    // Need to run off main thread
    void deleteCard(Card card) {
        new deleteCardAsyncTask(mCardDao).execute(card);
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a card into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao mAsyncTaskDao;

        insertAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     *  Delete a single card from the database.
     */
    private static class deleteCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao mAsyncTaskDao;

        deleteCardAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.deleteCard(params[0]);
            return null;
        }
    }
}
