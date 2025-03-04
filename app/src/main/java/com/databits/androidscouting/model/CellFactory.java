package com.databits.androidscouting.model;

public class CellFactory {
    public static BaseCell createCell(String type, int mCellId, String mTitle, CellParam mParam) {
        if ("YesNo".equals(type)) {
            return new YesNoCell(mCellId, mTitle, mParam);
        } else if ("Counter".equals(type)) {
            return new CounterCell(mCellId, mTitle, mParam);
        } else if ("DoubleCounter".equals(type)) {
            return new DoubleCounterCell(mCellId, mTitle, mParam);
        } else if ("Segment".equals(type)) {
            return new SegmentCell(mCellId, mTitle, mParam);
        } else if ("List".equals(type)) {
            return new ListCell(mCellId, mTitle, mParam);
        } else if ("Text".equals(type)) {
            return new TextCell(mCellId, mTitle, mParam);
        } else if ("Special".equals(type)) {
            return new SpecialCell(mCellId, mTitle, mParam);
        }
        return new UnknownCell(mCellId, mTitle, mParam);
    }
}
