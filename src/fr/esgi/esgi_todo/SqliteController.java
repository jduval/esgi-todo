package fr.esgi.esgi_todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteController extends SQLiteOpenHelper {
	public static final String TABLE_TASK = "task";

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

	public SqliteController(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_INI_D + " TEXT," + KEY_INI_H + " TEXT,"
								   + KEY_REC_D + " TEXT," + KEY_REC_H + " TEXT," + KEY_PRIO + " TEXT," + KEY_CAT + " TEXT," + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT);";
        db.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create tables again
        onCreate(db);
	}
}
