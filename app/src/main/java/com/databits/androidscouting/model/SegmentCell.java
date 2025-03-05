package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;
import java.util.List;

public class SegmentCell extends BaseCell {

    public SegmentCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        // 1. Set the title
        TextView titleView = itemView.findViewById(R.id.segment_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }

        // 2. Grab the segmented button group
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);

        // 3. Get your label list from the param
        List<String> labels = mParam.getSegmentLabels();
        int segmentCount = mParam.getSegments(); // e.g. 4

        // 4. Optionally, you might have pre-declared buttons in your layout (like 6).
        //    For example, if you have up to 6 possible segments, do something like:
        SegmentedButton btn1 = itemView.findViewById(R.id.button_one);
        SegmentedButton btn2 = itemView.findViewById(R.id.button_two);
        SegmentedButton btn3 = itemView.findViewById(R.id.button_three);
        SegmentedButton btn4 = itemView.findViewById(R.id.button_four);
        SegmentedButton btn5 = itemView.findViewById(R.id.button_five);
        SegmentedButton btn6 = itemView.findViewById(R.id.button_six);

        // Put them in an array for easy iteration
        SegmentedButton[] allButtons = { btn1, btn2, btn3, btn4, btn5, btn6 };

        // 5. For each label, set the text and make the button visible.
        //    For any remaining buttons, hide them.
        for (int i = 0; i < allButtons.length; i++) {
            if (i < segmentCount && i < labels.size()) {
                allButtons[i].setVisibility(View.VISIBLE);
                allButtons[i].setText(labels.get(i));
            } else {
                // Hide any extra buttons
                allButtons[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public String exportData(View itemView) {
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);
        // Return the position of the selected button
        return String.valueOf(group.getPosition());
    }

    @Override
    public String getType() {
        return "Segment";
    }
}
