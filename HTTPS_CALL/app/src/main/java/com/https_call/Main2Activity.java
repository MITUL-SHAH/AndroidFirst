package com.https_call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    TextView mTextmac;
    EditText mRegno;
    TextView mMacid,mact2,mtv2;
    Button mReq;
    Button mReqintent;
    //ActionBar avi;
    //View mloader;
    //View mprgbar;
    ProgressBar mprogbar;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextmac = (TextView) findViewById(R.id.output);
        mRegno = (EditText) findViewById(R.id.reg_no);
        mMacid = (TextView) findViewById(R.id.mac_id);
        mReq = (Button) findViewById(R.id.req);
        mReqintent = (Button) findViewById(R.id.reqintent);
        //mloader = (View)findViewById(R.id.loader);
        //mprgbar = (View) findViewById(R.id.prgbar);
        mprogbar = (ProgressBar) findViewById(R.id.progBar);
        mact2 = (TextView) findViewById(R.id.act2);
        mtv2=(TextView) findViewById(R.id.textView2);
        mprogbar.setVisibility(View.INVISIBLE);
        //AlertDialog dialog = new SpotsDialog(Main2Activity.this);
        //final SpotsDialog.Builder dialog = new SpotsDialog.Builder(this);
        //dialog.show();


        mReqintent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mprogbar.setVisibility(View.VISIBLE);
                //avi.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                        intent.putExtra("msg",str);
                        startActivity(intent);
                    }
                },3000);


                final Handler handler = new Handler();
                final int[] progressStatus = {0};
                // Start long running operation in a background thread
                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus[0] < 100) {
                            progressStatus[0]++;
                            // Update the progress bar and display the
                            //current value in the text view

                            handler.post(new Runnable() {
                                public void run() {
                                    mprogbar.setProgress(progressStatus[0]);
                                    //textView.setText(progressStatus[0] +"/"+mprogbar.getMax());
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

        });


        mReq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String regno = mRegno.getText().toString();
                boolean b = Pattern.matches("\\d{4}", regno);
                if (!b) {
                    mtv2.setText("Enter only 4 digits of Reg.No.");
                    //mprgbar.animate();
                    //stopAnim();
                }
                else {
                    //mMacid.setText("YES");
                    //boolean network = false;
                    if (!this.getActiveNetworkInfo()) {
                        mTextmac.setText("Please Connect to the internet");
                    }
                    else {
                        //mMacid.setText("1ahead");

                        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                            mMacid.setText("error1");
                        }

    /*                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                        mMacid.setText("error2");
                    }

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                        mMacid.setText("er3");
                    }
    */

                        //mMacid.setText("2ahead");
                        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wInfo = wifiManager.getConnectionInfo();
                        String macAddress = wInfo.getMacAddress();
                        mMacid.setText(macAddress);

                        try {
                            // get all the interfaces
                            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                            //find network interface wlan0
                            for (NetworkInterface networkInterface : all) {
                                if (!networkInterface.getName().equalsIgnoreCase("wlan0")) continue;
                                //get the hardware address (MAC) of the interface
                                byte[] macBytes = networkInterface.getHardwareAddress();
                                if (macBytes == null) {
                                    mMacid.setText("Mac Address is unable to be fetched");
                                }

                                StringBuilder res1 = new StringBuilder();
                                for (byte k : macBytes) {
                                    //gets the last byte of b
                                    res1.append(Integer.toHexString(k & 0xFF) + ":");
                                }

                                if (res1.length() > 0) {
                                    res1.deleteCharAt(res1.length()-1);
                                    res1.deleteCharAt(res1.length()-1);
                                    res1.deleteCharAt(res1.length()-1);
                                    res1.deleteCharAt(res1.length()-1);
                                    //res1.deleteCharAt(res1.length() - 3);
                                }
                                mMacid.setText(res1.toString());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }



                        //ComponentName componentName = new ComponentName("MainActivity.this","onClick()");
                        //mMacid.setText(DevicePolicyManager.getWifiMacAddress(componentName));

                        //startAnim();

                        String reg = mRegno.getText().toString();
                        String mac = mMacid.getText().toString();
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
                        String url = "https://android-club-project.herokuapp.com/upload_details?"+"reg_no="+reg+"&mac="+mac;
                        //mMacid.setText(url);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                str = "Server Response is: " + response.substring(0, 10);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mact2.setText("Unable to fetch response");
                            }
                        });
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }
                    //stopAnim();
                }

            }

            private boolean getActiveNetworkInfo() {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo.isConnected();
            }

        });

    }

}


