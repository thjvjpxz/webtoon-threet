package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;

import okhttp3.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivitySecurityBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.UserDialogFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.UserViewModel;

public class SecurityActivity extends AppCompatActivity {
    ActivitySecurityBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    UserViewModel userViewModel;
    SharedPrefManager sharedPrefManager;
    Uri fileUri;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecurityBinding.inflate(getLayoutInflater());
        userViewModel = new UserViewModel(getApplication());
        sharedPrefManager = new SharedPrefManager(this);
        setContentView(binding.getRoot());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        fileUri = result.getData().getData();
                        assert fileUri != null;
                        Glide.with(this)
                                .load(fileUri)
                                .circleCrop()
                                .into(binding.ivAvatar);
                    }
                }
        );

        observe();
        processEditText();
        processBack();
        processSave();
        requestReadExternalStoragePermission();
    }
    private void processEditText() {
        binding.ivAvatar.setOnClickListener(v-> {
            openImagePicker();
        });

        binding.ivEditEmail.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            UserDialogFragment dialog = new UserDialogFragment("EmailDialog");
            dialog.setOnConfirmListener(new UserDialogFragment.OnConfirmListener() {
                @Override
                public void onConfirm(String email) {
                    binding.tvEmail.setText(email);
                }
            });
            dialog.show(fragmentManager, "EmailDialog");
        });

        binding.ivEditName.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            UserDialogFragment dialog = new UserDialogFragment("NicknameDialog");
            dialog.setOnConfirmListener(new UserDialogFragment.OnConfirmListener() {
                @Override
                public void onConfirm(String nickname) {
                    binding.tvName.setText(nickname);
                }
            });
            dialog.show(fragmentManager, "NicknameDialog");
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void observe() {
        userViewModel.fetchUserData().observe(this, user -> {
            if (user != null) {
                binding.tvName.setText(user.getUser().getName());
                binding.tvEmail.setText(user.getUser().getEmail());
                Glide.with(this)
                        .load(user.getUser().getAvatar()).circleCrop()
                        .into(binding.ivAvatar);
            }
        });
    }

    private void processBack() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void processSave() {
        binding.btnUpdate.setOnClickListener(v -> {
            uploadFileToServer();
        });
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void uploadFileToServer () {
        try {
            if (fileUri == null) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
                return;
            }

            String path = getPathFromUri(this, fileUri);
            if (path == null) {
                Toast.makeText(this, "Unable to get file path", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "uploadFileToServer: " + fileUri.getPath());
                return;
            }

            File file = new File(path);

            Log.d("TAG", "uploadFileToServer: " + file.getName());

            RequestBody fileBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar", file.getName(), fileBody);

            userViewModel.updateUser(binding.tvName.getText().toString(), binding.tvEmail.getText().toString(), 1, filePart).observe(this, updateResponse -> {
                if (updateResponse != null) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void uploadFileToServer() {
//        try {
//            String token = sharedPrefManager.getToken();
//            Log.d("TOKEN SHARE", "TOKEN: " + token);
//            OkHttpClient client = new OkHttpClient();
//
//            RequestBody name = RequestBody.create(binding.tvName.getText().toString(), MediaType.parse("multipart/form-data"));
//            RequestBody email = RequestBody.create(binding.tvEmail.getText().toString(), MediaType.parse("multipart/form-data"));
//            RequestBody roadId = RequestBody.create("1", MediaType.parse("multipart/form-data"));
//
//            MultipartBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("name", name.toString())
//                    .addFormDataPart("email", email.toString())
//                    .addFormDataPart("road_id", roadId.toString())
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url("https://truyenhdc.com/api/user/update")
//                    .post(requestBody)
//                    .addHeader("Authorization", token)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                    runOnUiThread(() -> Toast.makeText(SecurityActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        Log.d("TAG", "PARAM: " + call.request().body());
//                        runOnUiThread(() -> Toast.makeText(SecurityActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show());
//                    } else {
//                        runOnUiThread(() -> Toast.makeText(SecurityActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show());
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private String getPathFromUri(Context context, Uri uri) {
        Log.d("TAG", "URI: " + uri.toString());
        String wholeID = DocumentsContract.getDocumentId(uri);
        Log.d("TAG", "Whole ID: " + wholeID);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];
        Log.d("TAG", "ID: " + id);

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        if (cursor == null) {
            Log.d("TAG", "Cursor is null");
            return null;
        }

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        } else {
            Log.d("TAG", "Cursor is empty");
        }
        cursor.close();
        Log.d("TAG", "File Path: " + filePath);
        return filePath;
    }

    private void checkMediaStoreData() {
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                int id = cursor.getInt(idColumn);
                Log.d("TAG", "ID: " + id);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d("TAG", "Cursor is null or empty");
        }
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            checkMediaStoreData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkMediaStoreData();
            } else {
                Log.d("TAG", "Read External Storage Permission Denied");
            }
        }
    }
}