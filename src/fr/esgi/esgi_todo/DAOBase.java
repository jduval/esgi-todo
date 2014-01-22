package fr.esgi.esgi_todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    protected static final int DATABASE_VERSION = 6;

    protected static final String DATABASE_NAME = "esgi_todo.db";
    
    protected SQLiteDatabase mDb = null;
    protected SqliteController mHandler = null;
      
    public DAOBase(Context pContext) {
    	this.mHandler = new SqliteController(pContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
      
    public SQLiteDatabase open() {
    	mDb = mHandler.getWritableDatabase();
    	return mDb;
    }
      
    public void close() {
      mDb.close();
    }
      
    public SQLiteDatabase getDb() {
      return mDb;
    }
}
