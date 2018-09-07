package edu.okstate.osuresearch.Contact;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import edu.okstate.osuresearch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements AdapterView.OnItemClickListener {
    static final String CLICKED_POSITION_GRID_VIEW_KEY = "edu.okstate.osuresearchmatters.Contact.CLICK_KEY_POSITION";
    static final String[] contacts = new String[]{
            "VPR", "Research Compliance", "Research Services", "Animal Resources",
            "HPCC", "EPSCoR", "Proposal Development", "Technology Development Center", "Microscopy Laboratory"
    };
    private GridView contactsGridView;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        contactsGridView = (GridView) v.findViewById(R.id.contacts_grid_view);
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

        int padding = 30;
        int r = (metrics.widthPixels / 3) - padding;
        ContactsGridAdapter contactsGridAdapter = new ContactsGridAdapter(getContext(), r, contacts);
        contactsGridView.setAdapter(contactsGridAdapter);
        contactsGridView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ContactWebViewActivity.class);
        if (position == 1) {
            intent = new Intent(getContext(), ResearchComplianceContacts.class);
        }
        intent.putExtra(CLICKED_POSITION_GRID_VIEW_KEY, position);
        startActivity(intent);
    }
}
