package com.eldhelm.g2s.iap {
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	/**
	 * ...
	 * @author Andrey Glavchev
	 */
	public class InAppPurchase extends EventDispatcher {
		
		public var created:Boolean;
		
		private var extContext:ExtensionContext;
		private var _callbackMethod:String;
		private var _callbackArgs:Array;
		private var defaultMode:int;
		
		public function InAppPurchase(mode:int = 0) {
			defaultMode = mode;
			extContext = ExtensionContext.createExtensionContext("com.eldhelm.g2s.iap.InAppPurchase", "");
			if (extContext != null) {
				trace("IAP: context created");
				initialize();
				extContext.addEventListener(StatusEvent.STATUS, onStatus);				
				created = true;
			} else {
				trace("IAP: context creation failed");
			}
		}
		
		private function onStatus(event:StatusEvent):void {		
			if (event.level == "payment_declined" || event.level == "payment_canceled") {
				trace("IAP Extension: " + event.level);
				dispatchEvent(new IapEvent("iapEvent_" + event.level, { productId: event.code } ));
				return;
				
			} else if (event.level == "payment_accepted") {
				trace("IAP Extension: payment accepted: " + event.code);
				var params:Array = event.code.split(";");
				dispatchEvent(new IapEvent(IapEvent.ON_ACCEPTED, { productId: params[0], authCode: params[1] } ));
				return;
				
			} else if (event.level == "exception") {
				trace("================ " + event.code + " ===================");
				trace(extContext.actionScriptData);
				dispatchEvent(new IapEvent(IapEvent.ON_EXCEPTION, { code: event.code, data: extContext.actionScriptData } ));
				
			} else if (event.level == "warning") {
				trace("IAP Extension: " + event.code);
				return;
				
			} else if (event.level == "error") {
				trace("================ Extension error ===================");
				trace("IAP Extension: " + event.code);
				dispatchEvent(new IapEvent(IapEvent.ON_ERROR, { code: event.code }));
			}
		}
		
		/**
		 * Initilizes the extenison. This method is called automtically when the object is created.
		 */
		public function initialize():void {
			if (!extContext) return;
			
			trace("IAP: execute initialize");
			extContext.call("initializeExtension");
		}
		
		/**
		 * Initiate a purchase of an item
		 * @param	productId
		 */
		public function purchase(productId:String, languageCode:String = "en"):void {
			if (!extContext) return;
			
			trace("IAP: execute purchase");
			extContext.call("purchase", productId, languageCode);
		}
		
		/**
		 * Disposes the iap and related objects
		 */
		public function dispose():void {
			if (extContext != null) {
				extContext.removeEventListener(StatusEvent.STATUS, onStatus);	
				extContext.dispose();
				extContext = null;
			}
		}
		
	}

}