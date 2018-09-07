package edu.okstate.osuresearch.NewsFeed;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import edu.okstate.osuresearch.R;


public class NewsFeedFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static String NEWS_FEED_ITEM_WEB_URL_KEY = "edu.okstate.osuresearchmatters.NEWS_FEED_ITEM_WEB_URL_KEY";
    String researchNewsURL = "https://omnisandbox2.okstate.edu/_resources/php/news/rss-svc.php?tags=Research&count=5&offset=";
    int offset = 0;
    ProgressDialog pDialog;
    Button btnLoadMore;
    AlertDialog errorAlertDialog;
    NewsFeedListAdapter newsFeedListAdapter = null;
    ListView listView;
    SwipeRefreshLayout swipeLayout = null;
    TextView onErrorTextView;

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    private void createErrorAlertDialogBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Please check your internet connection and hit refresh.");

        builder1.setPositiveButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int id) {
                        onRefresh();
                        Log.e("ERROR", "REFERSH");
                    }
                });

        errorAlertDialog = builder1.create();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createErrorAlertDialogBox();
        newsFeedListAdapter = new NewsFeedListAdapter(getContext(), -1);

        btnLoadMore = new Button(getContext());
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        btnLoadMore.setText("Load More");
        onErrorTextView = new TextView(getActivity());
        onErrorTextView.setText("Please check your internet connection. Swipe to refresh");
        onErrorTextView.setTextColor(Color.BLACK);
        onErrorTextView.setGravity(Gravity.CENTER);
        new DownloadXML().execute(researchNewsURL);


    }

    @Override
    public void onRefresh() {
        new DownloadXML().execute((researchNewsURL + offset));
        Log.e("ERROR", (researchNewsURL + offset));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SingleNewsItemDescription.class);
        Node nNode = newsFeedListAdapter.xmlPasedNodes.get(position);
        Element element = (Element) nNode;
        String URL = newsFeedListAdapter.getNode("link", element);
        intent.putExtra(NEWS_FEED_ITEM_WEB_URL_KEY, URL);
        startActivity(intent);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        listView = (ListView) view.findViewById(R.id.news_feed_list_view);
        listView.setOnItemClickListener(this);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
        listView.addFooterView(btnLoadMore);
        if (newsFeedListAdapter != null)
            listView.setAdapter(newsFeedListAdapter);
        return view;
    }

    private class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (swipeLayout != null)
                swipeLayout.setRefreshing(false);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setTitle("Loading...");
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                URL url = new URL(params[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new InputSource(url.openStream()));
                document.getDocumentElement().normalize();
                NodeList nodeList = document.getElementsByTagName("item");
                if (nodeList.getLength() != 0) {
                    for (int temp = 0; temp < nodeList.getLength(); temp++) {
                        Node nNode = nodeList.item(temp);
                        newsFeedListAdapter.addNode(nNode);
                        Element element = (Element) nNode;
                        String bitmapUrl = NewsFeedListAdapter.getNode("media:thumbnail", element);
                        if (bitmapUrl == null || bitmapUrl.length() == 0) {
                            newsFeedListAdapter.addBitmap(null);
                        } else {
                            InputStream is = new URL(bitmapUrl).openStream();
                            newsFeedListAdapter.addBitmap(BitmapFactory.decodeStream(is));
                        }
                    }
                    offset += 5;
                } else {
                    offset -= 5;

                }
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());

                e.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if (offset != newsFeedListAdapter.getCount()) {
                Toast.makeText(getContext(), "Please check your internet connection and press load more", Toast.LENGTH_LONG).show();
            }


            if (newsFeedListAdapter != null)
                listView.setAdapter(newsFeedListAdapter);
            if (swipeLayout != null)
                swipeLayout.setRefreshing(false);
            pDialog.hide();


        }
    }


}
