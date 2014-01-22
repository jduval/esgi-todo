package fr.esgi.esgi_todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView)findViewById(android.R.id.list);
		
		TaskDAO db = new TaskDAO(this);
		ArrayList<Task> tasks = db.getAllTasks();
		
		lv.setAdapter(new TaskCustomAdapter(this, tasks));


//		Log.d("TEST", tasks.get(3).getTitle());
//		for (Task tsk : tasks) {
//			String log = "Id: " + tsk.getId() + ", initial date: " + tsk.getInitialDate() + ", initial hour: " + tsk.getInitialHour() + ", title: " + tsk.getTitle();
//			Log.d("LOL", log);
//		}
		
		//dbCursor = db.getAllTasks();
//		String[] columns = {"id", "title"};
//		int [] to = {android.R.id.text1};
//		SimpleCursorAdapter adapter =  new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, (Cursor) tasks, columns, to);
		
		//SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
//		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myList));
//        
//		lv.setAdapter(adapter);
//		lv.setOnItemClickListener(new OnItemClickListener()
//		{
//		     @Override
//		     public void onItemClick(AdapterView<String> a, View v,int position, long id) 
//		     {
//		          Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_LONG).show();
//		     }
//		});
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
