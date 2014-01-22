package fr.esgi.esgi_todo;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskDAO extends DAOBase {

	public TaskDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	// Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_INI_D = "initial_date";
    public static final String KEY_INI_H = "initial_hour";
    public static final String KEY_REC_D = "recall_date";
    public static final String KEY_REC_H = "recall_hour";
    public static final String KEY_PRIO = "priority";
    public static final String KEY_CAT = "category";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";

    public static final String TABLE_TASK = "task";

   // Adding new task
   void addTask(Task task) {
       SQLiteDatabase db = this.open();

       ContentValues values = new ContentValues();
       values.put(KEY_INI_D, task.getInitialDate());
       values.put(KEY_INI_H, task.getInitialHour());
       values.put(KEY_REC_D, task.getRecallDate());
       values.put(KEY_REC_H, task.getRecallHour());
       values.put(KEY_PRIO, task.getPriority());
       values.put(KEY_CAT, task.getCategory());
       values.put(KEY_TITLE, task.getTitle());
       values.put(KEY_CONTENT, task.getContent());

       db.insert(TABLE_TASK, null, values);
       db.close();
   }

   // Getting single task
	public Task getTask(long id) {
		SQLiteDatabase db = this.open();

		String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE _id = " + id;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
//		Cursor cursor = db.query(TABLE_TASK,
//				new String[] { KEY_ID, KEY_INI_D, KEY_TITLE }, KEY_ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		Task task = new Task(cursor.getString(cursor
				.getColumnIndex("initial_date")), cursor.getString(cursor
				.getColumnIndex("initial_hour")), cursor.getString(cursor
				.getColumnIndex("recall_date")), cursor.getString(cursor
				.getColumnIndex("recall_hour")), cursor.getString(cursor
				.getColumnIndex("priority")), cursor.getString(cursor
				.getColumnIndex("category")), cursor.getString(cursor
				.getColumnIndex("title")), cursor.getString(cursor
				.getColumnIndex("content")));

//		Log.d("CURSORTEST", cursor.toString());
//
//		Task task = new Task(cursor.getLong(0),
//				cursor.getString(1), cursor.getString(2));
		return task;
	}
   
   // Getting All Task
   public ArrayList<Task> getAllTasks() {
       ArrayList<Task> TaskList = new ArrayList<Task>();
       // Select All Query
       String selectQuery = "SELECT * FROM " + TABLE_TASK;

       SQLiteDatabase db = this.open();
       Cursor cursor = db.rawQuery(selectQuery, null);

       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
               Task task = new Task();
               task.setId(cursor.getLong(0));
               task.setInitialDate(cursor.getString(1));
               task.setInitialHour(cursor.getString(2));
               task.setRecallDate(cursor.getString(3));
               task.setRecallHour(cursor.getString(4));
               task.setPriority(cursor.getString(5));
               task.setCategory(cursor.getString(6));
               task.setTitle(cursor.getString(7));
               task.setContent(cursor.getString(8));

               // Adding task to list
               TaskList.add(task);
           } while (cursor.moveToNext());
       }
       
       cursor.close();

       // return task list
       return TaskList;
   }
}