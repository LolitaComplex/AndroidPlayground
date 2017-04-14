package com.doing.androidipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doing.androidipc.aidl.AidlActivity;
import com.doing.androidipc.messager.MessagerActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("AidlActivity");
    }

    public void startMessager(View view) {
        startActivity(new Intent(this, MessagerActivity.class));
    }

    public void startAidl(View view) {
        startActivity(new Intent(this, AidlActivity.class));
    }


}
