package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static List<Stickers> readJson(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("stickers.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type type = new TypeToken<HashMap<String, List<String>>>() {}.getType();
            HashMap<String, List<String>> data = new Gson().fromJson(reader, type);
            List<Stickers> stickerList = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                stickerList.add(new Stickers(entry.getKey(), entry.getValue()));
            }
            return stickerList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

