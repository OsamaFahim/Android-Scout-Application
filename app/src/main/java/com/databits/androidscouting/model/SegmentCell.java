package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;
import java.util.List;

//NEW FILE ADDED
// SegmentCell extends BaseCell to implement a cell that uses a segmented button group.
// This cell type is used for selection among multiple segments.
public class SegmentCell extends BaseCell {
    // Constructor: Initializes the SegmentCell with an ID, title, and cell parameters.
    public SegmentCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views of the SegmentCell.
     * This method sets up the title and configures the segmented button group based on the provided labels.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     */
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

        // 5. Put them in an array for easy iteration
        SegmentedButton[] allButtons = { btn1, btn2, btn3, btn4, btn5, btn6 };

        // 6. For each label, set the text and make the button visible.
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

    /**
     * Exports the data from the SegmentCell.
     * Retrieves and returns the position of the selected segment as a String.
     *
     * @param itemView The view representing this cell.
     * @return A String representing the selected segment's position.
     */
    @Override
    public String exportData(View itemView) {
        // Retrieve the segmented button group.
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_segments);
        // Return the position of the selected button
        return String.valueOf(group.getPosition());
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "Segment" indicating that this cell is a SegmentCell.
     */
    @Override
    public String getType() {
        return "Segment";
    }
}
