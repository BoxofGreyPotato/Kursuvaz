package com.mirea.kt.ribo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SocityFrag extends Fragment {

    private final String TAG = "SocityFrag";

    ArrayAdapter adapter;

    private String path;

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> descriptions;
    ArrayList<String> pubDate;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_socity, container, false);

        Log.i(TAG, "otobrathenie fragmenta");

        lvRss = view.findViewById(R.id.lvRss);
        new ProcessInBackground().execute();
        Log.i(TAG, "thapusk class");

        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        pubDate = new ArrayList<String>();

        assert getArguments() != null;
        path = getArguments().getString("path");
        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("titles", titles.get(position));
                intent.putExtra("pubDate", pubDate.get(position));
                intent.putExtra("desc", descriptions.get(position));
                intent.putExtra("link", links.get(position));
                startActivity(intent);
                Log.i(TAG, "thapusk activity");
            }
        });

        return view;
    }

    public InputStream getInputStream(URL url) {
        try {
            Log.i(TAG, "soedinenie");
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {

        ProgressDialog progressDialog = new ProgressDialog(getContext());

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Загрузка Rss...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                URL url = new URL(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                Log.i(TAG, "sintaksich analis");
                xpp.setInput(getInputStream(url), "UTF_8");
                Log.i(TAG, "poluchenie vhodnih potok dannih");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                titles.add(xpp.nextText());
                                Log.i(TAG, "title poluchen");
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                links.add(xpp.nextText());
                                Log.i(TAG, "links poluchen");
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                descriptions.add(xpp.nextText());
                                Log.i(TAG, "descr poluchen");
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                pubDate.add(xpp.nextText());
                                Log.i(TAG, "pubDate poluchen");
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                    Log.i(TAG, "poluchenie sleduushei novosti");
                }


            } catch (XmlPullParserException | IOException e) {
                exception = e;
            } finally {
                new Handler(Looper.getMainLooper()).post(() -> {
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titles);
                    lvRss.setAdapter(adapter);
                    Log.i(TAG, "sothdaem adapter");
                    SearchView searchView = view.findViewById(R.id.searchView);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            filterData(query);
                            Log.i(TAG, "sortirovka thaprosa ot polthovatela");
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            filterData(newText);
                            Log.i(TAG, "sortirovka otpravlenogo");
                            return true;
                        }
                    });
                });
            }
            return exception;
        }

        private void filterData(String query) {
            List<String> filteredList = new ArrayList<>();
            for (String item : titles) {
                if (item.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            adapter.clear();
            adapter.addAll(filteredList);
            Log.i(TAG, "peredaem sortirovku");
            adapter.notifyDataSetChanged();
            Log.i(TAG, "perestroika spiska");
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, titles);
            lvRss.setAdapter(adapter);
            Log.i(TAG, "sothdaem adapter");
            progressDialog.dismiss();
        }
    }
}