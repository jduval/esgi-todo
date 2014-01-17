package fr.esgi.esgi_todo;

import fr.esgi.esgi_todo.NewTaskActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView)findViewById(android.R.id.list);
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
