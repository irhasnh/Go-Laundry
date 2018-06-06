package com.silva.golaundry;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private String user;

    private TextView mTextMessage;
    private LinearLayout layout1, layout2, layout3;
    private TextView textViewFile;
    private EditText textCatatan;
    private Spinner spinnerPengambilan, spinnerLayanan, spinnerKilo;
    private ListView list;

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private  String[] nama_file, id_print, layanan;


    EditText usertxt,pass,nama, harga;

    Get get = new Get();

    AlertDialog.Builder dialog;

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
                    layout3.setVisibility(View.GONE);
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    layout2.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.GONE);
                    layout3.setVisibility(View.GONE);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    layout3.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.GONE);
                    getPrintList();
//                    mTextMessage.setText(getIntent().getStringExtra("user"));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//
        user=getIntent().getStringExtra("user");
//
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        textCatatan = (EditText) findViewById(R.id.txt_catatan);
        spinnerLayanan = (Spinner) findViewById(R.id.sp_layanan);
        spinnerPengambilan = (Spinner) findViewById(R.id.sp_pengambilan);
        spinnerKilo = (Spinner) findViewById(R.id.sp_kilo);
        list  = (ListView) findViewById(R.id.list);
        
        Button kirimpesanan = (Button) findViewById(R.id.kirimpesanan);
        Button buttonProfilEdit = (Button) findViewById(R.id.profil_edit);
//
        usertxt = (EditText) findViewById(R.id.txtuser2);
        pass = (EditText) findViewById(R.id.txtpassword2);
        nama = (EditText) findViewById(R.id.txtnama2);
        harga = (EditText) findViewById(R.id.txt_harga);
//
        layout1 = (LinearLayout) findViewById(R.id.home);
        layout2 = (LinearLayout) findViewById(R.id.profil);
        layout3 = (LinearLayout) findViewById(R.id.pesanan);
        layout3.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
//
        getProfil();
//
        setSpinnerPengambilan();
        setSpinnerlayanan();
        setSpinnerKilo();
//
        getPrintList();
//
        kirimpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String user="silva";
                String kilo = String.valueOf(spinnerKilo.getSelectedItem().toString());
                String layanan=spinnerLayanan.getSelectedItem().toString();
                String pengambilan=spinnerPengambilan.getSelectedItem().toString();
                String catatan=textCatatan.getText().toString();


                new LaundryService(UserActivity.this).execute(user,kilo,layanan,pengambilan,catatan);
            }
        });

        spinnerLayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerLayanan.getSelectedItem().equals("Cuci Kering")){
                    harga.setText("3000");
                }else if (spinnerLayanan.getSelectedItem().equals("Cuci Kering Setrika")){
                    harga.setText("4000");
                }else {
                    harga.setText("5000");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        actionList();

    }

    public void EDIT(View view){
        String pass1=pass.getText().toString();
        String nama1=nama.getText().toString();

        new EditProfilService(this).execute(user,pass1,nama1);
    }

    private void actionList(){
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(UserActivity.this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, ListPrintActivity.class);
                intent.putExtra("id_transaksi",id_print[position]);
                startActivity(intent);
            }
        });
    }

    private void setSpinnerPengambilan(){
        ArrayList<String> menu;
        menu = new ArrayList<>();
        menu.add("Kirim ke tukang Cuci");
        menu.add("Tukang cuci mengambil di lokasi");
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, menu);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPengambilan.setAdapter(LTRadapter);
    }
    private void setSpinnerKilo(){
        ArrayList<String> menu;
        menu = new ArrayList<>();
        for (int i=1; i<=20; i++) {
            menu.add(String.valueOf(i));
        }
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, menu);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKilo.setAdapter(LTRadapter);
    }

    private void setSpinnerlayanan(){
        ArrayList<String> menu;
        menu = new ArrayList<>();
        menu.add("Cuci Kering");
        menu.add("Cuci Kering Setrika");
        menu.add("Cuci Kering Setrika+Parfum");
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, menu);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLayanan.setAdapter(LTRadapter);
//        getLayananList();

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
                nama_file = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        usertxt.setText(obj.getString("username"));
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

    private void getPrintList(){

        String link=get.getUrl() + "list_pesanan.php";;
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
                nama_file = new String[response.length()];
                id_print = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        int a = i+1;
                        String no = String.valueOf(a);
                        nama_file[i]=(no+". "+obj.getString("layanan")+"\n"+obj.getString("status"));
                        id_print[i]=String.valueOf(obj.getInt("id_transaksi"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errorjsonn", e.toString());
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(UserActivity.this, android.R.layout.simple_list_item_1, nama_file);
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

//    private void getLayananList(){
//
//        String link=get.getUrl() + "get_operator.php";
//        String data="";
//
//        Log.d("linkk",link+data);
//        JsonArrayRequest jArr = new JsonArrayRequest(link+data, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d("respontttt", response.toString());
//
//                layanan = new String[response.length()];
//
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject obj = response.getJSONObject(i);
//                        layanan[i]=obj.getString("user");
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.d("errorjsonn", e.toString());
//                    }
//                }
////                ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(UserActivity.this,android.R.layout.simple_spinner_item, layanan);
////                LTRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spinnerLayanan.setAdapter(LTRadapter);
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Error: " + error.getMessage());
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(jArr);
//    }




}
