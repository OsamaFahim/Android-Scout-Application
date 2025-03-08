package com.databits.androidscouting.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import com.databits.androidscouting.R;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;

//NEW FILE ADDED
// Abstract BaseCell class that serves as the foundation for various cell types.
// It provides common functionality such as binding the help balloon and exporting data.
public abstract class BaseCell {
    // Unique identifier for the cell.
    protected int mCellId;
    // Title or label for the cell.
    protected String mTitle;
    // Cell configuration parameters encapsulated in a CellParam object.
    protected CellParam mParam;

    // Constructor to initialize the cell with its ID, title, and configuration parameters.
    public BaseCell(int mCellId, String mTitle, CellParam mParam) {
        this.mCellId = mCellId;
        this.mTitle = mTitle;
        this.mParam = mParam;
    }

    /**
     * The final bind method that first sets up the help balloon for the question mark
     * then delegates to bindMainViews() for cell-specific UI binding.
     */
    public final void bind(View itemView, Context context) {
        // Find the help button in the itemView using its resource ID.
        ImageButton helpButton = itemView.findViewById(R.id.help_button);
        // If the help button exists and cell parameters are available, set up the help balloon.
        if (helpButton != null && mParam != null) {
            // Build a help balloon using the cell parameters.
            Balloon helpBalloon = buildHelpBalloon(context);
            // Set an OnClickListener on the help button to display the balloon aligned at the bottom.
            helpButton.setOnClickListener(v -> helpBalloon.showAlignBottom(helpButton));
        }
        // Delegate the binding of cell-specific views to the subclass.
        bindMainViews(itemView, context);
    }

    /**
     * Abstract method for binding the main views of the cell.
     * Subclasses must implement this to bind their specific UI elements (titles, spinners, etc.).
     *
     * @param itemView The view representing this cell.
     * @param context  The context in which the view exists.
     * Each subclass must implement this to bind its main views (titles, spinners, etc.)
     */
    protected abstract void bindMainViews(View itemView, Context context);

    /**
     * Abstract method for exporting the cell's data.
     * Subclasses must implement this to define how to export their data.
     *
     * @param itemView The view representing this cell.
     * @return A String representing the exported data.
     * Each subclass must implement how to export its data.
     */
    public abstract String exportData(View itemView);

    /**
     * Abstract method for retrieving the type of the cell.
     * Subclasses must return a String representing the cell type (e.g., "YesNo", "Counter", etc.).
     *
     * @return A String indicating the cell type.
     * Returns the cell type as a String (e.g. "YesNo", "Counter", etc.)
     */
    public abstract String getType();

    /**
     * Builds a Balloon (tooltip) using a custom layout to display help information.
     * The balloon displays the help title, help text, and an image based on the cell's parameters.
     *
     * @param context The context in which the balloon will be displayed.
     * @return A configured Balloon instance.
     * Builds a Balloon using a custom layout, setting the help title, text, and image.
     */
    private Balloon buildHelpBalloon(Context context) {
        // Initialize helpPicture to null. It will be set based on the helpPictureSelector.
        // Determine the drawable based on the helpPictureSelector from mParam.
        Drawable helpPicture = null;
        // Retrieve the help picture selector value from the cell parameters.
        String selector = mParam.getHelpPictureSelector();
        // If a selector is provided, choose the corresponding drawable.
        if (selector != null) {
            switch (selector) {
                case "Speaker":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.speaker);
                    break;
                case "Amp":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.amp);
                    break;
                case "Stage":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.stage);
                    break;
                case "Endgame":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.endgame);
                    break;
                case "Feeder":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.feeder);
                    break;
                case "Auto":
                    helpPicture = AppCompatResources.getDrawable(context, R.drawable.auto);
                    break;
                case "None":
                    helpPicture = AppCompatResources.getDrawable(context, com.anggrayudi.storage.R.drawable.md_transparent);
                    break;
            }
        }

        // Build the Balloon instance using the Builder pattern.
        Balloon balloon = new Balloon.Builder(context)
                .setLayout(R.layout.help_bubble_layout)                  // Set custom layout for the balloon.
                .setArrowSize(15)                                        // Set the size of the arrow.
                .setArrowOrientation(ArrowOrientation.TOP)               // Position the arrow on the top.
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)  // Align the arrow with the anchor.
                .setArrowPosition(0.5f)                                   // Center the arrow horizontally.
                .setWidth(BalloonSizeSpec.WRAP)                           // Wrap content width.
                .setHeight(BalloonSizeSpec.WRAP)                           // Wrap content height.
                .setPadding(6)                                             // Set padding for the balloon.
                .setTextSize(20f)                                           // Set text size inside the balloon.
                .setCornerRadius(4f)                                         // Set corner radius for rounded corners.
                .setAlpha(0.8f)                                              // Set transparency level.
                .setBalloonAnimation(BalloonAnimation.CIRCULAR)             // Use a circular animation when showing the balloon.
                .setDismissWhenClicked(true)                                 // Dismiss the balloon when clicked.
                .setBackgroundColor(ContextCompat.getColor(context, R.color.white))  // Set background color.
                .setTextColor(ContextCompat.getColor(context, R.color.white))         // Set text color.
                .build();

        // Retrieve the content view of the balloon to customize its content.
        // Set the custom balloon content.
        View balloonView = balloon.getContentView();
        // Get references to the TextView for the help title and content, and the ImageView for the help image.
        TextView titleView = balloonView.findViewById(R.id.help_title);
        TextView contentView = balloonView.findViewById(R.id.help_content);
        ImageView imageView = balloonView.findViewById(R.id.help_image);

        // Set the help title if the TextView exists.
        if (titleView != null) {
            titleView.setText(mParam.getHelpTitle());
        }
        // Set the help text if the TextView exists.
        if (contentView != null) {
            contentView.setText(mParam.getHelpText());
        }
        // Set the help image drawable if the ImageView exists.
        if (imageView != null) {
            imageView.setImageDrawable(helpPicture);
        }

        // Return the fully configured balloon.
        return balloon;
    }
}
