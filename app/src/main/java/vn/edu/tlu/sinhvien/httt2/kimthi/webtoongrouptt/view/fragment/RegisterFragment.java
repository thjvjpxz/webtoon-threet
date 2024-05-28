package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import static vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.api.ApiService.apiService;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.ResponseBody;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentRegisterBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        String message = "Chưa làm xong nhấn cc";

        binding.btnRegister.setOnClickListener(v -> {
//            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            EditText name = binding.edName;
            EditText email = binding.edEmail;
            EditText password = binding.edPassword;
            EditText confirmPassword = binding.edConfirmPassword;
            if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                RegisterRequest registerRequest = new RegisterRequest(name.getText().toString(), email.getText().toString(), password.getText().toString());
                Call<ResponseBody> call = apiService.registerUser(registerRequest);
                call.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            Intent intent = new Intent(getContext(), SignActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.loginFacebook.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        binding.loginGoogle.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        binding.loginTwitter.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }
}