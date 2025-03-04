package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.databits.androidscouting.R;

public class TitleCell extends BaseCell {

    public TitleCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        // Use the correct TextView ID from your layout.
        TextView titleView = itemView.findViewById(R.id.cell_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Optionally bind additional data (like team/match info)
    }

    @Override
    public String exportData(View itemView) {
        // Title cells might not need to export data; return an empty string.
        return "";
    }

    @Override
    public String getType() {
        return "Title";
    }
}
