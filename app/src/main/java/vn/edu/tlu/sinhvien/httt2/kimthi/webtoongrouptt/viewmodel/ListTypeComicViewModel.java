package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ListTypeComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.ComicByCategoryRepository;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.ListTypeComicRepository;

public class ListTypeComicViewModel extends ViewModel {
    private ListTypeComicRepository listTypeComicRepository;
    private MutableLiveData<ListTypeComicResponse> responseListTypeComic;
    private MutableLiveData<Boolean> isLoaded;
    private int page;
    private String slug;
    public ListTypeComicViewModel(String slug, int page){
        this.page = page;
        this.slug = slug;
        this.listTypeComicRepository = ListTypeComicRepository.getInstance();
        this.isLoaded = new MutableLiveData<>(false);
        this.responseListTypeComic = new MutableLiveData<>();
        fetchData(slug, page);
    }

    public LiveData<ListTypeComicResponse> getResponse() {
        return responseListTypeComic;
    }

    public LiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }

    public void fetchData(String slug, int page) {
        isLoaded.setValue(false);
        listTypeComicRepository.fetchData(slug, page, () -> isLoaded.setValue(true))
                .observeForever(new Observer<ListTypeComicResponse>() {
                    @Override
                    public void onChanged(ListTypeComicResponse response) {
                        if (response != null) {
                            if (responseListTypeComic.getValue() == null) {
                                responseListTypeComic.setValue(response);
                            } else {
                                ListTypeComicResponse currentResponse = responseListTypeComic.getValue();
                                List<Comic> currentData = currentResponse.getComics().getData();
                                currentData.clear();
                                for (Comic newComic : response.getComics().getData()) {
                                    if (!currentData.contains(newComic)) {
                                        currentData.add(newComic);
                                    }
                                }
                                responseListTypeComic.setValue(currentResponse);
                            }
                        }
                        isLoaded.setValue(true);
                    }
                });
    }

    public void loadMoreData() {
        page++;
        fetchData(slug, page);
    }
}
