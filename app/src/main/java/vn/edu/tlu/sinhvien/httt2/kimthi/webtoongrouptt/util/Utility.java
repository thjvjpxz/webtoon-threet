package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;

public class Utility {
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    public static List<Author> stringToListAuthor(String authors) {
        List<Author> authorList = null;
        if (authors != null) {
            String[] authorArr = authors.split(", ");
            for (String author : authorArr) {
                Author a = new Author();
                a.setName(author);
                authorList.add(a);
            }
        }
        return authorList;
    }

    public static List<Category> stringToListCategory(String categories) {
        List<Category> categoryList = null;
        if (categories != null) {
            String[] categoryArr = categories.split(", ");
            for (String category : categoryArr) {
                Category c = new Category();
                c.setName(category);
                categoryList.add(c);
            }
        }
        return categoryList;
    }

    public static String getFullHtml(String tag) {
        return "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link href='https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind" +
                ".min.css' rel='stylesheet'>" +
                "<title>Chapter Content</title>" +
                "</head>" +
                "<body>" +
                tag +
                "</body>" +
                "</html>";
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
