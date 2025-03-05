package com.databits.androidscouting.model;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.databits.androidscouting.R;

public class TextCell extends BaseCell {

    public TextCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
    }

    @Override
    protected void bindMainViews(View itemView, Context context) {
        TextView titleView = itemView.findViewById(R.id.textbox_title);
        if (titleView != null) {
            titleView.setText(mTitle);
        }
        // Set hint if text is not hidden
        if (mParam != null && !mParam.isTextHidden()) {
            TextInputLayout layout = itemView.findViewById(R.id.textbox_text_layout);
            if (layout != null) {
                layout.setHint(mParam.getTextHint());
            }
        }
    }

    @Override
    public String exportData(View itemView) {
        EditText editText = itemView.findViewById(R.id.enteredText);
        return editText.getText().toString().replaceAll(",", ".");
    }

    @Override
    public String getType() {
        return "Text";
    }
}
