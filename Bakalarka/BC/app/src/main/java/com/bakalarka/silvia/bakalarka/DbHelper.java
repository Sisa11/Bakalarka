//package com.bakalarka.silvia.bakalarka;
//
//    import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static android.database.DatabaseUtils.queryNumEntries;
//
//    public class DBHelper extends SQLiteOpenHelper {
//
//        public static final String DATABASE_NAME = "my_database.db";
//        public static final String WORDS_TABLE_NAME = "item";
//        public static final String WORDS_ENGLISH = "english";
//        public static final String WORDS_SLOVAK = "slovak";
//        public static final String WORDS_PRONUNCIATIONS = "pronunciations";
//        public static final String WORDS_EXCLUDED = "excluded";
//        public static final String WORDS_VERSION = "version";
//        public static final String WORDS_EDITED = "edited";
//        private HashMap hp;
//
//        public DBHelper(Context context) {
//            super(context, DATABASE_NAME, null, 1);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            // TODO Auto-generated method stub
//            db.execSQL(
//                    "create table User " + " (objectID string primary key, username text unique, name text, surname text)"
//            );
//            db.execSQL(
//                    "create table Medicine " + " (objectID string primary key, username text unique, name text, surname text)"
//            );
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // TODO Auto-generated method stub
//            db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE_NAME);
//            onCreate(db);
//        }
//
//        public boolean insertWord(
//                String english,
//                String slovak,
//                String pronunciations,
//                boolean excluded,
//                int version,
//                boolean edited)
//        {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(WORDS_ENGLISH, english);
//            contentValues.put(WORDS_SLOVAK, slovak);
//            contentValues.put(WORDS_PRONUNCIATIONS, pronunciations);
//            contentValues.put(WORDS_EXCLUDED, (excluded) ? 1 : 0);
//            contentValues.put(WORDS_VERSION, version);
//            contentValues.put(WORDS_EDITED, (edited) ? 1 : 0);
//
//            return db.insert(WORDS_TABLE_NAME, null, contentValues) >= 0;
//        }
//
//        public Cursor getData(String engWord) {
//            SQLiteDatabase db = this.getReadableDatabase();
//            return db.rawQuery(
//                    "select * from " + WORDS_TABLE_NAME + " where " + WORDS_ENGLISH + " = " + engWord + "",
//                    null
//            );
//        }
//
////        public ArrayList<Item> getEditedData() {
////            ArrayList<Item> array_list = new ArrayList<>();
////            SQLiteDatabase db = this.getReadableDatabase();
////            Cursor res = db.rawQuery("select * from " + WORDS_TABLE_NAME + " where " + WORDS_EDITED + " = true", null);
////            res.moveToFirst();
////
////            while (!res.isAfterLast()) {
////                array_list.add(
////                        new Item(
////                                res.getString(res.getColumnIndex(WORDS_SLOVAK))
////                                , res.getString(res.getColumnIndex(WORDS_ENGLISH))
////                                , res.getString(res.getColumnIndex(WORDS_PRONUNCIATIONS))
////                        )
////                );
////                res.moveToNext();
////            }
////            res.close();
////            return array_list;
////        }
////
////        public Item getRandomWord() {
////            SQLiteDatabase db = this.getReadableDatabase();
////            Cursor res = db.rawQuery(
////                    "SELECT * FROM item where excluded = 0 ORDER BY RANDOM() LIMIT 1;",
////                    null
////            );
////
////            res.moveToFirst();
////
////            Item item = new Item(res.getString(res.getColumnIndex(WORDS_ENGLISH))
////                    , res.getString(res.getColumnIndex(WORDS_SLOVAK))
////                    , res.getString(res.getColumnIndex(WORDS_PRONUNCIATIONS))
////            );
////            if (!res.isClosed())  {
////                res.close();
////            }
////            return item;
////        }
////
////        public int numberOfRows() {
////            SQLiteDatabase db = this.getReadableDatabase();
////            return (int) queryNumEntries(db, WORDS_TABLE_NAME);
////        }
//
////        public boolean updateWord(
////                Integer id,
////                String english,
////                String slovak,
////                String pronunciations,
////                boolean excluded,
////                int version,
////                boolean edited)
////        {
////            SQLiteDatabase db = this.getWritableDatabase();
////            ContentValues contentValues = new ContentValues();
////            contentValues.put(WORDS_ENGLISH, english);
////            contentValues.put(WORDS_SLOVAK, slovak);
////            contentValues.put(WORDS_PRONUNCIATIONS, pronunciations);
////            contentValues.put(WORDS_EXCLUDED, excluded);
////            contentValues.put(WORDS_VERSION, version);
////            contentValues.put(WORDS_EDITED, edited);
////
////            db.update(WORDS_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
////            return true;
////        }
////
////        public Integer deleteWord(Integer id) {
////            SQLiteDatabase db = this.getWritableDatabase();
////            return db.delete(
////                    WORDS_TABLE_NAME,
////                    "id = ? ",
////                    new String[]{Integer.toString(id)}
////            );
////        }
////
////        public ArrayList<Item> getAllWords() {
////            ArrayList<Item> array_list = new ArrayList<>();
////
////            //hp = new HashMap();
////            SQLiteDatabase db = this.getReadableDatabase();
////            Cursor res = db.rawQuery("select * from " + WORDS_TABLE_NAME, null);
////            res.moveToFirst();
////
////            while (!res.isAfterLast()) {
////                array_list.add(
////                        new Item(
////                                res.getString(res.getColumnIndex(WORDS_SLOVAK))
////                                , res.getString(res.getColumnIndex(WORDS_ENGLISH))
////                                , res.getString(res.getColumnIndex(WORDS_PRONUNCIATIONS))
////                        )
////                );
////                res.moveToNext();
////            }
////            res.close();
////            return array_list;
////        }
//    }
//
