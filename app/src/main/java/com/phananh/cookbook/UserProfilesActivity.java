package com.phananh.cookbook;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.phananh.api.results.UserProfilesResults;
import com.phananh.model.UserProfiles;
import com.phananh.sqlite.SQLiteDatabaseHandler;
import com.phananh.util.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiles);
        db = new SQLiteDatabaseHandler(UserProfilesActivity.this);
        token = db.getToken();
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
    }

    private void updateUserProfiles() {
        UserProfiles userProfiles = new UserProfiles(fullName.getText().toString(),gender.isChecked(),phomeNumber.getText().toString(),address.getText().toString());
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




}
