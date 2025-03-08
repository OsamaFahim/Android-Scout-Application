package com.databits.androidscouting.model;

import java.util.List;
import java.util.Map;

//NEW FILE ADDED
// The Layout class encapsulates the structure of a layout, organizing cells into categories and cell types.
public class Layout {
    // The 'categories' field represents a nested structure:
    // The outer map key is the category name (e.g., "Auto", "Teleop", "Endgame")
    // The inner map key is the cell type (e.g., "DoubleCounter", "Segment", etc.)
    // The value is a list of Cell objects.
    private Map<String, Map<String, List<Cell>>> categories;

    /**
     * Returns the entire categories mapping.
     *
     * @return A Map where keys are category names and values are maps of cell types to lists of Cells.
     */
    public Map<String, Map<String, List<Cell>>> getCategories() {
        return categories;
    }
}
