package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import okhttp3.ResponseBody;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.UserResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }


    public LiveData<UserResponse> fetchUserData() {
        return userRepository.fetchUserData();
    }

    public LiveData<ResponseBody> logOut() {
        return userRepository.LogOut();
    }
}
