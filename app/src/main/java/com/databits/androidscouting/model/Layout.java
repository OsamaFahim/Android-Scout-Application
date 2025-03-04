package com.databits.androidscouting.model;

import java.util.List;
import java.util.Map;

public class Layout {
    // The outer map key is the category name (e.g., "Auto", "Teleop", "Endgame")
    // The inner map key is the cell type (e.g., "DoubleCounter", "Segment", etc.)
    // The value is a list of Cell objects.
    private Map<String, Map<String, List<Cell>>> categories;

    public Map<String, Map<String, List<Cell>>> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Map<String, List<Cell>>> categories) {
        this.categories = categories;
    }
}
