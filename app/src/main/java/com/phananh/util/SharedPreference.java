package com.phananh.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.phananh.model.Food;
import com.phananh.model.Food;

/**
 * Created by Phan Anh on 3/26/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Food_Favorite";

    public SharedPreference() {
        super();
    }


    public void saveFavorites(Context context, List<Food> favorites) {
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

    public void addFavorite(Context context, Food product) {
        List<Food> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Food>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Food product) {
        List<Food> favorites = getFavorites(context);

        if (favorites != null) {
            for(Food food:favorites)
            {
                if(food.name.equals(product.name))
                {
                    favorites.remove(food);
                    saveFavorites(context, favorites);
                    return;
                }
            }

        }
    }
    public boolean checkFavoriteItem(Context context,Food Food) {
        boolean check = false;
        List<Food> favorites = getFavorites(context);
        if (favorites != null) {
            for (Food food : favorites) {
                if (food.name.equals(Food.name)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public List<Food> getFavorites(Context context) {
        SharedPreferences settings;
        List<Food> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Food[] favoriteItems = gson.fromJson(jsonFavorites,
                    Food[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return  favorites;
    }
}
