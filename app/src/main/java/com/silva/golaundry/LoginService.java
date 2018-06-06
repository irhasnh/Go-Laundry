package com.silva.golaundry;

/**
 * Created by ASUS on 10/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginService extends AsyncTask<String, Void, String> {
    private Context context;

    Get get = new Get();
    String level;

    public LoginService(Context context){
        this.context = context;
    }




    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground (String... arg0){
        String user = arg0[0];
        String pass = arg0[1];
        level = arg0[2];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try{

            data = "?username=" + URLEncoder.encode(user, "UTF-8");
            data += "&pass=" + URLEncoder.encode(pass, "UTF-8");
            data += "&level=" + URLEncoder.encode(level, "UTF-8");


            link = get.getUrl() + "login.php" + data;
            Log.d("linkk",link);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        }catch (Exception e){
            return new String("Exception:" +e.getMessage());
        }
    }
    protected void onPostExecute(String resut){
        String jsonStr = resut;
        Log.d("ressul",resut);
//        Toast.makeText(context, "exe "+resut, Toast.LENGTH_SHORT).show();
        if (jsonStr != null){
            try {
                JSONObject jsonobj =new JSONObject(jsonStr);
                String query_result = jsonobj.getString("query_result");
                if (query_result.equals("KOSONG")){
                    Toast.makeText(context, "User/Password Salah", Toast.LENGTH_SHORT).show();
                }else if (query_result.equals("FAILURE")){
                    Toast.makeText(context, "Proses Gagal", Toast.LENGTH_SHORT).show();
                }else {
                    if (level.equals("Operator")){
                        Toast.makeText(context, "Selamat Datang "+query_result, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, OperatorActivity.class);
                        intent.putExtra("user",query_result);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "Selamat Datang "+query_result, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, UserActivity.class);
                        intent.putExtra("user",query_result);
                        context.startActivity(intent);
                    }

                }
            }catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(context, " "+e, Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(context, "Tidak Dapat Get JSON Data", Toast.LENGTH_SHORT).show();
        }
    }

}

