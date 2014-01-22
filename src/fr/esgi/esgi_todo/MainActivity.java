package fr.esgi.esgi_todo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView)findViewById(android.R.id.list);
		
		final TaskDAO db = new TaskDAO(this);
		ArrayList<Task> tasks = db.getAllTasks();
		
		lv.setAdapter(new TaskCustomAdapter(this, tasks));
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Intent intent = new Intent(MainActivity.this, CurrentTaskActivity.class);
				intent.putExtra("ID_TASK", id);
				startActivity(intent);		
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void newTask(View v) {
		Intent intent = new Intent(this, NewTaskActivity.class);
		startActivity(intent);
	}

}
