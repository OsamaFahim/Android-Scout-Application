package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

//NEW FILE ADDED
// YesNoCell extends BaseCell and represents a cell that allows the user to select between two options (Yes or No).
public class YesNoCell extends BaseCell {

    // Constructor: Initializes the YesNoCell with its unique ID, title, and configuration parameters.
    public YesNoCell(int mCellId, String mTitle, CellParam mParam) {
        // Call the superclass constructor to initialize common cell properties.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views for the YesNoCell.
     * This method sets the title and configures the segmented button group used for Yes/No selection.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view is running.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Retrieve the TextView for the title using its resource ID.
        TextView titleView = itemView.findViewById(R.id.yesno_title);
        // If the title view is found, set its text to the cell's title.
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Retrieve the segmented button group used for Yes/No selection.
        // Additional UI binding for segmented button group can be done here.
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_yes_no);
        // (Configure the group if necessary.)
        // Additional configuration for the segmented button group can be added here if necessary.
    }

    /**
     * Exports the data from the YesNoCell.
     * Retrieves the currently selected position from the segmented button group and returns it as a String.
     *
     * @param itemView The view representing this cell.
     * @return A String representing the selected button's position.
     */
    @Override
    public String exportData(View itemView) {
        // Retrieve the segmented button group.
        SegmentedButtonGroup group = itemView.findViewById(R.id.buttonGroup_yes_no);
        // Return the position of the selected button converted to a String.
        return String.valueOf(group.getPosition());
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "YesNo" indicating that this cell is a YesNoCell.
     */
    @Override
    public String getType() {
        return "YesNo";
    }
}
