package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webtoon.db";
    private static final String DB_STORY = "stories";
    private static final String DB_CHAPTER = "chapters";
    private static final String DB_COMMENT = "comments";

    private static final int VERSION = 3;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_1 = "CREATE TABLE " + DB_STORY + " (id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "origin_name TEXT DEFAULT(NULL), " +
                "content TEXT DEFAULT(NULL), " +
                "slug TEXT, " +
                "thumbnail TEXT, " +
                "status TEXT, " +
                "authors TEXT DEFAULT(NULL), " +
                "categories TEXT, " +
                "total_views INTEGER, " +
                "rating TEXT);";
        db.execSQL(sql_1);

        String sql_2 = "CREATE TABLE " +
                DB_CHAPTER +
                " (id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "title TEXT DEFAULT(NULL), " +
                "chapter_number TEXT, " +
                "slug TEXT, " +
                "content TEXT DEFAULT(NULL), " +
                "story_id INTEGER, " +
                "updated_at TEXT, " +
                "FOREIGN KEY (story_id) REFERENCES " + DB_STORY + "(id) ON DELETE CASCADE)";
        db.execSQL(sql_2);

        String sql_3 = "CREATE TABLE " +
                DB_COMMENT +
                " (id INTEGER PRIMARY KEY, " +
                "content TEXT, " +
                "user_id INTEGER, " +
                "story_id INTEGER, " +
                "chapter_id INTEGER, " +
                "created_at TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (story_id) REFERENCES " + DB_STORY + "(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (chapter_id) REFERENCES " + DB_CHAPTER + "(id) ON DELETE CASCADE)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_1 = "DROP TABLE IF EXISTS " + DB_STORY;
        db.execSQL(sql_1);

        String sql_2 = "DROP TABLE IF EXISTS " + DB_CHAPTER;
        db.execSQL(sql_2);

        onCreate(db);
    }
}
