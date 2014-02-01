package com.eldhelm.g2s.iap {
	import flash.events.Event;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapEvent extends Event {
		
		/**
		 * Fires when an error occures while interacting with the api
		 * Please refer to the Samsung IAP Dev guide for the correspsonging error code or erro message
		 */
		public static const ON_ERROR:String = "iapEvent_error";
		
		/**
		 * Fires when an exception occures (and it is catched) in the native code
		 * This might mean that the device is not a Samsung one or the API is not supported
		 */
		public static const ON_EXCEPTION:String = "iapEvent_exception";
				
		public var sharedObject:Object;
		
		public function IapEvent(type:String, sharedObject:Object = null, bubbles:Boolean = false, cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
			this.sharedObject = sharedObject;
		}
		
	}

}