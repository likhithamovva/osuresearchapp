package edu.okstate.osuresearch.Contact;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Locale;

import edu.okstate.osuresearch.R;


public class ResearchComplianceContacts extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String CLICKED_POSITION_GRID_VIEW_KEY = "edu.okstate.osuresearchmatters.ResearchComplianceContacts.CLICK_KEY_POSITION";
    private GridView contactsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b> OSU</b>RESEARCH</font>"));

        contactsGridView = (GridView) findViewById(R.id.contacts_grid_view);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int padding = 30;
        int r = (metrics.widthPixels / 3) - padding;
        ContactsGridAdapter researchComplianceAdapter = new ContactsGridAdapter(this, r, ContactsListAdapter.contactOfficeNames[1]);
        contactsGridView.setAdapter(researchComplianceAdapter);
        contactsGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ContactWebViewActivity.class);
        intent.putExtra(ContactFragment.CLICKED_POSITION_GRID_VIEW_KEY, 1);
        intent.putExtra(ResearchComplianceContacts.CLICKED_POSITION_GRID_VIEW_KEY, position);
        startActivity(intent);
    }

}
