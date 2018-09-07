package edu.okstate.osuresearch.ResearchWeek;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Locale;

import edu.okstate.osuresearch.R;

public class ResearchWeekDetailActivity extends AppCompatActivity {

    ResearchWeekEventDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_detail_display);

        Intent callingIntent = getIntent();
        int clickedPosition = callingIntent.getIntExtra(ResearchWeekFragment.RESEARCH_WEEK_EVENT_ITEM_POSITION_KEY, -1);
        int clickedSubPosition = callingIntent.getIntExtra(ResearchWeekFragment.RESEARCH_WEEK_EVENT_ITEM_SUB_POSITION_KEY, -1);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));
        ListView lv = (ListView) findViewById(R.id.calendar_detail_list_view);
        adapter = new ResearchWeekEventDetailAdapter(this, -1, clickedPosition, clickedSubPosition);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                addToCalendar();

                break;
            case R.id.share:
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToCalendar() {

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, (String) adapter.getItem(1));
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, (String) adapter.getItem(3));
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, (String) adapter.getItem(0));
       /* String[] startDate = adapter.getItem();
        String[] startTime = data[2].split(":");
        String[] endDate = data[3].split("/");
        String[] endTime = data[4].split(":");
        GregorianCalendar calStartDate = new GregorianCalendar(Integer.parseInt(startDate[2]),Integer.parseInt(startDate[0])-1,Integer.parseInt(startDate[1]),
                Integer.parseInt(startTime[0]),Integer.parseInt(startTime[1]),Integer.parseInt(startTime[2]));
        GregorianCalendar calEndDate = new GregorianCalendar(Integer.parseInt(endDate[2]),Integer.parseInt(endDate[0])-1,Integer.parseInt(endDate[1]),
                Integer.parseInt(endTime[0]),Integer.parseInt(endTime[1]),Integer.parseInt(endTime[2]));
        */
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, (String) adapter.getItem(2));
        //calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,calEndDate.getTimeInMillis());
        startActivity(calIntent);
    }

    private void share() {
        String smsBody = "";
        for (int i = 1; i < 5; i++) {
            smsBody += adapter.dataDetail[i] + ": " + adapter.getItem(i) + "\n";
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, smsBody);
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "Select App to Share"));
    }
}
