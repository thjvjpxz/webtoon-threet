package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.database.DbHelper;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;

public class StoryDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public StoryDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void insertStory(Story story) {
        String authors = "";
        String categories = "";

        if (story.getAuthors() != null) {

            for (Author author : story.getAuthors()) {
                Utility.capitalizeFirstLetter(author.getName());
                authors += author.getName() + ", ";
            }
        }
        if (story.getCategories() != null) {

            for (Category category : story.getCategories()) {
                Utility.capitalizeFirstLetter(category.getName());
                categories += category.getName() + ", ";
            }
        }

        open();

        ContentValues values = new ContentValues();
        values.put("id", story.getId());
        values.put("name", story.getName());
        values.put("origin_name", story.getOrigin_name());
        values.put("content", story.getContent());
        values.put("slug", story.getSlug());
        values.put("thumbnail", story.getThumbnail());
        values.put("status", story.getStatus());
        values.put("authors", authors);
        values.put("categories", categories);
        values.put("total_views", story.getTotal_views());
        values.put("rating", story.getRating());

        db.insert("stories", null, values);
        close();
    }

    public Story getStoryById(int id) {
        Story story = new Story();
        open();
        Cursor cursor = db.query("stories", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            story.setId(cursor.getInt(0));
            story.setName(cursor.getString(1));
            story.setOrigin_name(cursor.getString(2));
            story.setContent(cursor.getString(3));
            story.setSlug(cursor.getString(4));
            story.setThumbnail(cursor.getString(5));
            story.setStatus(cursor.getString(6));
            story.setAuthors(Utility.stringToListAuthor(cursor.getString(7)));
            story.setCategories(Utility.stringToListCategory(cursor.getString(8)));
            story.setTotal_views(cursor.getInt(9));
            story.setRating(cursor.getFloat(10));
            close();
            return story;
        }
        return null;
    }

}
