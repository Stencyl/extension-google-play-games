package com.stencyl.googleplaygames;

class GooglePlayGames
{
    public static function initGooglePlayGames():Void {}
    public static function signOutGooglePlayGames():Void {}
    public static function getConnectionInfo(info:Int):Bool { return false; }
    public static function showAchievements():Void {}
    public static function unlockAchievement(id:String):Void {}
    public static function incrementAchievement(id:String, numSteps:Int):Void {}
    public static function unlockAchievementImmediate(id:String):Void {}
    public static function incrementAchievementImmediate(id:String, numSteps:Int):Void {}
    public static function showAllLeaderboards():Void {}
    public static function showLeaderboard(id:String):Void {}
    public static function submitScore(id:String, score:Int):Void {}
    public static function showQuests():Void {}
    public static function updateEvent(id:String, amount:Int):Void {}
    public static function hasNewQuestCompleted():Bool { return false; }
    public static function getQuestReward(id:String):String { return ""; }
    public static function getCompletedQuestList():Array<String> { return []; }
}