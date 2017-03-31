package com.doing.architectureencapsulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doing.architectureencapsulation.image.ImageActivity;
import com.doing.architectureencapsulation.listadapter.RecyclerActivity;
import com.doing.architectureencapsulation.net.test.OkHttpTestActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class ArchitectureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);

        findViewById(R.id.activity_btn_image).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageActivity.start(ArchitectureActivity.this);
                }
            }
        );

        findViewById(R.id.activity_btn_okhttp).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    map.put("Test Key", "Hash Value");
                    map.put("Test Key1", "Hash Value2");
                    map.put("Test Key2", "Hash Value3");
                    map.put("Test Key3", "Hash Value4");
                    map.put("Test Key4", "Hash Value5");
                    map.put("Test Key5", "Hash Value6");
                    map.put("Test Key6", "Hash Value7");
                    map.put("Test Key8", "Hash Value8");
                    map.put("Test Key9", "Hash Value9");
                    map.put("Test Key1", "Hash Value2");
//                    OkHttpTestActivity.start(ArchitectureActivity.this);
                }
            }
        );

        findViewById(R.id.activity_btn_recycler).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerActivity.start(ArchitectureActivity.this);
                    }
                }
        );



    }
}
