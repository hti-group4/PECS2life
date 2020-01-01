/*
 * Copyright (C) of the original file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019-2020 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: use word "Card" instead of "Word".
 *
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

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 * <p>
 * CardRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the CardViewModel.
 */

@Database(entities = {Card.class}, version = 4, exportSchema = false)
public abstract class CardRoomDatabase2 extends RoomDatabase {

    public abstract CardDao2 cardDao2();

    private static CardRoomDatabase2 INSTANCE;

    static CardRoomDatabase2 getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (CardRoomDatabase2.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase2.class, "card_database2")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     * <p>
     * This callback is called when the database has opened.
     * In this case, use PopulateDbAsync to populate the database
     * with the initial data set if the database has no entries.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CardDao2 mDao;

        String[] words = {"Paprika", "Lounas", "Voileipä", "Omena", "Hampurilainen", "Banaani",
                "Porkkana"};
        int[] images = {R.drawable.img_red_pepper, R.drawable.img_kebab_meal,
                R.drawable.img_sandwich, R.drawable.img_apple, R.drawable.img_hamburger,
                R.drawable.img_banana, R.drawable.img_carrot};

        String[] ids = {"pepper", "lunch", "sandwich", "apple", "hamburger", "banana", "carrot"};

        PopulateDbAsync(CardRoomDatabase2 db) {
            mDao = db.cardDao2();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i < words.length; i++) {
                Card card = new Card(words[i], images[i], ids[i]);
                mDao.insert(card);
            }
            return null;
        }
    }

}
