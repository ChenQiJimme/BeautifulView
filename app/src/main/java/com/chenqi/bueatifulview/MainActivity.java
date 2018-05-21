package com.chenqi.bueatifulview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chenqi.mylibrary.MainTest;

public class MainActivity extends AppCompatActivity {
DeformationView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.a);
        view.smoothScrollTo(-400,0);
        MainTest.sayHello();
    }
}
