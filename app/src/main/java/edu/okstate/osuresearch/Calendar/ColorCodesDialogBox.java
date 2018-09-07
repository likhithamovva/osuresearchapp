package edu.okstate.osuresearch.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import edu.okstate.osuresearch.R;

/**
 * Created by appcenter on 11/12/17.
 */

public class ColorCodesDialogBox {
    private Dialog dialogBox;

    public ColorCodesDialogBox(Context context, ColorCodeListAdapter adapter) {
        dialogBox = new Dialog(context);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_color_code, null);
        ListView lv = view.findViewById(R.id.dialog_list_view);
        lv.setAdapter(adapter);
        dialogBox.setContentView(view);
        dialogBox.setTitle("Color Codes");
        view.findViewById(R.id.dialog_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });
    }

    public void showDialog() {
        dialogBox.show();
    }
}
