package edu.okstate.osuresearch;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static edu.okstate.osuresearch.MainActivity.customTabItemImages;

/**
 * Created by appcenter on 12/15/17.
 */

public class HomeScreenListAdapter extends ArrayAdapter {
    String itemNames[] = {"News Feed", "Contacts", "Calendar", "Research\nWeek", "Social"};
    Context ctxt;

    public HomeScreenListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        ctxt = context;
    }

    @Override
    public int getCount() {
        return customTabItemImages.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeScreenViewHolder holder;
        if (convertView == null) {
            holder = new HomeScreenViewHolder();
            LayoutInflater inflater = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_screen_list_item, null);
            holder.itemImage = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.text = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else
            holder = (HomeScreenViewHolder) convertView.getTag();
        holder.itemImage.setImageResource(customTabItemImages[position]);
        holder.text.setText(itemNames[position]);
        return convertView;
    }

    class HomeScreenViewHolder {
        ImageView itemImage;
        TextView text;
    }
}
