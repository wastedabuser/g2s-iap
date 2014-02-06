package com.eldhelm.g2s.iap;

import com.eldhelm.g2s.iap.InAppExtensionContext;
import com.eldhelm.g2s.iap.InAppPurchase;
import com.safecharge.android.PayNowActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InAppPaymentActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(getApplicationContext(), PayNowActivity.class);
		intent.putExtras(getIntent().getExtras());
		
		// intent.putExtra(PayNowActivity.IN_THEME_LOGO_TOP, R.drawable.icon);       // A 
		intent.putExtra(PayNowActivity.IN_THEME_LIGHT_COLOR, 0xff0699df);     // B 
		intent.putExtra(PayNowActivity.IN_THEME_DARK_COLOR, 0xff00517d);      // C 
		intent.putExtra(PayNowActivity.IN_THEME_BACK_COLOR, 0xffdde2e3);      // D 
		intent.putExtra(PayNowActivity.IN_THEME_TEXT_COLOR, 0xff056598);      // E 
		intent.putExtra(PayNowActivity.IN_THEME_HIGHLIGHT_COLOR, 0xffae091a); // F 
		// intent.putExtra(PayNowActivity.IN_THEME_LOGO_BOTTOM, R.drawable.icon2); //G
		
		startActivityForResult(intent, PayNowActivity.REQUEST_CODE);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		InAppExtensionContext frecontext = InAppPurchase.context;
		frecontext.sendWarning("Received requestCode: " + requestCode
				+ " resultCode: " + resultCode);
		
		if (resultCode == RESULT_OK && requestCode == PayNowActivity.REQUEST_CODE){ 
			int transactionResult = intent.getIntExtra(PayNowActivity.OUT_TRX_RESULT_STATUS, PayNowActivity.TRX_RESULT_STATUS_CANCELLED_BY_USER); 
			String productId = intent.getStringExtra(PayNowActivity.IN_CHARGE_PRODUCT_ID);
			switch (transactionResult) { 
				case PayNowActivity.TRX_RESULT_STATUS_APPROVED: 
					frecontext.paymentAccepted(productId, intent.getStringExtra(PayNowActivity.OUT_TRX_AUTH_CODE));
					break; 
				case PayNowActivity.TRX_RESULT_STATUS_CANCELLED_BY_USER: 
					frecontext.paymentCanceled(productId);
					break; 
				case PayNowActivity.TRX_RESULT_STATUS_DECLINED: 
					frecontext.paymentDeclined(productId); 
					break; 
				case PayNowActivity.TRX_RESULT_STATUS_ERROR: 
				default: 
					frecontext.sendError(intent.getStringExtra(PayNowActivity.OUT_TRX_RESULT_ERROR_MSG)); 
				break; 
			} 
		}
		
		finish();
	}

}
