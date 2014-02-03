package com.eldhelm.g2s.iap {
	import flash.events.Event;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class IapEvent extends Event {
		
		public static const ON_ACCEPTED:String = "iapEvent_payment_accepted";
		
		public static const ON_DECLINED:String = "iapEvent_payment_declined";
		public static const ON_CANCELED:String = "iapEvent_payment_canceled";
		
		/**
		 * Fires when an error occures while interacting with the api
		 */
		public static const ON_ERROR:String = "iapEvent_error";
		
		/**
		 * Fires when an exception occures (and it is catched) in the native code
		 */
		public static const ON_EXCEPTION:String = "iapEvent_exception";
		
		public var sharedObject:Object;
		
		public function IapEvent(type:String, sharedObject:Object = null, bubbles:Boolean = false, cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
			this.sharedObject = sharedObject;
		}
		
	}

}