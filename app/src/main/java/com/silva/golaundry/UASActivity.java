package com.silva.golaundry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class UASActivity extends AppCompatActivity {

    Get get = new Get();
    Button proses;
    private TextView txt1, txt2, txt3, txt4;
    Button simpan, update, hapus;
    EditText id, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uas);

        simpan = findViewById(R.id.simpan);
        update = findViewById(R.id.update);
        hapus = findViewById(R.id.hapus);
        id = findViewById(R.id.txtid);
        user = findViewById(R.id.txtuser);


        txt1 = findViewById(R.id.textView1);
        txt2 = findViewById(R.id.textView2);
        proses = findViewById(R.id.hapus);


        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }


    private void getData(){

        String link=get.getUrl() + "get_transaksi.php";;
        String data="";
        try {
            data="?id=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("linkk",link+data);
        JsonArrayRequest jArr = new JsonArrayRequest(link+data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("respontttt", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        txt1.setText(obj.getString("kilo")+" kilogram");
                        txt2.setText(obj.getString("layanan"));
                        txt3.setText(obj.getString("pengambilan"));
                        txt4.setText(obj.getString("status"));
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
    public void daftaruas(View view){
        String id1=id.getText().toString();
        String user1=user.getText().toString();

//        Toast.makeText(this, "Proses Simpan..." , Toast.LENGTH_SHORT).show();
        new DaftarService(this).execute(id1,user1);
    }
    public void updateuas(View view){
        String id1=id.getText().toString();
        String user1=user.getText().toString();

//        Toast.makeText(this, "Proses Simpan..." , Toast.LENGTH_SHORT).show();
        new EditUasService(this).execute(id1,user1);
    }
    public void hapuseuas(View view){
        String id1=id.getText().toString();
        String user1=user.getText().toString();

//        Toast.makeText(this, "Proses Simpan..." , Toast.LENGTH_SHORT).show();
        new HapusUasService(this).execute(id1,user1);
    }
}
