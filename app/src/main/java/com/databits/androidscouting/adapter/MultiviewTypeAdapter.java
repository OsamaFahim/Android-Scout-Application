package com.databits.androidscouting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.databits.androidscouting.R;
import com.databits.androidscouting.model.BaseCell;
import java.util.Collections;
import java.util.List;

//FILE UPDATED
/**
 * MultiviewTypeAdapter.java
 *
 * This adapter handles multiple view types for a RecyclerView by dynamically inflating
 * different layouts based on the data type. It supports various UI components such as
 * counters, text inputs, segmented buttons, lists, and more.
 *
 * Key Optimizations:
 * - **ViewHolder Pattern**: Uses static inner classes to improve performance by reducing
 *   unnecessary `findViewById()` calls.
 * - **Enum-like View Type Constants**: Predefined integer values eliminate the need for
 *   string comparisons, making `getItemViewType()` more efficient.
 * - **Drag & Drop Support**: Implements `ItemTouchHelperAdapter` to allow reordering items
 *   within the RecyclerView.
 * - **Modular Binding Logic**: Delegates UI updates to each `BaseCell`, reducing redundancy.
 */

public class MultiviewTypeAdapter extends RecyclerView.Adapter<MultiviewTypeAdapter.BaseViewHolder> implements ItemTouchHelperAdapter {

    private transient List<BaseCell> mCells; // Stores the list of cells to be displayed
    private transient Context mContext; // Reference to the context for inflating views

    // Define view type constants for different cell types to improve efficiency.
    public static final int VIEW_TYPE_YESNO = 0;
    public static final int VIEW_TYPE_TEXT = 1;
    public static final int VIEW_TYPE_COUNTER = 2;
    public static final int VIEW_TYPE_DOUBLECOUNTER = 3;
    public static final int VIEW_TYPE_SEGMENT = 4;
    public static final int VIEW_TYPE_LIST = 5;
    public static final int VIEW_TYPE_TEAMSELECT = 6;
    public static final int VIEW_TYPE_DUALCOUNTER = 7;
    public static final int VIEW_TYPE_SPECIAL = 8;

     /**
     * Constructor for initializing the adapter with a list of cells and a context.
     */
    public MultiviewTypeAdapter(List<BaseCell> cells, Context context) {
        this.mCells = cells;
        this.mContext = context;
    }

    /**
     * Returns the list of cells currently stored in the adapter.
     */
    public List<BaseCell> getCells() {
        return mCells;
    }

    // Base ViewHolder that delegates binding to the cell.
    /**
     * Base ViewHolder class that all other ViewHolders extend.
     * Uses abstract `bind()` method to enforce a uniform binding structure.
     */
    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        public abstract void bind(BaseCell cell, Context context);
    }

    // Below are different ViewHolder classes for different UI elements.
    // Each ViewHolder extends `BaseViewHolder` and binds its respective data.

    public static class YesNoViewHolder extends BaseViewHolder {
        public YesNoViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class TextViewHolder extends BaseViewHolder {
        public TextViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class CounterViewHolder extends BaseViewHolder {
        public CounterViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class DoubleCounterViewHolder extends BaseViewHolder {
        public DoubleCounterViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class SegmentViewHolder extends BaseViewHolder {
        public SegmentViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class ListViewHolder extends BaseViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class TeamSelectViewHolder extends BaseViewHolder {
        public TeamSelectViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class DualCounterViewHolder extends BaseViewHolder {
        public DualCounterViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    public static class SpecialViewHolder extends BaseViewHolder {
        public SpecialViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void bind(BaseCell cell, Context context) {
            cell.bind(itemView, context);
        }
    }

    /**
     * Determines the type of view for each cell in the RecyclerView.
     */
    @Override
    public int getItemViewType(int position) {
        String type = mCells.get(position).getType();
        switch (type) {
            case "YesNo":
                return VIEW_TYPE_YESNO;
            case "Text":
                return VIEW_TYPE_TEXT;
            case "Counter":
                return VIEW_TYPE_COUNTER;
            case "DoubleCounter":
                return VIEW_TYPE_DOUBLECOUNTER;
            case "Segment":
                return VIEW_TYPE_SEGMENT;
            case "List":
                return VIEW_TYPE_LIST;
            case "TeamSelect":
                return VIEW_TYPE_TEAMSELECT;
            case "DualCounter":
                return VIEW_TYPE_DUALCOUNTER;
            case "Special":
                return VIEW_TYPE_SPECIAL;
            case "NONE":

            default:
                throw new IllegalArgumentException("Invalid cell type: " + type);
        }
    }

    /**
     * Creates a new ViewHolder based on the view type.
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_YESNO:
                view = inflater.inflate(R.layout.card_yesno, parent, false);
                return new YesNoViewHolder(view);
            case VIEW_TYPE_TEXT:
                view = inflater.inflate(R.layout.card_textbox, parent, false);
                return new TextViewHolder(view);
            case VIEW_TYPE_COUNTER:
                view = inflater.inflate(R.layout.card_counter, parent, false);
                return new CounterViewHolder(view);
            case VIEW_TYPE_DOUBLECOUNTER:
                view = inflater.inflate(R.layout.card_doublecounter, parent, false);
                return new DoubleCounterViewHolder(view);
            case VIEW_TYPE_SEGMENT:
                view = inflater.inflate(R.layout.card_segment, parent, false);
                return new SegmentViewHolder(view);
            case VIEW_TYPE_LIST:
                view = inflater.inflate(R.layout.card_list, parent, false);
                return new ListViewHolder(view);
            case VIEW_TYPE_TEAMSELECT:
                view = inflater.inflate(R.layout.card_teamselect, parent, false);
                return new TeamSelectViewHolder(view);
            case VIEW_TYPE_DUALCOUNTER:
                view = inflater.inflate(R.layout.card_dualcounter, parent, false);
                return new DualCounterViewHolder(view);
            case VIEW_TYPE_SPECIAL:
                view = inflater.inflate(R.layout.card_special, parent, false);
                return new SpecialViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    /**
     * Binds the ViewHolder to the corresponding cell data.
     */
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        BaseCell cell = mCells.get(position);
        holder.bind(cell, mContext);
    }

    @Override
    public int getItemCount() {
        return mCells.size();
    }

    // Implements drag-and-drop functionality
    // ItemTouchHelperAdapter methods for drag/drop support
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCells, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        // Handle dismissal if needed.
    }

    @Override
    public void onItemSelected() {
        // Optionally handle item selection.
    }

    @Override
    public void onItemClear() {
        // Optionally handle item clear.
    }

    /**
     * Exports all cells as a CSV-compatible string.
     */
    // Optional export method that delegates to each cell's exportData method.
    public String exportCells(RecyclerView recyclerView) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            BaseCell cell = mCells.get(i);
            View view = recyclerView.getChildAt(i);
            builder.append(",").append(cell.exportData(view));
        }
        return builder.toString();
    }
}
