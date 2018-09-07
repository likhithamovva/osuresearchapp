package edu.okstate.osuresearch.Calendar;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.okstate.osuresearch.R;

public class CalendarEventDetailDisplayActivity extends AppCompatActivity {
    CalendarEventDetailListAdapter adapter;
    int clickedPosition, clickedSubPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_detail_display);

        Intent callingIntent = getIntent();
        clickedPosition = callingIntent.getIntExtra(CalendarFragment.CALENDAR_EVENT_ITEM_POSITION_KEY, -1);
        clickedSubPosition = callingIntent.getIntExtra(CalendarFragment.CALENDAR_EVENT_ITEM_SUB_POSITION_KEY, -1);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));
        ListView lv = (ListView) findViewById(R.id.calendar_detail_list_view);
        adapter = new CalendarEventDetailListAdapter(this, -1, clickedPosition, clickedSubPosition);
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
        calIntent.putExtra(CalendarContract.Events.ALL_DAY, adapter.getItemId(4));
        ArrayList<CalendarDataModel> allEventsOfParticularDate = ((ArrayList<CalendarDataModel>) (CalendarAllEventsListViewAdapter.displayCalendarData.values().toArray()[clickedPosition]));

        String[] startDate = allEventsOfParticularDate.get(clickedSubPosition).startDate.split("/");
        String[] startTime = allEventsOfParticularDate.get(clickedSubPosition).startTimeInTwelveHourFormat.split(":");
        String[] endDate = allEventsOfParticularDate.get(clickedSubPosition).endDate.split("/");
        String[] endTime = allEventsOfParticularDate.get(clickedSubPosition).endTimeInTwelveHourFormat.split(":");
        GregorianCalendar strtDate = new GregorianCalendar();
        GregorianCalendar enDate = new GregorianCalendar();

        //mm-dd/yyyy
        strtDate.set(Calendar.MONTH, Integer.parseInt(startDate[0]) - 1);
        strtDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate[1]));
        strtDate.set(Calendar.YEAR, Integer.parseInt(startDate[2]));
        strtDate.set(Calendar.HOUR, Integer.parseInt(startTime[0]));
        strtDate.set(Calendar.MINUTE, Integer.parseInt(startTime[1].substring(0, 2)));
        if (startTime[1].contains("AM"))
            strtDate.set(Calendar.AM_PM, Calendar.AM);
        else
            strtDate.set(Calendar.AM_PM, Calendar.PM);


        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, strtDate.getTimeInMillis());

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
