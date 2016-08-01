package com.work.hsinwei.my0801_01_http;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<NewsLink> mylist;   //ArrayList 建構式
    LinkAdapter Ladpt;
    Handler hl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hl=new Handler();
        mylist = new ArrayList<>();

        new Thread()
        {

            @Override
            public void run()
            {

                String strurl ="http://udn.com/rssfeed/news/1";
                String result="";
                String s1="",s2="";//先設定空字串
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
                    boolean openprint=false,findlink=false,chk1=false,chk2=false;
                    int eventType = xpp.getEventType();
                    mylist = new ArrayList<NewsLink>();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_DOCUMENT){
                            //Log.d("T0801-XmlR","Start document");
                        }else if(eventType == XmlPullParser.START_TAG){
                            //Log.d("T0801-XmlR","Start Tag-"+xpp.getName());
                            if(xpp.getName().equals("title")) {
                                    openprint = true;
                                    //Log.d("T0801-XmlR","Start Tag-item-Title");

                            }
                            if(xpp.getName().equals("link"))
                            {
                                findlink=true;
                            }


                        }else if(eventType == XmlPullParser.END_TAG){
                            //Log.d("T0801-XmlR","END Tag-"+xpp.getName());
                            /*
                            if(xpp.getName().equals("title")) {
                                openprint = false;
                                //Log.d("T0801-XmlR","END Tag-Title");
                            }
                            if(xpp.getName().equals("link")){
                                findlink=false;
                            }*/
                        }else if(eventType == XmlPullParser.TEXT){
                            //Log.d("T0801-XmlR","Text Tag-"+xpp.getName());
                            if(openprint) {
                                Log.d("T0801-GET", "Text:" + xpp.getText());
                                s1=xpp.getText();
                                openprint = false;
                                chk1=true;
                            }
                            if(findlink){
                                Log.d("T0801-GET", "Link:" + xpp.getText());
                                s2=xpp.getText();
                                findlink=false;
                                chk2=true;
                            }
                        }
                        if(chk1&&chk2) {
                            //Log.d("T0801-Show1", s1);
                            //Log.d("T0801-Show2", s2);
                            mylist.add(new NewsLink(s1,s2));
                            chk1=false;
                            chk2=false;
                        }

                        eventType = xpp.next();

                    }
                    //Log.d("T0801-XmlR","END document");
                } catch (XmlPullParserException e) { //XmlPullParserFactory.newInstance();
                    e.printStackTrace();
                } catch (IOException e) { //xpp.next();
                    e.printStackTrace();
                }
                hl.post(new Runnable() {
                    @Override
                    public void run() {
                        mylist.remove(0);
                        Ladpt = new LinkAdapter(MainActivity.this, mylist);
                        lv = (ListView) findViewById(R.id.listView);

                        lv.setAdapter(Ladpt);
                    }
                });
            }

        }.start();
        //Log.d("T0801-mylist",""+mylist.size());


    }
}
