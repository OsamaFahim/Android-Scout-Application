package com.databits.androidscouting.model;

/**
 * ADDED NEW FILE
 * Factory method that creates and returns an instance of a BaseCell subclass
 * based on the provided type string.
 *
 * @param type   The type of cell to create (e.g., "YesNo", "Counter", etc.).
 * @param mCellId The identifier for the cell.
 * @param mTitle  The title or label for the cell.
 * @param mParam  The parameters required to configure the cell.
 * @return A specific subclass of BaseCell corresponding to the type,
 *         or an UnknownCell if the type is not recognized.
 */

public class CellFactory {
    public static BaseCell createCell(String type, int mCellId, String mTitle, CellParam mParam) {
        // Check if the type is "YesNo" and create a YesNoCell if so.
        if ("YesNo".equals(type)) {
            return new YesNoCell(mCellId, mTitle, mParam);
        }
        // If the type is "Counter", create and return a CounterCell.
        else if ("Counter".equals(type)) {
            return new CounterCell(mCellId, mTitle, mParam);
        }
        // If the type is "DoubleCounter", create and return a DoubleCounterCell.
        else if ("DoubleCounter".equals(type)) {
            return new DoubleCounterCell(mCellId, mTitle, mParam);
        }
        // If the type is "Segment", create and return a SegmentCell.
        else if ("Segment".equals(type)) {
            return new SegmentCell(mCellId, mTitle, mParam);
        } // If the type is "List", create and return a ListCell.
        else if ("List".equals(type)) {
            return new ListCell(mCellId, mTitle, mParam);
        }
        // If the type is "Text", create and return a TextCell.
        else if ("Text".equals(type)) {
            return new TextCell(mCellId, mTitle, mParam);
        }
        // If the type is "Special", create and return a SpecialCell.
        else if ("Special".equals(type)) {
            return new SpecialCell(mCellId, mTitle, mParam);
        }
        // If the type is "TeamSelect", create and return a TeamSelectCell.
        else if ("TeamSelect".equals(type)) {
            return new TeamSelectCell(mCellId, mTitle, mParam);
        }

        // If none of the above types match, return an UnknownCell as a fallback.
        return new UnknownCell(mCellId, mTitle, mParam);
    }
}
