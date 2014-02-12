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
		
		private var b:int = 0xff0699df;
		private var c:int = 0xff00517d;
		private var d:int = 0xffdde2e3;
		private var e:int = 0xff056598;
		private var f:int = 0xffae091a;
		
		/**
		 * The constructor accepts an optional configuration object for example:
		 * {
		 *  LIGHT_COLOR: 0xffedcd8c,
		 *	DARK_COLOR: 0xff756037,
		 *	BACK_COLOR: 0xfff1e6cc,
		 *	TEXT_COLOR: 0xff010101,
		 *	HIGHLIGHT_COLOR: 0xffedcd8c
		 * }
		 * Please rffer to the g2s developer manual for explanation what these properties mean
		 * @param	config
		 */
		public function InAppPurchase(config:Object = null) {
			if (config != null) {
				if (config.LIGHT_COLOR is uint) b = config.LIGHT_COLOR;
				if (config.DARK_COLOR is uint) c = config.DARK_COLOR;
				if (config.BACK_COLOR is uint) d = config.BACK_COLOR;
				if (config.TEXT_COLOR is uint) e = config.TEXT_COLOR;
				if (config.HIGHLIGHT_COLOR is uint) f = config.HIGHLIGHT_COLOR;
			}
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
			extContext.call("initializeExtension", b, c, d, e, f);
		}
		
		/**
		 * Initiate a purchase of an item
		 * @param	productId
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