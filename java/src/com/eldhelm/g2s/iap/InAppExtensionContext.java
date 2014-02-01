package com.eldhelm.g2s.iap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.g2s.iap.function.InitializeExtensionFunction;
import com.eldhelm.g2s.iap.function.PurchaseFunction;

public class InAppExtensionContext extends FREContext {

	@Override
	public void dispose() {
		
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("initializeExtension", new InitializeExtensionFunction());
		functions.put("purchase", new PurchaseFunction());
		return functions;
	}
	
	public void sendException(Exception e) {
		sendException(e, "");
	}

	public void sendException(Exception e, String id) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String err = sw.toString();
		try {
			setActionScriptData(FREObject.newObject(err));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispatchStatusEventAsync("Extension exception: " + id, "exception");
	}

	public void sendWarning(String msg) {
		dispatchStatusEventAsync(msg, "warning");
	}

	public void sendError(String msg) {
		dispatchStatusEventAsync(msg, "error");
	}
	
}
