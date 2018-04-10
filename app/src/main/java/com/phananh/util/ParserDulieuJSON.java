package com.phananh.util;

import android.util.Log;

import com.phananh.model.DanhMuc;
import com.phananh.model.MonAn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Minamino on 1/16/2016.
 */
public class ParserDulieuJSON {
    ArrayList<MonAn> dsMonAn;
    String dulieu;
    DanhMuc danhMuc;



    public ParserDulieuJSON(String dulieu,DanhMuc danhMuc) {

        this.dulieu = dulieu;
        this.danhMuc=danhMuc;
    }


    public ArrayList<MonAn> LayDuLieu(){
        dsMonAn=new ArrayList<>();

        try {
            JSONObject object=new JSONObject(dulieu);
            JSONArray array=object.getJSONArray(danhMuc.getIdDanhMuc().toString());
            for (int i=0;i<array.length();i++)
            {
                JSONObject resultsObject=array.getJSONObject(i);
                MonAn monAn=new MonAn(
                        resultsObject.getString("ID"),
                        resultsObject.getString("TenMonAn"),
                        resultsObject.getString("MoTa"),
                        resultsObject.getString("Url"),
                        resultsObject.getString("CachLam"),
                        resultsObject.getString("NguyenLieu")
                        );
                dsMonAn.add(monAn);
            }

            /* JSONObject object=new JSONObject(dulieu);
           JSONArray results=object.getJSONArray("results");
            for (int i=0;i<results.length();i++)
            {
                JSONObject resultsObject=results.getJSONObject(i);

                if(resultsObject.getString("ID").equalsIgnoreCase(danhMuc.getIdDanhMuc()))
                {
                    MonAn monAn=new MonAn(
                            resultsObject.getString("TenMonAn"),
                            resultsObject.getString("ID"),
                            resultsObject.getString("MoTa"),
                            resultsObject.getString("Url")
                    );
                    dsMonAn.add(monAn);

                }

            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("dsmonan", "LayDuLieu: "+dsMonAn.size());

        return dsMonAn;
    }
}
