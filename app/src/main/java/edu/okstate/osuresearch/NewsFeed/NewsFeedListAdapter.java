package edu.okstate.osuresearch.NewsFeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 12/13/17.
 */

public class NewsFeedListAdapter extends ArrayAdapter {
    List<Node> xmlPasedNodes;
    List<Bitmap> thumbnails = new ArrayList<Bitmap>();
    Context context;
    SimpleDateFormat dateFormat;

    public NewsFeedListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        xmlPasedNodes = new ArrayList<>();
        dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    }

    // getNode function
    public static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (sTag.equals("media:thumbnail"))
            return eElement.getElementsByTagName(sTag).item(0).getAttributes().item(0).getNodeValue();
        //Log.e("NEWS", eElement.getElementsByTagName(sTag).item(0).hasAttributes() + "");
        try {
            return nValue.getNodeValue();
        } catch (NullPointerException ignored) {
            return "";
        }
    }

    @Override
    public int getCount() {
        return xmlPasedNodes.size();
    }

    public void addBitmap(Bitmap logo) {
        thumbnails.add(logo);
    }

    public void addNode(Node n) {
        xmlPasedNodes.add(n);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsFeedItemHolder holder;
        if (convertView == null) {
            holder = new NewsFeedItemHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.news_feed_list_item, null);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.postDate = (TextView) convertView.findViewById(R.id.posted_date);
            convertView.setTag(holder);

        } else
            holder = (NewsFeedItemHolder) convertView.getTag();

        Node nNode = xmlPasedNodes.get(position);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) nNode;
            holder.title.setText(getNode("title", element));
            try {
                Date publishDate = new SimpleDateFormat("yyyy-MM-dd").parse(getNode("displayDate", element).split("T")[0]);
                holder.postDate.setText(dateFormat.format(publishDate));

            } catch (ParseException e) {
                Log.e("ERROR", e.toString());

            }

            holder.description.setText(getNode("description", element));
            holder.thumbnail.setImageBitmap(thumbnails.get(position));

        }

        return convertView;
    }

    public class NewsFeedItemHolder {
        TextView title, description, postDate;
        ImageView thumbnail;
    }

}
