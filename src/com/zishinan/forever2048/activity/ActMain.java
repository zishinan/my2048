package com.zishinan.forever2048.activity;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zishinan.forever2048.R;
import com.zishinan.forever2048.model.GameView;

public class ActMain extends Activity
{

	private TextView scoreTextView,bestScoreTextView;
	private Button btnNewGame,btnYangxi,btnSave,btnRead;
	
	private GameView gameView = null;
	
	private Score score;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
//		btnYangxi = (Button) findViewById(R.id.btnYangxi);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnRead = (Button) findViewById(R.id.btnRead);
		scoreTextView = (TextView) findViewById(R.id.score);
		bestScoreTextView = (TextView) findViewById(R.id.bestScore);
		
		gameView = (GameView) findViewById(R.id.gameView);

		score = new Score();
		gameView.setScore(score);
		
		btnNewGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startNewGame();
			}
		});
		/*btnYangxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				yangxiGame();
			}
		});*/
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveGame();
			}
		});
		btnRead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				readGame();
			}
		});
		
	}
	
	
	private void startNewGame()
	{
		score.clearScore();
		showScore();
		showBestScore();
		gameView.startGame();
	}
	
	private void yangxiGame()
	{
	}
	
	private void saveGame()
	{
		String cards = gameView.getCardsMap();
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putString("cards", cards);
		e.commit();
		score.saveScore(score.getScore());
		score.saveBestScore(score.getBestScore());
	}
	
	private void readGame()
	{
		String cardsMap = getPreferences(MODE_PRIVATE).getString("cards", "");
		if("".equals(cardsMap))
		{
			return;
		}
		gameView.setCardsMap(cardsMap);
		score.setScore(score.getTheScore());
		showTheScore();
		showBestScore();
	}
	
	private void showBestScore(){
		bestScoreTextView.setText(String.valueOf(score.getBestScore()));
	}
	
	private void showScore(){
		scoreTextView.setText(String.valueOf(score.getScore()));
	}
	
	private void showTheScore(){
		scoreTextView.setText(score.getTheScore() + "");
	}
	
	
	public class Score 
	{
		private int score=0;
		private static final String SP_KEY_BEST_SCORE = "bestScore";
		private static final String SP_KEY_SCORE = "theScore";
		
		public void clearScore()
		{
			score=0;
		}
		
		public int getScore()
		{
			return score;
		}
		
		public void setScore(int s)
		{
			this.score = s;
		}
		
		public void addScore(int s)
		{
			score+=s;
			showScore();
			saveBestScore(Math.max(score, getBestScore()));
			showBestScore();
		}
		
		public void saveBestScore(int s)
		{
			Editor e = getPreferences(MODE_PRIVATE).edit();
			e.putInt(SP_KEY_BEST_SCORE, s);
			e.commit();
		}

		public int getBestScore(){
			return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
		}
		
		public void saveScore(int s){
			Editor e = getPreferences(MODE_PRIVATE).edit();
			e.putInt(SP_KEY_SCORE, s);
			e.commit();
		}
		
		public int getTheScore()
		{
			return getPreferences(MODE_PRIVATE).getInt(SP_KEY_SCORE, 0);
		}
	}

}
