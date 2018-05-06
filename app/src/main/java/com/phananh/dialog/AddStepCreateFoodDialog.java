package com.phananh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.phananh.cookbook.R;

import java.util.List;

/**
 * Created by thanh on 06/05/2018.
 */

public class AddStepCreateFoodDialog extends Dialog {

    TextInputLayout edtStep;
    ImageView ivAddImage;
    RecyclerView rcvImage;

    private List<String> images;

    public AddStepCreateFoodDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();
        assert  window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(getContext(), R.color.light_green_actionbar)
            );
        }

        setContentView(R.layout.activity_add_step_create_food);

        initData();
        initListener();
    }

    private void initData() {
        edtStep = (TextInputLayout) findViewById(R.id.edt_step_create_food);
        ivAddImage = (ImageView) findViewById(R.id.iv_add_step);
        rcvImage = (RecyclerView) findViewById(R.id.rcv_image_step_create_food);

    }

    private void initListener() {

    }
}
