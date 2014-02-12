package com.eldhelm.g2s.iap.function;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.g2s.iap.InAppExtensionContext;

public class InitializeExtensionFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		
		frecontext.sendWarning("Initializing");
		
		try {
			frecontext.b = arg1[0].getAsInt();
			frecontext.c = arg1[1].getAsInt();
			frecontext.d = arg1[2].getAsInt();
			frecontext.e = arg1[3].getAsInt();
			frecontext.f = arg1[4].getAsInt();
		} catch (Exception e) {
			
		}
		
		return null;
	}

}
