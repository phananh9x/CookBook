package com.phananh.cookbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;


import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.phananh.model.MonAn;
import com.phananh.util.SharedPreference;
import com.squareup.picasso.Picasso;
import com.sromku.simple.fb.SimpleFacebook;

public class MonAnChiTietActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgHinh;
    TextView tenMonAn,moTa,titlenguyenLieu,nguyenLieu,titlehuongDan,huongDan;

    NestedScrollView nestedScrollView;
    private int mPreviousVisibleItem;
    SharedPreference sharedPreference;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab4;
    FloatingActionButton fab5;
    FloatingActionMenu fab;
    FloatingActionMenu fab3;
    MonAn monAn;
    Context context;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    SimpleFacebook simpleFacebook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_chi_tiet);
        Intent intent=getIntent();
        monAn= (MonAn) intent.getSerializableExtra("MONAN");
        FacebookSdk.sdkInitialize(getApplicationContext());
        simpleFacebook = SimpleFacebook.getInstance(this);


        sharedPreference=new SharedPreference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        context= getApplicationContext();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();






    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void addEvents() {

                if (sharedPreference.checkFavoriteItem(context, monAn)) {
                    fab3.hideMenu(false);
                    fab.hideMenu(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab3.showMenu(true);
                            fab3.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(MonAnChiTietActivity.this, R.anim.show_from_bottom));
                            fab3.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(MonAnChiTietActivity.this, R.anim.hide_to_bottom));


                        }
                    }, 300);
                    nestedScrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);

                    nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                            if (scrollY > mPreviousVisibleItem) {
                                fab3.hideMenu(true);
                            } else if (scrollY < mPreviousVisibleItem) {
                                fab3.showMenu(true);
                            }
                            mPreviousVisibleItem = scrollY;

                        }
                    });

                    fab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sharedPreference.removeFavorite(context, monAn);
                            Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();

                            fab3.hideMenu(true);
                            addEvents();
                        }
                    });
                    fab5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareFacebook();
                        }
                    });

                } else {

                    fab3.hideMenu(false);
                    fab.hideMenu(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab.showMenu(true);
                            fab.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(MonAnChiTietActivity.this, R.anim.show_from_bottom));
                            fab.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(MonAnChiTietActivity.this, R.anim.hide_to_bottom));


                        }
                    }, 300);
                    nestedScrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);

                    nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                            if (scrollY > mPreviousVisibleItem) {
                                fab.hideMenu(true);
                            } else if (scrollY < mPreviousVisibleItem) {
                                fab.showMenu(true);
                            }
                            mPreviousVisibleItem = scrollY;

                        }
                    });


                    fab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sharedPreference.addFavorite(context, monAn);
                            Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            fab.hideMenu(true);
                            addEvents();

                        }
                    });
                    fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareFacebook();
                        }
                    });

                }




    }




    private void shareFacebook() {
        callbackManager = CallbackManager.Factory.create();

        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
//                if (ShareDialog.canShow(ShareLinkContent.class)) {
//                    ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
//                            .setContentTitle(monAn.getTenMonAn().toString())
//                            .setContentDescription(monAn.getMoTa() + "")
//
//                            .setContentUrl(Uri.parse(monAn.getUrl().toString()))
//                            .setImageUrl(Uri.parse(monAn.getUrl().toString()))
//                            .build();
//                    // ShareDialog.show(MonAnChiTietActivity.this,shareLinkContent);
//                    shareDialog.show(shareLinkContent);
//                }


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setContentTitle(monAn.getTenMonAn().toString())
                    .setContentDescription(monAn.getMoTa() + "")
//                    .setContentUrl(Uri.parse(monAn.getUrl().toString()))
                    .setImageUrl(Uri.parse(monAn.getUrl().toString()))
                    .build();
                    // ShareDialog.show(MonAnChiTietActivity.this,shareLinkContent);
                //    shareDialog.show(shareLinkContent);
                    ShareDialog.show(MonAnChiTietActivity.this,shareLinkContent);
                }






    }




    private void addControls() {
        imgHinh= (ImageView) findViewById(R.id.imgHinh);
        tenMonAn= (TextView) findViewById(R.id.tenMonAn);
        moTa= (TextView) findViewById(R.id.moTa);
        titlenguyenLieu= (TextView) findViewById(R.id.titlenguyenLieu);
        nguyenLieu= (TextView) findViewById(R.id.nguyenLieu);
        titlehuongDan= (TextView) findViewById(R.id.titlehuongDan);
        huongDan= (TextView) findViewById(R.id.huongDan);
        fab1= (FloatingActionButton) findViewById(R.id.fab1);
        fab2= (FloatingActionButton) findViewById(R.id.fab2);
        fab4= (FloatingActionButton) findViewById(R.id.fab4);
        fab5= (FloatingActionButton) findViewById(R.id.fab5);
        fab= (FloatingActionMenu) findViewById(R.id.fab);
        fab3= (FloatingActionMenu) findViewById(R.id.fab3);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(monAn.getTenMonAn());
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        tenMonAn.setText(monAn.getTenMonAn());
        moTa.setText(monAn.getMoTa());
        titlenguyenLieu.setText("Nguyên Liệu");
        nguyenLieu.setText(monAn.getNgyenLieu());
        titlehuongDan.setText("Hướng Dẫn");
        huongDan.setText(monAn.getCachLam());
        Picasso.with(this).load(monAn.getUrl()).placeholder(R.drawable.none).error(R.drawable.none).into(imgHinh);




    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
