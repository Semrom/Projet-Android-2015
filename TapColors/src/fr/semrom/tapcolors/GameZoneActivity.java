package fr.semrom.tapcolors;


import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameZoneActivity extends Activity implements View.OnClickListener{
	public static final String SCORE_MESSAGE = "score";
	public static final String GAME_OVER_MESSAGE = "gameover";
	
	private static final int LINE = 4;
	private static final int COL = 3;
	private static final String RED = "#FF0000";
	private static final String GREEN = "#00FF00";
	private static final String BLUE = "#0000FF";
	private static final String LIGHT_BLUE = "#77B5FE";
	private static final String YELLOW = "#FFFF00";
	private static final String ORANGE = "#FFA500";
	private static final String PINK = "#FD6C9E";
	
	private int tailleGrille = LINE * COL;
	private int scoreNumber = 0;
	private int vitesse = 400;
	private int compteur = 0;
	
	private String finalScore;
	private String msgGameOver;
	private String[] tabColor = {RED, GREEN, BLUE, LIGHT_BLUE, YELLOW, ORANGE, PINK};
	
	private ArrayList<TextView> listTextView;
	
	private Random randColor = new Random();
	private Random randTextView = new Random();

	private TextView score;
	
	private GridLayout grilleJeu; 
	private GridLayout.LayoutParams params;
	
	private Handler myHandler;
	private Runnable runnable;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_zone);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		grilleJeu = (GridLayout) findViewById(R.id.game_zone_activity_grille_jeu);
		score = (TextView) findViewById(R.id.game_zone_activity_score_number);
		
		grilleJeu.setColumnCount(COL);
		grilleJeu.setRowCount(LINE);
		
		listTextView = new ArrayList<TextView>();
		
		for(int i = 0; i < tailleGrille; i++) {
			TextView tv = new TextView(GameZoneActivity.this);

			params = new GridLayout.LayoutParams();
	        params.setMargins(8, 8, 8, 8);
	        tv.setLayoutParams(params);
	        tv.getLayoutParams().width = (int) getResources().getDimension(R.dimen.width_tv_in_dp);
	        tv.getLayoutParams().height = (int) getResources().getDimension(R.dimen.height_tv_in_dp);
	        tv.setTag(R.string.life, (Integer) 0);
			tv.setOnClickListener(this); 
			tv.setBackgroundColor(Color.WHITE);
			score.setText(Integer.toString(scoreNumber));
			listTextView.add(tv);
		    grilleJeu.addView(tv);
		}
		
		myHandler = new Handler();  
	    
		runnable = new Runnable() {
			public void run() {
				//Compteur pour modifier la vitesse d'apparition.
				if (compteur == 30 || compteur == 60 || compteur == 90 || compteur == 120 || compteur == 150) {
					vitesse = vitesse - 40;
				}
				
				//Sélection d'une case et d'une couleur aléatoire.
				int colorRand = randColor.nextInt(7);
				int textViewRand = randTextView.nextInt(tailleGrille);
				TextView tvRandom = listTextView.get(textViewRand);
				
				//Empêcher le changement de case instantané en colorant que les cases blanches.
				if (((ColorDrawable)tvRandom.getBackground()).getColor() == Color.WHITE) {
					tvRandom.setBackgroundColor(Color.parseColor(tabColor[colorRand]));
				}
			 
				//Parcours des view pour éventuellement "remettre à blanc" une case.
				for (int i = 0, t = listTextView.size(); i < t; i++) {
					TextView tv = listTextView.get(i);
					
					//Si une case possède une couleur (différente du blanc).
					if (((ColorDrawable)tv.getBackground()).getColor() != Color.WHITE) {
						
						//Si le compteur de la case est supérieur ou égal à 5.
						if ((Integer) tv.getTag(R.string.life) >= 5) {
							
							//Si la couleur de la case est différente du rouge alors GameOver.
							if (((ColorDrawable)tv.getBackground()).getColor() != Color.parseColor(RED)) {
	
								myHandler.removeCallbacks(runnable);
								Intent gameOverActivity = new Intent(GameZoneActivity.this, GameOverActivity.class);
								finalScore = Integer.toString(scoreNumber);
								msgGameOver = getResources().getString(R.string.game_over_time_out);
								gameOverActivity.putExtra(SCORE_MESSAGE, finalScore);
								gameOverActivity.putExtra(GAME_OVER_MESSAGE, msgGameOver);
								startActivity(gameOverActivity);
								return;
							} else {
								//Sinon remettre le background à blanc.
								tv.setTag(R.string.life, 0);
								tv.setBackgroundColor(Color.WHITE);
							}							
						} else {
							int life = (Integer) tv.getTag(R.string.life);
							tv.setTag(R.string.life, life + 1);
						}
					}
				}
			    myHandler.postDelayed(this, vitesse);
			}
		};
		runnable.run();
	} 
	
	@Override
	public void onClick(View v) {
		
		if (((ColorDrawable) v.getBackground()).getColor() == Color.WHITE) {
			return;
		} else if (((ColorDrawable) v.getBackground()).getColor() == Color.parseColor(RED)) {
			myHandler.removeCallbacks(runnable);
			Intent gameOverActivity = new Intent(GameZoneActivity.this, GameOverActivity.class);
			finalScore = Integer.toString(scoreNumber);
			msgGameOver = getResources().getString(R.string.game_over_case_rouge);
			gameOverActivity.putExtra(SCORE_MESSAGE, finalScore);
			gameOverActivity.putExtra(GAME_OVER_MESSAGE, msgGameOver);
			startActivity(gameOverActivity);
		} else {
			compteur++;
			v.setBackgroundColor(Color.WHITE);
			v.setTag(R.string.life, 0);
			scoreNumber += 1;
			score.setText(Integer.toString(scoreNumber));
		}
	}
}
