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
    private static var android_showQuests:Dynamic;
    private static var android_getCompletedQuests:Dynamic;
    private static var android_updateEvent:Dynamic;
    private static var android_getCompletedQuestList:Dynamic;
    private static var android_hasNewQuestCompleted:Dynamic;
    private static var android_getQuestReward:Dynamic;
    
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
		else if (info == 3)
		{
			if (android_getConnectionInfoHasUserCancellation == null)
            {
                android_getConnectionInfoHasUserCancellation = JNI.createStaticMethod(ANDROID_CLASS, "hasUserCancellation", "()Z", true);
            }
            return android_getConnectionInfoHasUserCancellation();
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
	
	public static function showQuests():Void
    {
        if (android_showQuests == null)
        {
            android_showQuests = JNI.createStaticMethod(ANDROID_CLASS, "showQuests", "()V", true);
        }
        
        var args = new Array<Dynamic>();
        android_showQuests(args);
    }
	
	public static function updateEvent(id:String, amount:Int):Void
    {
        if (android_updateEvent == null)
        {
            android_updateEvent = JNI.createStaticMethod(ANDROID_CLASS, "updateEvent", "(Ljava/lang/String;I)V", true);
        }
        
        var args:Array<Dynamic> = [id, amount];
        android_updateEvent(args);
    }
	
	public static function hasNewQuestCompleted():Bool
    {
        if (android_hasNewQuestCompleted == null)
        {
            android_hasNewQuestCompleted = JNI.createStaticMethod(ANDROID_CLASS, "hasNewQuestCompleted", "()Z", true);
        }
        
        var args:Array<Dynamic> = new Array<Dynamic>();
		var ret:Bool = cast android_hasNewQuestCompleted(args);
        return ret;
    }
	
	public static function getQuestReward(id:String):String
    {
		if (android_getQuestReward == null)
        {
            android_getQuestReward = JNI.createStaticMethod(ANDROID_CLASS, "getQuestReward", "(Ljava/lang/String;)Ljava/lang/String;", true);
        }
        
        var args = [id];
		var ret:String = cast android_getQuestReward(args);
		
		if (ret == null)
		{
			trace("No reward text recieved for quest: " + id);
			ret = "";
		}
        return ret;
	}
	
	public static function getCompletedQuestList():Array<String>
    {
        if (android_getCompletedQuestList == null)
        {
            android_getCompletedQuestList = JNI.createStaticMethod(ANDROID_CLASS, "getCompletedQuestList", "()[Ljava/lang/String;", true);
        }
        
        var args:Array<Dynamic> = new Array<Dynamic>();
		var ret:Array<String> = cast android_getCompletedQuestList(args);
        return ret;
    }
	
	public function onSignInFailed(error:String)
	{
		trace(error);
	}
}
