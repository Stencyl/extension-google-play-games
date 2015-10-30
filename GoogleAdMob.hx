package;

#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#else
import openfl.Lib;
#end

#if android
import openfl.utils.JNI;
#end

import scripts.MyAssets;
import com.stencyl.Engine;
import com.stencyl.event.EventMaster;
import com.stencyl.event.StencylEvent;

import openfl.utils.ByteArray;
import openfl.display.BitmapData;
import openfl.geom.Rectangle;

class GoogleAdMob
{	
	private function new() {}
	
	//Universal
	private static var initialized:Bool = false;
	
	//Android-Only
    #if android
	public static var adwhirlCode:String = "none";
	public static var adwhirlCode1:String = "none";
	private static inline var ANDROID_CLASS:String = "com/stencyl/GoogleServices/AdMob";
	private static var _init_func:Dynamic;
	private static var _show_func:Dynamic;
	private static var _hide_func:Dynamic;
	
	private static var _load_full_func:Dynamic;
	private static var _show_full_func:Dynamic;
    #end
	
	//Ad Events only happen on iOS. AdMob provides no out-of-the-box way.
	private static function notifyListeners(inEvent:Dynamic)
	{
		
	}
	//Banner
	public static function initialize(apiCode:String = "none", apiCode1:String = "none", position:Int = 0):Void 
	{
		if(initialized)
		{
			return;
		}
		
		#if android
		//if(apiCode == "none" || apiCode == "")
		//{
			//return;
		//}
		
		adwhirlCode = apiCode;
		adwhirlCode1 = apiCode1;
		trace(adwhirlCode);
		trace(adwhirlCode1);
		
		if(_init_func == null)
		{
			_init_func = JNI.createStaticMethod("com/stencyl/GoogleServices/AdMob", "init", "(Lorg/haxe/lime/HaxeObject;Ljava/lang/String;Ljava/lang/String;I)V", true);
		}
	
		var args = new Array<Dynamic>();
		args.push(new GoogleAdMob());
		args.push(adwhirlCode);
		args.push(adwhirlCode1);
		args.push(MyAssets.adPositionBottom ? 0 : 1);
		_init_func(args);
			
		initialized = true;
		#end
	}

	public static function showAd(onBottom:Bool = true):Void
	{		
		#if android
		if(!initialized)
		{
			GoogleAdMob.initialize(MyAssets.whirlID, MyAssets.whirlID1, MyAssets.adPositionBottom ? 0 : 1);
		}
		
		if(_show_func == null)
		{
			_show_func = JNI.createStaticMethod("com/stencyl/GoogleServices/AdMob", "showBanner", "()V", true);
		}

		var args = new Array<Dynamic>();
		_show_func(args);
		#end
	}	
	
	public static function hideAd():Void
	{		
		#if android
		if(!initialized)
		{
			GoogleAdMob.initialize(MyAssets.whirlID, MyAssets.whirlID1, MyAssets.adPositionBottom ? 0 : 1);
		}
		
		if(_hide_func == null)
		{
			_hide_func = JNI.createStaticMethod("com/stencyl/GoogleServices/AdMob", "hideBanner", "()V", true);
		}

		var args = new Array<Dynamic>();
		_hide_func(args);
		#end
	}
	
	//Interstitial Ads
	
	public static function loadFullAd():Void
	{		
		#if android
		if(!initialized)
		{
			GoogleAdMob.initialize(MyAssets.whirlID, MyAssets.whirlID1, MyAssets.adPositionBottom ? 0 : 1);
		}
		
		if(_load_full_func == null)
		{
			_load_full_func = JNI.createStaticMethod("com/stencyl/GoogleServices/AdMob", "loadInterstitial", "()V", true);
		}

		var args = new Array<Dynamic>();
		_load_full_func(args);
		#end
	}	
	
	public static function showFullAd():Void
	{		
		#if android
		if(!initialized)
		{
			GoogleAdMob.initialize(MyAssets.whirlID, MyAssets.whirlID1, MyAssets.adPositionBottom ? 0 : 1);
		}
		
		if(_show_full_func == null)
		{
			_show_full_func = JNI.createStaticMethod("com/stencyl/GoogleServices/AdMob", "showInterstitial", "()V", true);
		}

		var args = new Array<Dynamic>();
		_show_full_func(args);
		#end
	}
	
	//Android Admob Callbacks
	public function onAdmobClosed() 
	{
		trace("USER CLOSED IT");
		Engine.events.addAdEvent(new StencylEvent(StencylEvent.AD_USER_CLOSE));
	}
			
	public function onAdmobOpened() 
	{
		trace("USER OPENED IT");
		Engine.events.addAdEvent(new StencylEvent(StencylEvent.AD_USER_OPEN));
	}
	
	public function onAdmobLoaded() 
	{		
		trace("IT SHOWED UP");
		Engine.events.addAdEvent(new StencylEvent(StencylEvent.AD_LOADED));
	}		
	
	public function onAdmobFailed() 
	{
		trace("IT FAILED TO LOAD");
		Engine.events.addAdEvent(new StencylEvent(StencylEvent.AD_FAILED));
	}
}