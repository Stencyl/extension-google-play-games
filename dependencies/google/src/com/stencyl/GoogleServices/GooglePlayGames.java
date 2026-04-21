package com.stencyl.GoogleServices;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

// Play Games: explicit imports only (no wildcards)
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.AuthenticationResult;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.EventsClient;

// --- NEW IMPORTS FOR SNAPSHOTS API ---
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
// -------------------------------------

import com.google.android.gms.tasks.Task;

public class GooglePlayGames extends Extension
{
    static GooglePlayGames mpg = null;
    static HaxeObject haxeCallback;

    static boolean signedIn;
    static boolean connecting;
    static boolean signInError;

    // We need a specific intent code for the Saved Games UI
    private static final int RC_SAVED_GAMES = 9009;

    public GooglePlayGames()
    {
        super();
        mpg = this;
    }

    @Override
    public void onStart()
    {
        if (haxeCallback != null)
        {
            signInSilently();
        }
    }

    @SuppressWarnings("unused")
    public static void initGooglePlayGames(final HaxeObject obj)
    {
        Log.d("GPG", "Initializing Google Play Games (Java)");
        mainActivity.runOnUiThread(new Runnable()
        {
            @Override public void run()
            {
                haxeCallback = obj;
                signInSilently();
            }
        });
    }

// --------- Silent Sign-In: checks status, NO dialog
private static void signInSilently() {
    GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(Extension.mainActivity);
    if (!signedIn) connecting = true;

    gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {
        connecting = false;
        signedIn = false;
        signInError = false;

        if (!isAuthenticatedTask.isSuccessful()) {
            String message = (isAuthenticatedTask.getException() == null)
                    ? "Failed to sign in"
                    : isAuthenticatedTask.getException().getMessage();
            Log.e("GPG", "onSignInFailed", isAuthenticatedTask.getException());
            signInError = true;
            if (haxeCallback != null) {
                haxeCallback.call("onSignInFailed", new Object[]{ message });
            }
        }
        else {
            AuthenticationResult result = isAuthenticatedTask.getResult();
            if (result == null || !result.isAuthenticated()) {
                Log.d("GPG", "notAuthenticated");
                signInError = true;
                if (haxeCallback != null) {
                    haxeCallback.call("onSignInFailed", new Object[]{ "Not signed in" });
                }
            }
            else {
                Log.d("GPG", "onSignInSucceeded");
                signedIn = true;      // <- important for leaderboards
                signInError = false;
            }
        }
    });
}


