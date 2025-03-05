package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.databits.androidscouting.R;

public class ListCell extends BaseCell {

    public ListCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.list_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Set up spinner adapter using mParam.getEntryLabels() if needed.
        // For example, create an ArrayAdapter with the list of entries and set it on the spinner.
    }

    @Override
    public String exportData(View itemView) {
        Spinner spinner = itemView.findViewById(R.id.spinner);
        return spinner.getSelectedItem().toString();
    }

    @Override
    public String getType() {
        return "List";
    }
}
