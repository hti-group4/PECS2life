package io.github.htigroup4.pecs2life;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class CardRoomDatabase extends RoomDatabase {

    public abstract CardDao cardDao();

    private static CardRoomDatabase INSTANCE;

    static CardRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase.class, "card_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

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

        private final CardDao mDao;

        String[] words = {"Paprika", "Lounas"};
        int[] images = {R.drawable.img_red_pepper, R.drawable.img_kebab_meal};


        PopulateDbAsync(CardRoomDatabase db) {
            mDao = db.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i < words.length; i++) {
                Card card = new Card(words[i], images[i]);
                mDao.insert(card);
            }
            return null;
        }
    }

}
