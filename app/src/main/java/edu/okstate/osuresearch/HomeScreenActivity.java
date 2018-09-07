package edu.okstate.osuresearch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Locale;

public class HomeScreenActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static String clickedTabKey = "edu.okstate.osuresearchmatters.HOME_SCREEN_CLICKED_TAB_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));
        HomeScreenListAdapter listAdapter = new HomeScreenListAdapter(this, -1);
        ListView listView = (ListView) findViewById(R.id.home_screen_list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(clickedTabKey, position);
        startActivity(intent);
    }
}
