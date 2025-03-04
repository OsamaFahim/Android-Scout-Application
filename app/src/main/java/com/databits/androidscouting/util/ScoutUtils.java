package com.databits.androidscouting.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.databits.androidscouting.R;
import com.databits.androidscouting.adapter.MultiviewTypeAdapter;
import com.databits.androidscouting.adapter.SimpleItemTouchHelperCallback;
import com.databits.androidscouting.model.BaseCell;
import com.databits.androidscouting.model.Cell;
import com.databits.androidscouting.model.CellFactory;
import com.databits.androidscouting.model.CellParam;
import com.databits.androidscouting.model.Layout;
import com.preference.PowerPreference;
import com.preference.Preference;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ScoutUtils {

  public static final int NONE = 0;
  public static final int AUTO = 1;
  public static final int TELEOP = 2;
  public static final int BOTH = 3;

  String[] cellTypes = {"YesNo", "Counter", "DoubleCounter", "Segment", "List", "Text", "Special"};
  String[] cellTitles = {"YesNo_title", "Counter_Title", "DoubleCounter_Title", "Segment_Title", "List_Title", "Textbox_title"};
  String[] topTitles = {"Left\nSide", "Autonomous\nCenter Side", "Right\nSide"};
  String[] botTitles = {"Left\nSide", "Teleop\nCenter Side", "Right\nSide"};

  public static final int REQUEST_CODE_PERMISSIONS = 10;
  public static final String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.CAMERA};

  Preference configPreference = PowerPreference.getFileByName("Config");
  Preference debugPreference = PowerPreference.getFileByName("Debug");
  Preference listPreference = PowerPreference.getFileByName("List");

  MatchInfo matchInfo;
  TeamInfo teamInfo;

  TableLayout[] tables;
  Context context;

  public ScoutUtils(Context context) {
    this.context = context;
  }

  private TableLayout[] provideTables(View v) {
    View[] topTables = {
            v.findViewById(R.id.left_table),
            v.findViewById(R.id.center_table),
            v.findViewById(R.id.right_table)
    };
    View[] botTables = {
            v.findViewById(R.id.bot_left_table),
            v.findViewById(R.id.bot_center_table),
            v.findViewById(R.id.bot_right_table)
    };

    tables = new TableLayout[]{
            botTables[0].findViewById(R.id.inner_table),
            botTables[1].findViewById(R.id.inner_table),
            botTables[2].findViewById(R.id.inner_table),
            topTables[0].findViewById(R.id.inner_table),
            topTables[1].findViewById(R.id.inner_table),
            topTables[2].findViewById(R.id.inner_table),
    };

    return tables;
  }

  // Delegate export to the adapter
  public String exportCells(RecyclerView recyclerView) {
    MultiviewTypeAdapter adapter = (MultiviewTypeAdapter) recyclerView.getAdapter();
    if (adapter != null) {
      return adapter.exportCells(recyclerView);
    }
    return "";
  }

  public String exportTable(View v) {
    tables = provideTables(v);
    StringBuilder export = new StringBuilder();
    for (TableLayout table : tables) {
      // 0 is the title row, so start at 1
      for (int i = 1; i < table.getChildCount(); i++) {
        TableRow row = (TableRow) table.getChildAt(i);
        for (int j = 0; j < row.getChildCount(); j++) {
          ImageButton button = (ImageButton) row.getChildAt(j);
          Drawable.ConstantState curDraw = button.getDrawable().getConstantState();
          int id = 0;
          if (!curDraw.equals(ContextCompat.getDrawable(context, R.drawable.android_x).getConstantState())) {
            if (curDraw.equals(ContextCompat.getDrawable(context, R.drawable.red_cube44).getConstantState())
                    || curDraw.equals(ContextCompat.getDrawable(context, R.drawable.cube44).getConstantState())) {
              id = 1;
            } else if (curDraw.equals(ContextCompat.getDrawable(context, R.drawable.red_cone44).getConstantState())
                    || curDraw.equals(ContextCompat.getDrawable(context, R.drawable.cone44).getConstantState())) {
              id = 2;
            }
          }
          if (table == tables[tables.length - 1] && i == table.getChildCount() - 1 && j == row.getChildCount() - 1) {
            export.append(id);
          } else {
            export.append(id).append(",");
          }
        }
      }
    }
    return export.toString();
  }

  public String saveData(View v, boolean special) {
    String cellData;
    int match = matchInfo.getMatch();
    int team = 9999;
    if (debugPreference.getBoolean("manual_team_override_toggle")) {
      team = debugPreference.getInt("manual_team_override_value");
    } else if (teamInfo.teamsLoaded() || listPreference.getBoolean("pit_remove_enabled")) {
      team = teamInfo.getTeam(match);
    }
    if (listPreference.getBoolean("pit_remove_enabled")) {
      cellData = exportCells(v.findViewById(R.id.recycler_view_top)).substring(1) + "," + teamInfo.getScouterName();
    } else if (special) {
      cellData = exportCells(v.findViewById(R.id.recycler_view_top)).substring(1) + "," + teamInfo.getScouterName();
    } else {
      cellData = team + "," + match + "," + exportCells(v.findViewById(R.id.recycler_view_top)).substring(1) + "," + teamInfo.getScouterName();
    }
    return cellData;
  }

  public void tableSorter(int table_status, View v, RecyclerView mRecyclerViewTop, RecyclerView mRecyclerViewBot) {
    RecyclerView recyclerViewTop = v.findViewById(R.id.recycler_view_top);
    ViewGroup.LayoutParams topParam = recyclerViewTop.getLayoutParams();
    ConstraintLayout constraintLayout = v.findViewById(R.id.Dynamic_layout);
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(constraintLayout);
    tables = provideTables(v);
    TextView title;
    switch (table_status) {
//      case NONE:
//        // (Your NONE case logic here, or leave as needed)
//        break;
      case AUTO:
        constraintSet.connect(R.id.recycler_view_top, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.setVisibility(R.id.recycler_view_bot, View.GONE);
        topParam.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        for (int j = 3; j < 6; j++) {
          tables[j].setVisibility(View.GONE);
          for (int k = 0; k < 3; k++) {
            tables[k].setVisibility(View.VISIBLE);
          }
          title = tables[4].getChildAt(0).findViewById(R.id.table_title);
          title.setText("Auto\nCenter Side");
          mRecyclerViewTop.setVisibility(View.VISIBLE);
          mRecyclerViewBot.setVisibility(View.GONE);
        }
        break;
      case TELEOP:
        constraintSet.connect(R.id.recycler_view_top, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.setVisibility(R.id.recycler_view_bot, View.GONE);
        topParam.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        for (int j = 3; j < 6; j++) {
          tables[j].setVisibility(View.GONE);
          for (int k = 0; k < 3; k++) {
            tables[k].setVisibility(View.VISIBLE);
          }
          title = tables[4].getChildAt(0).findViewById(R.id.table_title);
          title.setText("Teleop\nCenter Side");
          mRecyclerViewTop.setVisibility(View.VISIBLE);
          mRecyclerViewBot.setVisibility(View.GONE);
        }
        break;
      case BOTH:
        constraintSet.connect(R.id.recycler_view_top, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.recycler_view_top, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.recycler_view_top, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.setVisibility(R.id.recycler_view_top, View.VISIBLE);
        constraintSet.setVisibility(R.id.recycler_view_bot, View.VISIBLE);
        topParam.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        for (int j = 0; j < 6; j++) {
          tables[j].setVisibility(View.VISIBLE);
        }
        mRecyclerViewTop.setVisibility(View.VISIBLE);
        mRecyclerViewBot.setVisibility(View.VISIBLE);
        break;
    }
  }

  public void setupTables(View v) {
    View[] topTables = {
            v.findViewById(R.id.left_table),
            v.findViewById(R.id.center_table),
            v.findViewById(R.id.right_table)
    };
    View[] botTables = {
            v.findViewById(R.id.bot_left_table),
            v.findViewById(R.id.bot_center_table),
            v.findViewById(R.id.bot_right_table)
    };
    tables = new TableLayout[]{
            topTables[0].findViewById(R.id.inner_table),
            topTables[1].findViewById(R.id.inner_table),
            topTables[2].findViewById(R.id.inner_table),
            botTables[0].findViewById(R.id.inner_table),
            botTables[1].findViewById(R.id.inner_table),
            botTables[2].findViewById(R.id.inner_table),
    };
    for (int i = 0; i < 3; i++) {
      TextView topText = topTables[i].findViewById(R.id.table_title);
      TextView botText = botTables[i].findViewById(R.id.table_title);
      topText.setTextSize(20);
      topText.setTextColor(Color.WHITE);
      botText.setTextSize(20);
      botText.setTextColor(Color.WHITE);
      topText.setText(topTitles[i]);
      botText.setText(botTitles[i]);
    }
    for (TableLayout table : tables) {
      if (debugPreference.getBoolean("isRedteam")) {
        updateTableColor(table, android.R.color.holo_red_dark);
      } else {
        updateTableColor(table, R.color.map_blue);
      }
      for (int i = 1; i < table.getChildCount(); i++) {
        TableRow row = (TableRow) table.getChildAt(i);
        for (int j = 0; j < row.getChildCount(); j++) {
          ImageButton button = (ImageButton) row.getChildAt(j);
          if (i == 3) {
            button.setTag("Both");
          } else {
            if (j == 0) {
              button.setTag("Cone");
            } else if (j == 1) {
              button.setTag("Cube");
            } else if (j == 2) {
              button.setTag("Cone");
            }
          }
          button.setImageResource(R.drawable.android_x);
          final int[] counter = {1};
          button.setOnClickListener(v1 -> {
            boolean red = debugPreference.getBoolean("isRedteam");
            String curTag = String.valueOf(button.getTag());
            switch (curTag) {
              case "Both":
                if (counter[0] == 0) {
                  button.setImageResource(R.drawable.android_x);
                  counter[0]++;
                } else if (counter[0] == 1) {
                  button.setImageResource(red ? R.drawable.red_cube44 : R.drawable.cube44);
                  counter[0]++;
                } else if (counter[0] == 2) {
                  button.setImageResource(red ? R.drawable.red_cone44 : R.drawable.cone44);
                  counter[0] = 0;
                }
                break;
              case "Cone":
                if (counter[0] == 0) {
                  button.setImageResource(R.drawable.android_x);
                  counter[0]++;
                } else if (counter[0] == 1) {
                  button.setImageResource(red ? R.drawable.red_cone44 : R.drawable.cone44);
                  counter[0] = 0;
                }
                break;
              case "Cube":
                if (counter[0] == 0) {
                  button.setImageResource(R.drawable.android_x);
                  counter[0]++;
                } else if (counter[0] == 1) {
                  button.setImageResource(red ? R.drawable.red_cube44 : R.drawable.cube44);
                  counter[0] = 0;
                }
                break;
            }
          });
        }
      }
    }
  }

  // Create a list of BaseCell objects using CellFactory.
  public List<BaseCell> testCells(int cells) {
    List<BaseCell> baseCells = new ArrayList<>();
    for (int i = 0; i < cells; i++) {
      String cellType = cellTypes[i];
      CellParam cellParam = new CellParam(cellType);
      switch (cellType) {
        case "YesNo":
          cellParam.setType(context.getString(R.string.YesNoType));
          break;
        case "Counter":
          cellParam.setType(context.getString(R.string.CounterType));
          cellParam.setDefault(3);
          cellParam.setMax(5);
          cellParam.setMin(0);
          cellParam.setUnit(1);
          break;
        case "Segment":
          cellParam.setType(context.getString(R.string.SegmentType));
          cellParam.setSegments(6);
          cellParam.setSegmentLabels(Arrays.asList("One", "2", "Three", "4", "Five", "6"));
          break;
        case "List":
          cellParam.setType(context.getString(R.string.ListType));
          cellParam.setTotalEntries(3);
          cellParam.setEntryLabels(Arrays.asList("Java", "C++", "Labview"));
          break;
        case "Text":
          cellParam.setType(context.getString(R.string.TextType));
          cellParam.setTextHidden(false);
          cellParam.setTextHint("Enter life here");
          break;
        // Add additional cases if needed.
      }
      BaseCell baseCell = CellFactory.createCell(cellType, i, cellTitles[i], cellParam);
      baseCells.add(baseCell);
    }
    return baseCells;
  }

  // Updated import_cells() converts legacy Cell objects to BaseCell objects via CellFactory.
  @SuppressLint("NotifyDataSetChanged")
  public void import_cells(String optional_json, RecyclerView mRecyclerView) {
    Log.d("LayoutDebug", "Optional JSON received: " + optional_json);
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Layout> jsonAdapter = moshi.adapter(Layout.class);
    Layout layout = null;
    try {
      layout = jsonAdapter.fromJson(optional_json);
    } catch (IOException e) {
      Log.e("Dynamic", "Error parsing JSON", e);
    }
    List<BaseCell> baseCellList = new ArrayList<>();
    if (layout != null && layout.getCategories() != null) {
      for (Map.Entry<String, Map<String, List<Cell>>> outerEntry : layout.getCategories().entrySet()) {
        String categoryName = outerEntry.getKey();
        Map<String, List<Cell>> subMap = outerEntry.getValue();
        for (Map.Entry<String, List<Cell>> entry : subMap.entrySet()) {
          Log.d("LayoutDebug", "Category: " + categoryName + ", cell type: " + entry.getKey());
          for (Cell legacyCell : entry.getValue()) {
            BaseCell baseCell = CellFactory.createCell(
                    legacyCell.getType(),
                    legacyCell.getCellId(), // Updated getter
                    legacyCell.getTitle(),
                    legacyCell.getParam()
            );
            baseCellList.add(baseCell);
          }
        }
      }
      Log.d("LayoutDebug", "Total cells across all categories: " + baseCellList.size());
    } else {
      Log.d("LayoutDebug", "Layout or layout.getCategories() is null");
    }
    MultiviewTypeAdapter adapter = new MultiviewTypeAdapter(baseCellList, context);
    mRecyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    mRecyclerView.post(() -> setupTitle(mRecyclerView));
  }

  public void updateTableColor(TableLayout table, int colorResId) {
    TableRow row = (TableRow) table.getChildAt(0);
    row.setBackgroundColor(ContextCompat.getColor(context, colorResId));
    TextView title = row.findViewById(R.id.table_title);
    title.setTextColor(ContextCompat.getColor(context, R.color.white));
    ImageView icon = row.findViewById(R.id.table_image);
    icon.setOnClickListener(v -> {
      Balloon.Builder helpBuilder = new Balloon.Builder(context)
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
              .setTextColor(ContextCompat.getColor(context, R.color.white))
              .setBalloonAnimation(BalloonAnimation.FADE)
              .setText("This is the top side where the drivers stand");
      helpBuilder.build().showAlignBottom(icon);
    });
  }

  @SuppressLint("SetTextI18n")
  public void setupTitle(RecyclerView mRecyclerView) {
    MultiviewTypeAdapter adapter = (MultiviewTypeAdapter) mRecyclerView.getAdapter();
    matchInfo = new MatchInfo();
    teamInfo = new TeamInfo(context);
    if (adapter != null && !adapter.getCells().isEmpty()) {
      for (int i = 0; i < adapter.getCells().size(); i++) {
        if (Objects.equals(adapter.getCells().get(i).getType(), "Title")) {
          View v = mRecyclerView.getChildAt(i);
          TextView teamNumber = v.findViewById(R.id.team_number);
          TextView matchNumber = v.findViewById(R.id.match_number);
          if (!teamInfo.teamsLoaded()) {
            teamNumber.setText("No Team");
          } else {
            teamNumber.setText(String.valueOf(teamInfo.getTeam(matchInfo.getMatch())));
          }
          matchNumber.setText(String.valueOf(matchInfo.getMatch()));
        }
      }
    }
  }

  private MultiviewTypeAdapter makeAdapter() {
    return new MultiviewTypeAdapter(testCells(0), context);
  }

  public RecyclerView makeRecyclerView(Context context, View v, int viewId) {
    MultiviewTypeAdapter adapter = makeAdapter();
    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
    ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
    RecyclerView recyclerView = v.findViewById(viewId);
    if (configPreference.getBoolean("reorder_cells_toggle", false)) {
      mItemTouchHelper.attachToRecyclerView(recyclerView);
    } else {
      mItemTouchHelper.attachToRecyclerView(null);
    }
    if (configPreference.getBoolean("grid_toggle", true)) {
      recyclerView.setLayoutManager(new GridLayoutManager(context, 2) {
        @Override
        public boolean canScrollVertically() {
          return true;
        }
      });
    } else {
      recyclerView.setLayoutManager(new LinearLayoutManager(context) {
        @Override
        public boolean canScrollVertically() {
          return true;
        }
      });
    }
    //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
    //recyclerView.addItemDecoration(itemDecoration);
    recyclerView.setAdapter(adapter);
    recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);
    return recyclerView;
  }

  public void layoutMaker(int table_status, String import_json, View v, RecyclerView mRecyclerViewTop, RecyclerView mRecyclerViewBot) {
    String top;
    String bot;
    tableSorter(table_status, v, mRecyclerViewTop, mRecyclerViewBot);
    if (table_status == NONE || table_status == AUTO || table_status == TELEOP) {
      top = import_json.split("\\^")[0];
      import_cells(top, mRecyclerViewTop);
    } else {
      top = import_json.split("\\^")[0];
      bot = import_json.split("\\^")[1];
      import_cells(top, mRecyclerViewTop);
      import_cells(bot, mRecyclerViewBot);
    }
    Button importButton = v.findViewById(R.id.importButton);
    Button loadButton = v.findViewById(R.id.loadButton);
    // Uncomment and adjust visibility as needed.
    // if (!import_json.equals("")) {
    //     importButton.setVisibility(View.GONE);
    //     loadButton.setVisibility(View.GONE);
    // } else {
    //     importButton.setVisibility(View.VISIBLE);
    //     loadButton.setVisibility(View.VISIBLE);
    // }
  }

  public boolean allPermissionsGranted() {
    for (String permission : REQUIRED_PERMISSIONS) {
      if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  public void setButtonStatus(Button button, boolean condition, String trueText, String falseText) {
    button.setText(condition ? trueText : falseText);
    button.setBackgroundColor(ContextCompat.getColor(context, condition ? android.R.color.holo_green_light : android.R.color.holo_red_light));
  }
}
