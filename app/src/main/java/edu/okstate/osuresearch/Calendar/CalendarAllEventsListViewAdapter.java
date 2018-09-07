package edu.okstate.osuresearch.Calendar;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.okstate.osuresearch.Model.ColorCodesModel;
import edu.okstate.osuresearch.R;


/**
 * Created by appcenter on 10/29/17.
 */

public class CalendarAllEventsListViewAdapter extends ArrayAdapter {
    static HashMap<String, List<CalendarDataModel>> displayCalendarData = new LinkedHashMap<>();
    CalendarFragment calendarFragment;
    Context context;
    LayoutInflater inflater;
    CustomFilter filter;

    int maxEventsInAllDates;

    public CalendarAllEventsListViewAdapter(@NonNull Context context, @LayoutRes int resource, CalendarFragment fragment, int maxEventsInAllDates) {
        super(context, resource);
        this.context = context;
        this.calendarFragment = fragment;
        this.displayCalendarData = this.calendarFragment.calendarParsedData;
        this.maxEventsInAllDates = maxEventsInAllDates;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    @Override
    public int getCount() {
        return displayCalendarData.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return displayCalendarData.values().toArray()[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView != null)
            ((LinearLayout) convertView).removeAllViews();

        convertView = inflater.inflate(R.layout.calendar_all_events_list_view, parent, false);

        ArrayList<CalendarDataModel> allEventsOfParticularDate = ((ArrayList<CalendarDataModel>) getItem(position));
        CalendarDataModel dataModel = allEventsOfParticularDate.get(0);
        ((TextView) convertView.findViewById(R.id.event_day)).setText(dataModel.startDateDay);
        ((TextView) convertView.findViewById(R.id.event_month)).setText(dataModel.startDateMonth + dataModel.startDateYear);
        ((TextView) convertView.findViewById(R.id.event_week_day)).setText(dataModel.startDateWeekDay);
        LinearLayout eventsLinearLayout = (LinearLayout) convertView.findViewById(R.id.events_linear_layout);
        for (int i = 0; i < allEventsOfParticularDate.size(); i++) {
            View eachEventView = inflater.inflate(R.layout.calendar_all_events_list_view_each_item, parent, false);

            CalendarDataModel dataModel1 = allEventsOfParticularDate.get(i);
            LinearLayout layout = (LinearLayout) eachEventView.findViewById(R.id.each_calendar_item);

            int tag[] = new int[2];
            tag[0] = position;
            tag[1] = i;

            ((TextView) eachEventView.findViewById(R.id.event_start_time)).setText(dataModel1.startTimeInTwelveHourFormat);
            ((TextView) eachEventView.findViewById(R.id.event_subject)).setText(dataModel1.subject);
            int colorId;
            try {
                colorId = (int) ColorCodesModel.colorCodesHashMap.get(dataModel1.sponsor);
            } catch (Exception e) {
                colorId = R.color.colorCodeAnnualEvents;
            }
            Log.e("HELLO", "" + colorId);

            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            //shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
            shape.setStroke(8, context.getResources().getColor(R.color.strokeGray));
            shape.setColor(context.getResources().getColor(colorId));

            eachEventView.findViewById(R.id.event_color_coding).setBackground(shape);
            eachEventView.setTag(tag);
            eachEventView.setOnClickListener(calendarFragment);


            eventsLinearLayout.addView(eachEventView);

        }

        /*if(rowView==null)
        {
            rowView = inflater.inflate(R.layout.calendar_all_events_list_view,null);
            viewHolder =  new CalendarAllEventsItemViewHolder(1);
            viewHolder.day=(TextView)rowView.findViewById(R.id.event_day);
            viewHolder.weekDay = (TextView)rowView.findViewById(R.id.event_week_day);
            viewHolder.month = (TextView)rowView.findViewById(R.id.event_month);
            viewHolder.eventsLinearLayout = (LinearLayout)rowView.findViewById(R.id.events_linear_layout);
            for(int eachEvent=0;eachEvent<1; eachEvent++)
            {

                viewHolder.eachEventView = inflater.inflate(R.layout.calendar_all_events_list_view_each_item,null);
                viewHolder.eventsTime[eachEvent] = (TextView)viewHolder.eachEventView.findViewById(R.id.event_start_time);
                viewHolder.eventsSubject[eachEvent] =(TextView)viewHolder.eachEventView.findViewById(R.id.event_subject);
                viewHolder.colorCoderView[eachEvent] = (View) viewHolder.eachEventView.findViewById(R.id.event_color_coding);
                viewHolder.eventsSubject[eachEvent].setVisibility(View.GONE);
                viewHolder.eventsTime[eachEvent].setVisibility(View.GONE);
                viewHolder.colorCoderView[eachEvent].setVisibility(View.GONE);
                viewHolder.eventsLinearLayout.addView(viewHolder.eachEventView);
            }

            rowView.setTag(viewHolder);
        }else
            viewHolder = (CalendarAllEventsItemViewHolder)rowView.getTag();



        ArrayList<CalendarDataModel> allEventsOfParticularDate= ((ArrayList<CalendarDataModel>)getItem(position));
        CalendarDataModel dataModel = allEventsOfParticularDate.get(0);
        viewHolder.day.setText(dataModel.startDateDay);
        viewHolder.month.setText(dataModel.startDateMonth+dataModel.startDateYear);
        viewHolder.weekDay.setText(dataModel.startDateWeekDay);

        for(int eachView=allEventsOfParticularDate.size();eachView<viewHolder.eventsTime.length;eachView++)
        {
            viewHolder.eventsSubject[eachView].setVisibility(View.GONE);
            viewHolder.eventsTime[eachView].setVisibility(View.GONE);
            viewHolder.colorCoderView[eachView].setVisibility(View.GONE);
        }
        for(int eachEvent=0;eachEvent<allEventsOfParticularDate.size(); eachEvent++) {
            CalendarDataModel dataModel1 = allEventsOfParticularDate.get(eachEvent);
            viewHolder.eventsTime[eachEvent].setText(dataModel1.startTimeInTwelveHourFormat);
            viewHolder.eventsSubject[eachEvent].setText(dataModel1.subject);
            viewHolder.eventsSubject[eachEvent].setVisibility(View.VISIBLE);
            viewHolder.eventsTime[eachEvent].setVisibility(View.VISIBLE);
            viewHolder.colorCoderView[eachEvent].setVisibility(View.VISIBLE);

        }*/
        return convertView;
    }

    class CalendarAllEventsItemViewHolder {
        TextView weekDay, day, month;
        TextView eventsTime[];
        LinearLayout eventsLinearLayout;
        TextView eventsSubject[];
        View colorCoderView[];
        View eachEventView;

        CalendarAllEventsItemViewHolder(int numberOfEvents) {

            eventsTime = new TextView[numberOfEvents];
            eventsSubject = new TextView[numberOfEvents];
            colorCoderView = new View[numberOfEvents];
        }
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                //If there is only one word
                if (constraint.toString().split("\\s").length == 1)
                    constraint = constraint.toString().trim();
                HashMap<String, List<CalendarDataModel>> filters = new LinkedHashMap<>();
                for (int position = 0; position < calendarFragment.calendarParsedData.size(); position++) {
                    List<CalendarDataModel> listItem = new ArrayList<>();
                    ArrayList<CalendarDataModel> allEventsOfParticularDate = ((ArrayList<CalendarDataModel>) calendarFragment.calendarParsedData.values().toArray()[position]);
                    for (int dates = 0; dates < allEventsOfParticularDate.size(); dates++) {
                        String sponsorFieldString = allEventsOfParticularDate.get(dates).sponsor;
                        String descriptionString = allEventsOfParticularDate.get(dates).description != null ? allEventsOfParticularDate.get(dates).description.toUpperCase() : "";
                        String titleString = (allEventsOfParticularDate.get(dates).subject != null) ? allEventsOfParticularDate.get(dates).subject.toUpperCase() : "";

                        if (sponsorFieldString.contains(constraint) || descriptionString.contains(constraint) || titleString.contains(constraint)) {
                            listItem.add(allEventsOfParticularDate.get(dates));
                        }
                    }
                    if (listItem.size() != 0)
                        filters.put(allEventsOfParticularDate.get(0).startDate, listItem);
                }
                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = calendarFragment.calendarParsedData.size();
                results.values = calendarFragment.calendarParsedData;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            displayCalendarData = (HashMap<String, List<CalendarDataModel>>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
