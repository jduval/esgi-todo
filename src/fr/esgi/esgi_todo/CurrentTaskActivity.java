package fr.esgi.esgi_todo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CurrentTaskActivity extends Activity {
	
	private String pos_task;
	private String title_task;

	private String updated_priority;
	private String updated_category;

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
			}
		} 
	}

	@Override
	protected void onResume() {
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
		Intent intent1 = new Intent(this, PriorityActivity.class);
		intent1.putExtra("UPDATE_TASK_PRIO", "true");
		startActivityForResult(intent1, 1);
	}

	public void updateCategory(View v) {
		Intent intent2 = new Intent(this, CategoryActivity.class);
		intent2.putExtra("UPDATE_TASK_CAT", "true");
		startActivityForResult(intent2, 2);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				updated_priority = data.getStringExtra("updated_priority");
			}
			break;
		case 2:
			if (resultCode == RESULT_OK) {
				updated_category = data.getStringExtra("updated_category");
			}
			break;
		}
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
		
		//check if empty mandatory values
		String initial_date = prefs.getString("initial_date", null);
		String initial_hour = prefs.getString("initial_hour", null);
		String sTitleTask = titleTask.getText().toString();

		if (initial_date == null) {
		    Toast.makeText(this, "You didn't set any due date.", Toast.LENGTH_SHORT).show();
		    return;
		}
		if (initial_hour == null) {
		    Toast.makeText(this, "You didn't set any due hour.", Toast.LENGTH_SHORT).show();
		    return;
		}
		if (sTitleTask.matches("")) {
		    Toast.makeText(this, "You didn't set any title.", Toast.LENGTH_SHORT).show();
		    return;
		}

   	 	if (updated_priority == null) {
   	 		updated_priority = prefs.getString("priority", null);
   	 	}
   	 	if (updated_category == null) {
   	 		updated_category = prefs.getString("category", null);
   	 	}

		db.updateTask(new Task(
					initial_date,
					initial_hour,
					prefs.getString("recall_date", null),
					prefs.getString("recall_hour", null),
					updated_priority,
					updated_category,
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
