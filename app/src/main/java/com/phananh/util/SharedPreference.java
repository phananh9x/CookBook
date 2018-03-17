package com.phananh.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.phananh.model.MonAn;

/**
 * Created by Phan Anh on 3/26/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Food_Favorite";

    public SharedPreference() {
        super();
    }


    public void saveFavorites(Context context, List<MonAn> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, MonAn product) {
        List<MonAn> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<MonAn>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, MonAn product) {
        ArrayList<MonAn> favorites = getFavorites(context);

        if (favorites != null) {
            for(MonAn food:favorites)
            {
                if(food.getTenMonAn().equals(product.getTenMonAn()))
                {
                    favorites.remove(food);
                    saveFavorites(context, favorites);
                    return;
                }
            }

        }
    }
    public boolean checkFavoriteItem(Context context,MonAn monAn) {
        boolean check = false;
        List<MonAn> favorites = getFavorites(context);
        if (favorites != null) {
            for (MonAn food : favorites) {
                if (food.getTenMonAn().equals(monAn.getTenMonAn())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public ArrayList<MonAn> getFavorites(Context context) {
        SharedPreferences settings;
        List<MonAn> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MonAn[] favoriteItems = gson.fromJson(jsonFavorites,
                    MonAn[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MonAn>(favorites);
        } else
            return null;

        return (ArrayList<MonAn>) favorites;
    }
}
