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


public abstract class BaseCell {
    protected int mCellId;
    protected String mTitle;
    protected CellParam mParam;

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
        ImageButton helpButton = itemView.findViewById(R.id.help_button);
        if (helpButton != null && mParam != null) {
            Balloon helpBalloon = buildHelpBalloon(context);
            helpButton.setOnClickListener(v -> helpBalloon.showAlignBottom(helpButton));
        }
        bindMainViews(itemView, context);
    }

    /**
     * Each subclass must implement this to bind its main views (titles, spinners, etc.)
     */
    protected abstract void bindMainViews(View itemView, Context context);

    /**
     * Each subclass must implement how to export its data.
     */
    public abstract String exportData(View itemView);

    /**
     * Returns the cell type as a String (e.g. "YesNo", "Counter", etc.)
     */
    public abstract String getType();

    /**
     * Builds a Balloon using a custom layout, setting the help title, text, and image.
     */
    private Balloon buildHelpBalloon(Context context) {
        // Determine the drawable based on the helpPictureSelector from mParam.
        Drawable helpPicture = null;
        String selector = mParam.getHelpPictureSelector();
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

        Balloon balloon = new Balloon.Builder(context)
                .setLayout(R.layout.help_bubble_layout)
                .setArrowSize(15)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowPosition(0.5f)
                .setWidth(BalloonSizeSpec.WRAP)
                .setHeight(BalloonSizeSpec.WRAP)
                .setPadding(6)
                .setTextSize(20f)
                .setCornerRadius(4f)
                .setAlpha(0.8f)
                .setBalloonAnimation(BalloonAnimation.CIRCULAR)
                .setDismissWhenClicked(true)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .build();

        // Set the custom balloon content.
        View balloonView = balloon.getContentView();
        TextView titleView = balloonView.findViewById(R.id.help_title);
        TextView contentView = balloonView.findViewById(R.id.help_content);
        ImageView imageView = balloonView.findViewById(R.id.help_image);

        if (titleView != null) {
            titleView.setText(mParam.getHelpTitle());
        }
        if (contentView != null) {
            contentView.setText(mParam.getHelpText());
        }
        if (imageView != null) {
            imageView.setImageDrawable(helpPicture);
        }

        return balloon;
    }
}
