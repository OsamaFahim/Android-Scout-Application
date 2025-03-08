package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.databits.androidscouting.R;

//NEW FILE ADDED
// TextCell extends BaseCell to represent a cell that captures textual input from the user.
// It displays a title and an input field, optionally showing a hint.
public class TextCell extends BaseCell {

    // Constructor: Initializes the TextCell with its unique ID, title, and configuration parameters.
    public TextCell(int mCellId, String mTitle, CellParam mParam) {
        // Pass the parameters to the superclass constructor.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views for the TextCell.
     * This method sets the title text and, if the text is not hidden, sets a hint on the input layout.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Retrieve the TextView for the title using its resource ID.
        TextView titleView = itemView.findViewById(R.id.textbox_title);
        // If the title view exists, set its text to the cell's title.
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Check if cell parameters are available and if text should be visible.
        // Set hint if text is not hidden
        if (mParam != null && !mParam.isTextHidden()) {
            // Retrieve the TextInputLayout which wraps the EditText.
            TextInputLayout layout = itemView.findViewById(R.id.textbox_text_layout);
            // If the layout exists, set its hint to the value provided in cell parameters.
            if (layout != null) {
                layout.setHint(mParam.getTextHint());
            }
        }
    }

    /**
     * Exports the data from the TextCell.
     * Retrieves the text entered by the user in the EditText, and replaces commas with periods.
     *
     * @param itemView The view representing this cell.
     * @return A String containing the user-entered text, with commas replaced by periods.
     */
    @Override
    public String exportData(View itemView) {
        EditText editText = itemView.findViewById(R.id.enteredText);
        return editText.getText().toString().replaceAll(",", ".");
    }

    /**
     * Returns the type of this cell.
     *
     * @return A String "Text" indicating that this cell is a TextCell.
     */
    @Override
    public String getType() {
        return "Text";
    }
}
