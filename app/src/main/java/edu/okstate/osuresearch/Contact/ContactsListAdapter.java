package edu.okstate.osuresearch.Contact;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 10/28/17.
 */

public class ContactsListAdapter extends ArrayAdapter {
    static final String contactOfficeNames[][] = {{"Vice President of Research"},
            {"Research Compliance", "Institutional Animal Care and Use Committee (IACUC)", "Institutional Review Board (IRB)", "Radiation and Laser Safety", "Biological\nSafety"},
            {"Research Services"},
            {"Animal Resources"},
            {"High Performance Computing Center"},
            {"Established Program to Stimulate Competitive Research"},
            {"Proposal Development"},
            {"Technology Development Center"},
            {"Microscopy Laboratory"}
    };
    final Context context;
    final String contactPhoneNumbers[][] = {{"405.744.6501"},
            {"405.744.1676", "405.744.3592", "405.744.5700", "405.744.7890", "405.744.3203"},
            {"405.744.9991"},
            {"405.744.7631"},
            {"405.744.1914"},
            {"405.744.9964"},
            {"405.744.3660"},
            {"405.744.6930"},
            {"405.744.6765"}
    };
    final String contactOfficeEmails[][] = {{"vpr@okstate.edu"},
            {"rdiana@okstate.edu", "iacuc@okstate.edu", "irb@okstate.edu", "radsafe@okstate.edu", "ibc@okstate.edu"},
            {"research@okstate.edu"},
            {"Research.animals@okstate.edu"},
            {"hpcc@okstate.edu"},
            {"ok.nsf.epscor@okepscor.org"},
            {"Nani.pybus@okstate.edu"},
            {"tdc@okstate.edu"},
            {"microscopy@okstate.edu"}
    };
    int resource;
    int row;
    int column;

    public ContactsListAdapter(@NonNull Context context, @LayoutRes int resource, int position, int column, int equalWidthHeightImageButton) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
        row = position;
        this.column = column;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public String getContactName(int position) {
        return contactOfficeNames[row][column];
    }

    public String getContactPhoneNumber(int position) {
        return contactPhoneNumbers[row][column];
    }

    public String getContactOfficeEmails(int position) {
        return contactOfficeEmails[row][column];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            // get layout from bottom_navigation_item.xml ( Defined Below )
            convertView = inflater.inflate(R.layout.contacts_activity_list_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
            viewHolder.contactPhoneNumber = (TextView) convertView.findViewById(R.id.contact_phone_number);
            // viewHolder.contactOffice = (TextView) convertView.findViewById(R.id.contact_office);
            //viewHolder.contactEmail = (TextView) convertView.findViewById(R.id.contact_email);
            viewHolder.phoneButton = (ImageButton) convertView.findViewById(R.id.phone_button);
            //viewHolder.emailButton = (ImageButton)convertView.findViewById(R.id.email_button);
            viewHolder.phoneButton.setTag(position);
            final ItemViewHolder tempItemView = viewHolder;

            viewHolder.phoneButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int equalWidthHeightImageButton = Math.max(tempItemView.phoneButton.getWidth(), tempItemView.phoneButton.getHeight());
                    LinearLayout.LayoutParams imageButtonLayoutParameters = new LinearLayout.LayoutParams(equalWidthHeightImageButton, equalWidthHeightImageButton);

                    imageButtonLayoutParameters.gravity = Gravity.CENTER_VERTICAL;


                    tempItemView.phoneButton.setLayoutParams(imageButtonLayoutParameters);
                    tempItemView.phoneButton.setLayoutParams(imageButtonLayoutParameters);

                }
            });
            convertView.setTag(viewHolder);

        } else
            viewHolder = (ItemViewHolder) convertView.getTag();
        if (position == 0) {
            //viewHolder.contactName.setText(getContactName(position) + "");
            viewHolder.contactPhoneNumber.setText(getContactPhoneNumber(position));
            viewHolder.phoneButton.setImageResource(R.drawable.ic_phone);
        } else {
            //viewHolder.contactName.setText(getContactName(position) + "");
            viewHolder.contactPhoneNumber.setText(getContactOfficeEmails(position));
            viewHolder.phoneButton.setImageResource(R.drawable.ic_mail);

        }




        return convertView;
    }

    public class ItemViewHolder {
        TextView contactName, contactPhoneNumber;
        ImageButton phoneButton;
    }
}
