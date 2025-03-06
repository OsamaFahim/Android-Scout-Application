package com.databits.androidscouting.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
import com.preference.PowerPreference;
import com.preference.Preference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.databits.androidscouting.util.GoogleAuthActivity.REQUEST_ACCOUNT_PICKER;

public class SheetsUpdateTask {
  // Keep track of the last auth error
  public static UserRecoverableAuthIOException mLastError;

  private final Context context;
  private final Activity activity;
  private final Sheets sheetsService;
  private String spreadsheetId;

  // Preferences for configuration and debug info
//  Preference configPreference = PowerPreference.getFileByName("Config");
//  Preference debugPreference = PowerPreference.getFileByName("Debug");
//  Preference matchPreference = PowerPreference.getFileByName("Match");
  Preference configPreference = (Preference) PreferenceManager.getInstance().getConfigPreference();
  Preference debugPreference = PreferenceManager.getInstance().getDebugPreference();
  Preference matchPreference = PreferenceManager.getInstance().getMatchPreference();

  // Executor for background work and a handler for UI updates
  private final ExecutorService executor;
  private final Handler handler;

  public SheetsUpdateTask(Activity activity) {
    this.activity = activity;
    this.context = activity.getBaseContext();
    this.handler = new Handler(Looper.getMainLooper());
    this.executor = Executors.newSingleThreadExecutor();

    // Set up Google Account Credential
    GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                    context, Arrays.asList(SheetsScopes.SPREADSHEETS))
            .setBackOff(new ExponentialBackOff());
    String accountName = configPreference.getString("google_account_name", null);
    credential.setSelectedAccountName(accountName);
    Log.d("SheetsUpdateTaskRunner", "Selected Account Name: " + accountName);

    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    sheetsService = new Sheets.Builder(transport, jsonFactory, credential)
            .setApplicationName("Android Scouter")
            .build();
  }

  public void execute() {
    // Retrieve the spreadsheet ID (pre-created in Google Sheets)
    //spreadsheetId = configPreference.getString("workbook_id", "");
    spreadsheetId = "1ksCFboY3RF0d6eCQHtH2bdrPWFHXZXnicNJGz6pXndM";

    Log.d("SheetsUpdateTaskRunner", "Spreadsheet ID: " + spreadsheetId);

    // Determine the upload mode and log it
    String uploadMode = configPreference.getString("uploadMode", "");
    Log.d("SheetsUpdateTaskRunner", "Upload Mode: " + uploadMode);

    // Execute the task in a background thread
    executor.execute(() -> {
      AppendValuesResponse response = null;
      List<List<String>> columnData = null;

      if (uploadMode != null) {
        switch (uploadMode) {
          case "Crowd":
            columnData = new ArrayList<>(matchPreference.getObject("upload_data",
                    ArrayList.class, new ArrayList<>()));
            break;
          case "Pit":
            columnData = new ArrayList<>(matchPreference.getObject("pit_upload_data",
                    ArrayList.class, new ArrayList<>()));
            break;
          case "Speciality":
            columnData = new ArrayList<>(matchPreference.getObject("special_upload_data",
                    ArrayList.class, new ArrayList<>()));
            break;
          default:
            Log.d("SheetsUpdateTaskRunner", "No valid upload mode set.");
        }
      }
      Log.d("SheetsUpdateTaskRunner", "Column Data Size: " + (columnData != null ? columnData.size() : 0));

      try {
        // Determine the sheet range based on the upload mode
        String range = "";
        if (uploadMode != null) {
          switch (uploadMode) {
            case "Crowd":
              range = configPreference.getString("Crowd_range", "Sheet1!A1");
              break;
            case "Pit":
              range = configPreference.getString("Pit_range", "Sheet1!A1");
              break;
            case "Speciality":
              range = configPreference.getString("Speciality_range", "Sheet1!A1");
              break;
          }
        }
        Log.d("SheetsUpdateTaskRunner", "Sheet Range: " + range);

        // Configure the ValueRange object to hold our data
        ValueRange content = new ValueRange();
        content.setMajorDimension("ROWS");
        content.setRange(range);

        if (columnData != null && !columnData.isEmpty()) {
          // Convert List<List<String>> into List<List<Object>>
          List<List<Object>> upload = new ArrayList<>();
          for (List<String> rowData : columnData) {
            List<Object> row = new ArrayList<>(rowData);
            upload.add(row);
          }
          content.setValues(upload);

          Log.d("SheetsUpdateTaskRunner", "Uploading data to Google Sheets...");
          // Execute the API call to append data
          response = sheetsService.spreadsheets().values().append(spreadsheetId, range, content)
                  .setValueInputOption("USER_ENTERED")
                  .setInsertDataOption("OVERWRITE")
                  .execute();
          Log.d("SheetsUpdateTaskRunner", "Upload Response: " + response.getUpdates().toString());
        } else {
          Log.d("SheetsUpdateTaskRunner", "No data available to upload.");
        }
      } catch (UserRecoverableAuthIOException g) {
        mLastError = g;
        Log.d("SheetsUpdateTaskRunner", "UserRecoverableAuthIOException: " + g.getMessage());
        debugPreference.putBoolean("upload_error", true);
        debugPreference.setString("upload_error_message", g.getMessage());
        // Post to UI thread to start the account picker activity
        handler.post(() -> activity.startActivityForResult(mLastError.getIntent(), REQUEST_ACCOUNT_PICKER));
        return;
      } catch (IOException e) {
        Log.d("SheetsUpdateTaskRunner", "IOException: " + e.getMessage());
        e.printStackTrace();
        debugPreference.putBoolean("upload_error", true);
        debugPreference.setString("upload_error_message", e.getMessage());
      }

      // Post the result back to the UI thread
      final AppendValuesResponse finalResponse = response;
      handler.post(() -> {
        if (finalResponse == null) {
          if (debugPreference.getBoolean("upload_error", false)) {
            debugPreference.putBoolean("upload_error", false);
            Toast.makeText(context, debugPreference.getString("upload_error_message"),
                    Toast.LENGTH_LONG).show();
          } else {
            Toast.makeText(context, "No data to upload", Toast.LENGTH_LONG).show();
          }
        } else {
          String updatedRange = finalResponse.getUpdates().getUpdatedRange();
          Toast.makeText(context, "Updated Data range: " + updatedRange,
                  Toast.LENGTH_LONG).show();
          Log.d("SheetsUpdateTaskRunner", "onPostExecute: " + finalResponse.getUpdates().toString());
          matchPreference.clear();
        }
      });
    });
  }
}
