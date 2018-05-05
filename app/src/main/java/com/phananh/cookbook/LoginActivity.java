package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.results.LoginResults;
import com.phananh.model.LogIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText userName, passWord;
    private ProgressDialog progressDialog;

    private APIServices mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        addEvents();
    }

    private void anhxa() {
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                //  progressDialog.setTitle("Vui Lòng Chờ !");
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.show();
                login(userName.getText().toString(), passWord.getText().toString());
            }
        });
        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        findViewById(R.id.textView7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void login(String username, String pass) {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.login(new LogIn(username, pass)).enqueue(new Callback<LoginResults>() {
            @Override
            public void onResponse(Call<LoginResults> call, Response<LoginResults> response) {
                if (response.body().isSuccess()){
                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("", MODE_PRIVATE).edit();
                    editor.putString("token", response.body().getLoginResponse().getToken());
                    editor.putString("username", response.body().getLoginResponse().getEmail());
                    editor.putString("id", response.body().getLoginResponse().getId());
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else  {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<LoginResults> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvents() {

    }

//    private class LoginAsyctask extends AsyncTask<String, LogIn, String> {
//        StringBuffer dulieu;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            //  progressDialog.setTitle("Vui Lòng Chờ !");
//            progressDialog.setMessage("Loading...");
//            progressDialog.setIndeterminate(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            progressDialog.dismiss();
//        }
//
//        @Override
//        protected void onProgressUpdate(LogIn... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                URL url = new URL(params[0]);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.connect();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(reader);
//
//                String dong = "";
//
//                dulieu = new StringBuffer();
//
//                while ((dong = bufferedReader.readLine()) != null) {
//                    dulieu.append(dong);
//                }
//
//                bufferedReader.close();
//                reader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.d("Dữ liệu", "doInBackground: " + dulieu.toString());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return dulieu.toString();
//        }

//    }
}
