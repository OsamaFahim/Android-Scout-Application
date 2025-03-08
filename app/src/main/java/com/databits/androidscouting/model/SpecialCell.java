package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;

//NEW FILE ADDED
// SpecialCell extends BaseCell and represents a cell with specialized functionality,
// such as combining a team selector (using segmented buttons) and counters.
public class SpecialCell extends BaseCell {

    // Constructor: Initializes the SpecialCell with an ID, title, and cell parameters.
    public SpecialCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views of the SpecialCell.
     * This method sets the title and can be extended to include additional binding logic
     * for specialized UI elements, such as segmented buttons or counters.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Retrieve the TextView for displaying the cell title.
        TextView titleView = itemView.findViewById(R.id.special_title);
        if (titleView != null) {
            // Set the title text for the special cell.
            titleView.setText(mTitle);
        }
        // Additional binding for SpecialCell can be done here (e.g., configuring segmented buttons or counters).
    }

    /**
     * Exports the data from the SpecialCell.
     * Combines data from a team selector (segmented button group) and several counters,
     * and returns a comma-separated String.
     *
     * @param itemView The view representing this cell.
     * @return A String containing exported data from the team selector and counters.
     */
    @Override
    public String exportData(View itemView) {
        // For team selector, we assume it's a SegmentedButtonGroup nested inside a view with id teamSelector_segment.
        SegmentedButtonGroup teamSelector = itemView.findViewById(R.id.teamSelector_segment)
                .findViewById(R.id.buttonGroup_segments);
        StringBuilder builder = new StringBuilder();

        // Append the text of the selected button (team) to the output.
        builder.append(teamSelector.getButton(teamSelector.getPosition()).getText()).append(",");

        // Retrieve three NumberPickers representing different counter values.
        // Note: All NumberPickers are fetched from a container view with id counterUI1.
        // It's assumed that there are multiple NumberPickers within this container.
        // For each counter, use NumberPicker's getValue() after setting proper min/max.
        com.travijuu.numberpicker.library.NumberPicker algaeMiss = itemView.findViewById(R.id.counterUI1)
                .findViewById(R.id.number_counter_inside);
        com.travijuu.numberpicker.library.NumberPicker algaeSuccess = itemView.findViewById(R.id.counterUI1)
                .findViewById(R.id.number_counter_inside);

        com.travijuu.numberpicker.library.NumberPicker algaeReturned = itemView.findViewById(R.id.counterUI1)
                .findViewById(R.id.number_counter_inside);

        // Append the values from each NumberPicker to the output, separated by commas.
        builder.append(algaeMiss.getValue()).append(",");
        builder.append(algaeSuccess.getValue()).append(",");
        builder.append(algaeReturned.getValue());

        // Return the concatenated data as a String.
        return builder.toString();
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "Special" indicating that this is a SpecialCell.
     */
    @Override
    public String getType() {
        return "Special";
    }
}
