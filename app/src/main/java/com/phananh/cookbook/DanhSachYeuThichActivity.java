package com.phananh.cookbook;

import android.app.ProgressDialog;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phananh.adapter.FoodAdapter;
import com.phananh.model.Food;
import com.phananh.util.FirebaseHelper;
import com.phananh.util.RecyclerItemClickListener;

public class DanhSachYeuThichActivity extends AppCompatActivity {
    DatabaseReference db;
    FirebaseHelper helper;
    RecyclerView myRecyclerView;
    ProgressDialog progressDialog;
    List<Food> dsMonAnYeuThich;
    FoodAdapter foodAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog= new ProgressDialog(DanhSachYeuThichActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        setContentView(R.layout.activity_danh_sach_yeu_thich);
        context=getApplicationContext();
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        dsMonAnYeuThich = new ArrayList<>();
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
        dsMonAnYeuThich = helper.retrieve();
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
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dsMonAnYeuThich = helper.fetchData(dataSnapshot);
                if(dsMonAnYeuThich!=null) {
                    foodAdapter = new FoodAdapter(DanhSachYeuThichActivity.this, dsMonAnYeuThich);
                    myRecyclerView.setAdapter(foodAdapter);
                    foodAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dsMonAnYeuThich = helper.fetchData(dataSnapshot);
                foodAdapter = new FoodAdapter(DanhSachYeuThichActivity.this, dsMonAnYeuThich);
                myRecyclerView.setAdapter(foodAdapter);
                foodAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        myRecyclerView= (RecyclerView) findViewById(R.id.dsYeuThich);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);


    }

}
