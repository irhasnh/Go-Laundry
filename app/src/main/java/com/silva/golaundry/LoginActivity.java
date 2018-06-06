package com.silva.golaundry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Risky-PC on 27/11/2017.
 */


public class LoginActivity extends AppCompatActivity {
    EditText user,pass;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=(EditText) findViewById(R.id.txtuser);
        pass=(EditText) findViewById(R.id.txtpassword);
        radioGroup = (RadioGroup) findViewById(R.id.login_registration);

    }
    public void masuk(View view){
        String user1=user.getText().toString();
        String pass1=pass.getText().toString();
        int level=radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(level);
        String level1=radioButton.getText().toString();
//        Toast.makeText(this, "Proses Simpan..." , Toast.LENGTH_SHORT).show();
        new LoginService(this).execute(user1,pass1,level1);
//        Intent intent = new Intent(LoginActivity.this, UASActivity.class);
//        startActivity(intent);
    }

    public void onDaftar(View view){
        Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
        startActivity(intent);
    }
    public void uas(View view){
        Intent intent = new Intent(LoginActivity.this, UASActivity.class);
        startActivity(intent);
    }

}
