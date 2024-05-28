package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentForgotBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ForgotResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotFragment extends Fragment {
    private FragmentForgotBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotFragment newInstance(String param1, String param2) {
        ForgotFragment fragment = new ForgotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotBinding.inflate(inflater, container, false);
        binding.btnLoginNow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignActivity.class);
            startActivity(intent);
        });
        binding.btnForgot.setOnClickListener(v -> {
            ForgotRequest forgotRequest = new ForgotRequest(binding.edtEmail.getText().toString());
            ApiService apiService = ApiClient.getRetrofit(getContext()).create(ApiService.class);
            Call<ResponseBody> call = apiService.forgotPassword(forgotRequest);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String responseBodyString = response.body().toString();
                        new AlertDialog.Builder(getContext())
                                .setTitle("Thành công")
                                .setMessage("Email đã được gửi. Vui lòng kiểm tra Email của bạn!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        binding.edtEmail.setText("");
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Lỗi")
                                .setMessage("Email không tồn tại!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        });
        return binding.getRoot();
    }
}