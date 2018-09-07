package edu.okstate.osuresearch.Calendar;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.okstate.osuresearch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements View.OnClickListener {
    static final String CALENDAR_EVENT_ITEM_POSITION_KEY = "edu.okstate.osuresearchmatters.CALENDAR_EVENT_ITEM_POSITION_KEY";
    static final String CALENDAR_EVENT_ITEM_SUB_POSITION_KEY = "edu.okstate.osuresearchmatters.CALENDAR_EVENT_ITEM_SUB_POSITION_KEY";
    static CalendarFragment fragment;
    CalendarAllEventsListViewAdapter adapter;
    HashMap<String, List<CalendarDataModel>> calendarParsedData = new LinkedHashMap<>();
    Thread dataBackgroundParsingThread;
    String URLToFetchCalendarData = "http://www.trumba.com/calendars/okstate-research.csv";
    ProgressDialog dialog;
    ListView listView;
    int maxEventsInAllDates = 0;
    ColorCodesDialogBox colorCodesDialogBox;
    AlertDialog errorAlertDialogBox;

    public CalendarFragment() {
        fragment = this;
        // Required empty public constructor
    }

    private Thread createBackgroundThreadForParsingRawCalendarData(final CalendarFragment fragment) {

        return new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(URLToFetchCalendarData);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream is = httpURLConnection.getInputStream();
                    CsvParserSettings settings = new CsvParserSettings();
                    settings.setNullValue("N/A");
                    settings.getFormat().setLineSeparator("\n");
                    CsvParser parser = new CsvParser(settings);
                    List<String[]> allRows = parser.parseAll(is);
                    allRows.remove(0);

                    for (String[] tempRow : allRows) {
                        if (tempRow.length != 21 || tempRow[1] == null)
                            continue;
                        CalendarDataModel dataModel = CalendarRawDataToCalendarDataModelParser.parse(tempRow);

                        if (calendarParsedData.get(dataModel.startDate) == null) {
                            List<CalendarDataModel> tempModel = new ArrayList<>();
                            tempModel.add(dataModel);
                            calendarParsedData.put(dataModel.startDate, tempModel);
                        } else {
                            calendarParsedData.get(dataModel.startDate).add(dataModel);
                            maxEventsInAllDates = (calendarParsedData.get(dataModel.startDate).size() > maxEventsInAllDates) ? calendarParsedData.get(dataModel.startDate).size() : maxEventsInAllDates;
                        }

                    }
                    adapter = new CalendarAllEventsListViewAdapter(getContext(), -1, fragment, maxEventsInAllDates);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            listView.setAdapter(adapter);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            errorAlertDialogBox.show();
                            /*Toast.makeText(getContext(),"Please Check your internet connection",Toast.LENGTH_SHORT).show();
                            onLoadingFail.setVisibility(View.VISIBLE);
                              */
                        }
                    });

                }


            }
        });
    }

    private void createErrorAlertDialogBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Please check your internet connection and hit refresh.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int id) {
                        dialog.show();
                        dataBackgroundParsingThread.start();
                    }
                });


        errorAlertDialogBox = builder1.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ColorCodeListAdapter adapter = new ColorCodeListAdapter(getContext(), -1);
        colorCodesDialogBox = new ColorCodesDialogBox(getContext(), adapter);
        createErrorAlertDialogBox();
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading.. ");
        dialog.setCancelable(false);

        dataBackgroundParsingThread = createBackgroundThreadForParsingRawCalendarData(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.each_calendar_item:
                int itemClickedPosition[] = (int[]) v.getTag();
                Intent calendarEventDetailIntent = new Intent(getContext(), CalendarEventDetailDisplayActivity.class);
                calendarEventDetailIntent.putExtra(CALENDAR_EVENT_ITEM_POSITION_KEY, itemClickedPosition[0]);
                calendarEventDetailIntent.putExtra(CALENDAR_EVENT_ITEM_SUB_POSITION_KEY, itemClickedPosition[1]);
                startActivity(calendarEventDetailIntent);
                break;
            default:
                dialog.show();

                dataBackgroundParsingThread.start();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calendar_fragment_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(android.R.color.black));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                if (adapter != null)
                    adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.color_code:

                colorCodesDialogBox.showDialog();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_calendar, container, false);
        listView = (ListView) layoutView.findViewById(R.id.calendar_list_view);

        if (calendarParsedData.size() == 0) {
            dialog.show();
            dataBackgroundParsingThread.start();
        } else {
            adapter = new CalendarAllEventsListViewAdapter(getContext(), -1, this, maxEventsInAllDates);
            listView.setAdapter(adapter);

        }
        // Inflate the layout for this fragment
        return layoutView;
    }


}
