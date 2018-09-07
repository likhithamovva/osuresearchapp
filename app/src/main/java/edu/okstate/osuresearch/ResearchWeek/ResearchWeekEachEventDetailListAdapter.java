package edu.okstate.osuresearch.ResearchWeek;

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
import android.widget.TextView;

import java.util.ArrayList;
import edu.okstate.osuresearch.Model.ColorCodesModel;
import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 11/13/17.
 */

public class ResearchWeekEachEventDetailListAdapter extends ArrayAdapter {
    ResearchWeekFragment fragment;
    Context context;
    LayoutInflater inflater;
    int position;

    public ResearchWeekEachEventDetailListAdapter(@NonNull Context context, @LayoutRes int resource, ResearchWeekFragment fragment, int maxEventsInAllDates) {
        super(context, resource);
        this.context = context;
        this.fragment = fragment;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCount() {
            return ((ArrayList<ResearchWeekDataModel>) fragment.researchWeekParsedData.values().toArray()[position]).size();

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return ((ArrayList<ResearchWeekDataModel>) fragment.researchWeekParsedData.values().toArray()[this.position]).get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResearchWeekEachEventDetailViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.research_week_detail_list_item, parent, false);
            holder = new ResearchWeekEachEventDetailViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.research_week_event_name);
            holder.eventLocation = (TextView) convertView.findViewById(R.id.research_week_event_location);
            holder.eventTime = (TextView) convertView.findViewById(R.id.research_week_event_start_time);
            holder.eventColor = (TextView) convertView.findViewById(R.id.event_color);
            convertView.setTag(holder);
        } else
            holder = (ResearchWeekEachEventDetailViewHolder) convertView.getTag();

        ResearchWeekDataModel dataModel = (ResearchWeekDataModel) getItem(position);
        holder.eventTime.setText(dataModel.startTimeInTwelveHourFormat + "-" + dataModel.endTimeInTwelveHourFormat);
        holder.eventName.setText(dataModel.subject);
        holder.eventLocation.setText(dataModel.location);
        Log.e("SPONSOR", dataModel.sponsor);
        int colorId;
        try {
            colorId = ColorCodesModel.colorCodesHashMap.get(dataModel.sponsor);
        } catch (Exception e) {
            colorId = R.color.colorCodeAnnualEvents;
        }
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        //shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
        shape.setStroke(8, context.getResources().getColor(R.color.strokeGray));
        shape.setColor(context.getResources().getColor(colorId));
        holder.eventColor.setBackground(shape);

        return convertView;
    }

    class ResearchWeekEachEventDetailViewHolder {
        TextView eventTime, eventName, eventLocation, eventColor;
    }
}
