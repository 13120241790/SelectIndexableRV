package com.example.beibei.selectindexablerv;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



/**
 * Created by YoKey on 16/10/7.
 */
public class MainActivity extends AppCompatActivity {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_SELECT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_pick_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PickContactActivity.class);
                intent.putExtra("mode", MODE_NORMAL);
                startActivity(intent);

            }
        });
        findViewById(R.id.btn_pick_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PickContactActivity.class);
                intent.putExtra("mode", MODE_SELECT);
                startActivity(intent);
            }
        });
    }
}
