package com.ravikwow.servicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        Intent intent = new Intent(this, TestService.class);
        startService(intent.putExtra("duration", 7));
        startService(intent.putExtra("duration", 2));
    }
}
