package com.stencyl.googleplaygames;

import cpp.Lib;
import lime.system.JNI;

class GooglePlayGames
{
    private static var ANDROID_CLASS:String = "com/stencyl/GoogleServices/GooglePlayGames";
    private static var android_initGooglePlayGames:Dynamic;
    private static var android_signOutGooglePlayGames:Dynamic;
    private static var android_getConnectionInfoSignedIn:Dynamic;
    private static var android_getConnectionInfoIsConnected:Dynamic;
    private static var android_getConnectionInfoHasSignInError:Dynamic;
    private static var android_getConnectionInfoHasUserCancellation:Dynamic;
    private static var android_showAchievements:Dynamic;
    private static var android_unlockAchievement:Dynamic;
    private static var android_incrementAchievement:Dynamic;
    private static var android_unlockAchievementImmediate:Dynamic;
    private static var android_incrementAchievementImmediate:Dynamic;
    private static var android_showAllLeaderboards:Dynamic;
    private static var android_showLeaderboard:Dynamic;
    private static var android_submitScore:Dynamic;
	private static var android_forceLogin:Dynamic;
    private static var android_saveToCloud:Dynamic;
    private static var android_loadFromCloud:Dynamic;
    private static var android_showSavedGamesUI:Dynamic;
    
    public function new()
    {
    }
    
    public static function initGooglePlayGames():Void
    {
        if (android_initGooglePlayGames == null)
        {
            android_initGooglePlayGames = JNI.createStaticMethod(ANDROID_CLASS, "initGooglePlayGames", "(Lorg/haxe/lime/HaxeObject;)V", true);
        }
        
        var args = new Array<Dynamic>();
		args.push(new GooglePlayGames());
        android_initGooglePlayGames(args);
    }

    public static function forceLogin():Void
    {
        if (android_forceLogin == null)
        {
            android_forceLogin = JNI.createStaticMethod(ANDROID_CLASS, "forceLogin", "()V", true);
        }
        android_forceLogin();
    }

	public static function signOutGooglePlayGames():Void
    {
        if (android_signOutGooglePlayGames == null)
        {
            android_signOutGooglePlayGames = JNI.createStaticMethod(ANDROID_CLASS, "signOutGooglePlayGames", "()V", true);
        }
        
        var args = new Array<Dynamic>();
        android_signOutGooglePlayGames(args);
    }
    
	public static function getConnectionInfo(info:Int):Bool
	{
		if (info == 0)
        {
            if (android_getConnectionInfoSignedIn == null)
            {
                android_getConnectionInfoSignedIn = JNI.createStaticMethod(ANDROID_CLASS, "isSignedIn", "()Z", true);
            }
            return android_getConnectionInfoSignedIn();
        }
		else if (info == 1)
		{
			if (android_getConnectionInfoIsConnected == null)
            {
                android_getConnectionInfoIsConnected = JNI.createStaticMethod(ANDROID_CLASS, "isConnecting", "()Z", true);
            }
            return android_getConnectionInfoIsConnected();
		}
		else if (info == 2)
		{
			if (android_getConnectionInfoHasSignInError== null)
            {
                android_getConnectionInfoHasSignInError = JNI.createStaticMethod(ANDROID_CLASS, "hasSignInError", "()Z", true);
            }
            return android_getConnectionInfoHasSignInError();
		}
		else if (info == 3) //user cancellation
		{
			return false;
		}
		
		return false;
	}
	
    public static function showAchievements():Void
    {
        if (android_showAchievements == null)
        {
            android_showAchievements = JNI.createStaticMethod(ANDROID_CLASS, "showAchievements", "()V", true);
        }
        
        var args = new Array<Dynamic>();
        android_showAchievements(args);
    }
	
	public static function unlockAchievement(id:String):Void
    {
        if (android_unlockAchievement == null)
        {
            android_unlockAchievement = JNI.createStaticMethod(ANDROID_CLASS, "unlockAchievement", "(Ljava/lang/String;)V", true);
        }
        
        var args:Array<Dynamic> = [id];
        android_unlockAchievement(args);
    }
	
	public static function incrementAchievement(id:String, numSteps:Int):Void
    {
        if (android_incrementAchievement == null)
        {
            android_incrementAchievement = JNI.createStaticMethod(ANDROID_CLASS, "incrementAchievement", "(Ljava/lang/String;I)V", true);
        }
        
        var args:Array<Dynamic> = [id, numSteps];
        android_incrementAchievement(args);
    }
	
	public static function unlockAchievementImmediate(id:String):Void
    {
        if (android_unlockAchievementImmediate == null)
        {
            android_unlockAchievementImmediate = JNI.createStaticMethod(ANDROID_CLASS, "unlockAchievementImmediate", "(Ljava/lang/String;)V", true);
        }
        
        var args:Array<Dynamic> = [id];
        android_unlockAchievementImmediate(args);
    }
	
	public static function incrementAchievementImmediate(id:String, numSteps:Int):Void
    {
        if (android_incrementAchievementImmediate == null)
        {
            android_incrementAchievementImmediate = JNI.createStaticMethod(ANDROID_CLASS, "incrementAchievementImmediate", "(Ljava/lang/String;I)V", true);
        }
        
        var args:Array<Dynamic> = [id, numSteps];
        android_incrementAchievementImmediate(args);
    }
	
	public static function showAllLeaderboards():Void
    {
        if (android_showAllLeaderboards == null)
        {
            android_showAllLeaderboards = JNI.createStaticMethod(ANDROID_CLASS, "showAllLeaderboards", "()V", true);
        }
        
        android_showAllLeaderboards();
    }
	
	
	public static function showLeaderboard(id:String):Void
    {
        if (android_showLeaderboard == null)
        {
            android_showLeaderboard = JNI.createStaticMethod(ANDROID_CLASS, "showLeaderboard", "(Ljava/lang/String;)V", true);
        }
        
        var args = [id];
        android_showLeaderboard(args);
    }
	
	public static function submitScore(id:String, score:Int):Void
    {
        if (android_submitScore == null)
        {
            android_submitScore = JNI.createStaticMethod(ANDROID_CLASS, "submitScore", "(Ljava/lang/String;I)V", true);
        }
        
        var args:Array<Dynamic> = [id, score];
        android_submitScore(args);
    }
	
    public static function saveToCloud(localFileName:String, slotName:String, description:String):Void
    {
        if (android_saveToCloud == null)
        {
            android_saveToCloud = JNI.createStaticMethod(ANDROID_CLASS, "saveToCloud", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", true);
        }
    
        var args:Array<Dynamic> = [localFileName, slotName, description];
        android_saveToCloud(args);
    }

    public static function loadFromCloud(slotName:String, localFileName:String):Void
    {
        if (android_loadFromCloud == null)
        {
            android_loadFromCloud = JNI.createStaticMethod(ANDROID_CLASS, "loadFromCloud", "(Ljava/lang/String;Ljava/lang/String;)V", true);
        }
        
        var args:Array<Dynamic> = [slotName, localFileName];
        android_loadFromCloud(args);
    }

    public static function showSavedGamesUI(title:String):Void
    {
        if (android_showSavedGamesUI == null)
        {
            android_showSavedGamesUI = JNI.createStaticMethod(ANDROID_CLASS, "showSavedGamesUI", "(Ljava/lang/String;)V", true);
        }
        
        var args:Array<Dynamic> = [title];
        android_showSavedGamesUI(args);
    }

	public function onSignInFailed(error:String)
	{
		trace(error);
	}
}
