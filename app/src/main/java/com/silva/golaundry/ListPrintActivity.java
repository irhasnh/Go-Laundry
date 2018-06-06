package com.silva.golaundry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Risky-PC on 02/12/2017.
 */

public class ListPrintActivity extends AppCompatActivity {

    private ListView list;
    private  String[] nama_file, operator;
    Get get = new Get();
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listprint);

        user=getIntent().getStringExtra("id_print");

        list = (ListView) findViewById(R.id.list);

        getProsesList();
    }

    private void getProsesList(){

        String link=get.getUrl() + "get_status_laundry.php";;
        String data="";
        try {
            data="?id_transaksi=" + URLEncoder.encode(user, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("linkk",link+data);
        JsonArrayRequest jArr = new JsonArrayRequest(link+data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("respontttt", response.toString());

                nama_file = new String[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        nama_file[i]=(obj.getString("tanggal")+" pesanan "+obj.getString("status")+"\nOleh "+obj.getString("operator"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errorjsonn", e.toString());
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(ListPrintActivity.this, android.R.layout.simple_list_item_1, nama_file);
                list.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jArr);
    }


}
