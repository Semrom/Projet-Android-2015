package fr.semrom.tapcolors;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Button play = (Button) findViewById(R.id.menu_activity_play_button);
		Button howTo = (Button) findViewById(R.id.menu_activity_how_to_play_button);
		Button about = (Button) findViewById(R.id.menu_activity_about_button);
		
		play.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent gameZoneActivity = new Intent(MenuActivity.this, GameZoneActivity.class);
				startActivity(gameZoneActivity);
			}
			
		});
		
		howTo.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent howToActivity = new Intent(MenuActivity.this, HowToPlayActivity.class);
				startActivity(howToActivity);
			}
			
		});
		
		about.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent aboutActivity = new Intent(MenuActivity.this, AboutActivity.class);
				startActivity(aboutActivity);	
			}
			
		});
	}
}
