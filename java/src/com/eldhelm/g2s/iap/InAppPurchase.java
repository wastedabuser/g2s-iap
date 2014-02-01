package com.eldhelm.g2s.iap;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;
import com.eldhelm.g2s.iap.InAppExtensionContext;

public class InAppPurchase implements FREExtension {

	static InAppExtensionContext context;

	@Override
	public FREContext createContext(String arg0) {
		if (context == null) context = new InAppExtensionContext();
		return context;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

}
