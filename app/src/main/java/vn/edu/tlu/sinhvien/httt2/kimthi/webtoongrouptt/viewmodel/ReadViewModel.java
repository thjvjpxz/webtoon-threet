package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.ReadRepository;

public class ReadViewModel extends ViewModel {
    private ReadRepository readRepository;
    private LiveData<ReadComicResponse> responseRead;
    private MutableLiveData<Boolean> isLoaded;
    public ReadViewModel(String chapterId, Context context){
        isLoaded = new MutableLiveData<>();
        readRepository = ReadRepository.getInstance(context);
        isLoaded.setValue(false);
        responseRead = readRepository.fetchChapterData(chapterId, () -> isLoaded.setValue(true));
    }
    public LiveData<ReadComicResponse> fetchChapterData() {
        return responseRead;
    }

    public MutableLiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }
}
