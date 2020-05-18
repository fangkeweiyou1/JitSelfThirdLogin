package com.zhang.jitselfthirdlogin;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_main_gen;
    private AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        ll_main_gen = findViewById(R.id.ll_main_gen);
        ll_main_gen.removeAllViews();

        addButton("用2020Api吊起WhatsappAPP", v -> {

        });
    }

    void addButton(String text, View.OnClickListener clickListener) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(clickListener);
        ll_main_gen.addView(button);
    }
}
