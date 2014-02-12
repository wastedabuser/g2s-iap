package com.eldhelm.g2s.iap.function;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.g2s.iap.InAppExtensionContext;
import com.eldhelm.g2s.iap.InAppPaymentActivity;
import com.eldhelm.g2s.iap.R;
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
			Intent intent = new Intent(frecontext.getActivity().getApplicationContext(), InAppPaymentActivity.class);
			
			intent.putExtra(PayNowActivity.IN_THEME_TOP_LOGO, R.drawable.product_icon);       // A 
			intent.putExtra(PayNowActivity.IN_THEME_LIGHT_COLOR, frecontext.b);     // B 
			intent.putExtra(PayNowActivity.IN_THEME_DARK_COLOR, frecontext.c);      // C 
			intent.putExtra(PayNowActivity.IN_THEME_BACK_COLOR, frecontext.d);      // D 
			intent.putExtra(PayNowActivity.IN_THEME_TEXT_COLOR, frecontext.e);      // E 
			intent.putExtra(PayNowActivity.IN_THEME_HIGHLIGHT_COLOR, frecontext.f); // F 
			// intent.putExtra(PayNowActivity.IN_THEME_LOGO_BOTTOM, R.drawable.icon2); //G
			
			intent.putExtra(PayNowActivity.IN_CHARGE_PRODUCT_ID, productId);
			
			frecontext.getActivity().startActivity(intent);
			
		} catch (Exception e) {
			frecontext.sendException(e, "purchase");
		}

		return null;
	}

}
