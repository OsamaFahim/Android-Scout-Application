package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.databits.androidscouting.R;

//NEW FILE ADDED
// TeamSelectCell extends BaseCell and represents a cell where users can select a team from a list using a Spinner widget.
public class TeamSelectCell extends BaseCell {

    // Constructor: Initializes the TeamSelectCell with an ID, title, and cell parameters.
    public TeamSelectCell(int mCellId, String mTitle, CellParam mParam) {
        // Call the superclass constructor to initialize common properties.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views of the TeamSelectCell.
     * This method sets the cell's title and optionally initializes the Spinner widget.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view is running.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        //1. Bind the cell title. Adjust the ID to the one present in your TeamSelect layout.
        // Retrieve the TextView for the title using its resource ID specific to TeamSelect layout.
        TextView titleView = itemView.findViewById(R.id.teamselect_title);
        if (titleView != null) {
            // Set the title text to the cell's title.
            titleView.setText(mTitle);
        }
        // Optionally, initialize the spinner here.
        // For example, set an adapter using mParam.getEntryLabels() if needed.
    }

    /**
     * Exports the data from the TeamSelectCell.
     * Retrieves the currently selected value from the Spinner widget.
     *
     * @param itemView The view representing this cell.
     * @return A String representing the selected item from the Spinner. Returns an empty string if none is selected.
     */
    @Override
    public String exportData(View itemView) {
        // Retrieve the Spinner widget using its resource ID.
        // Export the currently selected value from the spinner.
        Spinner spinner = itemView.findViewById(R.id.spinner);
        // Check if the spinner and its selected item are available.
        if (spinner != null && spinner.getSelectedItem() != null) {
            // Return the selected item's string representation.
            return spinner.getSelectedItem().toString();
        }
        // Return an empty string if no item is selected or spinner is null.
        return "";
    }

     /**
     * Returns the type of this cell.
     *
     * @return A String "TeamSelect" indicating the cell type.
     */
    @Override
    public String getType() {
        return "TeamSelect";
    }
}
