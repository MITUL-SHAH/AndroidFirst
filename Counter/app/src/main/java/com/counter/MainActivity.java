package com.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mbutton1;
    TextView mtv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int[] i = {0};
        mbutton1 = (Button) findViewById(R.id.button_count);
        mtv1 = (TextView) findViewById(R.id.textview1);
        mbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i[0] = i[0] + 1;
                mtv1.setText("Counter "+i[0]);
            }
        });
    }
}
