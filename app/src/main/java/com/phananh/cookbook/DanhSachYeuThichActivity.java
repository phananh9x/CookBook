package com.phananh.cookbook;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.phananh.adapter.FoodAdapter;
import com.phananh.model.Food;
import com.phananh.model.MonAn;
import com.phananh.util.RecyclerItemClickListener;
import com.phananh.util.SharedPreference;

public class DanhSachYeuThichActivity extends AppCompatActivity {
    RecyclerView myRecyclerView;
    SharedPreference sharedPreference;

    List<Food> dsMonAnYeuThich;
    FoodAdapter foodAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_yeu_thich);
        context=getApplicationContext();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar5);

        toolbar.setTitle("Danh Sách Món Ăn Yêu Thích");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        addControls();
        addEvents();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsMonAnYeuThich=sharedPreference.getFavorites(context);
        foodAdapter=new FoodAdapter(DanhSachYeuThichActivity.this,dsMonAnYeuThich);
        myRecyclerView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }

    private void addEvents() {
        myRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(DanhSachYeuThichActivity.this, MonAnChiTietActivity.class);
                        intent.putExtra("MONAN", dsMonAnYeuThich.get(position));
                        if (checkInternet()) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(DanhSachYeuThichActivity.this, "Vui lòng kiểm tra kết nối Internet của bạn!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
        );

        sharedPreference=new SharedPreference();
        dsMonAnYeuThich=sharedPreference.getFavorites(context);
        if(dsMonAnYeuThich!=null) {
            foodAdapter = new FoodAdapter(DanhSachYeuThichActivity.this, dsMonAnYeuThich);
            myRecyclerView.setAdapter(foodAdapter);
            foodAdapter.notifyDataSetChanged();
        }
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
        myRecyclerView= (RecyclerView) findViewById(R.id.dsYeuThich);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);


    }

}
