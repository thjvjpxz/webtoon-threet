package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefManager mInstance;
    private SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void saveTypeWebtoon(int type) {
        getEditor().putInt("type", type).apply();
    }

    public int getTypeWebtoon() {
        return sharedPreferences.getInt("type", 0);
    }

    public void saveToken(String token) {
        getEditor().putString("token", token).apply();
    }

    public void saveAvatar(String avatar) {
        getEditor().putString("avatar", avatar).apply();
    }

    public void saveName(String name) {
        getEditor().putString("name", name).apply();
    }

    public void removeToken() {
        getEditor().clear().apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString("token", null) != null;
    }

    public String getToken() {
        return sharedPreferences.getString("token", null);
    }

    public String getAvatar() {
        return sharedPreferences.getString("avatar", null);
    }

    public String getName() {
        return sharedPreferences.getString("name", null);
    }
}
