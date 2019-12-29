package io.github.htigroup4.pecs2life;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Card2.class}, version = 1, exportSchema = false)
public abstract class CardRoomDatabase2 extends RoomDatabase {

    public abstract CardDao2 cardDao2();

    private static CardRoomDatabase2 INSTANCE;

    public static CardRoomDatabase2 getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (CardRoomDatabase2.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase2.class, "card_database2")
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

        private final CardDao2 mDao;

        String[] words = {"Sandwich", "Apple", "Hamburger", "Banana", "Carrot"};
        int[] images = {R.drawable.img_sandwich, R.drawable.img_apple, R.drawable.img_hamburger,
                R.drawable.img_banana, R.drawable.img_carrot};


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
                Card2 card = new Card2(words[i], images[i]);
                mDao.insert(card);
            }
            return null;
        }
    }

}
