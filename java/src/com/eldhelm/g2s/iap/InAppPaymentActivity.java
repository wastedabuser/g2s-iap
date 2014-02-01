package com.eldhelm.g2s.iap;

import com.eldhelm.g2s.iap.InAppExtensionContext;
import com.eldhelm.g2s.iap.InAppPurchase;
import com.safecharge.android.PayNowActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InAppPaymentActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(getApplicationContext(), PayNowActivity.class);
		intent.putExtras(getIntent().getExtras());
		startActivityForResult(intent, PayNowActivity.REQUEST_CODE);
    }
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode,
			Intent _intent) {

		InAppExtensionContext frecontext = InAppPurchase.context;
		frecontext.sendWarning("Received requestCode: " + _requestCode
				+ " resultCode: " + _resultCode);
		
	}

}
