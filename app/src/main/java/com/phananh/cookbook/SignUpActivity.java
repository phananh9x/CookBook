package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.response.LoginResponse;
import com.phananh.api.response.SignUpResponse;
import com.phananh.api.results.SignUpResults;
import com.phananh.model.LogIn;
import com.phananh.model.SignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName,tvEmail,passWord,rePassword;
    private ProgressDialog progressDialog;

    private APIServices mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
    }

    private void anhxa() {
        fullName = (EditText) findViewById(R.id.name_register);
        tvEmail = (EditText) findViewById(R.id.email_register);
        passWord = (EditText) findViewById(R.id.pass_regiser);
        rePassword = (EditText) findViewById(R.id.re_pass_register);
        findViewById(R.id.btn_signup_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(passWord.getText().toString()==rePassword.getText().toString()){
                    progressDialog = new ProgressDialog(SignUpActivity.this);
                    //  progressDialog.setTitle("Vui Lòng Chờ !");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setIndeterminate(false);
                    progressDialog.show();
                    signup(fullName.getText().toString(), passWord.getText().toString(),tvEmail.getText().toString());
//                }else {
//                    Toast.makeText(SignUpActivity.this, "re-pass wrong!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    };

    private void signup(String name, String pass, String mail) {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.SignUp(new SignUp(name, pass,mail)).enqueue(new Callback<SignUpResults>() {
            @Override
            public void onResponse(Call<SignUpResults> call, Response<SignUpResults> response) {
                if (response.body().isSuccess()){
                    Toast.makeText(SignUpActivity.this, response.body().getSignUpResults().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SignUpResults> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "SignUp falsed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}



