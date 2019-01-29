package com.example.katie.hrubieckatherine_ce07;

import android.app.IntentService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class ArticleIntentService extends IntentService {

    public static final String ARTICLE = "ARTICLE";

    public ArticleIntentService() {
        super("ArticleIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Article chosenArticle = runConnectivity(getApplicationContext());
        if (chosenArticle != null){
            setNotification(chosenArticle, getApplicationContext());
        }
    }

    private static void setNotification(Article article, Context context){
        URL url;
        Bitmap myBitmap = null;
        try {
            url = new URL(article.image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
                builder = new NotificationCompat.Builder(context, notificationChannel.getId());
            }
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        if(builder != null && notificationManager != null){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.urlToArticle));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, browserIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder = builder
                    .setSmallIcon(R.drawable.football)
                    .setContentTitle(article.title)
                    .setContentText(article.description)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(myBitmap);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(article.title);
            style.bigText(article.description);
            builder.setStyle(style);
            Intent saveArticle = new Intent(context ,SaveArticleIntentService.class);
            saveArticle.putExtra(ARTICLE, article);
            PendingIntent savePendingIntent = PendingIntent.getService(context, 0, saveArticle, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(android.R.drawable.ic_menu_save, "Save", savePendingIntent);
            notificationManager.notify(MainActivity.EXPANDED_NOTIFICATION, builder.build());
        }

    }

    private static Article runConnectivity(Context context) {
        String newsArticels = context.getString(R.string.headlines);
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null) {
                boolean isConnected = info.isConnected();
                if (isConnected) {
                    String articles = getNetworkData(newsArticels);
                    return parseNewsJson(articles);
                }
            } else {
                MainActivity.mToast.show();
            }
        }
        return null;
    }

    private static String getNetworkData(String urlString) {
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

    private static Article parseNewsJson(String json) {

        try {
            JSONObject outerMost = new JSONObject(json);
            int totalResults = outerMost.getInt("totalResults");
            JSONArray jArray = outerMost.getJSONArray("articles");
            Random r = new Random();
            int randArticle = r.nextInt(totalResults);
            String title = jArray.getJSONObject(randArticle).getString("title");
            String url = jArray.getJSONObject(randArticle).getString("url");
            String thumbnail = jArray.getJSONObject(randArticle).getString("urlToImage");
            String description = jArray.getJSONObject(randArticle).getString("description");
            return new Article(title, url, thumbnail, description);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
