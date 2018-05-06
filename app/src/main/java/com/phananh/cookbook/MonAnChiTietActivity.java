package com.phananh.cookbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phananh.adapter.PagerAdapter;
import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.response.CommentResponse;
import com.phananh.api.results.GetCategoryResults;
import com.phananh.api.results.GetCommentOfFood;
import com.phananh.model.Comment;
import com.phananh.model.Food;
import com.phananh.model.MonAn;
import com.phananh.sqlite.SQLiteDatabaseHandler;
import com.phananh.util.FirebaseHelper;
import com.phananh.util.SharedPreference;
import com.squareup.picasso.Picasso;
import com.sromku.simple.fb.SimpleFacebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonAnChiTietActivity extends AppCompatActivity {
//    SharedPreferences preferences ;
    SQLiteDatabaseHandler dbSQL;
    String token;
    private APIServices mAPIService;
    private ViewPager pager;
    private TabLayout tabLayout;
    DatabaseReference db;
    DatabaseReference foodRef;
    Boolean isExists;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgHinh;
    ImageView imgFavorite;
    List<Comment> dsComment;

    NestedScrollView nestedScrollView;
    private int mPreviousVisibleItem;

    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab4;
    FloatingActionButton fab5;
    FloatingActionMenu fab;
    FloatingActionMenu fab3;
    Food monAn;
    Context context;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    SimpleFacebook simpleFacebook;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_chi_tiet);
        Intent intent=getIntent();
        monAn= (Food) intent.getSerializableExtra("MONAN");
        FacebookSdk.sdkInitialize(getApplicationContext());
        simpleFacebook = SimpleFacebook.getInstance(this);
        db = FirebaseDatabase.getInstance().getReference();
        isExists = false;
        dbSQL = new SQLiteDatabaseHandler(this);

        token = dbSQL.getToken();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        context= getApplicationContext();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
        getComment();



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

        foodRef = db.child("Food");
        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(monAn.getId()).exists()) {
                    isExists = true;
                    floatingButton();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        LoadScrollEvent();
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFavorite.setBackgroundResource(R.drawable.ic_favorite_white_36dp);
            }
        });



    }

    private void LoadScrollEvent() {
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
//                            sharedPreference.addFavorite(context, monAn);
                foodRef.child(monAn.getId()).setValue(monAn);
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

    private void floatingButton() {
        if (isExists) {
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
//                            sharedPreference.removeFavorite(context, monAn);
                    DatabaseReference rm = db.child("Food");
                    rm.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postsnapshot :dataSnapshot.getChildren()) {
                                if (postsnapshot.getKey().equals(monAn.getId().toString())) {
                                    postsnapshot.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
                    .setContentTitle(monAn.getName())
                    .setContentDescription(monAn.getDecriptions() + "")
//                    .setContentUrl(Uri.parse(monAn.getUrl().toString()))
                    .setImageUrl(Uri.parse(monAn.getImage()))
                    .build();
                    // ShareDialog.show(MonAnChiTietActivity.this,shareLinkContent);
                //    shareDialog.show(shareLinkContent);
                    ShareDialog.show(MonAnChiTietActivity.this,shareLinkContent);
                }






    }




    private void addControls() {
        dsComment = new ArrayList<>();
        imgHinh= (ImageView) findViewById(R.id.imgHinh);
        imgFavorite = (ImageView) findViewById(R.id.icFavorite);
        fab1= (FloatingActionButton) findViewById(R.id.fab1);
        fab2= (FloatingActionButton) findViewById(R.id.fab2);
        fab4= (FloatingActionButton) findViewById(R.id.fab4);
        fab5= (FloatingActionButton) findViewById(R.id.fab5);
        fab= (FloatingActionMenu) findViewById(R.id.fab);
        fab3= (FloatingActionMenu) findViewById(R.id.fab3);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(monAn.name);
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));


        Picasso.with(this).load(monAn.image).placeholder(R.drawable.none).error(R.drawable.none).into(imgHinh);


        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public Food getMyData() {
        return monAn;
    }
    public String getToken() {
        return token;
    }

    public List<Comment> getListComment() {
        return dsComment;
    }

    public void getComment() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCommentOfFood(token, monAn.id).enqueue(new Callback<GetCommentOfFood>() {
            @Override
            public void onResponse(Call<GetCommentOfFood> call, Response<GetCommentOfFood> response) {
                if (response.body() != null){
                    Toast.makeText(MonAnChiTietActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    dsComment=response.body().getCommentOfFood();
                }
            }

            @Override
            public void onFailure(Call<GetCommentOfFood> call, Throwable t) {
                Toast.makeText(MonAnChiTietActivity.this, "Get list comment failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
