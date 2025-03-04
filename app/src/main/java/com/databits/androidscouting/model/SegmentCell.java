package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;
import java.util.List;

public class SegmentCell extends BaseCell {

    public SegmentCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    public void bind(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.segment_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Setup segmented button group labels if available
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);
        List<String> labels = mParam.getSegmentLabels();
        // (Assuming your layout has a fixed number of buttons, e.g., 6)
        // You can iterate over the group’s children to assign labels.
        // For simplicity, we assume the group is pre-configured.
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);
        return String.valueOf(group.getPosition());
    }

    @Override
    public String getType() {
        return "Segment";
    }
}
