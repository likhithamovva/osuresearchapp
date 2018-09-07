package edu.okstate.osuresearch.Contact;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import edu.okstate.osuresearch.R;

import static edu.okstate.osuresearch.Contact.ContactFragment.contacts;

public class ContactWebViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String addresses[] = new String[]{
            "   203 Whitehurst          ",
            "   223 Scott Hall          ",
            "   206 Whitehurst          ",
            "   101 McElroy Hall Annex  ",
            "   106 Math Science        ",
            "   415 Whitehurst          ",
            "   120 SC HBRC             ",
            "   1201 S.Innovation Way   ",
            "   1110 S.Innovation Way   "
    };
    ContactsListAdapter contactsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_web_view);
        int orange = getResources().getColor(android.R.color.holo_orange_dark);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"" + htmlColor + "\"><b>OSU</b> RESEARCH</font>"));
        Intent intent = getIntent();
        int gridViewClickedPosition = intent.getIntExtra(ContactFragment.CLICKED_POSITION_GRID_VIEW_KEY, -1);
        int gridViewRowClickedPosition = intent.getIntExtra(ResearchComplianceContacts.CLICKED_POSITION_GRID_VIEW_KEY, 0);
        // Inflate the layout for this fragment

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int padding = 30;
        final int r = (metrics.widthPixels / 3) - padding;
        final TextView circularTextView = (TextView) findViewById(R.id.contact_name);
        circularTextView.setText(contacts[gridViewClickedPosition]);
        final TextView clickedContactFullName = (TextView) findViewById(R.id.clicked_contact_fullName);
        final TextView clikedContactAddress = (TextView) findViewById(R.id.clicked_contact_address);
        final ImageView contactImageView = (ImageView) findViewById(R.id.contact_image);
        final ListView contactsListView = (ListView) findViewById(R.id.contacts_list_view);
        contactsListAdapter = new ContactsListAdapter(this, -1, gridViewClickedPosition, gridViewRowClickedPosition, (metrics.widthPixels / 5));
        clickedContactFullName.setText(contactsListAdapter.getContactName(0));
        clikedContactAddress.setText(addresses[gridViewClickedPosition]);
        contactsListView.setOnItemClickListener(this);
        contactsListView.setAdapter(contactsListAdapter);
        circularTextView.setHeight(r);
        circularTextView.setWidth(r);


        contactImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contactImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                RelativeLayout.LayoutParams imageViewLayoutParameters = new RelativeLayout.LayoutParams(contactImageView.getWidth(), (contactImageView.getHeight() - r / 2));
                contactImageView.setLayoutParams(imageViewLayoutParameters);

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("CLICKED", position + "");
        switch (position) {
            case 0:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactsListAdapter.getContactPhoneNumber(position)));
                startActivity(intent);
                break;
            case 1:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + contactsListAdapter.getContactOfficeEmails(position)));
                startActivity(emailIntent);
                break;
        }
    }
}
