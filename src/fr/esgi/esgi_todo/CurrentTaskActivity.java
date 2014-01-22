package fr.esgi.esgi_todo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class CurrentTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_task);
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("ID_TASK") != null) {
					Log.d("FTW", "test");
					Log.d("FTW", extras.get("ID_TASK").toString());
					TaskDAO db = new TaskDAO(this);
					//Task Task = db.getTask((Long) extras.get("ID_TASK"));
					Task task = db.getTask((Long) extras.get("ID_TASK"));
					Log.d("FTW", task.getTitle().toString()); // DOESNT WORK GOD DAMN ITTTTTTT !!!!!!!!!!!!!
				//	Log.d("LOLOL", Task.getTitle().toString());
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_task, menu);
		return true;
	}

}
