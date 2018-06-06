package com.silva.golaundry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class DetailOrderActivity extends AppCompatActivity {

    private String user,id;
    Button proses;
    private TextView txt1, txt2, txt3, txt4;
    private  String[] nama_file;

    AlertDialog.Builder dialog;

    Get get = new Get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        txt1 = findViewById(R.id.textView1);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        txt4 = findViewById(R.id.textView4);
        proses = findViewById(R.id.btn_proses);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        user = intent.getStringExtra("username");


        getData();
        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(DetailOrderActivity.this);
//                final String idx = nama_file[id].substring(0,nama_file[id].indexOf("\n"));
                final String idx = (String) txt2.getText();
                Log.d("posisi",String.valueOf(id));

                final CharSequence[] dialogitem = {"Proses Pesanan", "Pesanan Selesai", "Tolak Pesanan"};
                dialog = new AlertDialog.Builder(DetailOrderActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                dlgAlert.setMessage(idx+" akan diproses");
                                dlgAlert.setPositiveButton("OK", null);
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the dialog
                                                new StatusPrintService(DetailOrderActivity.this).execute(id,"Diproses",user);
                                            }
                                        });
                                dlgAlert.create().show();
                                break;
                            case  1:

                                dlgAlert.setMessage(idx+" telah selesai diproses dan siap untuk diambil");
                                dlgAlert.setPositiveButton("OK", null);
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the dialog
                                                new StatusPrintService(DetailOrderActivity.this).execute(id,"Selesai",user);
                                            }
                                        });
                                dlgAlert.create().show();
                                break;
                            case  2:
                                dlgAlert.setMessage(idx+" akan dibatalkan");
                                dlgAlert.setPositiveButton("OK", null);
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the dialog
                                                new StatusPrintService(DetailOrderActivity.this).execute(id,"Ditolak",user);
                                            }
                                        });
                                dlgAlert.create().show();
                        }
                    }
                }).show();
            }
        });

    }


    private void getData(){

        String link=get.getUrl() + "get_transaksi.php";;
        String data="";
        try {
            data="?id=" + URLEncoder.encode(id, "UTF-8");
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
}
