package com.smile.taobaodemo.Regist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.smile.taobaodemo.R;

public class Using extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using);
        findViewById(R.id.acess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
