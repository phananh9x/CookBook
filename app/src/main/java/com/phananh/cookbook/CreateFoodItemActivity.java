package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phananh.adapter.ContentAdapter;
import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.results.GetFoodDetailResults;
import com.phananh.api.results.GetUploadResults;
import com.phananh.model.Content;
import com.phananh.model.Food;
import com.phananh.model.Material;
import com.phananh.model.UploadImage;
import com.phananh.sqlite.SQLiteDatabaseHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFoodItemActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_STEP = 22;

    TextView tvCreateFood;
    ImageView imgFood;
    ImageView ivAdd;
    TextView txtUpload;
    RecyclerView rcv;
    EditText edtTenMonAn, edtMoTa, edtNguyeLieu;

    List<Content> listStep;
    String IMAGE_PATH;
    String IMAGE_FOOD;
    private String token;
    private String idDanhMuc;
    private  APIServices mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_item);

        token = new SQLiteDatabaseHandler(this).getToken();
        idDanhMuc = getIntent().getStringExtra("DANHMUC");

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar3);
        toolbar.setTitle("Tạo công thức món ăn");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        ivAdd = (ImageView) findViewById(R.id.iv_add_step);
        tvCreateFood = (TextView) findViewById(R.id.tvCreateFood);
        imgFood = (ImageView) findViewById(R.id.imgFood);
        txtUpload = (TextView) findViewById(R.id.txtUpload);
        edtTenMonAn = (EditText) findViewById(R.id.edtTenMonAn);
        edtMoTa = (EditText) findViewById(R.id.edtMoTa);
        edtNguyeLieu = (EditText) findViewById(R.id.edtNguyenLieu);

        listStep = new ArrayList<>();

        rcv = (RecyclerView) findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(new ContentAdapter(this, listStep));

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreateStep = new Intent(CreateFoodItemActivity.this, AddStepCreateFoodActivity.class);
                startActivityForResult(intentCreateStep, REQUEST_CODE_ADD_STEP);
            }
        });
        
        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        tvCreateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFood();
            }
        });
    }

    private void createFood() {
        List<Material> list = new ArrayList<>();
        List<String> arr = Arrays.asList(edtNguyeLieu.getText().toString().split("\n"));
        for (String s : arr) {
            if (!TextUtils.isEmpty(s)) {
                List<String> s2 = Arrays.asList(s.split("-"));
                Material material = new Material();
                material.setAmount(s2.get(0));
                material.setMaterial(s2.get(1));
                list.add(material);
            }
        }
        Food food = new Food();
        food.setCategoryId(idDanhMuc);
        food.setContent(listStep);
        food.setName(edtTenMonAn.getText().toString());
        food.setDecriptions(edtMoTa.getText().toString());
        food.setImage(IMAGE_FOOD);
        food.setMaterials(list);
        mAPIService = ApiUtils.getAPIService();
        mAPIService.createFood(token, idDanhMuc, food).enqueue(new Callback<GetFoodDetailResults>() {
            @Override
            public void onResponse(Call<GetFoodDetailResults> call, Response<GetFoodDetailResults> response) {
                Toast.makeText(CreateFoodItemActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<GetFoodDetailResults> call, Throwable t) {
                Toast.makeText(CreateFoodItemActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
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
        progressDialog = new ProgressDialog(CreateFoodItemActivity.this);
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
                IMAGE_FOOD = ApiUtils.BASE_URL+image.getPath();
                Picasso.with(CreateFoodItemActivity.this).load(ApiUtils.BASE_URL+image.getPath()).placeholder(R.drawable.none).error(R.drawable.none).into(imgFood);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetUploadResults> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
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


        if (requestCode == REQUEST_CODE_ADD_STEP && resultCode == RESULT_OK){
            Content content = (Content) data.getSerializableExtra(AddStepCreateFoodActivity.CONTENT_ADD_STEP);
            listStep.add(content);
            rcv.getAdapter().notifyDataSetChanged();
        }
    }
}
