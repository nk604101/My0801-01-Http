package com.work.hsinwei.my0801_01_http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread()
        {
            @Override
            public void run()
            {
                String strurl ="http://udn.com/rssfeed/news/1";
                String result="";

                try
                {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    ///*
                    StringBuilder sb = new StringBuilder();
                    String x = br.readLine();
                    while (x != null)
                    {
                        sb.append(x);
                        x= br.readLine();
                    }
                    Log.d("T0801-Read",sb.toString());
                    result = sb.toString();
                    //*/
                    /*
                    String x = "";
                    do {
                        x = br.readLine();
                    }while (!x.equals(""));
                    //*/

                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                //XMLparser
                try {
                    XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result));
                    boolean openprint=false;
                    int eventType = xpp.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_DOCUMENT){
                            //Log.d("T0801-XmlR","Start document");
                        }else if(eventType == XmlPullParser.START_TAG){
                            //Log.d("T0801-XmlR","Start Tag-"+xpp.getName());
                            if(xpp.getName().equals("title")) {
                                openprint = true;
                                //Log.d("T0801-XmlR","Start Tag-Title");
                            }
                        }else if(eventType == XmlPullParser.END_TAG){
                            //Log.d("T0801-XmlR","END Tag-"+xpp.getName());
                            if(xpp.getName().equals("title")) {
                                openprint = false;
                                //Log.d("T0801-XmlR","END Tag-Title");
                            }
                        }else if(eventType == XmlPullParser.TEXT){
                            //Log.d("T0801-XmlR","Text Tag-"+xpp.getName());
                            if(openprint) {
                                Log.d("T0801-XmlR", "Text:" + xpp.getText());
                            }
                        }
                        eventType = xpp.next();
                    }
                    Log.d("T0801-XmlR","END document");
                } catch (XmlPullParserException e) { //XmlPullParserFactory.newInstance();
                    e.printStackTrace();
                } catch (IOException e) { //xpp.next();
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
