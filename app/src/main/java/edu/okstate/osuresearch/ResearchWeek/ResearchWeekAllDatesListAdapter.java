package edu.okstate.osuresearch.ResearchWeek;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 11/13/17.
 */

public class ResearchWeekAllDatesListAdapter extends ArrayAdapter {
    ResearchWeekFragment fragment;
    Context context;
    LayoutInflater inflater;

    public ResearchWeekAllDatesListAdapter(@NonNull Context context, @LayoutRes int resource, ResearchWeekFragment fragment, int maxEventsInAllDates) {
        super(context, resource);
        this.context = context;
        this.fragment = fragment;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return fragment.researchWeekParsedData.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return fragment.researchWeekParsedData.values().toArray()[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResearchWeekListViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.research_week_date_list_item, parent, false);
            holder = new ResearchWeekListViewHolder();
            holder.day = (TextView) convertView.findViewById(R.id.event_day);
            holder.weekDay = (TextView) convertView.findViewById(R.id.event_week_day);
            holder.month = (TextView) convertView.findViewById(R.id.event_month);
            convertView.setTag(holder);
        } else
            holder = (ResearchWeekListViewHolder) convertView.getTag();

        ArrayList<ResearchWeekDataModel> allEventsOfParticularDate = ((ArrayList<ResearchWeekDataModel>) getItem(position));
        ResearchWeekDataModel dataModel = allEventsOfParticularDate.get(0);
        holder.day.setText(dataModel.startDateDay);
        holder.month.setText(dataModel.startDateMonth + dataModel.startDateYear);
        holder.weekDay.setText(dataModel.startDateWeekDay);

        return convertView;
    }

    class ResearchWeekListViewHolder {
        TextView weekDay, day, month;
    }
}
