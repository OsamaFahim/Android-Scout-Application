package com.databits.androidscouting.fragment.settings;

import static com.databits.androidscouting.util.GoogleAuthActivity.REQUEST_ACCOUNT_PICKER;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.databits.androidscouting.R;
import com.databits.androidscouting.databinding.FragmentSettingsGoogleconfigBinding;
import com.databits.androidscouting.util.FileUtils;
import com.databits.androidscouting.util.GoogleAuthActivity;
import com.databits.androidscouting.util.MatchInfo;
import com.databits.androidscouting.util.PreferenceManager;
import com.databits.androidscouting.util.ScoutUtils;
import com.databits.androidscouting.util.TeamInfo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.opencsv.CSVReader;
import com.preference.PowerPreference;
import com.preference.Preference;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoogleConfig extends Fragment {
  private FragmentSettingsGoogleconfigBinding binding;
//  Preference configPreference = PowerPreference.getFileByName("Config");
//  Preference listPreference = PowerPreference.getFileByName("List");
Preference configPreference = PreferenceManager.getInstance().getConfigPreference();
  Preference listPreference = PreferenceManager.getInstance().getListPreference();
  ScoutUtils scoutUtils;
  FileUtils fileUtils;
  MatchInfo matchInfo;
  TeamInfo teamInfo;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {

    binding = FragmentSettingsGoogleconfigBinding.inflate(inflater, container, false);
    return binding.getRoot();

  }

  @Override
  public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
    super.onViewCreated(v, savedInstanceState);

    // Go Full screen
    View decorView = requireActivity().getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    decorView.setSystemUiVisibility(uiOptions);

    NavController controller = NavHostFragment.findNavController(GoogleConfig.this);

    // Helper Classes
    scoutUtils = new ScoutUtils(requireContext());
    fileUtils = new FileUtils(requireContext());
    matchInfo = new MatchInfo();
    teamInfo = new TeamInfo(requireContext());

    binding.buttonBack.setOnClickListener(v1 -> controller.navigateUp());

    binding.buttonGoogleId.setOnClickListener(view -> {
      updateID(true);
    });

    binding.crowdSheetLocation.buttonGoogleRange.setOnClickListener(view -> {
      updateRange(binding.crowdSheetLocation.getRoot(), "Crowd", true);
    });

    binding.pitSheetLocation.buttonGoogleRange.setOnClickListener(view -> {
      updateRange(binding.pitSheetLocation.getRoot(), "Pit", true);
    });

    binding.specialtySheetLocation.buttonGoogleRange.setOnClickListener(view -> {
      updateRange(binding.specialtySheetLocation.getRoot(), "Specialty", true);
    });

    // Run on page startup
    updateRange(binding.crowdSheetLocation.getRoot(), "Crowd", false);
    updateRange(binding.pitSheetLocation.getRoot(), "Pit", false);
    updateRange(binding.specialtySheetLocation.getRoot(), "Specialty", false);
    updateID(false);
  }

  private void updateID(boolean save) {
    TextInputEditText editText = requireView().findViewById(R.id.google_id_text_edit);
    String sheet = configPreference.getString("workbook_id","");
    if (sheet.isEmpty() || save) {
      configPreference.setString("workbook_id", Objects.requireNonNull(
          editText.getText()).toString());
    } else {
      editText.setText(sheet);
    }
    Toast.makeText(requireContext(), configPreference.getString("workbook_id",""), Toast.LENGTH_SHORT).show();
  }

  private void updateRange(View view, String id_prefix, boolean save) {
    TextInputEditText name = view.findViewById(R.id.sheet_name_text_edit);
    TextInputEditText lower = view.findViewById(R.id.lower_range_text_edit);
    TextInputEditText upper = view.findViewById(R.id.upper_range_text_edit);

    String title = String.format("%s Data Location", id_prefix);

    // Set view title based on id_prefix
    Toast.makeText(requireContext(), id_prefix, Toast.LENGTH_SHORT).show();

    switch (id_prefix) {
      case "Crowd":
        binding.crowdSheetLocation.dataTitle.setText(title);
        break;
      case "Pit":
        binding.pitSheetLocation.dataTitle.setText(title);
        break;
      case "Specialty":
        binding.specialtySheetLocation.dataTitle.setText(title);
    }

    if (save) {
      // Range is name!lower:upper ex= StatsRaw!A2:Z700
      String range = Objects.requireNonNull(name.getText()) + "!" + Objects.requireNonNull(
          lower.getText()) + ":" +
          Objects.requireNonNull(upper.getText());

      // Set configPreference string to the range based on id_prefix
      configPreference.setString(id_prefix + "_range", range);
      Toast.makeText(requireContext(), configPreference.getString("Specialty_range",""), Toast.LENGTH_SHORT).show();

    } else {
      // Get the range from configPreference and set the name, lower, and upper text fields
      String range = configPreference.getString(id_prefix + "_range","");
      if (!range.isEmpty()) {
        String[] split = range.split("!");
        name.setText(split[0]);
        split = split[1].split(":");
        lower.setText(split[0]);
        upper.setText(split[1]);
      }
    }

    binding.importButton.setOnClickListener(view1 -> {
      AlertDialog googleConfigDialog = new AlertDialog.Builder(requireContext())
          .setTitle("Do you want to import a new Google Config?")
          .setMessage("The format is a csv file with the following format: "
              + "workbook_id,crowd_range,pit_range,specialty_range\n\n"
              + "Range Example: CrowdRaw!A2:Z700\n\n"
              + "Select Yes and then pick your csv file to import from your device.")
          .setPositiveButton("Yes", (dialog1, which1) -> {
            Intent data = fileUtils.intentFileDialog();
            Intent.createChooser(data, "Select a google_config.csv file to import");
            importGoogleLauncher.launch(data);
          })
          .setNegativeButton("Cancel", (dialog1, which1) -> {
            // Do nothing
          })
          .create();
      googleConfigDialog.show();
    });

    scoutUtils.setButtonStatus(binding.googleButton,
        !configPreference.getString("google_account_name").isEmpty(),
        "Logged into Google", "Sign into Google");

    binding.googleButton.setOnClickListener(view1 -> {
      Intent intent = new Intent(requireContext(), GoogleAuthActivity.class);
      startActivity(intent);
    });
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  public ActivityResultLauncher<Intent> importGoogleLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
          Intent data = result.getData();
          if (data != null) {
            FileUtils.copyFileToInternal(requireContext(),data.getData(),
                "google_config.csv");
            String[][] dataArr;
            try {
              File teams = new File(requireContext().getFilesDir() + "/" + "google_config.csv");
              CSVReader csvReader = new CSVReader(new FileReader(teams));
              List<String[]> list = csvReader.readAll();
              int size = list.size();
              dataArr = new String[size][];
              dataArr = list.toArray(dataArr);
              for (String[] row : dataArr) {
                configPreference.setString("workbook_id", row[0]);
                configPreference.setString("Crowd_range", row[1]);
                configPreference.setString("Pit_range", row[2]);
                configPreference.setString("Specialty_range", row[3]);
              }
              updateRange(binding.crowdSheetLocation.getRoot(), "Crowd", false);
              updateRange(binding.pitSheetLocation.getRoot(), "Pit", false);
              updateRange(binding.specialtySheetLocation.getRoot(), "Specialty", false);
              updateID(false);
              listPreference.setObject("google_config", dataArr);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
  );
}

