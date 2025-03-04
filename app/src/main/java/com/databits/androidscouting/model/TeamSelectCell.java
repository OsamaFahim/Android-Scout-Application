package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.databits.androidscouting.R;

public class TeamSelectCell extends BaseCell {

    public TeamSelectCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Bind the cell title. Adjust the ID to the one present in your TeamSelect layout.
        TextView titleView = itemView.findViewById(R.id.teamselect_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Optionally, initialize the spinner here.
        // For example, set an adapter using mParam.getEntryLabels() if needed.
    }

    @Override
    public String exportData(View itemView) {
        // Export the currently selected value from the spinner.
        Spinner spinner = itemView.findViewById(R.id.spinner);
        if (spinner != null && spinner.getSelectedItem() != null) {
            return spinner.getSelectedItem().toString();
        }
        return "";
    }

    @Override
    public String getType() {
        return "TeamSelect";
    }
}
