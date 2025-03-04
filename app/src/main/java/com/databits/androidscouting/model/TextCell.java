package com.databits.androidscouting.model;

import android.view.View;
import android.widget.EditText;
import com.databits.androidscouting.R;

public class TextCell extends BaseCell {

    public TextCell(int mCellId, String mTitle, CellParam mParam) {
        super(mCellId, mTitle, mParam);
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
