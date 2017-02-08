package main.java.com.doing.canvas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doing.canvas.R;

public class TableCanvasActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TableCanvasActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_canvas);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PixelColorCanvasActivity.start(TableCanvasActivity.this);
            }
        });
    }
}
