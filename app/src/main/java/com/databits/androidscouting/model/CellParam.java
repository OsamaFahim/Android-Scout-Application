package com.databits.androidscouting.model;

import java.util.List;
// This class holds the parameters/configuration for a cell.
// It encapsulates various properties that define how a cell behaves and is displayed.
public class
CellParam {
    // Type of the cell (e.g., "Counter", "YesNo", etc.).
    private String cellType;
    // Default value for the cell.
    private int cellDefault;
    // Maximum allowed value for the cell.
    private int cellMax;
    // Minimum allowed value for the cell.
    private int cellMin;
    // Unit of increment/decrement for the cell.
    private int cellUnit;
    // Number of segments in a segmented cell.
    private int cellSegments;
    // Labels for each segment in a segmented cell.
    private List<String> cellSegmentLabels;
    // Total number of entries for a list-based cell.
    private int cellTotalEntries;
    // Labels for each entry in a list-based cell.
    private List<String> cellEntryLabels;
    // Flag to indicate if the text should be hidden.
    private boolean cellTextHidden;
    // Hint text to display when the cell's text is empty.
    private String cellTextHint;
    // Category to which this cell belongs (e.g., "Auto", "Teleop", etc.).


    private String cellCategory;

    // Title for the help information associated with this cell.
    private String helpTitle;
    // Selector for a help picture resource.
    private String helpPictureSelector;
    // Additional help text or description.
    private String helpText;

    // A special cell attribute (could be used for custom logic).
    private int cellSpecial;
    // The team color title for a special cell.
    private String cellSpecialTeamColor;

    // Constructor that sets the cell type. Other properties are expected to be set via setters.
    public CellParam(String cellType) {
        this.cellType = cellType;
    }

    // Returns the type of the cell.
    public String getType() {
        return cellType;
    }

    // Sets the type of the cell.
    public void setType(String cellType) {
        this.cellType = cellType;
    }

    // Returns the default value for the cell.
    public int getDefault() {
        return cellDefault;
    }

    // Sets the default value for the cell.
    public void setDefault(int cellDefault) {
        this.cellDefault = cellDefault;
    }

    // Returns the maximum allowed value for the cell.
    public int getMax() {
        return cellMax;
    }

    // Sets the maximum allowed value for the cell.
    public void setMax(int cellMax) {
        this.cellMax = cellMax;
    }

    // Returns the minimum allowed value for the cell.
    public int getMin() {
        return cellMin;
    }

    // Sets the minimum allowed value for the cell.
    public void setMin(int cellMin) {
        this.cellMin = cellMin;
    }

    // Sets the unit increment/decrement for the cell.
    public void setUnit(int cellUnit) {
        this.cellUnit = cellUnit;
    }

    // Returns the number of segments in the cell.
    public int getSegments() {
        return cellSegments;
    }

    // Sets the number of segments in the cell.
    public void setSegments(int cellSegments) {
        this.cellSegments = cellSegments;
    }

    // Returns the list of labels for each segment.
    public List<String> getSegmentLabels() {
        return cellSegmentLabels;
    }

    // Sets the list of labels for each segment.
    public void setSegmentLabels(List<String> cellSegmentLabels) {
        this.cellSegmentLabels = cellSegmentLabels;
    }

    // Sets the total number of entries for the cell.
    public void setTotalEntries(int cellTotalEntries) {
        this.cellTotalEntries = cellTotalEntries;
    }

    // Sets the list of entry labels.
    public void setEntryLabels(List<String> cellEntryLabels) {
        this.cellEntryLabels = cellEntryLabels;
    }

    // Sets the list of entry labels.
    public boolean isTextHidden() {
        return cellTextHidden;
    }

    // Sets whether the text should be hidden.
    public void setTextHidden(boolean cellTextHidden) {
        this.cellTextHidden = cellTextHidden;
    }

    // Returns the hint text for the cell.
    public String getTextHint() {
        return cellTextHint;
    }

    // Sets the hint text for the cell.
    public void setTextHint(String cellTextHint) {
        this.cellTextHint = cellTextHint;
    }

    // Returns the help text associated with the cell.
    public  String getHelpText() {return helpText;}

    // Returns the title for the help information.
    public  String getHelpTitle() {return helpTitle;}

    // Returns the selector for the help picture.
    public  String getHelpPictureSelector() {return helpPictureSelector;}
}