package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.phananh.adapter.CreateFoodAdapter;
import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.results.GetUploadResults;
import com.phananh.model.Category;
import com.phananh.model.Food;
import com.phananh.model.UploadImage;
import com.phananh.sqlite.SQLiteDatabaseHandler;
import com.phananh.util.ReadPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateFoodActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    CreateFoodAdapter mRcvAdapter;
    List<String> data;
    ImageView imgFood;
    String IMAGE_PATH;
    Category danhMuc;
    Food food;

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InBoYW5hbmgxMjNxcXFAZ21haWwuY29tIiwiZnVsbE5hbWUiOiJOaGF0IEFuaCIsIl9pZCI6IjVhYWE0MTU1YjM4Yjg3MjBjODAxNWM1MCIsInBob25lIjoiMDk4OTg4ODg4OCIsImFkZHJlc3MiOiI5NyBNYW4gdGhpZW4gcXVhbiA5IHRwIEhvIENoaSBNaW5oIiwiZ2VuZGVyIjp0cnVlLCJiaXJ0aGRheSI6IjE5OTYtMTItMTJUMDA6MDA6MDAuMDAwWiIsImlhdCI6MTUyNTI3NDUxOH0.SWxNngIwIhIOlb-XncrwQwfUrLOT0ijc8zFqoa3W2y4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar3);
        toolbar.setTitle("Tạo công thức món ăn");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        Intent intent = getIntent();
        danhMuc = (Category) intent.getSerializableExtra("DANHMUC");
        Toast.makeText(this, danhMuc.getName(), Toast.LENGTH_SHORT).show();
        imgFood = (ImageView) findViewById(R.id.imgFood);

        findViewById(R.id.txtUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
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

    public void chooseImage(){
        Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
        fintent.setType("image/*");
        try {
            startActivityForResult(fintent, 100);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void UploadImage(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CreateFoodActivity.this);
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
                Picasso.with(CreateFoodActivity.this).load(ApiUtils.BASE_URL+image.getPath()).placeholder(R.drawable.none).error(R.drawable.none).into(imgFood);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetUploadResults> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

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

