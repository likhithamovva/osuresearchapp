package edu.okstate.osuresearch.ResearchWeek;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.okstate.osuresearch.Calendar.ColorCodeListAdapter;
import edu.okstate.osuresearch.Calendar.ColorCodesDialogBox;
import edu.okstate.osuresearch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchWeekFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    static final String RESEARCH_WEEK_EVENT_ITEM_POSITION_KEY = "edu.okstate.osuresearchmatters.CALENDAR_EVENT_ITEM_POSITION_KEY";
    static final String RESEARCH_WEEK_EVENT_ITEM_SUB_POSITION_KEY = "edu.okstate.osuresearchmatters.CALENDAR_EVENT_ITEM_SUB_POSITION_KEY";
    static ResearchWeekFragment fragment;
    static final String URLToFetchCalendarData = "http://www.trumba.com/calendars/okstate-research-week.csv";
    HashMap<String, List<ResearchWeekDataModel>> researchWeekParsedData = new LinkedHashMap<>();
    Thread dataBackgroundParsingThread;
    ProgressDialog dialog;
    Button onLoadingFail;
    ListView researchWeekDateListView;
    ListView researchWeekDetailListView;
    ColorCodesDialogBox colorCodesDialogBox;
    int maxEventsInAllDates = 0;
    AlertDialog errorAlertDialogBox, errorAlertDialogBox1;
    ResearchWeekEachEventDetailListAdapter eachEventDetailListAdapter = null;

    public ResearchWeekFragment() {
        // Required empty public constructor
        fragment = this;
    }

    private void createErrorAlertDialogBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Please check your internet connection and hit refresh.");

        builder1.setPositiveButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int id) {
                        dataBackgroundParsingThread.start();
                    }
                });

        errorAlertDialogBox = builder1.create();
    }

    private Thread createBackgroundThreadForParsingRawCalendarData(final ResearchWeekFragment fragment) {
        return new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //http://www.trumba.com/calendars/okstate-stillwater.csv
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

                    if (allRows.size() > 1) {

                        ResearchWeekKeyCheck check = new ResearchWeekKeyCheck();

                        for (String key : allRows.remove(0)) {
                            if (key.contains("Subject")) {
                                check.subject = true;
                            } else if (key.contains("Start Date")) {
                                check.startDate = true;
                            } else if (key.contains("Start Time")) {
                                check.startTime = true;
                            } else if (key.contains("End Date")) {
                                check.endDate = true;
                            } else if (key.contains("End Time")) {
                                check.endTime = true;
                            } else if (key.contains("All day event")) {
                                check.allDayEvent = true;
                            } else if (key.contains("Description")) {
                                check.description = true;
                            } else if (key.contains("Location")) {
                                check.location = true;
                            } else if (key.contains("Web Link")) {
                                check.webLink = true;
                            } else if (key.contains("Event Template")) {
                                check.eventTemplate = true;
                            } else if (key.contains("Audience")) {
                                check.audience = true;
                            } else if (key.contains("Building")) {
                                check.building = true;
                            } else if (key.contains("Campus")) {
                                check.campus = true;
                            } else if (key.contains("Category")) {
                                check.category = true;
                            } else if (key.contains("Contact Email")) {
                                check.contactEmail = true;
                            } else if (key.contains("Contact Name")) {
                                check.contactName = true;
                            } else if (key.contains("Contact Phone Number")) {
                                check.contactPhoneNumber = true;
                            } else if (key.contains("Cost")) {
                                check.cost = true;
                            } else if (key.contains("Event Image")) {
                                check.eventImage = true;
                            } else if (key.contains("Room")) {
                                check.room = true;
                            } else if (key.contains("Sponsor")) {
                                check.sponsor = true;
                            } else if (key.contains("Type Of Event")) {
                                check.typeOfEvent = true;
                            }
                        }

                        for (String[] tempRow : allRows) {
                            Log.e("tempRow", Arrays.deepToString(tempRow));
                            Log.e("tempRow", tempRow.length + "");
                            if (tempRow.length != 10 || tempRow[1] == null)
                                continue;
                            Log.e("ParsingDate", tempRow.length + " " + tempRow[0]);

                            ResearchWeekDataModel dataModel = ResearchWeekRawDataToResearchWeekDataModelParser.parse(tempRow, check);

                            if (researchWeekParsedData.get(dataModel.startDate) == null) {
                                List<ResearchWeekDataModel> tempModel = new ArrayList<>();
                                tempModel.add(dataModel);
                                researchWeekParsedData.put(dataModel.startDate, tempModel);
                            } else {
                                researchWeekParsedData.get(dataModel.startDate).add(dataModel);
                                maxEventsInAllDates = (researchWeekParsedData.get(dataModel.startDate).size() > maxEventsInAllDates) ? researchWeekParsedData.get(dataModel.startDate).size() : maxEventsInAllDates;
                            }
                        }
                        final ResearchWeekAllDatesListAdapter adapter = new ResearchWeekAllDatesListAdapter(getContext(), -1, fragment, maxEventsInAllDates);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                onLoadingFail.setVisibility(View.GONE);
                                researchWeekDateListView.setAdapter(adapter);
                                researchWeekDateListView.performItemClick(researchWeekDateListView.getChildAt(0), 0, researchWeekDateListView.getFirstVisiblePosition());
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                builder2.setMessage("No avialable dates to show on Research Week");
                                builder2.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface d, int id) {
                                                errorAlertDialogBox1.dismiss();
                                            }
                                        });
                                errorAlertDialogBox1 = builder2.create();
                                errorAlertDialogBox1.show();

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            errorAlertDialogBox.show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createErrorAlertDialogBox();
        ColorCodeListAdapter adapter = new ColorCodeListAdapter(getContext(), -1);
        colorCodesDialogBox = new ColorCodesDialogBox(getContext(), adapter);
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading..");
        dialog.setCancelable(false);
        onLoadingFail = new Button(getContext());
        onLoadingFail.setText("Refresh");
        onLoadingFail.setOnClickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        onLoadingFail.setLayoutParams(params);
        onLoadingFail.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(android.R.drawable.ic_menu_rotate), null, null, null);
        dataBackgroundParsingThread = createBackgroundThreadForParsingRawCalendarData(this);
    }

    @Override
    public void onClick(View v) {
        dialog.show();
        dataBackgroundParsingThread.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calendar_fragment_menu, menu);
        menu.findItem(R.id.search).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        colorCodesDialogBox.showDialog();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (eachEventDetailListAdapter == null) {
            eachEventDetailListAdapter = new ResearchWeekEachEventDetailListAdapter(getContext(), -1, this, maxEventsInAllDates);
            researchWeekDetailListView.setAdapter(eachEventDetailListAdapter);
        }
        eachEventDetailListAdapter.setPosition(position);
        eachEventDetailListAdapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View layoutView = inflater.inflate(R.layout.fragment_research_week, container, false);
        researchWeekDateListView = (ListView) layoutView.findViewById(R.id.research_week_dates);
        researchWeekDateListView.setOnItemClickListener(this);
        researchWeekDetailListView = (ListView) layoutView.findViewById(R.id.research_week_detail);
        researchWeekDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent calendarEventDetailIntent = new Intent(getContext(), ResearchWeekDetailActivity.class);
                calendarEventDetailIntent.putExtra(RESEARCH_WEEK_EVENT_ITEM_POSITION_KEY, eachEventDetailListAdapter.position);
                calendarEventDetailIntent.putExtra(RESEARCH_WEEK_EVENT_ITEM_SUB_POSITION_KEY, i);
                startActivity(calendarEventDetailIntent);


            }
        });
        if (researchWeekParsedData.size() == 0) {

            RelativeLayout relativeLayout = (RelativeLayout) layoutView.findViewById(R.id.calendar_list_view_main_layout);
            //frelativeLayout.addView(onLoadingFail);

            dialog.show();
            dataBackgroundParsingThread.start();
        } else {
            final ResearchWeekAllDatesListAdapter adapter = new ResearchWeekAllDatesListAdapter(getContext(), -1, this, maxEventsInAllDates);
            researchWeekDateListView.setAdapter(adapter);
            researchWeekDetailListView.setAdapter(eachEventDetailListAdapter);
            researchWeekDateListView.performItemClick(researchWeekDateListView.getChildAt(0), 0, researchWeekDateListView.getFirstVisiblePosition());
        }
        // Inflate the layout for this fragment
        return layoutView;


    }

}
