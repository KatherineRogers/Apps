package com.example.katie.hrubieckatheirne_ce05;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements BookListFrag.SelectedBookListener {

    private static final ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runConnictivity();
    }

    private void runConnictivity() {
        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null) {
                boolean isConnected = info.isConnected();
                if (isConnected) {
                    DataTask task = new DataTask();
                    task.execute(getString(R.string.espn_news));
                }
            } else {
                Toast.makeText(MainActivity.this, R.string.no_network_connection, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setList() {
        getFragmentManager().beginTransaction().replace(R.id.frame, BookListFrag.newInstance(books)).commit();
    }

    private void getBooks() {
        Cursor c = this.getContentResolver().query(BookDataContract.CONTENT_URI, null, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {
                String title = c.getString(c.getColumnIndex(BookDataContract.TITLE));
                String thumbnail = c.getString(c.getColumnIndex(BookDataContract.THUMBNAIL));
                String description = c.getString(c.getColumnIndex(BookDataContract.DESCRIPTION));
                books.add(new Book(title, thumbnail, description));
            }
            c.close();
        }
    }

    @Override
    public void selectedBook(int position) {
        Book selectedBook = books.get(position);
        new AlertDialog.Builder(this)
                .setTitle(selectedBook.title)
                .setMessage(selectedBook.description)
                .setPositiveButton("Ok", null)
                .show();
    }

    private String getNetworkData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            String isString = IOUtils.toString(is, "UTF-8");
            is.close();
            connection.disconnect();
            return isString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseNewsJson(String json) {

        try {
            JSONObject outerMost = new JSONObject(json);
            JSONArray jArray = outerMost.getJSONArray("articles");
            for (int i = 0; i < jArray.length(); i++) {

                String title = jArray.getJSONObject(i).getString("title");
                String thumbnail = jArray.getJSONObject(i).getString("urlToImage");
                String description = jArray.getJSONObject(i).getString("description");

                ArticleDatabaseHelper db = new ArticleDatabaseHelper(this);
                ContentValues cv = new ContentValues();
                cv.put(ArticleDataContract.TITLE, title);
                cv.put(ArticleDataContract.THUMBNAIL, thumbnail);
                cv.put(ArticleDataContract.BODY, description);
                db.getWritableDatabase().insert(ArticleDataContract.DATA_TABLE, null, cv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class DataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            getBooks();
            String json = getNetworkData(strings[0]);
            parseNewsJson(json);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setList();
        }
    }


}
