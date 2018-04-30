package com.phananh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.phananh.fragment.CommentFragment;
import com.phananh.fragment.ContentFragment;
import com.phananh.fragment.MaterialsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new MaterialsFragment();
                break;
            case 1:
                frag = new ContentFragment();
                break;
            case 2:
                frag = new CommentFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Nguyên Liệu";
                break;
            case 1:
                title = "Hướng dẫn";
                break;
            case 2:
                title = "Đánh giá";
                break;
        }
        return title;
    }
}
