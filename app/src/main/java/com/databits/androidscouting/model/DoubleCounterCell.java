package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.databits.androidscouting.R;
import com.travijuu.numberpicker.library.NumberPicker;

public class DoubleCounterCell extends BaseCell {

    // Constructor: Initializes the CounterCell with its ID, title, and cell parameters.
    public DoubleCounterCell(int mCellId, String mTitle, CellParam mParam) {
        // Call the superclass (BaseCell) constructor to initialize common properties.
        super(mCellId, mTitle, mParam);
    }

    /**
     * Binds the main views of the cell to their corresponding UI elements.
     * This method sets up the title and configures the NumberPicker based on the cell parameters.
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view is running.
     */
    @Override
    protected void bindMainViews(View itemView, Context context) {
        // Retrieve the TextView for displaying the cell title using its resource ID.
        TextView titleView = itemView.findViewById(R.id.double_counter_title);
        // If the title view is found, set its text to the cell's title.
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Use the travijuu NumberPicker (fully qualified)
        com.travijuu.numberpicker.library.NumberPicker picker1 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_one);
        com.travijuu.numberpicker.library.NumberPicker picker2 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_two);
        if (picker1 != null && picker2 != null && mParam != null) {
            // Set the minimum value of the picker based on the cell parameter.
            picker1.setMin(mParam.getMin());
            // Set the maximum value of the picker based on the cell parameter.
            picker1.setMax(mParam.getMax());
            // Set the current value of the picker to the default value specified in the cell parameter.
            picker1.setValue(mParam.getDefault());

            // Set the minimum value of the picker based on the cell parameter.
            picker2.setMin(mParam.getMin());
            // Set the maximum value of the picker based on the cell parameter.
            picker2.setMax(mParam.getMax());
            // Set the current value of the picker to the default value specified in the cell parameter.
            picker2.setValue(mParam.getDefault());
        }
    }

    /**
     * Exports the data from the cell.
     * Retrieves the current value from the NumberPicker and returns it as a string.
     *
     * @param itemView The view representing this cell.
     * @return The current value of the counter as a String.
     */
    @Override
    public String exportData(View itemView) {
        // Retrieve the NumberPicker view.
        com.travijuu.numberpicker.library.NumberPicker picker1 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_one);
        com.travijuu.numberpicker.library.NumberPicker picker2 =
                (com.travijuu.numberpicker.library.NumberPicker) itemView.findViewById(R.id.number_counter_two);
        // Return the current value from the picker as a String.
        return picker1.getValue() + "," + picker2.getValue();
    }

    /**
     * Returns the type of this cell as a String.
     *
     * @return "Counter" indicating the type of the cell.
     */
    @Override
    public String getType() {
        return "DoubleCounter";
    }
}
