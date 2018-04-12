package com.phananh.cookbook;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.phananh.adapter.FoodAdapter;
import com.phananh.api.ApiUtils;
import com.phananh.api.results.GetFoodsResults;
import com.phananh.model.Category;
import com.phananh.model.Food;
import com.phananh.util.RecyclerItemClickListener;
import com.phananh.util.Storage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachMonAnActivity extends AppCompatActivity {

    List<Food> dsMonAn;

    FoodAdapter foodAdapter;
    RecyclerView myRecyclerView;
    ProgressDialog progressDialog;
    Category danhMuc=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_mon_an_reclerview);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar1);
        toolbar.setTitle("Danh Sách Món Ăn");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        addControls();
        addEvents();
        Intent intent = getIntent();
        danhMuc = (Category) intent.getSerializableExtra("DanhMuc");

        loadFood(danhMuc.getId());

        /*if(checkInternet())
        {
            new LoadAsyctask().execute(configuration.SERVER_URL);
        }
        else {
        Toast.makeText(DanhSachMonAnActivity.this,"Vui lòng kiểm tra kết nối Internet của bạn!!",Toast.LENGTH_SHORT).show();}
*/


    }

    private void loadFood(String idDanhMuc) {
        progressDialog= new ProgressDialog(DanhSachMonAnActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        ApiUtils.getAPIService().getFood(Storage.getToken(this), idDanhMuc).enqueue(new Callback<GetFoodsResults>() {
            @Override
            public void onResponse(Call<GetFoodsResults> call, Response<GetFoodsResults> response) {
                dsMonAn = response.body().getFood();
                loadFoodSuccess();
                Toast.makeText(DanhSachMonAnActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GetFoodsResults> call, Throwable t) {
                Toast.makeText(DanhSachMonAnActivity.this, "Get food failed!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

    private void loadFoodSuccess() {
        foodAdapter=new FoodAdapter(DanhSachMonAnActivity.this,dsMonAn);
        myRecyclerView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
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
        myRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent=new Intent(DanhSachMonAnActivity.this,MonAnChiTietActivity.class);
                        intent.putExtra("MONAN", dsMonAn.get(position));
                        if(checkInternet()) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(DanhSachMonAnActivity.this,"Vui lòng kiểm tra kết nối Internet của bạn!!",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
        );
    }

    private boolean checkInternet() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            return true;
        } else
            return false;
    }

    private void addControls() {
        myRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);

        dsMonAn=new ArrayList<>();

    }


/*
    private class LoadAsyctask extends AsyncTask<String,Void,String>
    {
        StringBuffer dulieu;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(DanhSachMonAnActivity.this);
          //  progressDialog.setTitle("Vui Lòng Chờ !");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ParserDulieuJSON parser=new ParserDulieuJSON(s,danhMuc);
            dsMonAn=parser.LayDuLieu();
            foodAdapter=new FoodAdapter(DanhSachMonAnActivity.this,dsMonAn);
            myRecyclerView.setAdapter(foodAdapter);
            foodAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url=new URL(params[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(reader);

                String dong="";

                 dulieu=new StringBuffer();

                while ((dong=bufferedReader.readLine())!=null)
                {
                    dulieu.append(dong);
                }
                
                bufferedReader.close();
                reader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("Dữ liệu", "doInBackground: "+ dulieu.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dulieu.toString();
        }

    }*/
}
