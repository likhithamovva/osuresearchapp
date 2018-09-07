package edu.okstate.osuresearch.NewsFeed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Locale;

import edu.okstate.osuresearch.R;

public class SingleNewsItemDescription extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));

        Intent intent = getIntent();
        String urlExtra = intent.getStringExtra(NewsFeedFragment.NEWS_FEED_ITEM_WEB_URL_KEY);
        setContentView(R.layout.activity_single_news_item_description);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(urlExtra);

    }
}
