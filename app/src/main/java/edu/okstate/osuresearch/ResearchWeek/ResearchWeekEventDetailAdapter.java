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
 * Created by krishna on 11/29/2017.
 */

public class ResearchWeekEventDetailAdapter extends ArrayAdapter {
    public String dataDetail[] = {"Description", "Title", "Start Time", "End Time", "Location", "Category", "Contact Name", "Contact Phone", "Contact Email", "Sponsor", "Link"};
    Context context;
    int clickedPosition;
    int clickedSubPosition;

    public ResearchWeekEventDetailAdapter(@NonNull Context context, @LayoutRes int resource, int clickedPosition, int clickedSubPosition) {

        super(context, resource);
        this.context = context;
        this.clickedPosition = clickedPosition;
        this.clickedSubPosition = clickedSubPosition;
    }

    public int getCount() {
        ArrayList<ResearchWeekDataModel> allEventsOfParticularDate = ((ArrayList<ResearchWeekDataModel>) (ResearchWeekFragment.fragment.researchWeekParsedData.values().toArray()[clickedPosition]));

        return allEventsOfParticularDate.get(clickedSubPosition).allData.size();
    }

    @Nullable

    public Object getItem(int position) {
        ArrayList<ResearchWeekDataModel> allEventsOfParticularDate = ((ArrayList<ResearchWeekDataModel>) (ResearchWeekFragment.fragment.researchWeekParsedData.values().toArray()[clickedPosition]));

        return allEventsOfParticularDate.get(clickedSubPosition).allData.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ResearchWeekEventDetailAdapter.DetailEventListAdapter detailEventListAdapter;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.detail_event_list_item, null);
            detailEventListAdapter = new ResearchWeekEventDetailAdapter.DetailEventListAdapter();
            detailEventListAdapter.eventItemName = (TextView) rowView.findViewById(R.id.event_name);
            detailEventListAdapter.eventItemDetail = (TextView) rowView.findViewById(R.id.event_detail);
            rowView.setTag(detailEventListAdapter);
        } else
            detailEventListAdapter = (ResearchWeekEventDetailAdapter.DetailEventListAdapter) rowView.getTag();
        String itemDetail = (String) getItem(position);
        if (itemDetail == null)
            itemDetail = "N/A";
        detailEventListAdapter.eventItemName.setText(dataDetail[position]);
        detailEventListAdapter.eventItemDetail.setText(itemDetail.replace("&nbsp;", "").replace("&amp", ""));
        return rowView;
    }

    class DetailEventListAdapter {
        TextView eventItemName, eventItemDetail;
    }
}
