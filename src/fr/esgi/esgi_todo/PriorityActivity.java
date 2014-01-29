package fr.esgi.esgi_todo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PriorityActivity extends Activity {
	
	static String[] PriorityOfTask = {"High", "Medium", "Low"};
	private String isUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority);
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("UPDATE_TASK_PRIO") != null) {
					isUpdate = "yes";
				}
			}
		}
		
		final ListView listview = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PriorityOfTask);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PriorityActivity.this);
					Editor editor = prefs.edit();

					String priorityKey = "priority";

					editor.putString(priorityKey, PriorityOfTask[position]);
					
					editor.commit();
					
					if (isUpdate == "yes") {
						Intent intent1 = new Intent(PriorityActivity.this, CurrentTaskActivity.class);
						intent1.putExtra("updated_priority", PriorityOfTask[position]);
						setResult(RESULT_OK, intent1);
						finish();
					} else {
						Intent intent = new Intent(PriorityActivity.this, NewTaskActivity.class);
						intent.putExtra("EXTRA_PRIO", "set!");
						startActivity(intent);
					}

			  }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.priority, menu);
		return true;
	}

}
