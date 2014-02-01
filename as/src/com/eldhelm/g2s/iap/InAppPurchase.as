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
		public var ready:Boolean;
		public var inited:Boolean;
		
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
			var errorObj:Object;
			
			if (event.level == "result") {
				trace("IAP Extension: result " + event.code);
				var data:*;
				if (event.code == "payment_completed") {
					trace("IAP: execute getPurchaseResult");
					data = extContext.call("getPurchaseResult");
					trace("IAP Extension: returned " + data);
				}
				dispatchEvent(new IapEvent("iapEvent_" + event.code, data));
				return;
				
			} else if (event.level == "exception") {
				trace("================ " + event.code + " ===================");
				trace(extContext.actionScriptData);
				errorObj = { code: event.code, data: extContext.actionScriptData };
				
			} else if (event.level == "warning") {
				trace("IAP Extension: " + event.code);
				return;
				
			} else if (event.level == "error") {
				trace("================ Extension error ===================");
				trace("IAP Extension: " + event.code);
				errorObj = { code: event.code };
			}
			
			dispatchEvent(new IapEvent("iapEvent_" + event.level, errorObj));
		}
		
		/**
		 * Initilizes the extenison. This method is called automtically when the object is created.
		 * You could call it once again again if an exeption arises, like when the g2s IAP plugin is not installed.
		 */
		public function initialize():void {
			if (!extContext) return;
			
			trace("IAP: execute initialize");
			extContext.call("initializeExtension");
		}
		
		/**
		 * Initiate a purchase of an item
		 * @param	itemGroupId
		 * @param	itemId
		 */
		public function purchase(productId:String):void {
			if (!extContext) return;
			
			trace("IAP: execute purchase");
			extContext.call("purchase", productId);
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