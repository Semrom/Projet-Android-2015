package fr.semrom.tapcolors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Button replay = (Button) findViewById(R.id.game_over_activity_replay_button);
		Button menu = (Button) findViewById(R.id.game_over_activity_menu_button);
		
		TextView bestScoreText = (TextView) findViewById(R.id.game_over_activity_highScore_number);
		TextView gameOver = (TextView) findViewById(R.id.game_over_activity_description);
		TextView score = (TextView) findViewById(R.id.game_over_activity_score);
		
		Intent getScore = getIntent();
		
		String finalScore = getScore.getStringExtra(GameZoneActivity.SCORE_MESSAGE);
		String gameOverText = getScore.getStringExtra(GameZoneActivity.GAME_OVER_MESSAGE);
		
		score.setText(finalScore);
		gameOver.setText(gameOverText);
		
		replay.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent gameZoneActivity = new Intent(GameOverActivity.this, GameZoneActivity.class);
				startActivity(gameZoneActivity);	
			}
			
		});
		
		menu.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent menuActivity = new Intent(GameOverActivity.this, MenuActivity.class);
				startActivity(menuActivity);	
			}
			
		});
		
		//getting preferences.
		SharedPreferences getPrefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		int bestScore = getPrefs.getInt("key", 0);
		
		//Si on a un meilleur score.
		if (Integer.parseInt(finalScore) > bestScore) {
			score.setText(finalScore + " (Nouveau Record !)");
			//setting preferences.
			SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.putInt("key", Integer.parseInt(finalScore));
			editor.commit();
		}
		
		bestScoreText.setText(Integer.toString(bestScore));
		
	}
}
