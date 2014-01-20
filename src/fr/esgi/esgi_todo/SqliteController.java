package fr.esgi.esgi_todo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "esgiTodo";

    private static final String TABLE_TASK = "task";
 
    // Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_INI_D = "initial_date";
//    private static final String KEY_PH_NO = "phone_number";
    
	public SqliteController(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INI_D + " TEXT" 
				+ ")";
        db.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
//        values.put(KEY_PH_NO, contact.getPhoneNumber());

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
				cursor.getString(1));
		return task;
	}
	
    // Getting All Task
    public List<Task> getAllTasks() {
        List<Task> contactList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setInitialDate(cursor.getString(1));
            //    contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(task);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
}
