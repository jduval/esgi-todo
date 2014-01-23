package fr.esgi.esgi_todo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CurrentTaskActivity extends Activity {
	
	private String pos_task;
	private String title_task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_task);
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("TITLE_TASK") != null) {
					pos_task = extras.get("POS_TASK").toString();
					title_task = extras.get("TITLE_TASK").toString();
					
					TaskDAO db = new TaskDAO(this);

					Task task = db.getTaskByTitle((String) extras.get("TITLE_TASK"));

					Log.d("FTW_LOLZ", task.getTitle()); // it works \o/
					Log.d("FTW_LOLZ", task.getInitialDate()); // it works \o/
					
					//Sharedpref set to keep all data while updating (delete when back to home / update / delete)
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
					Editor editor = prefs.edit();
					
					String initialDateKey = "initial_date";
					String initialHourKey = "initial_hour";
					String recallDateKey = "recall_date";
					String recallHourKey = "recall_hour";
					String priorityKey = "priority";
					String categoryKey = "category";
					String titleKey = "title";
					String contentKey = "content";
					
					editor.putString(initialDateKey, task.getInitialDate());
					editor.putString(initialHourKey, task.getInitialHour());
					editor.putString(recallDateKey, task.getRecallDate());
					editor.putString(recallHourKey, task.getRecallHour());
					editor.putString(priorityKey, task.getPriority());
					editor.putString(categoryKey, task.getCategory());
					editor.putString(titleKey, task.getTitle());
					editor.putString(contentKey, task.getContent());

					editor.commit();

					Button ini_d_button = (Button)findViewById(R.id.button2);
					ini_d_button.setText(task.getInitialDate(), null);

					Button ini_prio_button = (Button)findViewById(R.id.button3);
					ini_prio_button.setText(task.getPriority(), null);

					Button ini_cat_button = (Button)findViewById(R.id.button4);
					ini_cat_button.setText(task.getCategory(), null);

					EditText titleEditText = (EditText)findViewById(R.id.editTextTitleTask);
					titleEditText.setText(task.getTitle(), null);

					EditText contentEditText = (EditText)findViewById(R.id.editTextContentTask);
					contentEditText.setText(task.getContent(), null);
				}
//			} else { // if no extra set (come back from date/category/priority)
//				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//				
//				Button ini_d_button = (Button)findViewById(R.id.button2);
//				ini_d_button.setText(prefs.getString("initial_date", null), null);
//
//				Button ini_prio_button = (Button)findViewById(R.id.button3);
//				ini_prio_button.setText(prefs.getString("priority", null), null);
//
//				Button ini_cat_button = (Button)findViewById(R.id.button4);
//				ini_cat_button.setText(prefs.getString("category", null), null);
//
//				EditText titleEditText = (EditText)findViewById(R.id.editTextTitleTask);
//				titleEditText.setText(prefs.getString("title", null), null);
//
//				EditText contentEditText = (EditText)findViewById(R.id.editTextContentTask);
//				contentEditText.setText(prefs.getString("content", null), null);
			}
		} 
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		Button ini_d_button = (Button)findViewById(R.id.button2);
		ini_d_button.setText(prefs.getString("initial_date", null), null);

		Button ini_prio_button = (Button)findViewById(R.id.button3);
		ini_prio_button.setText(prefs.getString("priority", null), null);

		Button ini_cat_button = (Button)findViewById(R.id.button4);
		ini_cat_button.setText(prefs.getString("category", null), null);

		EditText titleEditText = (EditText)findViewById(R.id.editTextTitleTask);
		titleEditText.setText(prefs.getString("title", null), null);

		EditText contentEditText = (EditText)findViewById(R.id.editTextContentTask);
		contentEditText.setText(prefs.getString("content", null), null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_task, menu);
		return true;
	}
	
	public void updateDate(View v) {
		Intent intent = new Intent(this, DateActivity.class);
		intent.putExtra("UPDATE_TASK_DATE", "true");
		startActivity(intent);
	}
	
	public void updatePriority(View v) {
		Intent intent = new Intent(this, PriorityActivity.class);
		intent.putExtra("UPDATE_TASK_PRIO", "true");
		startActivity(intent);
	}
	
	public void updateCategory(View v) {
		Intent intent = new Intent(this, CategoryActivity.class);
		intent.putExtra("UPDATE_TASK_CAT", "true");
		startActivity(intent);
	}
	
	public void backToMainActivity(View v) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.edit().clear().commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void updateCurrentTask(View v) {
		TaskDAO db = new TaskDAO(this);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		EditText titleTask = (EditText) findViewById( R.id.editTextTitleTask );
		EditText contentTask = (EditText) findViewById( R.id.editTextContentTask );
		
		Log.d("DAFUQ_DATE", prefs.getString("initial_date", null));

		db.updateTask(new Task(
					prefs.getString("initial_date", null),
					prefs.getString("initial_hour", null),
					prefs.getString("recall_date", null),
					prefs.getString("recall_hour", null),
					prefs.getString("priority", null),
					prefs.getString("category", null),
					titleTask.getText().toString(),
					contentTask.getText().toString()
				), title_task);

		prefs.edit().clear().commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void deleteCurrentTask(View v) {
		TaskDAO db = new TaskDAO(this);
		db.deleteTask(title_task);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.edit().clear().commit();
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
