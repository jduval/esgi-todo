package fr.esgi.esgi_todo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class CategoryActivity extends Activity {
	
	protected static final String TAG = "test";
	static String[] CategoryOfTask = {"Work", "School", "Shopping"};
	private String isUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				if (extras.get("UPDATE_TASK_CAT") != null) {
					isUpdate = "yes";
				}
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		final ListView listview = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CategoryOfTask);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CategoryActivity.this);
					Editor editor = prefs.edit();

					String categoryKey = "category";

					editor.putString(categoryKey, CategoryOfTask[position]);

					editor.commit();

					if (isUpdate == "yes") {
						Intent intent2 = new Intent(CategoryActivity.this, CurrentTaskActivity.class);
						intent2.putExtra("updated_category", CategoryOfTask[position]);
						setResult(RESULT_OK, intent2);
						finish();
					} else {
						Intent intent = new Intent(CategoryActivity.this, NewTaskActivity.class);
						intent.putExtra("EXTRA_CAT", "set!");
						startActivity(intent);
					}
			  }
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

}
