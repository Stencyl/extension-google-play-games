package com.stencyl.GoogleServices;

import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.games.*;
import com.google.android.gms.tasks.Task;

public class GooglePlayGames extends Extension
{
    static GooglePlayGames mpg = null;
    static HaxeObject haxeCallback;

    static boolean signedIn;
    static boolean connecting;
    static boolean signInError;

    public GooglePlayGames()
    {
        super();
        
        mpg = this;
    }

    @Override
    public void onStart()
    {
        if(haxeCallback != null)
        {
            signInSilently();
        }
    }

    @SuppressWarnings("unused")
    static public void initGooglePlayGames(final HaxeObject obj)
    {
        Log.d("GPG", "Initialiazing Google Play Games (Java)");        
        
        mainActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                haxeCallback = obj;

                signInSilently();
            }
        });
    }

    private static void signInSilently() {
        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(mainActivity);
        if(!signedIn)
            connecting = true;

        gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {
            connecting = false;
            signedIn = false;
            signInError = false;
            if(!isAuthenticatedTask.isSuccessful())
            {
                String message = isAuthenticatedTask.getException() == null ? "Failed to sign in" : isAuthenticatedTask.getException().getMessage();
                Log.e("GPG", "onSignInFailed", isAuthenticatedTask.getException());
                signInError = true;
                haxeCallback.call("onSignInFailed", new Object[] {message});
            }
            else
            {
                AuthenticationResult result = isAuthenticatedTask.getResult();
                if(!result.isAuthenticated())
                {
                    Log.d("GPG", "notAuthenticated");
                    signInError = true;
                    haxeCallback.call("onSignInFailed", new Object[] {"Not signed in"});
                }
                else
                {
                    Log.d("GPG", "onSignInSucceeded");
                    signedIn = true;
                    //successfully signed in
                }
            }
        });
    }
    
    static private void startActivity(Task<Intent> intentTask, String info)
    {
        intentTask.addOnCompleteListener(getIntentTask -> {
            if(getIntentTask.isSuccessful()) {
                mainActivity.startActivityForResult(intentTask.getResult(), 1);
            }
            else
            {
                Log.e("GPG", "Failed to " + info, getIntentTask.getException());
            }
        });
    }

    @SuppressWarnings("unused")
    static public boolean isSignedIn()
    {
        return signedIn;
    }

    @SuppressWarnings("unused")
    static public boolean isConnecting()
    {
        return connecting;
    }

    @SuppressWarnings("unused")
    static public boolean hasSignInError()
    {
        return signInError;
    }

    @SuppressWarnings("unused")
    static public void signOutGooglePlayGames()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage("Use the Play Games app to manage the connected Play Games account for this app, or sign out completely.")
                .setTitle("Sign Out of Google Play Games")
                .setPositiveButton("OK", (dialog, id) -> {
                    // User taps OK button.
                });
        AlertDialog dialog = builder.show();
    }

    @SuppressWarnings("unused")
    static public void showAchievements()
    {
        Log.d("GPG", "Showing Achievements (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);

        startActivity(achievementsClient.getAchievementsIntent(), "show achievements");
    }

    @SuppressWarnings("unused")
    static public void unlockAchievement(String id)
    {
        Log.d("GPG", "Unlocking Achievement " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);

        achievementsClient.unlock(id);
    }

    @SuppressWarnings("unused")
    static public void incrementAchievement(String id, int numSteps)
    {
        Log.d("GPG", "Incrementing Achievement " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);

        achievementsClient.increment(id, numSteps);
    }

    @SuppressWarnings("unused")
    static public void unlockAchievementImmediate(String id)
    {
        Log.d("GPG", "Unlocking Achievement Immediate: " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);

        achievementsClient.unlockImmediate(id);
    }

    @SuppressWarnings("unused")
    static public void incrementAchievementImmediate(String id, int numSteps)
    {
        Log.d("GPG", "Incrementing Achievement Immediate: " + id + " (Java)");
        AchievementsClient achievementsClient = PlayGames.getAchievementsClient(mainActivity);

        achievementsClient.incrementImmediate(id, numSteps);
    }

    @SuppressWarnings("unused")
    static public void showAllLeaderboards()
    {
        Log.d("GPG", "Showing All Leaderboards (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);

        startActivity(leaderboardsClient.getAllLeaderboardsIntent(), "show all leaderboards");
    }

    @SuppressWarnings("unused")
    static public void showLeaderboard(final String id)
    {
        Log.d("GPG", "Showing Leaderboards (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);

        startActivity(leaderboardsClient.getLeaderboardIntent(id), "show leaderboards");
    }

    @SuppressWarnings("unused")
    static public void submitScore(String id, int score)
    {
        Log.d("GPG", "Submitting Score " + score + " to " + id + " (Java)");
        LeaderboardsClient leaderboardsClient = PlayGames.getLeaderboardsClient(mainActivity);

        leaderboardsClient.submitScore(id, score);
    }

    @SuppressWarnings("unused")
    static public void updateEvent(String id, int amount)
    {
        Log.d("GPG", "Updating Event " + id + " by " + amount + " (Java)");
        EventsClient eventsClient = PlayGames.getEventsClient(mainActivity);

        eventsClient.increment(id, amount);
    }
}