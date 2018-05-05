package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.phananh.adapter.CustomBaseAdapter;
import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.results.GetCategoryResults;
import com.phananh.model.Category;
import com.phananh.model.DanhMuc;
import com.phananh.util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    GridView gridView;
    ProgressDialog progressDialog;

    String regId;

    List<Category> dsDanhMuc;
    Toolbar toolbar;

    private APIServices mAPIService;

    AsyncTask<Void, Void, Void> gcmRegisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();

//        new LoadAsyctask().execute();
        loadCategory();

    }

    private void loadCategory() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        SharedPreferences preferences = this.getSharedPreferences("", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCategories(token).enqueue(new Callback<GetCategoryResults>() {
            @Override
            public void onResponse(Call<GetCategoryResults> call, Response<GetCategoryResults> response) {
                if (response.body() != null){
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    dsDanhMuc=response.body().getCategory();
                    getCategorySuccess(response.body().getCategory());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetCategoryResults> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // trả về listCategory
    private void getCategorySuccess(List<Category> response) {
        CustomBaseAdapter adapter = new CustomBaseAdapter(MainActivity.this, R.layout.custom_layout_gridview,response);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
        progressDialog.dismiss();
    }
    //Chỉ cần đăng ký 1 lần, các lần khác không cần--> tự động nhận biết rồi

    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DanhSachMonAnActivity.class);
                intent.putExtra("DanhMuc", dsDanhMuc.get(position));
                if (checkInternet()) {
                    startActivity(intent);
                } else {

                    Toast.makeText(MainActivity.this, "Vui lòng kiểm tra kết nối Internet của bạn!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId())
                {
                    case R.id.danhMuc:
                        break;
                    case R.id.danhSachYeuThich:
                        Intent intent=new Intent(MainActivity.this,DanhSachYeuThichActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.About:
                        Intent intent1=new Intent(MainActivity.this,ThongTinUngDungActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.updateProfiles:
                        Intent intentProfiles = new Intent(MainActivity.this,UserProfilesActivity.class);
                        startActivity(intentProfiles);

                    case R.id.checkUpdate:
                        Toast.makeText(MainActivity.this,"Bạn đã cập nhật phiên bản mới nhất ! Vui vòng quay lại sau.",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.moreApp:
                        Toast.makeText(MainActivity.this,"Tính năng này đang hoàn thành ! Vui vòng quay lại sau.",Toast.LENGTH_SHORT).show();
                        break;


                }
                return true;
            }
        });
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

        gridView = (GridView) findViewById(R.id.gvHinhAnh);

        toolbar = (Toolbar) findViewById(R.id.toolBar3);
        toolbar.setTitle("Danh Mục");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        xuLyDangKyGCMServer();



    }

//    private ArrayList<DanhMuc> getData() {
//        int[] danhSachHinhAnh = {R.drawable.thitheo, R.drawable.thitvit,
//                R.drawable.raucu, R.drawable.thitbo, R.drawable.dauphu,
//                R.drawable.thitga, R.drawable.haisankhac, R.drawable.trung};
//        String[] kytu = {"Thịt Heo", "Thịt Vịt", "Rau Củ", "Thịt Bò", "Đậu phụ", "Thịt Gà", "Hải sản", "Trứng"};
//        String[] id = {"Heo", "Vit", "Rau", "Bo", "Dau", "Ga", "HS", "Trung"};
//        dsDanhMuc = new ArrayList<>();
//        for (int i = 0; i < danhSachHinhAnh.length; i++) {
//            DanhMuc danhMuc = new DanhMuc(id[i], danhSachHinhAnh[i], kytu[i]);
//            dsDanhMuc.add(danhMuc);
//        }
//        return dsDanhMuc;
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }


    private class LoadAsyctask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           /* super.onPostExecute(aVoid);
            CustomBaseAdapter adapter = new CustomBaseAdapter(MainActivity.this, R.layout.custom_layout_gridview, getData());
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);
            progressDialog.dismiss();*/
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


    }

}
