package fr.esgi.esgi_todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTaskActivity extends Activity {

	private static final String TAG = "shared";
	private SqliteController SqliteClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("EXTRA_DATE") != null) {
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

					Map<String,?> keys = prefs.getAll();

					for(Map.Entry<String, ?> entry : keys.entrySet()) {
						//Log.d(TAG, entry.getKey() + " : " +  entry.getValue().toString());
						if (entry.getKey().equals("initial_date")) {
							Button ini_d_button = (Button)findViewById(R.id.button2);
							ini_d_button.setText(entry.getValue().toString());
						}
					}
				} // endif extra date
				if (extras.get("EXTRA_PRIO") != null) {
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
					
					Button ini_prio_button = (Button)findViewById(R.id.button3);
					ini_prio_button.setText(prefs.getString("priority", null));
				} // endif extra prio
				if (extras.get("EXTRA_CAT") != null) {
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
					
					Button ini_cat_button = (Button)findViewById(R.id.button4);
					ini_cat_button.setText(prefs.getString("category", null));
				} // endif extra cat

			}
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		Map<String,?> keys = prefs.getAll();
		for(Map.Entry<String, ?> entry : keys.entrySet()) {
			if (entry.getKey().equals("initial_date")) {
				if (entry.getKey() != "") {
					Button ini_d_button = (Button)findViewById(R.id.button2);
					ini_d_button.setText(entry.getValue().toString());
				}
			} // endif date
			if (entry.getKey().equals("priority")) {
				if (entry.getKey() != "") {
					Button ini_prio_button = (Button)findViewById(R.id.button3);
					ini_prio_button.setText(entry.getValue().toString());
				}
			} // endif priority
			if (entry.getKey().equals("category")) {
				if (entry.getKey() != "") {
					Button ini_cat_button = (Button)findViewById(R.id.button4);
					ini_cat_button.setText(entry.getValue().toString());
				}
			} // endif category
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_task, menu);
		return true;
	}
	
	public void setDate(View v) {
		Intent intent = new Intent(this, DateActivity.class);
		startActivity(intent);
	}

	public void setPriority(View v) {
		Intent intent = new Intent(this, PriorityActivity.class);
		startActivity(intent);
	}

	public void setCategory(View v) {
		Intent intent = new Intent(this, CategoryActivity.class);
		startActivity(intent);
	}
	
	public void validateNewTask(View v) {
		EditText titleTask = (EditText) findViewById( R.id.editTextTitleTask );
		EditText contentTask = (EditText) findViewById( R.id.editTextContentTask );

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		TaskDAO db = new TaskDAO(this);

		Map<String,?> keys = prefs.getAll();
		Map<String, String> map = new HashMap<String, String>();

		for(Map.Entry<String, ?> entry : keys.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toString());
		}

		// no recall date/hour set
		db.addTask(new Task(
					map.get("initial_date"),
					map.get("initial_hour"),
					map.get("recall_date"),
					map.get("recall_hour"),
					map.get("priority").toString(),
					map.get("category").toString(),
					titleTask.getText().toString(),
					contentTask.getText().toString()
				));

		List<Task> tasks = db.getAllTasks();
		for (Task tsk : tasks) {
			String log = "Id: " + tsk.getId() + ", initial date: " + tsk.getInitialDate() + ", initial hour: " + tsk.getInitialHour() 
					+ ", recall_date: " + tsk.getRecallDate() + ", recall_hour: " + tsk.getRecallHour() + ", priority: " + tsk.getPriority() 
					+ ", category: " + tsk.getCategory() + ", title: " + tsk.getTitle() + ", content: " + tsk.getContent();
			Log.d(TAG, log);
		}
		
		//delete sharedpreferences
		prefs.edit().clear().commit();
		//back to main activity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}

}
