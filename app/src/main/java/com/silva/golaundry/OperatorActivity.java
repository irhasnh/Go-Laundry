package com.silva.golaundry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OperatorActivity extends AppCompatActivity {
    private Context context;
    String aa;
    private String user;
    String idpos;

    private TextView mTextMessage;
    private LinearLayout layout1, layout2, layout3;
    private ListView list;
    private  EditText user_txt,pass,nama;

    private  String[] nama_file, id;

    AlertDialog.Builder dialog;

    Get get = new Get();

    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
//                    layout3.setVisibility(View.GONE);
//                    getProsesList();
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    layout2.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.GONE);
//                    layout3.setVisibility(View.GONE);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
//                case R.id.navigation_notifications:
//                    layout3.setVisibility(View.VISIBLE);
//                    layout1.setVisibility(View.GONE);
//                    layout2.setVisibility(View.GONE);
//
////                    mTextMessage.setText(getIntent().getStringExtra("user"));
//                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        user=getIntent().getStringExtra("user");
        aa="Pesanan Terkirim";
//
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        list = (ListView) findViewById(R.id.listproses);
//
        user_txt = (EditText) findViewById(R.id.txtuserop);
        pass = (EditText) findViewById(R.id.txtpasswordop);
        nama = (EditText) findViewById(R.id.txtopnama);
//
        layout1 = (LinearLayout) findViewById(R.id.proses);
        layout2 = (LinearLayout) findViewById(R.id.profil);
//        layout3 = (LinearLayout) findViewById(R.id.pesanan);
        layout2.setVisibility(View.GONE);
//        layout3.setVisibility(View.GONE);
//
        getProfil();
        getProsesList();
//        actionList();
        actionlist();
    }

    public void EDIT(View view){
        String pass1=pass.getText().toString();
        String nama1=nama.getText().toString();

        new EditProfilService(this).execute(user,pass1,nama1);
    }

    private void actionlist() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, long idd) {
                // TODO Auto-generated method stub
                try {
                    Intent intent = new Intent(getApplicationContext(), DetailOrderActivity.class);
                    intent.putExtra("id",id[position]);
                    intent.putExtra("username",user);
                    startActivity(intent);
                }catch (Exception e) {
                    Log.d(e.toString(),"error");
                }
            }
        });
    }


    private void getProsesList(){

        String link=get.getUrl() + "list_pesanan_operator.php";;
        String data="";
        try {
            data="?status=" + URLEncoder.encode(aa, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("linkk",link+data);
        JsonArrayRequest jArr = new JsonArrayRequest(link+data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("respontttt", response.toString());

                nama_file = new String[response.length()];
                id = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        int a = i+1;
                        nama_file[i]=a+". "+(obj.getString("layanan")+
//                                "\nPemesan : "+obj.getString("username")+
                                "\nKeterangan : "+obj.getString("status"));
                        id[i]=String.valueOf(obj.getInt("id_transaksi"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errorjsonn", e.toString());
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(OperatorActivity.this, android.R.layout.simple_list_item_1, nama_file);
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

    private void getProfil(){

        String link=get.getUrl() + "get_profil.php";;
        String data="";
        try {
            data="?username=" + URLEncoder.encode(user, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("linkk",link+data);
        JsonArrayRequest jArr = new JsonArrayRequest(link+data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("respontttt", response.toString());
//                nama_file = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        user_txt.setText(obj.getString("username"));
                        pass.setText(obj.getString("password"));
                        nama.setText(obj.getString("nama"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errorjsonn", e.toString());
                    }
                }

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
