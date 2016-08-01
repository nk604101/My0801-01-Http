package com.work.hsinwei.my0801_01_http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            @Override
            public void run(){
                String strurl ="http://udn.com/rssfeed/news/1";

                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    StringBuilder sb = new StringBuilder();
                    String x = br.readLine();
                    while (x != null)
                    {
                        sb.append(x);
                        x= br.readLine();
                    }
                    /*
                    String x = "";
                    do {
                        x = br.readLine();
                    }while (!x.equals(""));*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}
