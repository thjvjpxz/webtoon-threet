package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import okhttp3.ResponseBody;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ForgotResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.SignRepository;

public class SignViewModel extends AndroidViewModel {
    private SignRepository signRepository;

    public SignViewModel(@NonNull Application application) {
        super(application);
        signRepository = SignRepository.getInstance(application);
    }

    public LiveData<LoginResponse> loginUser(String username, String password) {
        return signRepository.loginUser(new LoginRequest(username, password));
    }

    public LiveData<GoogleResponse> loginGoogle(GoogleRequest request) {
        return signRepository.loginGoogle(request);
    }

    public LiveData<ResponseBody> forgotPassword(ForgotRequest request) {
        return signRepository.forgotPass(request);
    }

    public LiveData<ResponseBody> registerUser(RegisterRequest request) {
        return signRepository.registerUser(request);
    }
}
