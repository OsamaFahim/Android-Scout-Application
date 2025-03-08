package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.util.Log;

//NEW FILE ADDED
// UnknownCell extends BaseCell and serves as a fallback cell type when an unrecognized type is encountered.
// It does minimal UI binding and exports no data.
public class UnknownCell extends BaseCell {

    // Constructor: Initializes the UnknownCell with an ID, title, and cell parameters.
    public UnknownCell(int mCellId, String mTitle, CellParam mParam) {
        // Call the superclass constructor to initialize common properties.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views for the UnknownCell.
     * Since this is a fallback cell, no specific UI binding is performed.
     * Optionally logs a warning that an unknown cell type was encountered.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // No UI binding is necessary for unknown cell types.
        // Optionally, log that an unknown cell was encountered.
        Log.w("UnknownCell", "Binding unknown cell: " + mTitle);
    }

    /**
     * Exports the data from the UnknownCell.
     * Since unknown cells do not hold exportable data, this method returns an empty string.
     *
     * @param itemView The view representing this cell.
     * @return An empty string as no data is available.
     */
    @Override
    public String exportData(View itemView) {
        return "";
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "Unknown" indicating that this cell is an unknown cell type.
     */
    @Override
    public String getType() {
        return "Unknown";
    }
}
