package com.example.katie.hrubieckatherine_ce07;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class SaveArticleIntentService extends IntentService {

    public SaveArticleIntentService() {
        super("SaveArticleIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            Article article = (Article) intent.getSerializableExtra(ArticleIntentService.ARTICLE);
            MainActivity.readObjectFromCache(getApplicationContext(), MainActivity.ACTION_SAVE_ARRAY);
            MainActivity.articles = (ArrayList<Article>) MainActivity.readObjectFromCache(getApplicationContext(), MainActivity.ACTION_SAVE_ARRAY);
            MainActivity.articles.add(article);
            MainActivity.writeObjectInCache(getApplicationContext(), MainActivity.ACTION_SAVE_ARRAY, MainActivity.articles);
            Intent saveArticle = new Intent(MainActivity.ACTION_SAVE_ARTICLE);
            sendBroadcast(saveArticle);
        }
    }
}
