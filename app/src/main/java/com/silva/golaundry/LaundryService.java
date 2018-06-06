package com.silva.golaundry;

/**
 * Created by ASUS on 10/10/2017.
 */

import android.content.Context;
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

public class LaundryService extends AsyncTask<String, Void, String> {
    private Context context;

    Get get = new Get();

    public LaundryService(Context context){
        this.context = context;
    }




    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground (String... arg0){
        String user = arg0[0];
        String kilo = arg0[1];
        String layanan = arg0[2];
        String pengambilan = arg0[3];
        String catatan = arg0[4];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try{

            data = "?username=" + URLEncoder.encode(user, "UTF-8");
            data += "&kilo=" + URLEncoder.encode(kilo, "UTF-8");
            data += "&layanan=" + URLEncoder.encode(layanan, "UTF-8");
            data += "&pengambilan=" + URLEncoder.encode(pengambilan, "UTF-8");
            data += "&catatan=" + URLEncoder.encode(catatan, "UTF-8");


            link = get.getUrl() + "print.php" + data;
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
                if (query_result.equals("SUCCESS")){
                    Toast.makeText(context, "Data Terkirim", Toast.LENGTH_SHORT).show();
                }else if (query_result.equals("FAILURE")){
                    Toast.makeText(context, "Proses Gagal", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, query_result, Toast.LENGTH_SHORT).show();

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

