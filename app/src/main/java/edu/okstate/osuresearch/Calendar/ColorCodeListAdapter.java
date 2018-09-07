package edu.okstate.osuresearch.Calendar;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 11/12/17.
 */

public class ColorCodeListAdapter extends ArrayAdapter {
    static int colorCodes[] = new int[]{
            R.color.colorCodeVPROffice,
            R.color.colorCodeAnnualEvents,
            R.color.colorCodeArts,
            R.color.colorCodeBusiness,
            R.color.colorCodeEducation,
            R.color.colorCodeEngineering,
            R.color.colorCodeHumanSciences,
            R.color.colorCodeVeternity,
            R.color.colorCodeGradCollege,
            R.color.colorCodeLibrary,
            R.color.colorCodeHealthSciences
    };
    Context context;
    String departmentNames[] = new String[]{
            "VPR Office And Signature Events",
            "Divison Of Agricutural Sciences & Natural Resources",
            "Arts & Sciences",
            "Business",
            "Education",
            "College of Engineering,Architecture and Technology",
            "Human Sciences",
            "Center for Veterinary Health Sciences",
            "Grad College",
            "Library",
            "Center for Health Sciences"
    };

    public ColorCodeListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return super.getItem(position);
    }

    ;

    @Override
    public int getCount() {
        return colorCodes.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ColorCodeViewHolder holder;
        if (convertView == null) {
            holder = new ColorCodeViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.color_code_list_item, parent, false);
            holder.departmentName = (TextView) convertView.findViewById(R.id.department_name);
            holder.colorCode = (TextView) convertView.findViewById(R.id.department_color);

            convertView.setTag(holder);
        } else
            holder = (ColorCodeViewHolder) convertView.getTag();
        holder.departmentName.setText(departmentNames[position]);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setStroke(8, context.getResources().getColor(R.color.strokeGray));
        shape.setColor(context.getResources().getColor(colorCodes[position]));
        holder.colorCode.setBackground(shape);

        return convertView;
    }

    class ColorCodeViewHolder {
        TextView departmentName;
        View colorCode;
    }
}
