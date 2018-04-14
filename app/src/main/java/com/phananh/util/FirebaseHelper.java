package com.phananh.util;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phananh.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAOPHUONGDUC on 4/14/2018.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved;
    List<Food> dsFood = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }
    //WRITE IF NOT NULL
    public Boolean save(Food food)
    {
        if(food==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Food").push().setValue(food);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    public List<Food> fetchData(DataSnapshot dataSnapshot)
    {
        dsFood.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Food food=ds.getValue(Food.class);
            dsFood.add(food);
        }
        return dsFood;
    }

    //RETRIEVE
    public List<Food> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

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

        return dsFood;
    }
}
