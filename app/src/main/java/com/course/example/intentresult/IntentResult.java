/*
 * For this demo to work, you need a few contacts in your contact list.
 * Your contact list is presented. You select an entry, the entry URI is
 * returned to the calling activity, a toast displays the URI, and then
 * the dialer is opened ready to call the contact.
 * 
 * Notice the permission in the Manifest. It's needed because we didn't
 * go through the Dialer UI to place the call.
 */
package com.course.example.intentresult;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class IntentResult extends Activity {
	private TextView label1;
	private EditText text1;
	private Button btnCallActivity2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		label1 = (TextView) findViewById(R.id.label1);
		text1 = (EditText) findViewById(R.id.text1);

		btnCallActivity2 = (Button) findViewById(R.id.btnPickContact);
		btnCallActivity2.setOnClickListener(new ClickHandler());

	}// onCreate

	private class ClickHandler implements OnClickListener {
		@Override
		public void onClick(View v) {

			// open Contacts list with URI for the Contacts database.
			String myData = "content://contacts/people/";

			Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(myData));
			startActivityForResult(intent, 222);
			Toast.makeText(IntentResult.this, "Asynch mechanism",
					Toast.LENGTH_LONG).show();

		}// onClick
	}// ClickHandler

	// This method is called when the ActivityForResult is finished.
	// It first checks the request code to see who finished.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// use requestCode to find out who is talking to us
		switch (requestCode) {
		case (222): {
			// 222 is the contact-picker activity
			if (resultCode == Activity.RESULT_OK) {

				String selectedContact = data.getDataString();

				// the returned URI looks like: content://contacts/people/n
				// where n is the selected contact's ID
				Toast.makeText(this, selectedContact, Toast.LENGTH_LONG).show();
				Toast.makeText(this, "Result OK", Toast.LENGTH_LONG).show();
				text1.setText(selectedContact);

				label1.setText("Result OK, " + "request=" + requestCode + ", result=" + resultCode);

				// show contact name and phone number
				Intent myAct3 = new Intent(Intent.ACTION_VIEW,
						Uri.parse(selectedContact));
				startActivity(myAct3);
			} else {
				// user pressed the BACK button
				Toast.makeText(
						this,
						"Selection cancelled, resquest code = " + requestCode
								+ " result code = " + resultCode,
						Toast.LENGTH_LONG).show();
				label1.setText("Selection CANCELLED " + requestCode + " "
						+ resultCode);
			}
			break;
		}
		}// switch

	}// onActivityResult

}// IntentDemo2