// --------- ForceLogin: interactive, set flags cleanly
public static void forceLogin() {
    final Activity activity = Extension.mainActivity;
    activity.runOnUiThread(new Runnable() {
        @Override public void run() {
            GamesSignInClient client = PlayGames.getGamesSignInClient(activity);
            connecting = true;
            client.signIn().addOnCompleteListener(signInTask -> {
                connecting = false;
                if (signInTask.isSuccessful()
                        && signInTask.getResult() != null
                        && signInTask.getResult().isAuthenticated()) {
                    signedIn = true;
                    signInError = false;
                    Log.d("GPG", "Interactive sign-in succeeded");
                } else {
                    signedIn = false;
                    signInError = true;
                    String msg = (signInTask.getException() != null)
                            ? signInTask.getException().getMessage()
                            : "Sign-in cancelled or failed";
                    Log.d("GPG", "Interactive sign-in failed: " + msg);
                    if (haxeCallback != null) {
                        haxeCallback.call("onSignInFailed", new Object[]{ msg });
                    }
                }
            });
        }
    });
}



    // Helper to start Task<Intent> (for achievements/leaderboards)
    private static void startActivity(Task<Intent> intentTask, String info)
    {
        intentTask.addOnCompleteListener(getIntentTask -> {
            if (getIntentTask.isSuccessful())
            {
                mainActivity.startActivityForResult(getIntentTask.getResult(), 1);
            }
            else
            {
                Log.e("GPG", "Failed to " + info, getIntentTask.getException());
            }
        });
    }

    @SuppressWarnings("unused")
    public static boolean isSignedIn()    { return signedIn; }
    @SuppressWarnings("unused")
    public static boolean isConnecting()  { return connecting; }
    @SuppressWarnings("unused")
    public static boolean hasSignInError(){ return signInError; }

    @SuppressWarnings("unused")
    public static void signOutGooglePlayGames()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage("Use the Play Games app to manage the connected Play Games account for this app, or sign out completely.")
               .setTitle("Sign Out of Google Play Games")
               .setPositiveButton("OK", (dialog, id) -> {});
        builder.show();
    }

    @SuppressWarnings("unused")
    public static void showAchievements()
    {
        Log.d("GPG", "Showing Achievements (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);
        startActivity(achievementsClient.getAchievementsIntent(), "show achievements");
    }

    @SuppressWarnings("unused")
    public static void unlockAchievement(String id)
    {
        Log.d("GPG", "Unlocking Achievement " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);
        achievementsClient.unlock(id);
    }

    @SuppressWarnings("unused")
    public static void incrementAchievement(String id, int numSteps)
    {
        Log.d("GPG", "Incrementing Achievement " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);
        achievementsClient.increment(id, numSteps);
    }

    @SuppressWarnings("unused")
    public static void unlockAchievementImmediate(String id)
    {
        Log.d("GPG", "Unlocking Achievement Immediate: " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);
        achievementsClient.unlockImmediate(id);
    }

    @SuppressWarnings("unused")
    public static void incrementAchievementImmediate(String id, int numSteps)
    {
        Log.d("GPG", "Incrementing Achievement Immediate: " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);
        achievementsClient.incrementImmediate(id, numSteps);
    }

    @SuppressWarnings("unused")
    public static void showAllLeaderboards()
    {
        Log.d("GPG", "Showing All Leaderboards (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);
        startActivity(leaderboardsClient.getAllLeaderboardsIntent(), "show all leaderboards");
    }

    @SuppressWarnings("unused")
    public static void showLeaderboard(final String id)
    {
        Log.d("GPG", "Showing Leaderboards (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);
        startActivity(leaderboardsClient.getLeaderboardIntent(id), "show leaderboards");
    }

    @SuppressWarnings("unused")
    public static void submitScore(String id, int score)
    {
        Log.d("GPG", "Submitting Score " + score + " to " + id + " (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);
        leaderboardsClient.submitScore(id, score);
    }

    @SuppressWarnings("unused")
    public static void updateEvent(String id, int amount)
    {
        Log.d("GPG", "Updating Event " + id + " by " + amount + " (Java)");
        EventsClient eventsClient = PlayGames.getEventsClient(mainActivity);
        eventsClient.increment(id, amount);
    }

    // =========================================================================
    // --- NEW SNAPSHOTS API IMPLEMENTATION (SAVED GAMES) ---
    // =========================================================================

    @SuppressWarnings("unused")
    public static void saveToCloud(final String localFileName, final String slotName, final String description)
    {
        if (!signedIn) {
            Log.e("GPG", "Cannot save to cloud: User is not signed in.");
            return;
        }

        Log.d("GPG", "Starting Cloud Save for slot: " + slotName);
        SnapshotsClient snapshotsClient = PlayGames.getSnapshotsClient(mainActivity);

        // First, open the snapshot
        snapshotsClient.open(slotName, true, SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Snapshot snapshot = task.getResult().getData();
                    
                    // Read the local file Stencyl created
                    byte[] dataToSave = readLocalFile(localFileName);
                    
                    if (dataToSave != null) {
                        snapshot.getSnapshotContents().writeBytes(dataToSave);
                        
                        SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                                .setDescription(description)
                                .build();
                                
                        snapshotsClient.commitAndClose(snapshot, metadataChange)
                            .addOnCompleteListener(commitTask -> {
                                if (commitTask.isSuccessful()) {
                                    Log.d("GPG", "Successfully saved to Cloud!");
                                } else {
                                    Log.e("GPG", "Failed to commit Cloud Save", commitTask.getException());
                                }
                            });
                    } else {
                        Log.e("GPG", "Local save file was empty or not found. Cannot upload to cloud.");
                    }
                } else {
                    Log.e("GPG", "Failed to open snapshot for saving", task.getException());
                }
            });
    }

    @SuppressWarnings("unused")
    public static void loadFromCloud(final String slotName, final String localFileName)
    {
        if (!signedIn) {
            Log.e("GPG", "Cannot load from cloud: User is not signed in.");
            return;
        }

        Log.d("GPG", "Starting Cloud Load for slot: " + slotName);
        SnapshotsClient snapshotsClient = PlayGames.getSnapshotsClient(mainActivity);

        snapshotsClient.open(slotName, false, SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Snapshot snapshot = task.getResult().getData();
                    try {
                        byte[] cloudData = snapshot.getSnapshotContents().readFully();
                        
                        if (cloudData != null && cloudData.length > 0) {
                            writeLocalFile(localFileName, cloudData);
                            Log.d("GPG", "Successfully downloaded from Cloud and overwritten local file.");
                        } else {
                            Log.d("GPG", "Cloud save was empty, nothing to load.");
                        }
                    } catch (IOException e) {
                        Log.e("GPG", "Error reading cloud data", e);
                    }
                } else {
                    Log.e("GPG", "Failed to open snapshot for loading", task.getException());
                }
            });
    }

    @SuppressWarnings("unused")
    public static void showSavedGamesUI(final String title)
    {
        if (!signedIn) {
            Log.e("GPG", "Cannot show Saved Games UI: User is not signed in.");
            return;
        }

        SnapshotsClient snapshotsClient = PlayGames.getSnapshotsClient(mainActivity);
        int maxNumberOfSavedGamesToShow = 5;

        snapshotsClient.getSelectSnapshotIntent(title, true, true, maxNumberOfSavedGamesToShow)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    mainActivity.startActivityForResult(task.getResult(), RC_SAVED_GAMES);
                } else {
                    Log.e("GPG", "Failed to open Saved Games UI", task.getException());
                }
            });
    }

    // --- Helper File IO Methods ---
    private static byte[] readLocalFile(String fileName) {
        // Stencyl saves attributes in the app's internal storage directory under "gamesaves" or root.
        File dir = Extension.mainActivity.getFilesDir();
        File file = new File(dir, fileName);
        
        if(!file.exists()) {
             Log.d("GPG", "Save file " + fileName + " not found in direct path, trying fallback...");
             // Sometimes stencyl uses a '.sol' extension or a subdirectory depending on the engine version
             file = new File(dir, fileName + ".sol");
        }

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                return data;
            } catch (IOException e) {
                Log.e("GPG", "Failed to read local save file", e);
            }
        }
        return null;
    }

    private static void writeLocalFile(String fileName, byte[] data) {
        File dir = Extension.mainActivity.getFilesDir();
        File file = new File(dir, fileName);
        
        // Ensure we don't end up writing "mySave" if stencyl actually expects "mySave.sol"
        if(!file.exists()) {
             File altFile = new File(dir, fileName + ".sol");
             if(altFile.exists()) file = altFile;
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException e) {
            Log.e("GPG", "Failed to write local save file", e);
        }
    }
}
