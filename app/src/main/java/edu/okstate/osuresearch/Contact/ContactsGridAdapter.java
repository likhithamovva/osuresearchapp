package edu.okstate.osuresearch.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 9/15/17.
 */

public class ContactsGridAdapter extends BaseAdapter {

    int width;
    private Context context;
    private String data[];

    public ContactsGridAdapter(Context context, int width, String[] data) {
        this.context = context;
        this.width = width;
        this.data = data;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridNames.length
        return data.length;
    }

    @Override
    public Object getItem(int position) {

        return data[position];
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {
        // LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            // get layout from bottom_navigation_item.xml ( Defined Below )
            gridView = inflater.inflate(R.layout.contacts_grid_item, null);
            // set value into text view

            TextView textView = (TextView) gridView
                    .findViewById(R.id.cuboidButton);
            textView.setText((String) getItem(position));
            textView.setWidth(width);
            textView.setHeight(width);
        } else {
            gridView = convertView;
        }
        return gridView;
    }
}