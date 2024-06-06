package com.mirea.kt.ribo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;

public class LobbyAct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);


        Bundle bundle = new Bundle();
        bundle.putString("path", "https://news.rambler.ru/rss/moscow_city/");
        SocityFrag frag = new SocityFrag();
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.lsNews, frag).commit();

        HashMap<Integer, Fragment> map = new HashMap<>();
        map.put(R.id.nwmsc, new SocityFrag());
        map.put(R.id.plt, new SocityFrag());
        map.put(R.id.obsh, new SocityFrag());
        map.put(R.id.proish, new SocityFrag());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = map.get(item.getItemId());
                Bundle bundle = new Bundle();
                bundle.putString("path", getPathLs(item.getItemId()));
                assert fragment != null;
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.lsNews, fragment).commit();
                return true;
            }
        });

        /*lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/

        /*new ProcessInBackground().execute();*/
    }

    private String getPathLs(int res) {
        if (res == R.id.nwmsc) {
            return "https://news.rambler.ru/rss/moscow_city/";
        } else if (res == R.id.plt) {
            return "https://news.rambler.ru/rss/incidents/";
        } else if (res == R.id.obsh) {
            return "https://news.rambler.ru/rss/community/";
        } else {
            return "https://news.rambler.ru/rss/politics/";
        }
    }
}

    /*public InputStream getInputStream(URL url){
        try{
            return url.openConnection().getInputStream();
        }catch (IOException e){
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>{

        ProgressDialog progressDialog = new ProgressDialog(LobbyAct.this);

        Exception exception = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Загрузка Rss...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            https://news.rambler.ru/rss/moscow_city/
            https://news.rambler.ru/rss/incidents/
            https://news.rambler.ru/rss/community/
            https://news.rambler.ru/rss/politics/
            try{
                URL url = new URL("https://news.rambler.ru/rss/moscow_city/");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        if (xpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem){
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem){
                                links.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                descriptions.add(xpp.nextText());
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }


            }catch (MalformedURLException e){
                exception = e;
            }catch (XmlPullParserException e){
                exception = e;
            }catch (IOException e){
                exception = e;
            }



            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LobbyAct.this, android.R.layout.simple_list_item_1, titles);
            lvRss.setAdapter(adapter);




            progressDialog.dismiss();
        }
    }*/
