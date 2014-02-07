package com.eldhelm.g2s.iap.function;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.g2s.iap.InAppExtensionContext;
import com.eldhelm.g2s.iap.InAppPaymentActivity;
import com.safecharge.android.PayNowActivity;

public class PurchaseFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;

		String productId = null;
		String langCode = null;

		try {
			productId = arg1[0].getAsString();
			langCode = arg1[1].getAsString();
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		frecontext.sendWarning("Calling purchase:" + productId + ";" + langCode + ";");
		
		try {
			
			// PreloadSingelton.getInstance().preloadInit(frecontext.getActivity().getApplicationContext(), "Hebrew", productId, "");
			
			Intent intent = new Intent(frecontext.getActivity().getApplicationContext(), InAppPaymentActivity.class);
			intent.putExtra(PayNowActivity.IN_CHARGE_PRODUCT_ID, productId);
			frecontext.getActivity().startActivity(intent);
			
		} catch (Exception e) {
			frecontext.sendException(e, "purchase");
		}

		return null;
	}

}
