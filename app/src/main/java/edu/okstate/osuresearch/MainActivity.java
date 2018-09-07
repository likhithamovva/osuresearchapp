package edu.okstate.osuresearch;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import edu.okstate.osuresearch.Calendar.CalendarFragment;
import edu.okstate.osuresearch.Contact.ContactFragment;
import edu.okstate.osuresearch.NewsFeed.NewsFeedFragment;
import edu.okstate.osuresearch.ResearchWeek.ResearchWeekFragment;
import edu.okstate.osuresearch.Social.SocialFragment;

import static edu.okstate.osuresearch.HomeScreenActivity.clickedTabKey;

public class MainActivity extends AppCompatActivity {
    static final int customTabItemImages[] = {R.mipmap.ic_news_feed, R.mipmap.ic_contact, R.mipmap.ic_calendar, R.mipmap.ic_research_week, R.mipmap.ic_social};
    static final Class fragmentTabsClasses[] = {NewsFeedFragment.class, ContactFragment.class, CalendarFragment.class, ResearchWeekFragment.class, SocialFragment.class};
    final String itemNames[] = {"News Feed", "Contacts", "Calendar", "Research Week", "Social"};
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        initializeTabsData(intent.getIntExtra(clickedTabKey, 0));
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));

    }

    private void initializeTabsData(int currentTab) {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        setupTab(new TextView(this), 0);
        setupTab(new TextView(this), 1);
        setupTab(new TextView(this), 2);
        setupTab(new TextView(this), 3);
        setupTab(new TextView(this), 4);
        mTabHost.setCurrentTab(currentTab);
    }
    private void setupTab(final View view, final int position) {
        String tag = "groups" + position;
        View tabview = createTabView(mTabHost.getContext(), position);
        FragmentTabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new FragmentTabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent, fragmentTabsClasses[position], null);
    }

    private View createTabView(final Context context, final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main_custom_tab_item, null);
        TextView textview = (TextView) view.findViewById(R.id.tab_item_text);
        textview.setText(itemNames[position]);
        ImageView iv = (ImageView) view.findViewById(R.id.tab_item_image);
        iv.setImageResource(customTabItemImages[position]);

        //For older versions supporting tint mode on Image views
        ColorStateList csl = ContextCompat.getColorStateList(this, R.color.activity_main_custom_tab_item_selector);
        Drawable drawable = DrawableCompat.wrap(iv.getDrawable());
        DrawableCompat.setTintList(drawable, csl);
        iv.setImageDrawable(drawable);

        return view;
    }
}
