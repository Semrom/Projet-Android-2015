package fr.semrom.tapcolors;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HowToPlayActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_play);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Button cancel = (Button) findViewById(R.id.howTo_activity_cancel_button);
		cancel.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent menuActivity = new Intent(HowToPlayActivity.this, MenuActivity.class);
				startActivity(menuActivity);	
			}
			
		});
	}

}
