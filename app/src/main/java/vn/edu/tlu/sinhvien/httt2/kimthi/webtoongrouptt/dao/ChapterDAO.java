package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.database.DbHelper;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Chapter;

public class ChapterDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ChapterDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void insertChapter(Chapter chapter) {
        open();

        ContentValues values = new ContentValues();
        values.put("id", chapter.getId());
        values.put("name", chapter.getName());
        values.put("title", chapter.getTitle());
        values.put("chapter_number", chapter.getChapterNumber());
        values.put("slug", chapter.getSlug());
        values.put("content", chapter.getContent());
        values.put("story_id", chapter.getStory_id());
        values.put("updated_at", chapter.getUpdated_at());

        db.insert("chapters", null, values);
        close();
    }

    public void insertListChapter(List<Chapter> chapters) {
        for (Chapter chapter : chapters) {
            insertChapter(chapter);
        }
    }

    public List<Chapter> getListChapter(int storyId) {
        List<Chapter> chapters = null;
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM chapters WHERE story_id = " + storyId, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Chapter chapter = new Chapter();
            chapter.setId(cursor.getInt(0));
            chapter.setName(cursor.getString(1));
            chapter.setTitle(cursor.getString(2));
            chapter.setChapterNumber(cursor.getString(3));
            chapter.setSlug(cursor.getString(4));
            chapter.setContent(cursor.getString(5));
            chapter.setStory_id(cursor.getInt(6));
            chapter.setUpdated_at(cursor.getString(7));
            chapters.add(chapter);
            cursor.moveToNext();
        }
        close();
        return chapters;
    }
}
