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

		String _productId = null;
		String _packageName = null;

		try {
			_productId = arg1[0].getAsString();
			_packageName = frecontext.getActivity().getPackageName();
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		frecontext.sendWarning("Calling purchase:" + _packageName + ";"
				+ _productId + ";");

		try {
			
			Intent intent = new Intent(frecontext.getActivity().getApplicationContext(), InAppPaymentActivity.class);
			intent.putExtra(PayNowActivity.IN_CHARGE_PRODUCT_ID, _productId);
			frecontext.getActivity().startActivity(intent);
			
		} catch (Exception e) {
			frecontext.sendException(e, "purchase");
		}

		return null;
	}

}
