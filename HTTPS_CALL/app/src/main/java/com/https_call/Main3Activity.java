package com.https_call;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class Main3Activity extends AppCompatActivity {

    TextView mMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mMsg = (TextView) findViewById(R.id.msg);

        String s = getIntent().getStringExtra("msg");
        mMsg.setText(s);
    }
}
