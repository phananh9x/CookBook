package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.response.UserProfileResponse;
import com.phananh.api.results.GetUploadResults;
import com.phananh.api.results.UserProfilesResults;
import com.phananh.model.UploadImage;
import com.phananh.model.UserProfiles;
import com.phananh.sqlite.SQLiteDatabaseHandler;
import com.phananh.util.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilesActivity extends AppCompatActivity {
    private APIServices mAPIService;
    SQLiteDatabaseHandler db;
    String token ="";
    String id ="";
    EditText address, phomeNumber,fullName;
    TextView email,name;
    CheckBox gender;
    ImageView img, imgProfile;
    String IMAGE_PATH;
    String IMAGE_UPDATE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiles);
        db = new SQLiteDatabaseHandler(UserProfilesActivity.this);
        token = db.getToken();
        IMAGE_UPDATE = "";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);



//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        img = (ImageView) findViewById(R.id.imgProfiles);
        address = (EditText) findViewById(R.id.occupation);
        phomeNumber = (EditText) findViewById(R.id.mobileNumber);
        fullName = (EditText) findViewById(R.id.tv_fullName_info);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.tv_email_info);
        gender = (CheckBox) findViewById(R.id.gender);
        imgProfile = (ImageView) findViewById(R.id.imgProfiles);
        loadProfiles();

        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfiles();
            }
        });
        findViewById(R.id.btn_update_profiles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfiles();
            }
        });
        findViewById(R.id.imgProfiles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImageProfile();
            }
        });
    }

    private void updateImageProfile() {
        Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
        fintent.setType("image/*");
        try {
            startActivityForResult(fintent, 100);
        } catch (ActivityNotFoundException e) {

        }
    }

    private void updateUserProfiles() {
        UserProfiles userProfiles = new UserProfiles(fullName.getText().toString(),gender.isChecked(),phomeNumber.getText().toString(),address.getText().toString());
        if (!IMAGE_UPDATE.isEmpty()) {
            userProfiles.setImage(IMAGE_UPDATE);
        }
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getUserProfiles(token, userProfiles).enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                Toast.makeText(UserProfilesActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Toast.makeText(UserProfilesActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void loadProfiles(){
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getUserProfiles(token).enqueue(new Callback<UserProfilesResults>() {
            @Override
            public void onResponse(Call<UserProfilesResults> call, Response<UserProfilesResults> response) {
                UserProfiles userProfiles;
                Toast.makeText(UserProfilesActivity.this, "Thông tin người dùng", Toast.LENGTH_SHORT).show();
                name.setText(response.body().getUserProfiles().get(0).getFullName());
                address.setText(response.body().getUserProfiles().get(0).getAddress());
                phomeNumber.setText(response.body().getUserProfiles().get(0).getPhone());
                fullName.setText(response.body().getUserProfiles().get(0).getFullName());
                gender.setChecked(response.body().getUserProfiles().get(0).isGender());
                email.setText(response.body().getUserProfiles().get(0).getEmail());
                Picasso.with(UserProfilesActivity.this).load(response.body().getUserProfiles().get(0).getImage()).placeholder(R.drawable.man).error(R.drawable.man).into(img);

            }

            @Override
            public void onFailure(Call<UserProfilesResults> call, Throwable t) {
                Toast.makeText(UserProfilesActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

    }


    public void UploadImage(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(UserProfilesActivity.this);
        progressDialog.setMessage("Uploading!!");
        progressDialog.show();

        APIServices service = ApiUtils.getAPIService();

        File file = new File(IMAGE_PATH);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<GetUploadResults> call = service.uploadImage(token,body);
        call.enqueue(new Callback<GetUploadResults>() {

            @Override
            public void onResponse(Call<GetUploadResults> call, Response<GetUploadResults> response) {
                UploadImage image = response.body().getUploadImage();
                Picasso.with(UserProfilesActivity.this).load(ApiUtils.BASE_URL+image.getPath()).placeholder(R.drawable.none).error(R.drawable.none).into(imgProfile);
                progressDialog.dismiss();
                IMAGE_UPDATE = ApiUtils.BASE_URL+image.getPath();
            }

            @Override
            public void onFailure(Call<GetUploadResults> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
//                    imgFood.setImageURI(data.getData());
                    Uri selectedImage = data.getData();
                    String wholeID = DocumentsContract.getDocumentId(selectedImage);

                    // Split at colon, use second item in the array
                    String id = wholeID.split(":")[1];

                    String[] column = { MediaStore.Images.Media.DATA };

                    // where id is equal to
                    String sel = MediaStore.Images.Media._ID + "=?";

                    Cursor cursor = getContentResolver().
                            query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    column, sel, new String[]{ id }, null);


                    int columnIndex = cursor.getColumnIndex(column[0]);

                    if (cursor.moveToFirst()) {
                        IMAGE_PATH = cursor.getString(columnIndex);
                    }
                    cursor.close();
                    UploadImage();
                }
        }
    }

}
