package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.databits.androidscouting.R;

//NEW FILE ADDED
// ListCell extends BaseCell and represents a UI cell that uses a Spinner widget for list selection.
public class ListCell extends BaseCell {

    // Constructor: Initializes a new ListCell with an ID, title, and cell parameters.
    public ListCell(int mCellId, String mTitle, CellParam mParam) {
        // Pass the provided parameters to the BaseCell constructor.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the primary views (UI elements) to this cell's data.
     * This method is responsible for setting up the title and preparing the spinner if needed.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Locate the TextView for the cell title using its resource ID.
        TextView titleView = itemView.findViewById(R.id.list_title);
        // If the TextView is found, update its text with the cell's title.
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // TODO: Set up the Spinner adapter using mParam.getEntryLabels() if needed.
        // For example, you can create an ArrayAdapter with the list of entries provided in mParam
        // and attach it to the Spinner to allow user selection.
    }

    /**
     * Exports the current data of the cell.
     * Retrieves the selected item from the Spinner and returns it as a String.
     *
     * @param itemView The view representing this cell.
     * @return The selected item from the Spinner converted to a String.
     */
    @Override
    public String exportData(View itemView) {
        Spinner spinner = itemView.findViewById(R.id.spinner);
        return spinner.getSelectedItem().toString();
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "List" that identifies this cell as a ListCell.
     */
    @Override
    public String getType() {
        return "List";
    }
}
