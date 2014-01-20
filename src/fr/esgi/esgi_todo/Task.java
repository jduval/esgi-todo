package fr.esgi.esgi_todo;

import android.widget.EditText;

public class Task {
	
	int id;
	String initial_date;
	
	public Task() {}
	public Task(int id, String initial_date) {
		this.id = id;
		this.initial_date = initial_date;
	}
	public Task(String initial_date) {
		this.initial_date = initial_date;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getInitialDate() {
		return this.initial_date;
	}
	
	public void setInitialDate(String initial_date) {
		this.initial_date = initial_date;
	}
}
