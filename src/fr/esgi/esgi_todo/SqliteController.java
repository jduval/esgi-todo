package fr.esgi.esgi_todo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "esgiTodo";

    private static final String TABLE_TASK = "task";

    // Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_INI_D = "initial_date";
    private static final String KEY_INI_H = "initial_hour";
    private static final String KEY_REC_D = "recall_date";
    private static final String KEY_REC_H = "recall_hour";
    private static final String KEY_PRIO = "priority";
    private static final String KEY_CAT = "category";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

	public SqliteController(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INI_D + " TEXT" 
				+ KEY_INI_H + " TEXT"
				+ KEY_REC_D + " TEXT"
				+ KEY_REC_H + " TEXT"
				+ KEY_PRIO + " TEXT"
				+ KEY_CAT + " TEXT"
				+ KEY_TITLE + " TEXT"
				+ KEY_CONTENT + " TEXT)";
        db.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("TEST", "table drop!");
		 // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create tables again
        onCreate(db);
	}


	/**
	 *
	 * CRUD Operations
	 *
	 */
    // Adding new task
    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_INI_D, task.getInitialDate());
        values.put(KEY_INI_H, task.getInitialHour());
//        values.put(KEY_REC_D, task.getRecallDate());
//        values.put(KEY_REC_H, task.getRecallHour());
        values.put(KEY_PRIO, task.getPriority());
        values.put(KEY_CAT, task.getCategory());
        values.put(KEY_TITLE, task.getTitle());

        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    // Getting single task
	public Task getTask(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TASK,
				new String[] { KEY_ID, KEY_INI_D }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Task task = new Task(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(1));
		return task;
	}

    // Getting All Task
    public List<Task> getAllTasks() {
        List<Task> TaskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setInitialDate(cursor.getString(1));
                task.setInitialHour(cursor.getString(1));
                task.setRecallDate(cursor.getString(1));
                task.setRecallHour(cursor.getString(1));
                task.setPriority(cursor.getString(1));
                task.setCategory(cursor.getString(1));
                task.setTitle(cursor.getString(1));
                task.setContent(cursor.getString(1));
                
            //    contact.setPhoneNumber(cursor.getString(2));
                // Adding task to list
                TaskList.add(task);
            } while (cursor.moveToNext());
        }

        // return task list
        return TaskList;
    }
}
